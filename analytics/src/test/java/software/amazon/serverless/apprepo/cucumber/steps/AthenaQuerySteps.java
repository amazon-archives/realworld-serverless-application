package software.amazon.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.api.client.model.Application;
import software.amazon.serverless.apprepo.api.client.model.CreateApplicationInput;
import software.amazon.serverless.apprepo.api.client.model.CreateApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.GetApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.GetApplicationResult;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.GetQueryExecutionRequest;
import software.amazon.awssdk.services.athena.model.GetQueryExecutionResponse;
import software.amazon.awssdk.services.athena.model.GetQueryResultsRequest;
import software.amazon.awssdk.services.athena.model.QueryExecutionState;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;
import software.amazon.awssdk.services.athena.model.Row;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionResponse;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;
import software.amazon.serverless.ssmcachingclient.SsmParameterCachingClient;

/**
 * Implementation of the steps in AthenaQuery.feature.
 */
@Slf4j
public class AthenaQuerySteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;

  @Inject
  private AthenaClient athenaClient;

  @Inject
  private SsmParameterCachingClient ssmParameterCachingClient;

  @Given("^a user has an application$")
  @When("^(?:a|the) user creates (?:an|another) application$")
  public void a_user_creates_an_application() {
    CreateApplicationInput input = new CreateApplicationInput()
        .applicationId("applicationId-" + UUID.randomUUID().toString())
        .author("author-" + UUID.randomUUID().toString())
        .description("description-" + UUID.randomUUID().toString())
        .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString());
    CreateApplicationRequest request = new CreateApplicationRequest()
        .createApplicationInput(input);

    Application application = appRepo.createApplication(request).getApplication();

    assertThat(TestEnv.getLastException()).isNull();
    assertThat(application.getApplicationId()).isEqualTo(input.getApplicationId());
    assertThat(application.getAuthor()).isEqualTo(input.getAuthor());
    assertThat(application.getDescription()).isEqualTo(input.getDescription());
    assertThat(application.getHomePageUrl()).isEqualTo(input.getHomePageUrl());
    assertThat(application.getCreationTime()).isNotBlank();
  }

  @Then("^a new application should be created|the application should be updated$")
  public void a_new_application_should_be_created() {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    GetApplicationResult result = appRepo.getApplication(new GetApplicationRequest().applicationId(TestEnv.getApplicationId()));
    assertThat(result.getApplication())
        .isNotNull()
        .isEqualTo(TestEnv.getApplication());
  }

  @And("the administrator can analyze the data with Athena queries")
  public void the_administrator_can_analyze_the_data_with_athena_queries() throws InterruptedException {
    String loadPartitionsQuery = String.format("MSCK REPAIR TABLE `%s`.`%s`;",
        ssmParameterCachingClient.getAsString("glue/database"),
        ssmParameterCachingClient.getAsString("glue/table/applications")
    );

    String createdAppQuery = "SELECT detail.eventname,\n" +
        "         detail.dynamodb.keys.applicationid.s AS applicationid,\n" +
        "         detail.dynamodb.keys.userid.s AS userid,\n" +
        "         detail.dynamodb.newimage.author.s AS author,\n" +
        "         detail.dynamodb.newimage.description.s AS description\n" +
        String.format("FROM \"%s\".\"%s\"\n",
            ssmParameterCachingClient.getAsString("glue/database"),
            ssmParameterCachingClient.getAsString("glue/table/applications")) +
        String.format("WHERE detail.dynamodb.keys.applicationid.s='%s' limit 10", TestEnv.getApplicationId());

    List<Row> rows = Collections.emptyList();
    int attempts = 0;
    while (rows.size() < 2 && attempts < 10) {
      log.info("Waiting for Firehose to flush it's buffer into S3...");
      Thread.sleep(30000);
      runAthenaQuery(loadPartitionsQuery);
      rows = runAthenaQuery(createdAppQuery);
      attempts++;
    }

    assertThat(rows).hasSize(2); // Results always have 1 row of headers
    assertThat(rows.get(1).data().get(0).varCharValue()).isEqualTo("INSERT");
    assertThat(rows.get(1).data().get(1).varCharValue()).isEqualTo(TestEnv.getApplication().getApplicationId());
    assertThat(rows.get(1).data().get(3).varCharValue()).isEqualTo(TestEnv.getApplication().getAuthor());
    assertThat(rows.get(1).data().get(4).varCharValue()).isEqualTo(TestEnv.getApplication().getDescription());
  }


  private List<Row> runAthenaQuery(final String query) {
    log.info("Running Athena query: {}", query);
    StartQueryExecutionResponse startQueryExecutionResponse = athenaClient.startQueryExecution(StartQueryExecutionRequest.builder()
        .queryString(query)
        .resultConfiguration(ResultConfiguration.builder()
            .outputLocation(String.format("s3://%s",
                ssmParameterCachingClient.getAsString("s3/AthenaQueryResultsBucketName/IntegTest/Name")))
            .build())
        .build());

    String queryExecutionId = startQueryExecutionResponse.queryExecutionId();

    waitForQueryToComplete(queryExecutionId);
    List<Row> resultRows = getResultRows(queryExecutionId);
    log.info("Query results: {}", resultRows);
    return resultRows;
  }

  @SneakyThrows(InterruptedException.class)
  private void waitForQueryToComplete(String queryExecutionId) {
    GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
        .queryExecutionId(queryExecutionId).build();

    GetQueryExecutionResponse getQueryExecutionResponse;
    boolean isQueryStillRunning = true;
    while (isQueryStillRunning) {
      getQueryExecutionResponse = athenaClient.getQueryExecution(getQueryExecutionRequest);
      String queryState = getQueryExecutionResponse.queryExecution().status().state().toString();
      if (queryState.equals(QueryExecutionState.FAILED.toString())) {
        throw new RuntimeException("Query Failed to run with Error Message: " + getQueryExecutionResponse
            .queryExecution().status().stateChangeReason());
      } else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
        throw new RuntimeException("Query was cancelled.");
      } else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
        isQueryStillRunning = false;
      } else {
        // Sleep an amount of time before retrying again.
        Thread.sleep(5000);
      }
      log.info("Current Status is: " + queryState);
    }
  }

  private List<Row> getResultRows(String queryExecutionId) {
    GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
        .queryExecutionId(queryExecutionId).build();

    GetQueryResultsIterable getQueryResultsResults = athenaClient.getQueryResultsPaginator(getQueryResultsRequest);

    return getQueryResultsResults.stream()
        .flatMap(getQueryResultsResponse -> getQueryResultsResponse.resultSet().rows().stream())
        .collect(Collectors.toList());
  }
}

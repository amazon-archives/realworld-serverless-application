package software.amazon.serverless.apprepo.cucumber.steps;

import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import com.google.inject.Inject;

import java.time.Duration;
import java.time.Instant;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import software.amazon.serverless.ssmcachingclient.SsmParameterCachingClient;

/**
 * Setup and teardown implementation.
 */
@Slf4j
public class SetupAndTeardownSteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;
  @Inject
  private CognitoUserManager cognitoUserManager;
  @Inject
  private SsmParameterCachingClient ssmParameterCachingClient;
  @Inject
  private S3Helper s3Helper;

  @Before
  public void beforeScenario(final Scenario s) {
    TestEnv.reset();
    Instant startedAt = Instant.now();
    TestEnv.setScenarioStartedAt(startedAt);
    cognitoUserManager.setupCognitoUser();
    log.info("Starting scenario \"{}\" at {}", s.getName(), startedAt);
  }

  @After
  public void afterScenario(Scenario s) {
    TestEnv.getApplications().forEach(app -> tryDeleteApplication(app.getApplicationId()));

    try {
      cognitoUserManager.cleanupCognitoUser();
    } catch (Throwable t) {
      log.warn(String.format("Failed to clean up cognito user %s", TestEnv.getUsername()), t);
    }

    Instant endedAt = Instant.now();
    long durationMillis = Duration.between(TestEnv.getScenarioStartedAt(), endedAt).toMillis();
    if (s.isFailed()) {
      log.info("Failed scenario \"{}\" at {} (Duration: {} ms)", s.getName(), endedAt, durationMillis);
      log.info("TestData: {}", TestEnv.getTestData().toString());
    } else {
      log.info("Completed scenario \"{}\" at {} (Duration: {} ms)", s.getName(), endedAt, durationMillis);
    }

    tryEmptyTestBuckets();
  }

  private void tryDeleteApplication(String applicationId) {
    try {
      log.info("Cleaning up application {}", applicationId);
      appRepo.deleteApplication(new DeleteApplicationRequest().applicationId(applicationId));
    } catch (Throwable t) {
      log.warn("Failed to clean up application {}", applicationId, t);
    }
  }

  private void tryEmptyTestBuckets(){
    try {
      log.info("Emptying test buckets so that CloudFormation can delete them");
      s3Helper.emptyBucket(ssmParameterCachingClient.getAsString("s3/DataBucket/Name"));
      s3Helper.emptyBucket(ssmParameterCachingClient.getAsString("s3/AthenaQueryResultsBucketName/IntegTest/Name"));
    } catch (Throwable t) {
      log.warn("Failed to empty test buckets", t);
    }

  }
}

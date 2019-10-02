package com.amazonaws.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import com.amazonaws.serverless.apprepo.api.client.model.ApplicationSummary;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsRequest;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsResult;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

/**
 * Implementation of the steps in ListApplications.feature.
 */
@Slf4j
public class ListApplicationsSteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;

  @When("the user lists applications")
  public void the_user_lists_applications() {
    try {
      appRepo.listApplications(new ListApplicationsRequest());
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

  @When("the user lists applications with ([1-9][0-9]*)? max items")
  public void the_user_lists_applications_with_max_items(int maxItems) {
    try {
      appRepo.listApplications(new ListApplicationsRequest()
            .maxItems(Integer.toString(maxItems)));
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

  @When("the user lists applications with next token")
  public void the_user_lists_applications_with_next_token() {
    Preconditions.checkState(TestEnv.getApplicationList().getNextToken() != null, "Step assumes next token exists.");

    try {
      appRepo.listApplications(new ListApplicationsRequest()
            .nextToken(TestEnv.getApplicationList().getNextToken()));
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

  @When("^an unauthorized user lists applications$")
  public void an_unauthorized_user_lists_applications() {
    // Set a wrong password
    TestEnv.setPassword(UUID.randomUUID().toString());
    try {
      appRepo.listApplications(new ListApplicationsRequest());
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

  @Then("all applications should be listed")
  public void all_applications_should_be_listed() {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(!TestEnv.getApplications().isEmpty(), "Step assumes previous applications exist");
    Preconditions.checkState(TestEnv.getApplicationList() != null, "Step assumes listApplications has been called");

    List<ApplicationSummary> expectedApplicationSummaries = TestEnv.getApplications().stream()
          .map(app -> new ApplicationSummary()
                .applicationId(app.getApplicationId())
                .description(app.getDescription())
                .creationTime(app.getCreationTime()))
          .collect(Collectors.toList());

    assertThat(TestEnv.getApplicationList().getApplications()).containsAll(expectedApplicationSummaries);
    assertThat(TestEnv.getApplicationList().getNextToken()).isNull();
  }

  @Then("([1-9][0-9]*)? applications should be listed")
  public void applications_should_be_listed(int count) {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(!TestEnv.getApplications().isEmpty(), "Step assumes previous applications exist");
    Preconditions.checkState(TestEnv.getApplicationList() != null, "Step assumes listApplications has been called");

    List<ApplicationSummary> expectedApplicationSummaries = TestEnv.getApplications().stream()
          .map(app -> new ApplicationSummary()
                .applicationId(app.getApplicationId())
                .description(app.getDescription())
                .creationTime(app.getCreationTime()))
          .collect(Collectors.toList());

    List<ApplicationSummary> applicationSummaries = TestEnv.getApplicationList().getApplications();
    assertThat(applicationSummaries).hasSize(count);
    assertThat(expectedApplicationSummaries).containsAll(applicationSummaries);
  }

  @And("the application should no longer be listed")
  public void the_application_should_no_longer_be_listed() {
    Preconditions.checkState(TestEnv.getApplication() != null, "Step assumes an application has been created before");

    ApplicationSummary applicationSummary = new ApplicationSummary()
          .applicationId(TestEnv.getApplication().getApplicationId())
          .description(TestEnv.getApplication().getDescription())
          .creationTime(TestEnv.getApplication().getCreationTime());

    ListApplicationsResult listApplicationsResult = appRepo.listApplications(new ListApplicationsRequest());

    assertThat(listApplicationsResult.getApplicationList().getApplications())
          .doesNotContain(applicationSummary);
  }

  @And("the listed applications should be in alphabetical order")
  public void the_listed_applications_should_be_in_alphabetical_order() {
    Preconditions.checkState(TestEnv.getApplicationList() != null, "Step assumes listApplications has been called");
    assertThat(TestEnv.getApplicationList().getApplications())
          .isSortedAccordingTo(Comparator.comparing(ApplicationSummary::getApplicationId));
  }
}

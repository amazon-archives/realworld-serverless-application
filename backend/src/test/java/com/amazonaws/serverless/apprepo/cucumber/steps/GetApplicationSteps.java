package com.amazonaws.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import com.amazonaws.serverless.apprepo.api.client.model.Application;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationResult;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.util.UUID;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the steps in GetApplication.feature.
 */
@Slf4j
public class GetApplicationSteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;

  @When("^the user gets the application$")
  public void the_user_gets_the_application() {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    GetApplicationResult result = appRepo.getApplication(new GetApplicationRequest().applicationId(TestEnv.getApplicationId()));
    TestEnv.setApplication(result.getApplication());
  }

  @When("^a user gets a non-existent application$")
  public void a_user_gets_a_non_existent_application() {
    try {
      appRepo.getApplication(new GetApplicationRequest()
            .applicationId("applicationId-" + UUID.randomUUID().toString()));
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

  @Then("^the application should be returned$")
  public void the_application_should_be_returned() {
    assertThat(TestEnv.getLastException()).isNull();
    Application application = TestEnv.getApplication();
    assertThat(application.getApplicationId()).isEqualTo(TestEnv.getApplicationId());
    assertThat(application.getAuthor()).isEqualTo(TestEnv.getAuthor());
    assertThat(application.getDescription()).isEqualTo(TestEnv.getApplicationDescription());
    assertThat(application.getHomePageUrl()).isEqualTo(TestEnv.getHomePageUrl());
  }
}

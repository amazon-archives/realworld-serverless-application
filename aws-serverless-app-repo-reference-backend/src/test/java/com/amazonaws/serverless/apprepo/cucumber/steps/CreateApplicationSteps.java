package com.amazonaws.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.serverless.apprepo.api.client.AWSSarBackend;
import com.amazonaws.serverless.apprepo.api.client.model.Application;
import com.amazonaws.serverless.apprepo.api.client.model.ConflictException;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationInput;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationResult;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.util.UUID;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the steps in CreateApplication.feature.
 */
@Slf4j
public class CreateApplicationSteps {
  @Inject
  private AWSSarBackend sarBackend;

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

    Application application = sarBackend.createApplication(request).getApplication();

    assertThat(TestEnv.getLastException()).isNull();
    assertThat(application.getApplicationId()).isEqualTo(input.getApplicationId());
    assertThat(application.getAuthor()).isEqualTo(input.getAuthor());
    assertThat(application.getDescription()).isEqualTo(input.getDescription());
    assertThat(application.getHomePageUrl()).isEqualTo(input.getHomePageUrl());
    assertThat(application.getCreationTime()).isNotBlank();
  }

  @When("^the user creates an application with the same id$")
  public void the_user_creates_an_application_with_the_same_id() {
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId(TestEnv.getApplicationId())
                .author("author-" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application without application id$")
  public void a_user_creates_an_application_without_application_id() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .author("author-" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application without author$")
  public void a_user_creates_an_application_without_author() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId("applicationId-" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application without description$")
  public void a_user_creates_an_application_without_description() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId("applicationId-" + UUID.randomUUID().toString())
                .author("author-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application with invalid application id$")
  public void a_user_creates_an_application_with_invalid_application_id() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId("applicationId?" + UUID.randomUUID().toString())
                .author("author-" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application with invalid author$")
  public void a_user_creates_an_application_with_invalid_author() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId("applicationId-" + UUID.randomUUID().toString())
                .author("author?" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application with invalid description$")
  public void a_user_creates_an_application_with_invalid_description() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId("applicationId-" + UUID.randomUUID().toString())
                .author("author-" + UUID.randomUUID().toString())
                .description("description?" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @When("^a user creates an application with invalid home page URL$")
  public void a_user_creates_an_application_with_invalid_homepageurl() {
    CreateApplicationRequest request = new CreateApplicationRequest()
          .createApplicationInput(new CreateApplicationInput()
                .applicationId("applicationId-" + UUID.randomUUID().toString())
                .author("author-" + UUID.randomUUID().toString())
                .description("description?" + UUID.randomUUID().toString())
                .homePageUrl("invalid/" + UUID.randomUUID().toString()));

    createApplication(request);
  }

  @Then("^a new application should be created|the application should be updated$")
  public void a_new_application_should_be_created() {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    GetApplicationResult result = sarBackend.getApplication(new GetApplicationRequest().applicationId(TestEnv.getApplicationId()));
    assertThat(result.getApplication())
          .isNotNull()
          .isEqualTo(TestEnv.getApplication());
  }

  @Then("^the call should fail because the application already exists$")
  public void the_call_should_fail_because_the_application_already_exists() {
    assertThat(TestEnv.getLastException())
          .isNotNull()
          .isInstanceOf(ConflictException.class);
  }

  private void createApplication(final CreateApplicationRequest request) {
    try {
      sarBackend.createApplication(request);
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

}

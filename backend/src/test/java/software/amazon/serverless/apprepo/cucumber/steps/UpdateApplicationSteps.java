package software.amazon.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.api.client.model.Application;
import software.amazon.serverless.apprepo.api.client.model.BadRequestException;
import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationInput;
import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.util.UUID;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of the steps in UpdateApplication.feature.
 */
@Slf4j
public class UpdateApplicationSteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;

  @When("^the user updates the application$")
  public void the_user_updates_the_application() {
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    UpdateApplicationInput input = new UpdateApplicationInput()
          .author("author-" + UUID.randomUUID().toString())
          .description("description-" + UUID.randomUUID().toString())
          .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString());
    UpdateApplicationRequest request = new UpdateApplicationRequest()
          .applicationId(TestEnv.getApplicationId())
          .updateApplicationInput(input);

    Application application = appRepo.updateApplication(request).getApplication();

    assertThat(TestEnv.getLastException()).isNull();
    assertThat(application.getApplicationId()).isEqualTo(TestEnv.getApplicationId());
    assertThat(application.getAuthor()).isEqualTo(input.getAuthor());
    assertThat(application.getDescription()).isEqualTo(input.getDescription());
    assertThat(application.getHomePageUrl()).isEqualTo(input.getHomePageUrl());
  }

  @When("^the user updates the application with no update$")
  public void the_user_updates_the_application_with_no_update() {
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    UpdateApplicationRequest request = new UpdateApplicationRequest()
          .applicationId(TestEnv.getApplicationId())
          .updateApplicationInput(new UpdateApplicationInput());

    updateApplication(request);
  }

  @When("^a user updates a non-existent application$")
  public void a_user_updates_a_non_existent_application() {
    UpdateApplicationRequest request = new UpdateApplicationRequest()
          .applicationId("applicationId-" + UUID.randomUUID().toString())
          .updateApplicationInput(new UpdateApplicationInput()
                .author("author-" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    updateApplication(request);
  }


  @When("^the user updates the application with invalid author$")
  public void the_user_updates_the_application_with_invalid_author() {
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    UpdateApplicationRequest request = new UpdateApplicationRequest()
          .applicationId(TestEnv.getApplicationId())
          .updateApplicationInput(new UpdateApplicationInput()
                .author("author?" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    updateApplication(request);
  }

  @When("^the user updates the application with invalid description$")
  public void the_user_updates_the_application_with_invalid_description() {
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    UpdateApplicationRequest request = new UpdateApplicationRequest()
          .applicationId(TestEnv.getApplicationId())
          .updateApplicationInput(new UpdateApplicationInput()
                .author("author-" + UUID.randomUUID().toString())
                .description(StringUtils.repeat("a", 300))
                .homePageUrl("https://github.com/awslabs/" + UUID.randomUUID().toString()));

    updateApplication(request);
  }

  @When("^the user updates the application with invalid home page URL$")
  public void the_user_updates_the_application_with_invalid_homepageurl() {
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    UpdateApplicationRequest request = new UpdateApplicationRequest()
          .applicationId(TestEnv.getApplicationId())
          .updateApplicationInput(new UpdateApplicationInput()
                .author("author-" + UUID.randomUUID().toString())
                .description("description-" + UUID.randomUUID().toString())
                .homePageUrl("invalid/" + UUID.randomUUID().toString()));

    updateApplication(request);
  }

  @Then("^the call should fail because there is no update$")
  public void the_call_should_fail_because_there_is_no_update() {
    assertThat(TestEnv.getLastException())
          .isNotNull()
          .isInstanceOf(BadRequestException.class);
  }

  private void updateApplication(final UpdateApplicationRequest request) {
    try {
      appRepo.updateApplication(request);
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }
}

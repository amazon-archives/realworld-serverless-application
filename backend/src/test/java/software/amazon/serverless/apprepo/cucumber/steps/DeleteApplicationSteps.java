package software.amazon.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.NotFoundException;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.util.UUID;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the steps in DeleteApplication.feature.
 */
@Slf4j
public class DeleteApplicationSteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;

  @When("^the user deletes the application$")
  public void the_user_deletes_the_application() {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");

    appRepo.deleteApplication(new DeleteApplicationRequest().applicationId(TestEnv.getApplicationId()));
  }

  @When("^a user deletes a non-existent application$")
  public void a_user_deletes_a_non_existent_application() {
    try {
      appRepo.deleteApplication(new DeleteApplicationRequest()
            .applicationId("applicationId" + UUID.randomUUID().toString()));
    } catch (Exception e) {
      // do nothing and verify exception in the next step
    }
  }

  @Then("^the application should be deleted$")
  public void the_application_should_be_deleted() {
    assertThat(TestEnv.getLastException()).isNull();
    Preconditions.checkState(TestEnv.getApplicationId() != null, "Step assumes previous application id exists");
    assertThatThrownBy(() -> appRepo.deleteApplication(new DeleteApplicationRequest()
          .applicationId("applicationId" + UUID.randomUUID().toString())))
          .isInstanceOf(NotFoundException.class);

  }
}

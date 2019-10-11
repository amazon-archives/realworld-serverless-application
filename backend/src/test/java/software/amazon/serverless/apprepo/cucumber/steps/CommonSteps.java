package software.amazon.serverless.apprepo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.api.client.model.BadRequestException;
import software.amazon.serverless.apprepo.api.client.model.NotFoundException;
import software.amazon.serverless.apprepo.api.client.model.UnauthorizedException;
import com.google.inject.Inject;

import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of common steps in multiple features.
 */
@Slf4j
public class CommonSteps {
  @Inject
  private AWSServerlessApplicationRepository appRepo;

  @Then("^the call should fail because the application does not exist$")
  public void the_call_should_fail_because_the_application_does_not_exist() {
    assertThat(TestEnv.getLastException())
          .isNotNull()
          .isInstanceOf(NotFoundException.class);
  }

  @Then("^the call should fail because of bad request$")
  public void the_call_should_fail_because_of_bad_request() {
    assertThat(TestEnv.getLastException())
          .isNotNull()
          .isInstanceOf(BadRequestException.class);
  }

  @Then("^the call should fail because of access denied$")
  public void the_call_should_fail_because_of_access_denied() {
    assertThat(TestEnv.getLastException())
          .isNotNull()
          .isInstanceOf(UnauthorizedException.class);
  }
}

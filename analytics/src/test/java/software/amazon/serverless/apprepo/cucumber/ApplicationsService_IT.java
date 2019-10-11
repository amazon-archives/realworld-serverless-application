package software.amazon.serverless.apprepo.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Junit entry point for cucumber tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/software/amazon/serverless/apprepo/cucumber/features",
      glue = "software.amazon.serverless.apprepo.cucumber.steps",
      tags = {"not @NotImplemented"},// Use @NotImplemented tag for scenarios that have not been implemented
      plugin = {"pretty"})
public class ApplicationsService_IT {
}

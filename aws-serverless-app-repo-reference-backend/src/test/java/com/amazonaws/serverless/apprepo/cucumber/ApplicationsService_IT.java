package com.amazonaws.serverless.apprepo.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Junit entry point for cucumber tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/amazonaws/serverless/apprepo/cucumber/features",
      glue = "com.amazonaws.serverless.apprepo.cucumber.steps",
      tags = {"not @NotImplemented"},// Use @NotImplemented tag for scenarios that have not been implemented
      plugin = {"pretty"})
public class ApplicationsService_IT {
}

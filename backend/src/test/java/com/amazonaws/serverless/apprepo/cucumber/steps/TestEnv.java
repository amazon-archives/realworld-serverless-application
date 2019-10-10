package com.amazonaws.serverless.apprepo.cucumber.steps;

import com.amazonaws.serverless.apprepo.api.client.model.Application;
import com.amazonaws.serverless.apprepo.api.client.model.ApplicationList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.ToString;

/**
 * Stores test data needed across scenario steps.
 */
public final class TestEnv {
  private static final ThreadLocal<TestData> data = new ThreadLocal<>();
  private static final String SCENARIO_STARTED_AT_KEY = TestEnv.TestData.key(TestEnv.class, "scenarioStartedAt");
  private static final String LAST_EXCEPTION_KEY = TestData.key(TestEnv.class, "lastThrowable");
  private static final String APPLICATION_ID_KEY = TestEnv.TestData.key(TestEnv.class, "applicationId");
  private static final String APPLICATION_DESCRIPTION_KEY = TestEnv.TestData.key(TestEnv.class, "applicationDescription");
  private static final String AUTHOR_KEY = TestEnv.TestData.key(TestEnv.class, "author");
  private static final String HOME_PAGE_URL_KEY = TestEnv.TestData.key(TestEnv.class, "homePageUrl");
  private static final String APPLICATION_KEY = TestEnv.TestData.key(TestEnv.class, "application");
  private static final String APPLICATION_LIST_KEY = TestEnv.TestData.key(TestEnv.class, "applicationList");
  private static final String APPLICATIONS_KEY = TestEnv.TestData.key(TestEnv.class, "applicationsKey");
  private static final String COGNITO_USERNAME = TestEnv.TestData.key(TestEnv.class, "cognitoUsername");
  private static final String COGNITO_PASSWORD = TestEnv.TestData.key(TestEnv.class, "cognitoPassword");

  private TestEnv() {
  }

  public static void reset() {
    data.set(new TestData());
  }

  public static TestData getTestData() {
    return data.get();
  }

  public static void setScenarioStartedAt(final Instant startedAt) {
    TestEnv.getTestData().set(SCENARIO_STARTED_AT_KEY, startedAt);
  }

  public static Instant getScenarioStartedAt() {
    return TestEnv.getTestData().get(SCENARIO_STARTED_AT_KEY);
  }

  public static void setLastException(final Throwable t) {
    getTestData().set(LAST_EXCEPTION_KEY, t);
  }

  public static Throwable getLastException() {
    return getTestData().get(LAST_EXCEPTION_KEY);
  }

  public static void setApplicationId(final String applicationId) {
    TestEnv.getTestData().set(APPLICATION_ID_KEY, applicationId);
  }

  public static String getApplicationId() {
    return TestEnv.getTestData().get(APPLICATION_ID_KEY);
  }

  public static void setApplicationDescription(final String description) {
    TestEnv.getTestData().set(APPLICATION_DESCRIPTION_KEY, description);
  }

  public static String getApplicationDescription() {
    return TestEnv.getTestData().get(APPLICATION_DESCRIPTION_KEY);
  }

  public static void setAuthor(final String author) {
    TestEnv.getTestData().set(AUTHOR_KEY, author);
  }

  public static String getAuthor() {
    return TestEnv.getTestData().get(AUTHOR_KEY);
  }

  public static void setHomePageUrl(final String homePageUrl) {
    TestEnv.getTestData().set(HOME_PAGE_URL_KEY, homePageUrl);
  }

  public static String getHomePageUrl() {
    return TestEnv.getTestData().get(HOME_PAGE_URL_KEY);
  }

  public static void setUsername(final String username) {
    TestEnv.getTestData().set(COGNITO_USERNAME, username);
  }

  public static String getUsername() {
    return TestEnv.getTestData().get(COGNITO_USERNAME);
  }

  public static void setPassword(final String password) {
    TestEnv.getTestData().set(COGNITO_PASSWORD, password);
  }

  public static String getPassword() {
    return TestEnv.getTestData().get(COGNITO_PASSWORD);
  }

  public static void setApplication(final Application application) {
    TestEnv.getTestData().set(APPLICATION_KEY, application);
  }

  public static Application getApplication() {
    return TestEnv.getTestData().get(APPLICATION_KEY);
  }

  public static void addApplication(Application application) {
    Map<String, Application> applications = TestEnv.getTestData().get(APPLICATIONS_KEY);
    if (applications == null) {
      applications = new HashMap<>();
      TestEnv.getTestData().set(APPLICATIONS_KEY, applications);
    }
    applications.put(application.getApplicationId(), application);
  }

  public static List<Application> getApplications() {
    Map<String, Application> applications = TestEnv.getTestData().get(APPLICATIONS_KEY);
    if (applications == null) {
      applications = new HashMap<>();
      TestEnv.getTestData().set(APPLICATIONS_KEY, applications);
    }
    return new ArrayList<>(applications.values());
  }

  public static void setApplicationList(final ApplicationList listApplicationsResponse) {
    TestEnv.getTestData().set(APPLICATION_LIST_KEY, listApplicationsResponse);
  }

  public static ApplicationList getApplicationList() {
    return TestEnv.getTestData().get(APPLICATION_LIST_KEY);
  }

  @ToString
  public static final class TestData {
    private final Map<String, Object> data = new HashMap<>();

    public static String key(final Class<?> klass, final String keyName) {
      return klass.getSimpleName() + "." + keyName;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
      return (T) data.get(key);
    }

    public <T> void set(final String key, final T value) {
      data.put(key, value);
    }
  }
}

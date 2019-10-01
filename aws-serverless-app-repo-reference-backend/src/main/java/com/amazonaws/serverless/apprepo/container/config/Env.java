package com.amazonaws.serverless.apprepo.container.config;

/**
 * System environment variable helper class.
 */
public final class Env {
  private static String getEnv(final String key) {
    return System.getenv(key);
  }

  private static String getStage() {
    return getEnv("STAGE");
  }

  public static String getSsmConfigKeyPrefix() {
    return String.format("/applications/apprepo/%s/", getStage());
  }

  public static String getRegion() {
    return getEnv("AWS_REGION");
  }

}

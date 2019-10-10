package com.amazonaws.serverless.apprepo.cucumber.guice;

import com.amazonaws.ImmutableRequest;
import com.amazonaws.serverless.apprepo.api.client.auth.CognitoAuthorizer;
import com.amazonaws.serverless.apprepo.cucumber.steps.CognitoUserManager;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link CognitoAuthorizer} to provide signer for API requests.
 */
@Slf4j
@RequiredArgsConstructor
public class CognitoAuthorizerImpl implements CognitoAuthorizer {
  private final CognitoUserManager cognitoUserManager;

  @Override
  public String generateToken(ImmutableRequest<?> request) {
    try {
      return cognitoUserManager.generateIdToken();
    } catch (Throwable t) {
      log.warn("Exception thrown when generating token", t);
      return UUID.randomUUID().toString();
    }
  }
}

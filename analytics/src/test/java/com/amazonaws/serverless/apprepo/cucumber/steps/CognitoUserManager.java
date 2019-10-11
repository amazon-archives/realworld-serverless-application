package com.amazonaws.serverless.apprepo.cucumber.steps;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminConfirmSignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDeleteUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

/**
 * This class is used to manage Cognito User creation, authentication and deletion.
 */
@RequiredArgsConstructor
@Slf4j
public class CognitoUserManager {
  private final CognitoIdentityProviderClient cognito;
  private final String clientId;
  private final String userPoolId;

  public void setupCognitoUser() {
    String username = String.format("success+%s@simulator.amazonses.com", UUID.randomUUID().toString());
    String password = UUID.randomUUID().toString();
    cognito.signUp(SignUpRequest.builder()
          .clientId(clientId)
          .username(username)
          .password(password)
          .build());
    cognito.adminConfirmSignUp(AdminConfirmSignUpRequest.builder()
          .userPoolId(userPoolId)
          .username(username)
          .build());
    TestEnv.setUsername(username);
    TestEnv.setPassword(password);
    log.info("Created Cognito user {}", username);
  }

  public void cleanupCognitoUser() {
    String username = TestEnv.getUsername();
    log.info("Cleaning up Cognito user {}...", username);
    cognito.adminDeleteUser(AdminDeleteUserRequest.builder()
          .username(username)
          .userPoolId(userPoolId)
          .build());

  }

  public String generateIdToken() {
    Map<String, String> authParams = new HashMap<>();
    authParams.put("USERNAME", TestEnv.getUsername());
    authParams.put("PASSWORD", TestEnv.getPassword());
    AuthenticationResultType authResult = cognito.adminInitiateAuth(AdminInitiateAuthRequest.builder()
          .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
          .clientId(clientId)
          .userPoolId(userPoolId)
          .authParameters(authParams)
          .build())
          .authenticationResult();
    return authResult.idToken();
  }
}

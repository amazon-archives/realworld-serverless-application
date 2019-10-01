/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.auth;

import com.amazonaws.ImmutableRequest;
import com.amazonaws.SignableRequest;
import com.amazonaws.auth.RequestSigner;
import com.amazonaws.serverless.apprepo.api.client.AWSSarBackend;
import com.amazonaws.serverless.apprepo.api.client.AWSSarBackendClientBuilder;

import javax.annotation.Generated;

/**
 * A default implementation of {@link RequestSigner} that puts a generated token into the header. An implementation of
 * this can to be supplied during construction of a {@link AWSSarBackend} via
 * {@link AWSSarBackendClientBuilder#signer(CognitoAuthorizer)} like so
 *
 * <pre>
 * <code>
 *  AWSSarBackend client = AWSSarBackend.builder().signer((CognitoAuthorizer) request -> "some token").build();
 * </code>
 * </pre>
 */
@FunctionalInterface
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public interface CognitoAuthorizer extends RequestSigner {

  /**
   * Generate a token that will be added to Authorization in the header of the request during signing
   *
   * @param request
   *        an immutable view of the request for which to generate a token
   * @return the token to use for signing
   */
  String generateToken(ImmutableRequest<?> request);

  /**
   * @see RequestSigner#sign(SignableRequest)
   */
  @Override
  default void sign(SignableRequest<?> request) {
    request.addHeader("Authorization", generateToken(request));
  }
}

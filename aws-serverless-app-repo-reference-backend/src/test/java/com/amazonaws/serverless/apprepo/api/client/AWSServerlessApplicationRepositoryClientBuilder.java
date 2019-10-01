/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client;

import com.amazonaws.Protocol;
import com.amazonaws.annotation.NotThreadSafe;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.opensdk.internal.config.ApiGatewayClientConfigurationFactory;
import com.amazonaws.opensdk.protect.client.SdkSyncClientBuilder;
import com.amazonaws.serverless.apprepo.api.client.auth.CognitoAuthorizer;
import com.amazonaws.util.RuntimeHttpUtils;

import java.net.URI;
import javax.annotation.Generated;

/**
 * Fluent builder for {@link com.amazonaws.serverless.apprepo.api.client.AWSServerlessApplicationRepository}.
 *
 * @see com.amazonaws.serverless.apprepo.api.client.AWSServerlessApplicationRepository#builder
 **/
@NotThreadSafe
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public final class AWSServerlessApplicationRepositoryClientBuilder extends
      SdkSyncClientBuilder<AWSServerlessApplicationRepositoryClientBuilder, AWSServerlessApplicationRepository> {

  private static final URI DEFAULT_ENDPOINT = RuntimeHttpUtils.toUri("xxx.execute-api.us-east-1.amazonaws.com", Protocol.HTTPS);
  private static final String DEFAULT_REGION = "us-east-1";

  /**
   * Package private constructor - builder should be created via {@link AWSServerlessApplicationRepository#builder()}
   */
  AWSServerlessApplicationRepositoryClientBuilder() {
    super(new ApiGatewayClientConfigurationFactory());
  }

  /**
   * Specify an implementation of the CognitoAuthorizer to be used during signing
   *
   * @param requestSigner
   *        the requestSigner implementation to use
   * @return This object for method chaining.
   */
  public AWSServerlessApplicationRepositoryClientBuilder signer(CognitoAuthorizer requestSigner) {
    return signer(requestSigner, CognitoAuthorizer.class);
  }

  /**
   * Specify an implementation of the CognitoAuthorizer to be used during signing
   *
   * @param requestSigner
   *        the requestSigner implementation to use
   */
  public void setSigner(CognitoAuthorizer requestSigner) {
    signer(requestSigner);
  }

  /**
   * Construct a synchronous implementation of AWSServerlessApplicationRepository using the current builder
   * configuration.
   *
   * @param params
   *        Current builder configuration represented as a parameter object.
   * @return Fully configured implementation of AWSServerlessApplicationRepository.
   */
  @Override
  protected AWSServerlessApplicationRepository build(AwsSyncClientParams params) {
    return new AWSServerlessApplicationRepositoryClient(params);
  }

  @Override
  protected URI defaultEndpoint() {
    return DEFAULT_ENDPOINT;
  }

  @Override
  protected String defaultRegion() {
    return DEFAULT_REGION;
  }

}

package software.amazon.serverless.apprepo.cucumber.guice;

import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.cucumber.steps.AWSServerlessApplicationRepositoryRecordingClient;
import software.amazon.serverless.apprepo.cucumber.steps.CognitoUserManager;

import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.AthenaClientBuilder;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.serverless.apprepo.cucumber.steps.S3Helper;
import software.amazon.serverless.ssmcachingclient.SsmParameterCachingClient;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.strategy.LogErrorContextMissingStrategy;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.time.Duration;

import cucumber.runtime.java.guice.ScenarioScope;
import cucumber.runtime.java.guice.impl.SequentialScenarioScope;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.ssm.SsmClient;

/**
 * DI wiring for ApplicationSteps.
 */
public class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    // Disable AWS x-ray in integration tests.
    // See doc: https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-configuration.html#xray-sdk-java-configuration-sysprops
    AWSXRay.setGlobalRecorder(AWSXRayRecorderBuilder.standard().withContextMissingStrategy(new LogErrorContextMissingStrategy()).build());
    bind(ScenarioScope.class).toInstance(new SequentialScenarioScope());
  }

  @Singleton
  @Inject
  @Provides
  AWSServerlessApplicationRepository AWSServerlessApplicationRepository(final SsmParameterCachingClient ssm, final CognitoUserManager cognitoUserManager) {
    String endpoint = ssm.getAsString("apigateway/ApplicationsApi/Endpoint");
    return new AWSServerlessApplicationRepositoryRecordingClient(AWSServerlessApplicationRepository.builder()
        .endpoint(endpoint)
        .signer(new CognitoAuthorizerImpl(cognitoUserManager))
        .build());
  }

  @Singleton
  @Inject
  @Provides
  SsmParameterCachingClient ssmParameterCachingClient() {
    String path = String.format("/applications/apprepo/%s/", System.getProperty("integtests.stage"));
    return new SsmParameterCachingClient(SsmClient.builder()
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build(),
        Duration.ofMinutes(5), path);
  }

  @Singleton
  @Inject
  @Provides
  CognitoUserManager cognitoUserManager(final SsmParameterCachingClient ssm) {
    String clientId = ssm.getAsString("cognito/userpoolclient/IntegTest/Id");
    String userPoolId = ssm.getAsString("cognito/userpool/ApplicationsApi/Id");
    return new CognitoUserManager(CognitoIdentityProviderClient.builder()
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build(), clientId, userPoolId);
  }

  @Singleton
  @Inject
  @Provides
  S3Helper s3Helper(final S3Client s3Client){
    return new S3Helper(s3Client);
  }

  @Singleton
  @Inject
  @Provides
  AthenaClient athenaClient(){
    return AthenaClient.builder()
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build();
  }

  @Singleton
  @Inject
  @Provides
  S3Client s3Client(){
    return S3Client.builder()
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build();
  }
}

package com.amazonaws.serverless.apprepo.container.factory;

import com.amazonaws.serverless.apprepo.container.config.Env;

import java.time.Duration;

import org.glassfish.hk2.api.Factory;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Factory for {@link DynamoDbClient} for HK2 DI wiring.
 */
public class DynamoDbClientFactory implements Factory<DynamoDbClient> {
  // Creating the DynamoDB client followed AWS SDK v2 best practice to improve Lambda performance:
  // https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/client-configuration-starttime.html
  private static final DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
        .region(Region.of(Env.getRegion()))
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .overrideConfiguration(ClientOverrideConfiguration.builder()
              .apiCallAttemptTimeout(Duration.ofSeconds(1))
              .retryPolicy(RetryPolicy.builder().numRetries(10).build())
              .build())
        .httpClientBuilder(UrlConnectionHttpClient.builder()).build();

  @Override
  public DynamoDbClient provide() {
    return dynamoDbClient;
  }

  @Override
  public void dispose(DynamoDbClient dynamoDbClient) {

  }
}

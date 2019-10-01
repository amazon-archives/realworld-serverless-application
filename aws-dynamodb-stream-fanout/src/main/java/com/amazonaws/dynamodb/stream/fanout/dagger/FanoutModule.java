package com.amazonaws.dynamodb.stream.fanout.dagger;

import javax.inject.Singleton;

import com.amazonaws.dynamodb.stream.fanout.EventBridgePublisher;
import com.amazonaws.dynamodb.stream.fanout.EventPublisher;

import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.time.Duration;

/**
 * Dagger wiring.
 */
@Module
public class FanoutModule {
    private final static String APPLICATIONS_TABLE_NAME_CONFIG_KEY = "ddb/Applications/TableName";

    @Provides
    @Singleton
    public EventBridgeClient provideEventBridgeClient() {
        return EventBridgeClient.builder()
                .region(Region.of(System.getenv("AWS_REGION")))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallAttemptTimeout(Duration.ofSeconds(1))
                        .retryPolicy(RetryPolicy.builder().numRetries(10).build())
                        .build())
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }

    @Provides
    @Singleton
    public SqsClient provideSqsClient() {
        return SqsClient.builder()
                .region(Region.of(System.getenv("AWS_REGION")))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallAttemptTimeout(Duration.ofSeconds(1))
                        .retryPolicy(RetryPolicy.builder().numRetries(10).build())
                        .build())
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }

    @Provides
    @Singleton
    public EventPublisher provideEventPublisher(final EventBridgeClient eventClient, final SqsClient sqsClient) {
        return new EventBridgePublisher(eventClient, sqsClient);
    }
}
package com.amazonaws.serverless.apprepo.container;

import com.amazonaws.serverless.apprepo.api.exception.ApiExceptionMapper;
import com.amazonaws.serverless.apprepo.api.impl.ApplicationsService;
import com.amazonaws.serverless.apprepo.api.impl.pagination.PaginationTokenSerializer;
import com.amazonaws.serverless.apprepo.api.impl.pagination.TokenSerializer;
import com.amazonaws.serverless.apprepo.container.config.ConfigProvider;
import com.amazonaws.serverless.apprepo.container.factory.DynamoDbClientFactory;
import com.amazonaws.serverless.apprepo.container.factory.KmsClientFactory;
import com.amazonaws.serverless.apprepo.container.factory.SsmConfigProviderFactory;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.kms.KmsClient;

/**
 * API Lambda handler. This is the entry point for the API Lambda.
 */
public class ApiLambdaHandler implements RequestStreamHandler {
  private static final ResourceConfig jerseyApplication = new ResourceConfig()
        .registerClasses(ApplicationsService.class, ApiExceptionMapper.class, ResponseFilter.class)
        .register(JacksonFeature.class)
        .register(new AbstractBinder() {
          @Override
          protected void configure() {
            bindFactory(DynamoDbClientFactory.class)
                  .to(DynamoDbClient.class).in(Singleton.class);
            bindFactory(SsmConfigProviderFactory.class)
                  .to(ConfigProvider.class).in(Singleton.class);
            bindFactory(KmsClientFactory.class)
                  .to(KmsClient.class).in(Singleton.class);
            bind(PaginationTokenSerializer.class)
                  .to(new TypeLiteral<TokenSerializer<Map<String, AttributeValue>>>() {
                  })
                  .in(Singleton.class);
          }
        });

  private static final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler
        = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
        throws IOException {
    handler.proxyStream(inputStream, outputStream, context);
  }
}

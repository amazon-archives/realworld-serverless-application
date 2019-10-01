package com.amazonaws.serverless.apprepo.api.impl;

import com.amazonaws.serverless.apprepo.api.ApplicationsApi;
import com.amazonaws.serverless.apprepo.api.exception.BadRequestApiException;
import com.amazonaws.serverless.apprepo.api.exception.ConflictApiException;
import com.amazonaws.serverless.apprepo.api.exception.NotFoundApiException;
import com.amazonaws.serverless.apprepo.api.impl.pagination.InvalidTokenException;
import com.amazonaws.serverless.apprepo.api.impl.pagination.TokenSerializer;
import com.amazonaws.serverless.apprepo.api.model.Application;
import com.amazonaws.serverless.apprepo.api.model.ApplicationList;
import com.amazonaws.serverless.apprepo.api.model.ApplicationSummary;
import com.amazonaws.serverless.apprepo.api.model.BadRequestException;
import com.amazonaws.serverless.apprepo.api.model.ConflictException;
import com.amazonaws.serverless.apprepo.api.model.CreateApplicationInput;
import com.amazonaws.serverless.apprepo.api.model.NotFoundException;
import com.amazonaws.serverless.apprepo.api.model.UpdateApplicationInput;
import com.amazonaws.serverless.apprepo.container.config.ConfigProvider;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

/**
 * ApplicationsService implements {@link ApplicationsApi}.
 *
 * <p>It interacts with DynamoDB when processing each API.
 */
@Slf4j
@RequiredArgsConstructor
public class ApplicationsService implements ApplicationsApi {
  static final Integer DEFAULT_LIST_APPLICATIONS_LIMIT = 10;
  private final TokenSerializer<Map<String, AttributeValue>> paginationTokenSerializer;
  private final DynamoDbClient dynamodb;
  private final ModelMapper modelMapper;
  private final String tableName;
  private final Clock clock;
  @Context
  @Setter
  private SecurityContext securityContext;

  @Inject
  public ApplicationsService(
        final TokenSerializer<Map<String, AttributeValue>> paginationTokenSerializer,
        final DynamoDbClient dynamodb, final ConfigProvider configProvider) {
    this(paginationTokenSerializer, dynamodb, configureModelMapper(),
          configProvider.getApplicationsTableName(), Clock.systemUTC());
  }

  public ApplicationsService(
        final TokenSerializer<Map<String, AttributeValue>> paginationTokenSerializer,
        final DynamoDbClient dynamodb, final ConfigProvider configProvider,
        final Clock clock) {
    this(paginationTokenSerializer, dynamodb, configureModelMapper(),
          configProvider.getApplicationsTableName(), clock);
  }

  private static ModelMapper configureModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    PropertyMap<ApplicationRecord, Application> applicationMap =
        new PropertyMap<ApplicationRecord, Application>() {
          protected void configure() {
            map(source.getCreatedAt()).setCreationTime(null);
          }
        };

    PropertyMap<ApplicationRecord, ApplicationSummary> applicationSummaryMap =
        new PropertyMap<ApplicationRecord, ApplicationSummary>() {
          protected void configure() {
            map(source.getCreatedAt()).setCreationTime(null);
          }
        };

    modelMapper.addMappings(applicationMap);
    modelMapper.addMappings(applicationSummaryMap);
    return modelMapper;
  }

  @Override
  public Application createApplication(CreateApplicationInput createApplicationInput) {
    log.info("Creating application with input {}", createApplicationInput);
    ApplicationRecord applicationRecord = modelMapper.map(createApplicationInput,
          ApplicationRecord.class);
    applicationRecord.setCreatedAt(Instant.now(clock));
    applicationRecord.setVersion(1L);
    applicationRecord.setUserId(securityContext.getUserPrincipal().getName());
    try {
      dynamodb.putItem(PutItemRequest.builder()
            .tableName(tableName)
            .item(applicationRecord.toAttributeMap())
            .conditionExpression(
                  String.format("attribute_not_exists(%s) AND attribute_not_exists(%s)",
                        ApplicationRecord.USER_ID_ATTRIBUTE_NAME,
                        ApplicationRecord.APPLICATION_ID_ATTRIBUTE_NAME))
            .build());
    } catch (ConditionalCheckFailedException e) {
      throw new ConflictApiException(new ConflictException()
            .errorCode("ApplicationAlreadyExist")
            .message(String.format("Application %s already exists.",
                  createApplicationInput.getApplicationId())));
    }
    return modelMapper.map(applicationRecord, Application.class);
  }

  @Override
  public void deleteApplication(String applicationId) {
    log.info("Deleting application {}", applicationId);
    ApplicationRecord applicationRecord = loadApplication(applicationId);
    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":v", AttributeValue.builder()
          .n(applicationRecord.getVersion().toString())
          .build());
    dynamodb.deleteItem(DeleteItemRequest.builder()
          .tableName(tableName)
          .key(toKeyRecord(applicationId))
          .conditionExpression(String.format("%s = :v", ApplicationRecord.VERSION_ATTRIBUTE_NAME))
          .expressionAttributeValues(expressionAttributeValues)
          .build());
  }

  @Override
  public Application getApplication(String applicationId) {
    log.info("Getting application {}", applicationId);
    ApplicationRecord applicationRecord = loadApplication(applicationId);
    return modelMapper.map(applicationRecord, Application.class);
  }

  @Override
  public ApplicationList listApplications(final String nextToken, final Integer maxItems) {
    log.info("Listing applications with nextToken {} and maxItems {}", nextToken, maxItems);
    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":u", AttributeValue.builder()
          .s(securityContext.getUserPrincipal().getName())
          .build());

    QueryRequest.Builder requestBuilder = QueryRequest.builder()
          .consistentRead(true)
          .tableName(tableName)
          .keyConditionExpression(String.format("%s = :u",
                ApplicationRecord.USER_ID_ATTRIBUTE_NAME))
          .expressionAttributeValues(expressionAttributeValues)
          .limit(maxItems == null ? DEFAULT_LIST_APPLICATIONS_LIMIT : maxItems);
    if (nextToken != null) {
      try {
        requestBuilder.exclusiveStartKey(paginationTokenSerializer.deserialize(nextToken));
      } catch (InvalidTokenException e) {
        throw new BadRequestApiException(new BadRequestException()
              .errorCode("InvalidRequest")
              .message(String.format("NextToken %s is invalid.", nextToken)));
      }
    }
    QueryResponse queryResponse = dynamodb.query(requestBuilder.build());

    List<ApplicationSummary> applicationSummaries = queryResponse.items()
          .stream()
          .map(ApplicationRecord::new)
          .map(record -> modelMapper.map(record, ApplicationSummary.class))
          .collect(Collectors.toList());

    ApplicationList result = new ApplicationList()
          .applications(applicationSummaries);
    if (queryResponse.lastEvaluatedKey() != null) {
      result.nextToken(paginationTokenSerializer
            .serialize(queryResponse.lastEvaluatedKey()));
    }
    return result;
  }

  @Override
  public Application updateApplication(String applicationId,
                                       UpdateApplicationInput updateApplicationInput) {
    log.info("Updating application {} with input {}", applicationId, updateApplicationInput);
    if (updateApplicationInput.getHomePageUrl() == null
          && updateApplicationInput.getDescription() == null
          && updateApplicationInput.getAuthor() == null) {
      throw new BadRequestApiException(new BadRequestException()
            .errorCode("InvalidRequest")
            .message("No update is present."));
    }

    ApplicationRecord applicationRecord = loadApplication(applicationId);
    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    String author = updateApplicationInput.getAuthor();
    String description = updateApplicationInput.getDescription();
    String homePageUrl = updateApplicationInput.getHomePageUrl();
    List<String> updateExpressionList = new ArrayList<>();
    if (author != null) {
      applicationRecord.setAuthor(author);
      expressionAttributeValues.put(":a", AttributeValue.builder().s(author).build());
      updateExpressionList.add(String.format("%s = :a",
            ApplicationRecord.AUTHOR_ATTRIBUTE_NAME));
    }
    if (description != null) {
      applicationRecord.setDescription(description);
      expressionAttributeValues.put(":d", AttributeValue.builder().s(description).build());
      updateExpressionList.add(String.format("%s = :d",
            ApplicationRecord.DESCRIPTION_ATTRIBUTE_NAME));
    }
    if (homePageUrl != null) {
      applicationRecord.setHomePageUrl(homePageUrl);
      expressionAttributeValues.put(":h", AttributeValue.builder().s(homePageUrl).build());
      updateExpressionList.add(String.format("%s = :h",
            ApplicationRecord.HOME_PAGE_URL_ATTRIBUTE_NAME));
    }

    long newVersion = applicationRecord.getVersion() + 1;
    expressionAttributeValues.put(":nv", AttributeValue.builder()
          .n(Long.toString(newVersion))
          .build());
    updateExpressionList.add(String.format("%s = :nv",
          ApplicationRecord.VERSION_ATTRIBUTE_NAME));
    String updateExpression = String.format("SET %s", String.join(",", updateExpressionList));
    expressionAttributeValues.put(":v", AttributeValue.builder()
          .n(applicationRecord.getVersion().toString())
          .build());
    dynamodb.updateItem(UpdateItemRequest.builder()
          .tableName(tableName)
          .key(toKeyRecord(applicationId))
          .updateExpression(updateExpression)
          .expressionAttributeValues(expressionAttributeValues)
          .conditionExpression(String.format("%s = :v", ApplicationRecord.VERSION_ATTRIBUTE_NAME))
          .build());
    return modelMapper.map(applicationRecord, Application.class);
  }

  private ApplicationRecord loadApplication(final String applicationId) {
    Map<String, AttributeValue> applicationMap = dynamodb.getItem(GetItemRequest.builder()
          .tableName(tableName)
          .consistentRead(Boolean.TRUE)
          .key(toKeyRecord(applicationId))
          .build()).item();
    if (applicationMap == null || applicationMap.isEmpty()) {
      throw new NotFoundApiException(new NotFoundException()
            .errorCode("ApplicationNotFound")
            .message(String.format("Application %s can not be found.", applicationId)));
    }
    return new ApplicationRecord(applicationMap);
  }

  private Map<String, AttributeValue> toKeyRecord(final String applicationId) {
    return ApplicationRecord.builder()
          .userId(securityContext.getUserPrincipal().getName())
          .applicationId(applicationId)
          .build()
          .toAttributeMap();
  }
}

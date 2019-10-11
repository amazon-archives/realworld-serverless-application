package software.amazon.serverless.apprepo.api.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import software.amazon.serverless.apprepo.api.exception.BadRequestApiException;
import software.amazon.serverless.apprepo.api.exception.ConflictApiException;
import software.amazon.serverless.apprepo.api.exception.NotFoundApiException;
import software.amazon.serverless.apprepo.api.impl.pagination.InvalidTokenException;
import software.amazon.serverless.apprepo.api.impl.pagination.TokenSerializer;
import software.amazon.serverless.apprepo.container.config.ConfigProvider;
import io.swagger.model.Application;
import io.swagger.model.ApplicationList;
import io.swagger.model.ApplicationSummary;
import io.swagger.model.CreateApplicationInput;
import io.swagger.model.UpdateApplicationInput;

import java.security.Principal;
import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.core.SecurityContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class ApplicationsServiceTest {
  private static final String TABLE_NAME = "ApplicationsTable";

  @Mock
  private TokenSerializer<Map<String, AttributeValue>> tokenSerializer;
  @Mock
  private DynamoDbClient dynamodb;
  @Mock
  private ConfigProvider configProvider;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private Principal principal;
  @Mock
  private Clock clock;

  private ApplicationsService service;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(configProvider.getApplicationsTableName()).thenReturn(TABLE_NAME);
    when(securityContext.getUserPrincipal()).thenReturn(principal);
    service = new ApplicationsService(tokenSerializer, dynamodb, configProvider, clock);
    service.setSecurityContext(securityContext);
  }

  @Test
  public void getApplication_null_notFound() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    GetItemResponse response = GetItemResponse.builder().build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    assertThatThrownBy(() -> service.getApplication(applicationId))
          .isInstanceOf(NotFoundApiException.class);
  }

  @Test
  public void getApplication_empty_notFound() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    GetItemResponse response = GetItemResponse.builder().item(new HashMap<>()).build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    assertThatThrownBy(() -> service.getApplication(applicationId))
          .isInstanceOf(NotFoundApiException.class);
  }

  @Test
  public void getApplication() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);
    GetItemResponse response = GetItemResponse.builder()
          .item(recordMap)
          .build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    GetItemRequest expectedGetItemRequest = GetItemRequest.builder()
          .tableName(TABLE_NAME)
          .consistentRead(Boolean.TRUE)
          .key(recordMap)
          .build();

    Application application = service.getApplication(applicationId);
    ArgumentCaptor<GetItemRequest> getItemRequestArgumentCaptor = ArgumentCaptor.forClass(GetItemRequest.class);
    verify(dynamodb).getItem(getItemRequestArgumentCaptor.capture());

    assertThat(application.getApplicationId()).isEqualTo(applicationId);
    assertThat(getItemRequestArgumentCaptor.getValue()).isEqualTo(expectedGetItemRequest);
  }

  @Test
  public void listApplications() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();

    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);

    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":u", AttributeValue.builder()
          .s(userId)
          .build());
    QueryRequest expectedQueryRequest = QueryRequest.builder()
          .consistentRead(true)
          .tableName(TABLE_NAME)
          .keyConditionExpression(String.format("%s = :u",
                ApplicationRecord.USER_ID_ATTRIBUTE_NAME))
          .expressionAttributeValues(expressionAttributeValues)
          .limit(ApplicationsService.DEFAULT_LIST_APPLICATIONS_LIMIT)
          .build();

    QueryResponse queryResponse = QueryResponse.builder()
          .items(Collections.singletonList(recordMap))
          .build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.query(any(QueryRequest.class))).thenReturn(queryResponse);

    ApplicationList applicationList = service.listApplications(null, null);
    List<ApplicationSummary> applicationSummaries = applicationList.getApplications();

    ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(dynamodb).query(queryRequestArgumentCaptor.capture());
    assertThat(queryRequestArgumentCaptor.getValue()).isEqualTo(expectedQueryRequest);
    assertThat(applicationSummaries.get(0).getApplicationId()).isEqualTo(applicationId);
    assertThat(applicationList.getNextToken()).isNull();
  }

  @Test
  public void listApplications_maxItems() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    Integer maxItems = ApplicationsService.DEFAULT_LIST_APPLICATIONS_LIMIT * 2;

    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);

    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":u", AttributeValue.builder()
          .s(userId)
          .build());
    QueryRequest expectedQueryRequest = QueryRequest.builder()
          .consistentRead(true)
          .tableName(TABLE_NAME)
          .keyConditionExpression(String.format("%s = :u",
                ApplicationRecord.USER_ID_ATTRIBUTE_NAME))
          .expressionAttributeValues(expressionAttributeValues)
          .limit(maxItems)
          .build();

    QueryResponse queryResponse = QueryResponse.builder()
          .items(Collections.singletonList(recordMap))
          .build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.query(any(QueryRequest.class))).thenReturn(queryResponse);

    ApplicationList applicationList = service.listApplications(null, maxItems);
    List<ApplicationSummary> applicationSummaries = applicationList.getApplications();

    ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(dynamodb).query(queryRequestArgumentCaptor.capture());
    assertThat(queryRequestArgumentCaptor.getValue()).isEqualTo(expectedQueryRequest);
    assertThat(applicationSummaries.get(0).getApplicationId()).isEqualTo(applicationId);
    assertThat(applicationList.getNextToken()).isNull();
  }

  @Test
  public void listApplications_nextToken() throws Exception {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    String nextToken = UUID.randomUUID().toString();

    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);

    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":u", AttributeValue.builder()
          .s(userId)
          .build());
    QueryRequest expectedQueryRequest = QueryRequest.builder()
          .consistentRead(true)
          .tableName(TABLE_NAME)
          .keyConditionExpression(String.format("%s = :u",
                ApplicationRecord.USER_ID_ATTRIBUTE_NAME))
          .expressionAttributeValues(expressionAttributeValues)
          .limit(ApplicationsService.DEFAULT_LIST_APPLICATIONS_LIMIT)
          .exclusiveStartKey(recordMap)
          .build();

    QueryResponse queryResponse = QueryResponse.builder()
          .items(Collections.singletonList(recordMap))
          .lastEvaluatedKey(expressionAttributeValues)
          .build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.query(any(QueryRequest.class))).thenReturn(queryResponse);
    when(tokenSerializer.deserialize(nextToken)).thenReturn(recordMap);
    when(tokenSerializer.serialize(expressionAttributeValues)).thenReturn(nextToken);

    ApplicationList applicationList = service.listApplications(nextToken, null);
    List<ApplicationSummary> applicationSummaries = applicationList.getApplications();

    ArgumentCaptor<QueryRequest> queryRequestArgumentCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(dynamodb).query(queryRequestArgumentCaptor.capture());
    verify(tokenSerializer).deserialize(nextToken);
    verify(tokenSerializer).serialize(expressionAttributeValues);
    assertThat(queryRequestArgumentCaptor.getValue()).isEqualTo(expectedQueryRequest);
    assertThat(applicationSummaries.get(0).getApplicationId()).isEqualTo(applicationId);
    assertThat(applicationList.getNextToken()).isEqualTo(nextToken);
  }


  @Test
  public void listApplications_nextToken_exception() throws Exception {
    String userId = UUID.randomUUID().toString();
    String nextToken = UUID.randomUUID().toString();

    when(principal.getName()).thenReturn(userId);
    Mockito.doThrow(InvalidTokenException.class).when(tokenSerializer).deserialize(nextToken);

    assertThatThrownBy(() -> service.listApplications(nextToken, null))
          .isInstanceOf(BadRequestApiException.class);
  }

  @Test
  public void deleteApplication() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    Long version = 1L;
    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);
    recordMap.put("version", AttributeValue.builder().n(version.toString()).build());
    GetItemResponse response = GetItemResponse.builder()
          .item(recordMap)
          .build();
    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    Map<String, AttributeValue> expectedAttributeValueMap = new HashMap<>();
    expectedAttributeValueMap.put(":v", AttributeValue.builder().n(version.toString()).build());
    DeleteItemRequest expectedDeleteItemRequest = DeleteItemRequest.builder()
          .tableName(TABLE_NAME)
          .key(keyMap(userId, applicationId))
          .conditionExpression("version = :v")
          .expressionAttributeValues(expectedAttributeValueMap)
          .build();

    service.deleteApplication(applicationId);
    ArgumentCaptor<DeleteItemRequest> deleteItemRequestArgumentCaptor = ArgumentCaptor.forClass(DeleteItemRequest.class);
    verify(dynamodb).deleteItem(deleteItemRequestArgumentCaptor.capture());

    DeleteItemRequest deleteItemRequest = deleteItemRequestArgumentCaptor.getValue();
    assertThat(deleteItemRequest).isEqualTo(expectedDeleteItemRequest);
  }

  @Test
  public void createApplication_alreadyExist() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    CreateApplicationInput input = new CreateApplicationInput()
          .applicationId(applicationId);
    when(principal.getName()).thenReturn(userId);
    doThrow(ConditionalCheckFailedException.class).when(dynamodb).putItem(any(PutItemRequest.class));

    assertThatThrownBy(() -> service.createApplication(input))
          .isInstanceOf(ConflictApiException.class);
  }

  @Test
  public void createApplication() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    Instant createdAt = Instant.now();
    CreateApplicationInput input = new CreateApplicationInput()
          .applicationId(applicationId);
    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);
    recordMap.put("version", AttributeValue.builder().n("1").build());
    recordMap.put("createdAt", AttributeValue.builder().s(createdAt.toString()).build());

    when(principal.getName()).thenReturn(userId);
    when(clock.instant()).thenReturn(createdAt);

    PutItemRequest expectedPutItemRequest = PutItemRequest.builder()
          .tableName(TABLE_NAME)
          .item(recordMap)
          .conditionExpression("attribute_not_exists(userId) AND attribute_not_exists(applicationId)")
          .build();

    Application application = service.createApplication(input);

    ArgumentCaptor<PutItemRequest> putItemRequestArgumentCaptor = ArgumentCaptor.forClass(PutItemRequest.class);
    verify(dynamodb).putItem(putItemRequestArgumentCaptor.capture());

    assertThat(application.getApplicationId()).isEqualTo(applicationId);
    assertThat(putItemRequestArgumentCaptor.getValue()).isEqualTo(expectedPutItemRequest);
  }


  @Test
  public void updateApplication_noUpdate() {
    String applicationId = UUID.randomUUID().toString();
    assertThatThrownBy(() -> service.updateApplication(new UpdateApplicationInput(), applicationId))
          .isInstanceOf(BadRequestApiException.class);
  }

  @Test
  public void updateApplication_author() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    String author = UUID.randomUUID().toString();
    Long version = 1L;
    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);
    recordMap.put("version", AttributeValue.builder().n(version.toString()).build());
    GetItemResponse response = GetItemResponse.builder()
          .item(recordMap)
          .build();
    Map<String, AttributeValue> updateMap = new HashMap<>();
    updateMap.put(":nv", AttributeValue.builder().n("2").build());
    updateMap.put(":v", AttributeValue.builder().n("1").build());
    updateMap.put(":a", AttributeValue.builder().s(author).build());

    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    UpdateApplicationInput input = new UpdateApplicationInput().author(author);
    UpdateItemRequest expectedUpdateItemRequest = UpdateItemRequest.builder()
          .tableName(TABLE_NAME)
          .key(keyMap(userId, applicationId))
          .updateExpression("SET author = :a,version = :nv")
          .expressionAttributeValues(updateMap)
          .conditionExpression("version = :v")
          .build();

    Application application = service.updateApplication(input, applicationId);
    ArgumentCaptor<UpdateItemRequest> updateItemRequestArgumentCaptor = ArgumentCaptor.forClass(UpdateItemRequest.class);
    verify(dynamodb).updateItem(updateItemRequestArgumentCaptor.capture());

    UpdateItemRequest updateItemRequest = updateItemRequestArgumentCaptor.getValue();
    assertThat(updateItemRequest).isEqualTo(expectedUpdateItemRequest);
    assertThat(application.getApplicationId()).isEqualTo(applicationId);
    assertThat(application.getAuthor()).isEqualTo(author);
  }

  @Test
  public void updateApplication_description() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    String description = UUID.randomUUID().toString();
    Long version = 1L;
    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);
    recordMap.put("version", AttributeValue.builder().n(version.toString()).build());
    GetItemResponse response = GetItemResponse.builder()
          .item(recordMap)
          .build();
    Map<String, AttributeValue> updateMap = new HashMap<>();
    updateMap.put(":nv", AttributeValue.builder().n("2").build());
    updateMap.put(":v", AttributeValue.builder().n("1").build());
    updateMap.put(":d", AttributeValue.builder().s(description).build());

    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    UpdateApplicationInput input = new UpdateApplicationInput().description(description);
    UpdateItemRequest expectedUpdateItemRequest = UpdateItemRequest.builder()
          .tableName(TABLE_NAME)
          .key(keyMap(userId, applicationId))
          .updateExpression("SET description = :d,version = :nv")
          .expressionAttributeValues(updateMap)
          .conditionExpression("version = :v")
          .build();

    Application application = service.updateApplication(input, applicationId);
    ArgumentCaptor<UpdateItemRequest> updateItemRequestArgumentCaptor = ArgumentCaptor.forClass(UpdateItemRequest.class);
    verify(dynamodb).updateItem(updateItemRequestArgumentCaptor.capture());

    UpdateItemRequest updateItemRequest = updateItemRequestArgumentCaptor.getValue();
    assertThat(updateItemRequest).isEqualTo(expectedUpdateItemRequest);
    assertThat(application.getApplicationId()).isEqualTo(applicationId);
    assertThat(application.getDescription()).isEqualTo(description);
  }

  @Test
  public void updateApplication_homePageUrl() {
    String userId = UUID.randomUUID().toString();
    String applicationId = UUID.randomUUID().toString();
    String homePageUrl = UUID.randomUUID().toString();
    Long version = 1L;
    Map<String, AttributeValue> recordMap = keyMap(userId, applicationId);
    recordMap.put("version", AttributeValue.builder().n(version.toString()).build());
    GetItemResponse response = GetItemResponse.builder()
          .item(recordMap)
          .build();
    Map<String, AttributeValue> updateMap = new HashMap<>();
    updateMap.put(":nv", AttributeValue.builder().n("2").build());
    updateMap.put(":v", AttributeValue.builder().n("1").build());
    updateMap.put(":h", AttributeValue.builder().s(homePageUrl).build());

    when(principal.getName()).thenReturn(userId);
    when(dynamodb.getItem(any(GetItemRequest.class))).thenReturn(response);

    UpdateApplicationInput input = new UpdateApplicationInput().homePageUrl(homePageUrl);
    UpdateItemRequest expectedUpdateItemRequest = UpdateItemRequest.builder()
          .tableName(TABLE_NAME)
          .key(keyMap(userId, applicationId))
          .updateExpression("SET homePageUrl = :h,version = :nv")
          .expressionAttributeValues(updateMap)
          .conditionExpression("version = :v")
          .build();

    Application application = service.updateApplication(input, applicationId);
    ArgumentCaptor<UpdateItemRequest> updateItemRequestArgumentCaptor = ArgumentCaptor.forClass(UpdateItemRequest.class);
    verify(dynamodb).updateItem(updateItemRequestArgumentCaptor.capture());

    UpdateItemRequest updateItemRequest = updateItemRequestArgumentCaptor.getValue();
    assertThat(updateItemRequest).isEqualTo(expectedUpdateItemRequest);
    assertThat(application.getApplicationId()).isEqualTo(applicationId);
    assertThat(application.getHomePageUrl()).isEqualTo(homePageUrl);
  }

  private Map<String, AttributeValue> keyMap(final String userId, final String applicationId) {
    Map<String, AttributeValue> keyMap = new HashMap<>();
    keyMap.put("userId", AttributeValue.builder().s(userId).build());
    keyMap.put("applicationId", AttributeValue.builder().s(applicationId).build());
    return keyMap;
  }

}

package com.amazonaws.serverless.apprepo.api.impl;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * ApplicationRecord represents a record in Applications DynamoDB table.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRecord {
  public static final String USER_ID_ATTRIBUTE_NAME = "userId";
  public static final String APPLICATION_ID_ATTRIBUTE_NAME = "applicationId";
  public static final String CREATED_AT_ATTRIBUTE_NAME = "createdAt";
  public static final String AUTHOR_ATTRIBUTE_NAME = "author";
  public static final String HOME_PAGE_URL_ATTRIBUTE_NAME = "homePageUrl";
  public static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
  public static final String VERSION_ATTRIBUTE_NAME = "version";
  public static final String TTL_ATTRIBUTE_NAME = "ttl";

  private String userId;
  private String applicationId;
  private Instant createdAt;
  private String author;
  private String homePageUrl;
  private String description;
  private Long version;
  private Long ttl;

  /**
   * Construct the record from a map of DynamoDB {@link AttributeValue}.
   *
   * @param record a map of DynamoDB {@link AttributeValue}
   */
  public ApplicationRecord(final Map<String, AttributeValue> record) {
    if (record.containsKey(USER_ID_ATTRIBUTE_NAME)) {
      this.userId = record.get(USER_ID_ATTRIBUTE_NAME).s();
    }
    if (record.containsKey(APPLICATION_ID_ATTRIBUTE_NAME)) {
      this.applicationId = record.get(APPLICATION_ID_ATTRIBUTE_NAME).s();
    }
    if (record.containsKey(AUTHOR_ATTRIBUTE_NAME)) {
      this.author = record.get(AUTHOR_ATTRIBUTE_NAME).s();
    }
    if (record.containsKey(CREATED_AT_ATTRIBUTE_NAME)) {
      this.createdAt = Instant.parse(record.get(CREATED_AT_ATTRIBUTE_NAME).s());
    }
    if (record.containsKey(DESCRIPTION_ATTRIBUTE_NAME)) {
      this.description = record.get(DESCRIPTION_ATTRIBUTE_NAME).s();
    }
    if (record.containsKey(HOME_PAGE_URL_ATTRIBUTE_NAME)) {
      this.homePageUrl = record.get(HOME_PAGE_URL_ATTRIBUTE_NAME).s();
    }
    if (record.containsKey(TTL_ATTRIBUTE_NAME)) {
      this.ttl = Long.parseLong(record.get(TTL_ATTRIBUTE_NAME).n());
    }
    if (record.containsKey(VERSION_ATTRIBUTE_NAME)) {
      this.version = Long.parseLong(record.get(VERSION_ATTRIBUTE_NAME).n());
    }
  }

  /**
   * Convert ApplicationRecord to a map of DynamoDB {@link AttributeValue}.
   *
   * @return a map of DynamoDB {@link AttributeValue}
   */
  public Map<String, AttributeValue> toAttributeMap() {
    Map<String, AttributeValue> applicationMap = new HashMap<>();
    if (userId != null) {
      applicationMap.put(USER_ID_ATTRIBUTE_NAME, AttributeValue.builder().s(userId).build());
    }
    if (applicationId != null) {
      applicationMap.put(APPLICATION_ID_ATTRIBUTE_NAME,
            AttributeValue.builder().s(applicationId).build());
    }
    if (author != null) {
      applicationMap.put(AUTHOR_ATTRIBUTE_NAME,
            AttributeValue.builder().s(author).build());
    }
    if (createdAt != null) {
      applicationMap.put(CREATED_AT_ATTRIBUTE_NAME,
            AttributeValue.builder().s(createdAt.toString()).build());
    }
    if (description != null) {
      applicationMap.put(DESCRIPTION_ATTRIBUTE_NAME,
            AttributeValue.builder().s(description).build());
    }
    if (homePageUrl != null) {
      applicationMap.put(HOME_PAGE_URL_ATTRIBUTE_NAME,
            AttributeValue.builder().s(homePageUrl).build());
    }
    if (ttl != null) {
      applicationMap.put(TTL_ATTRIBUTE_NAME,
            AttributeValue.builder().n(ttl.toString()).build());
    }
    if (version != null) {
      applicationMap.put(VERSION_ATTRIBUTE_NAME,
            AttributeValue.builder().n(version.toString()).build());
    }
    return applicationMap;
  }
}

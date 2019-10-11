package com.amazonaws.serverless.apprepo.api.impl.pagination;

import com.amazonaws.serverless.apprepo.container.config.ConfigProvider;

import java.util.Map;
import javax.inject.Inject;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.kms.KmsClient;

/**
 * Implementation of {@link TokenSerializer} to serialize/deserialize
 * pagination token for List APIs.
 *
 * <p>It chians {@link DynamoDbStartKeySerializer}, {@link TimeBasedTokenSerializer}
 * and {@link EncryptedTokenSerializer} to serialize/deserialize a DynamoDb start key
 * to a String token that is URL friendly.
 *
 * <p>Serialize flow:
 * DynamoDb start key -> Json string -> Json String with TTL -> base64 encoded cipher text (Token)
 *
 * <p>Deserialize flow:
 * Token -> Base64 decoded plaintext -> Json String with TTL -> Json String -> DynamoDb start key
 */
public class PaginationTokenSerializer implements TokenSerializer<Map<String, AttributeValue>> {
  private final TokenSerializer<Map<String, AttributeValue>> dynamoDbStartKeySerializer;
  private final TokenSerializer<String> timeBasedTokenSerializer;
  private final TokenSerializer<String> encryptedTokenSerializer;

  /**
   * Construct PaginationTokenSerializer from KmsClient and ConfigProvider.
   *
   * @param kms            KmsClient for token encryption and decryption.
   * @param configProvider ConfigProvider to provide configuration values.
   */
  @Inject
  public PaginationTokenSerializer(final KmsClient kms, final ConfigProvider configProvider) {
    this.dynamoDbStartKeySerializer = new DynamoDbStartKeySerializer();
    this.timeBasedTokenSerializer = new TimeBasedTokenSerializer(
          configProvider.getPaginationTokenTtl());
    this.encryptedTokenSerializer = new EncryptedTokenSerializer(
          kms, configProvider.getKmsKeyId());
  }

  @Override
  public Map<String, AttributeValue> deserialize(final String token)
        throws InvalidTokenException {
    String plaintext = encryptedTokenSerializer.deserialize(token);
    String json = timeBasedTokenSerializer.deserialize(plaintext);
    return dynamoDbStartKeySerializer.deserialize(json);
  }

  @Override
  public String serialize(final Map<String, AttributeValue> startKey) {
    String json = dynamoDbStartKeySerializer.serialize(startKey);
    String jsonWithTtl = timeBasedTokenSerializer.serialize(json);
    return encryptedTokenSerializer.serialize(jsonWithTtl);
  }
}

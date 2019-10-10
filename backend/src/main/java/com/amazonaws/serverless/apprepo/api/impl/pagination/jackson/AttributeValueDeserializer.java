package com.amazonaws.serverless.apprepo.api.impl.pagination.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Jackson Json Deserializer for {@link AttributeValue}.
 */
public class AttributeValueDeserializer extends JsonDeserializer<AttributeValue> {

  @Override
  public AttributeValue deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);

    AttributeValue.Builder result = AttributeValue.builder();
    JsonNode value;
    value = node.get("s");
    if (value != null) {
      result.s(value.asText());
      return result.build();
    }

    value = node.get("n");
    if (value != null) {
      result.n(Long.toString(value.asLong()));
      return result.build();
    }

    value = node.get("b");
    if (value != null) {
      result.b(SdkBytes.fromUtf8String(value.asText()));
      return result.build();
    }

    value = node.get("ss");
    if (value != null) {
      result.ss(oc.treeToValue(value, List.class));
      return result.build();
    }

    value = node.get("ns");
    if (value != null) {
      result.ns(oc.treeToValue(value, List.class));
      return result.build();
    }

    value = node.get("bs");
    if (value != null) {
      Iterator<JsonNode> iterator = value.iterator();
      List<SdkBytes> values = new ArrayList<>();
      while (iterator.hasNext()) {
        values.add(oc.treeToValue(iterator.next(), SdkBytes.class));
      }
      result.bs(values);
      return result.build();
    }

    value = node.get("m");
    if (value != null) {
      Iterator<String> iterator = value.fieldNames();
      Map<String, AttributeValue> values = new HashMap<>();
      while (iterator.hasNext()) {
        String fieldName = iterator.next();
        values.put(fieldName, oc.treeToValue(value.get(fieldName), AttributeValue.class));
      }
      result.m(values);
      return result.build();
    }

    value = node.get("l");
    if (value != null) {
      Iterator<JsonNode> iterator = value.iterator();
      List<AttributeValue> values = new ArrayList<>();
      while (iterator.hasNext()) {
        values.add(oc.treeToValue(iterator.next(), AttributeValue.class));
      }
      result.l(values);
      return result.build();
    }

    value = node.get("bool");
    if (value != null) {
      result.bool(value.asBoolean());
      return result.build();
    }

    value = node.get("nul");
    if (value != null) {
      result.nul(value.asBoolean());
      return result.build();
    }

    return result.build();
  }
}

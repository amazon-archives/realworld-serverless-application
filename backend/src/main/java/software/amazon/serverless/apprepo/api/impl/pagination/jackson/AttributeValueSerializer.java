package software.amazon.serverless.apprepo.api.impl.pagination.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Jackson Json Serializer for {@link AttributeValue}.
 */
public class AttributeValueSerializer extends JsonSerializer<AttributeValue> {
  @Override
  public void serialize(AttributeValue attributeValue, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    if (attributeValue.s() != null) {
      jsonGenerator.writeStringField("s", attributeValue.s());
      jsonGenerator.writeEndObject();
      return;
    }

    if (attributeValue.n() != null) {
      jsonGenerator.writeStringField("n", attributeValue.n());
      jsonGenerator.writeEndObject();
      return;
    }

    if (attributeValue.b() != null) {
      jsonGenerator.writeStringField("b", attributeValue.b().asUtf8String());
      jsonGenerator.writeEndObject();
      return;
    }

    List<String> ss = attributeValue.ss();
    if (ss != null && !ss.isEmpty()) {
      jsonGenerator.writeObjectField("ss", attributeValue.ss());
      jsonGenerator.writeEndObject();
      return;
    }

    List<String> ns = attributeValue.ns();
    if (ns != null && !ns.isEmpty()) {
      jsonGenerator.writeObjectField("ns", attributeValue.ns());
      jsonGenerator.writeEndObject();
      return;
    }

    List<SdkBytes> bs = attributeValue.bs();
    if (bs != null && !bs.isEmpty()) {
      jsonGenerator.writeObjectField("bs", attributeValue.bs());
      jsonGenerator.writeEndObject();
      return;
    }

    Map<String, AttributeValue> mapValue = attributeValue.m();
    if (mapValue != null && !mapValue.isEmpty()) {
      jsonGenerator.writeObjectField("m", attributeValue.m());
      jsonGenerator.writeEndObject();
      return;
    }

    List<AttributeValue> attributeValueList = attributeValue.l();
    if (attributeValueList != null && !attributeValueList.isEmpty()) {
      jsonGenerator.writeArrayFieldStart("l");
      for (AttributeValue s : attributeValue.l()) {
        jsonGenerator.writeObject(s);
      }
      jsonGenerator.writeEndArray();
      jsonGenerator.writeEndObject();
      return;
    }

    if (attributeValue.bool() != null) {
      jsonGenerator.writeBooleanField("bool", attributeValue.bool());
      jsonGenerator.writeEndObject();
      return;
    }

    if (attributeValue.nul() != null) {
      jsonGenerator.writeBooleanField("nul", attributeValue.nul());
      jsonGenerator.writeEndObject();
    }
  }
}

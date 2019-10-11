package software.amazon.serverless.apprepo.api.impl.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DynamoDbStartKeySerializerTest {
  private DynamoDbStartKeySerializer serializer;

  @Before
  public void setup() {
    serializer = new DynamoDbStartKeySerializer();
  }

  @Test
  public void testString() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("String", AttributeValue.builder().s("1.0.0").build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testNumber() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("Number", AttributeValue.builder().n("100").build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testByte() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("Byte", AttributeValue.builder().b(SdkBytes.fromUtf8String("test")).build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testStringList() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("StringList", AttributeValue.builder().ss("test").build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testNumberList() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("NumberList", AttributeValue.builder().ns("100").build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testByteList() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("ByteList", AttributeValue.builder().bs(SdkBytes.fromUtf8String("test")).build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testAttributeList() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("AttributeList", AttributeValue.builder().l(AttributeValue.builder().s("test").build()).build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testMap() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    Map<String, AttributeValue> nested = new HashMap<>();
    nested.put("Map", AttributeValue.builder().s("test").build());
    startKey.put("AttributeList", AttributeValue.builder().m(nested).build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testBool() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("AttributeList", AttributeValue.builder().bool(true).build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void testNul() throws Exception {
    Map<String, AttributeValue> startKey = new HashMap<>();
    startKey.put("AttributeList", AttributeValue.builder().nul(false).build());
    String startKeyString = serializer.serialize(startKey);
    assertThat(serializer.deserialize(startKeyString))
          .isEqualTo(startKey);
  }

  @Test
  public void deserialize_nonMap() {
    assertThatThrownBy(() -> serializer.deserialize("something"))
          .isInstanceOf(InvalidTokenException.class);
  }

  @Test
  public void deserialize_blankString() {
    assertThatThrownBy(() -> serializer.deserialize(" "))
          .isInstanceOf(InvalidTokenException.class);
  }
}

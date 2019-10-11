package software.amazon.serverless.apprepo.api.impl.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class TimeBasedTokenSerializerTest {
  private final static long TOKEN_TTL_IN_SECOND = 3600;
  private TokenSerializer<String> serializer;

  @Before
  public void setup() {
    serializer = new TimeBasedTokenSerializer(Duration.ofSeconds(TOKEN_TTL_IN_SECOND));
  }

  @Test
  public void testSerializeAndDeserialize() throws Exception {
    String plainToken = "something";
    String encodedToken = serializer.serialize(plainToken);
    assertThat(serializer.deserialize(encodedToken))
          .isEqualTo(plainToken);
  }

  @Test
  public void deserialize_noTimestamp() {
    assertThatThrownBy(() -> serializer.deserialize("something"))
          .isInstanceOf(InvalidTokenException.class);
  }

  @Test
  public void deserialize_timestampNonBase64() {
    assertThatThrownBy(() -> serializer.deserialize("&something"))
          .isInstanceOf(InvalidTokenException.class);
  }

  @Test
  public void deserialize_tokenExpired() {
    Instant expiredInstant = Instant.now().minusSeconds(TOKEN_TTL_IN_SECOND + 1);
    assertThatThrownBy(() -> serializer.deserialize("something&" + expiredInstant.toString()))
          .isInstanceOf(InvalidTokenException.class);
  }

  @Test
  public void deserialize_noToken() {
    assertThatThrownBy(() -> serializer.deserialize("&" + Instant.now().toString()))
          .isInstanceOf(InvalidTokenException.class);
  }
}

package com.amazonaws.serverless.apprepo.api.impl.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;

public class EncryptedTokenSerializerTest {
  private static final String KEY_ID = UUID.randomUUID().toString();

  @Mock
  private KmsClient kms;
  private TokenSerializer<String> serializer;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    serializer = new EncryptedTokenSerializer(kms, KEY_ID);
  }

  @Test
  public void testSerializeAndDeserialize() throws Exception {
    String plainText = "cat";
    String cipherText = "dog";
    EncryptResponse encryptResponse = EncryptResponse.builder()
          .ciphertextBlob(SdkBytes.fromUtf8String(cipherText))
          .build();
    DecryptResponse decryptResponse = DecryptResponse.builder()
          .plaintext(SdkBytes.fromUtf8String(plainText))
          .build();
    when(kms.encrypt(any(EncryptRequest.class))).thenReturn(encryptResponse);
    when(kms.decrypt(any(DecryptRequest.class))).thenReturn(decryptResponse);

    String encodedToken = serializer.serialize(plainText);
    assertThat(serializer.deserialize(encodedToken))
          .isEqualTo(plainText);
  }

  @Test
  public void deserialize_nonBase64() {
    assertThatThrownBy(() -> serializer.deserialize("something"))
          .isInstanceOf(InvalidTokenException.class);
  }
}

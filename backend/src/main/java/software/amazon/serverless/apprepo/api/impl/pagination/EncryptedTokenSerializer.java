package software.amazon.serverless.apprepo.api.impl.pagination;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.InvalidCiphertextException;

/**
 * Implementation of {@link TokenSerializer} that encrypts and decrypts the token
 * using KMS as well as encode and decode the token using Base64.
 *
 * <p>Note, this class uses KMS to encrypt and decrypt directly. KMS encrypt only allows plain text
 * that is smaller than 4096 bytes. If your plain text is larger than 4096 bytes, use envelope
 * encryption with KMS instead. See doc: https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#enveloping.
 */
@RequiredArgsConstructor
public class EncryptedTokenSerializer implements TokenSerializer<String> {
  private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
  private final KmsClient kms;
  private final String keyId;

  @Override
  public String deserialize(final String encodedStartKey) throws InvalidTokenException {
    try {
      return kms.decrypt(DecryptRequest.builder()
            .ciphertextBlob(SdkBytes.fromByteArray(base64Decode(encodedStartKey)))
            .build())
            .plaintext()
            .asUtf8String();
    } catch (InvalidCiphertextException e) {
      throw new InvalidTokenException("Failed to decrypt token:" + encodedStartKey, e);
    }
  }

  @Override
  public String serialize(final String startKey) {
    byte[] plainText = startKey.getBytes(DEFAULT_ENCODING);
    byte[] cipherText = kms.encrypt(EncryptRequest.builder()
          .plaintext(SdkBytes.fromByteArray(plainText))
          .keyId(keyId)
          .build())
          .ciphertextBlob()
          .asByteArray();
    return base64Encode(cipherText);
  }

  private String base64Encode(final byte[] token) {
    // Using UrlEncoder to avoid url unfriendly character in next token.
    return new String(Base64.getUrlEncoder().encode(token), DEFAULT_ENCODING);
  }

  private byte[] base64Decode(final String encodedToken) throws InvalidTokenException {
    if (StringUtils.isBlank(encodedToken)) {
      throw new InvalidTokenException("The token is blank.");
    }
    try {
      return Base64.getUrlDecoder().decode(encodedToken.getBytes(DEFAULT_ENCODING));
    } catch (IllegalArgumentException e) {
      throw new InvalidTokenException(String.format(
            "Failed to base64 decode token %s", encodedToken), e);
    }
  }
}

package com.amazonaws.serverless.apprepo.api.impl.pagination;

/**
 * Serialize/deserialize a token to/from a String.
 *
 * @param <T> type of the token
 */
public interface TokenSerializer<T> {
  /**
   * Deserialize the token into type T.
   *
   * @param tokenString token in String.
   * @return deserialized token.
   * @throws InvalidTokenException throws when the token in String is invalid.
   */
  T deserialize(String tokenString) throws InvalidTokenException;

  /**
   * Serialize the token into a String.
   *
   * @param token the token to be serialized.
   * @return serialized token in String.
   */
  String serialize(T token);
}

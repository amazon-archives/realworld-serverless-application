package com.amazonaws.serverless.apprepo.api.impl.pagination;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of {@link TokenSerializer} that adds time information
 * to ensure the token will expire.
 */
@RequiredArgsConstructor
public class TimeBasedTokenSerializer implements TokenSerializer<String> {
  static final String TIMESTAMP_DEMILITER = "&";

  private final Duration ttl;

  @Override
  public String deserialize(final String token) throws InvalidTokenException {
    validateTimestamp(token);
    String decodedToken = StringUtils.substringBeforeLast(token, TIMESTAMP_DEMILITER);
    if (StringUtils.isBlank(decodedToken)) {
      throw new InvalidTokenException("The token is blank.");
    }
    return decodedToken;
  }

  @Override
  public String serialize(final String token) {
    StringBuilder tokenBuilder = new StringBuilder(token);
    tokenBuilder.append(TIMESTAMP_DEMILITER);
    tokenBuilder.append(Instant.now().toString());
    return tokenBuilder.toString();
  }

  private void validateTimestamp(final String tokenString) throws InvalidTokenException {
    String timestampString = StringUtils.substringAfterLast(tokenString, TIMESTAMP_DEMILITER);
    Instant timestamp;
    try {
      timestamp = Instant.parse(timestampString);
    } catch (DateTimeParseException e) {
      throw new InvalidTokenException(
            String.format("Invalid timestamp string %s in token.", timestampString), e);
    }

    if (timestamp.plus(ttl).isBefore(Instant.now())) {
      throw new InvalidTokenException(
            String.format("Token %s has expired after timeout limit %s.", timestamp, ttl));
    }
  }
}

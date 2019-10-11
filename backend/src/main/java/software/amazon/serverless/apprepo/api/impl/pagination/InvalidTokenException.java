package software.amazon.serverless.apprepo.api.impl.pagination;

/**
 * Exception for invalid token.
 */
public class InvalidTokenException extends Exception {
  public InvalidTokenException(final String message) {
    super(message);
  }

  public InvalidTokenException(final String message, final Exception exception) {
    super(message, exception);
  }
}

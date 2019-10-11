package software.amazon.serverless.apprepo.api.exception;

import io.swagger.model.ConflictException;

import javax.ws.rs.core.Response;

/**
 * Conflict Api exception.
 */
public class ConflictApiException extends ApiException {
  public ConflictApiException(final ConflictException exception) {
    super(exception, Response.Status.CONFLICT);
  }
}

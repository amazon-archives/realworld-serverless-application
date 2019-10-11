package software.amazon.serverless.apprepo.api.exception;

import io.swagger.model.InternalServerErrorException;

import javax.ws.rs.core.Response;

/**
 * Internal Server Api Exception.
 */
public class InternalServerApiException extends ApiException {
  /**
   * Construct an InternalServerApiException that converts
   * to 500 Interal Server Error response.
   */
  public InternalServerApiException() {
    super(new InternalServerErrorException()
                .errorCode("InternalError")
                .message("Internal Server Error."),
          Response.Status.INTERNAL_SERVER_ERROR);
  }
}

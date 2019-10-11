package software.amazon.serverless.apprepo.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception mapper that maps throwables that are not {@link ApiException}
 * to {@link InternalServerApiException}.
 */
@Provider
@Slf4j
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(final Throwable throwable) {
    if (throwable instanceof ApiException) {
      log.info("4xx exception is thrown", throwable);
      ApiException apiException = (ApiException) throwable;
      return apiException.getResponse();
    } else {
      log.error("5xx exception is thrown", throwable);
      return new InternalServerApiException().getResponse();
    }
  }
}

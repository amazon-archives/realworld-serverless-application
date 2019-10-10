package com.amazonaws.serverless.apprepo.api.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * API exception that can be converted to JAX-RS {@link Response}.
 */
@Slf4j
public class ApiException extends WebApplicationException {
  /**
   * Construct an ApiException with body object and status code.
   *
   * @param exception  the exception object that will be deserialized to JSON string
   *                   as the response body.
   * @param statusCode response status code.
   */
  public ApiException(final Object exception, final Response.Status statusCode) {
    super(Response.status(statusCode)
          .entity(exception)
          .type(MediaType.APPLICATION_JSON)
          .build());
  }
}

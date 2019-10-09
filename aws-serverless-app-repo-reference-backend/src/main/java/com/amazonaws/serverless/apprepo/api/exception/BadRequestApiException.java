package com.amazonaws.serverless.apprepo.api.exception;

import io.swagger.model.BadRequestException;

import javax.ws.rs.core.Response;

/**
 * Bad Request Api exception.
 */
public class BadRequestApiException extends ApiException {
  public BadRequestApiException(final BadRequestException exception) {
    super(exception, Response.Status.BAD_REQUEST);
  }
}

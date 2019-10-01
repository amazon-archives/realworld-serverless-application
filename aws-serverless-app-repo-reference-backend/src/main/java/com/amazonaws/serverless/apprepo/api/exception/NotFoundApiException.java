package com.amazonaws.serverless.apprepo.api.exception;

import com.amazonaws.serverless.apprepo.api.model.NotFoundException;

import javax.ws.rs.core.Response;

/**
 * Not Found Api exception.
 */
public class NotFoundApiException extends ApiException {
  public NotFoundApiException(final NotFoundException exception) {
    super(exception, Response.Status.NOT_FOUND);
  }
}

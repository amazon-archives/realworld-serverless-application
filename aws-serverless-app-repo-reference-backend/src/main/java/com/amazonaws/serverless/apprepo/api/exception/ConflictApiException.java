package com.amazonaws.serverless.apprepo.api.exception;

import com.amazonaws.serverless.apprepo.api.model.ConflictException;

import javax.ws.rs.core.Response;

/**
 * Conflict Api exception.
 */
public class ConflictApiException extends ApiException {
  public ConflictApiException(final ConflictException exception) {
    super(exception, Response.Status.CONFLICT);
  }
}

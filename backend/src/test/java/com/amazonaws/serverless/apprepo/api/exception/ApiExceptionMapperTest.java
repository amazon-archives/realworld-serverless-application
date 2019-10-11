package com.amazonaws.serverless.apprepo.api.exception;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.model.InternalServerErrorException;
import io.swagger.model.NotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class ApiExceptionMapperTest {
  private ApiExceptionMapper exceptionMapper;

  @Before
  public void setup() {
    exceptionMapper = new ApiExceptionMapper();
  }

  @Test
  public void toResponse_apiException() {
    NotFoundException exception = new NotFoundException();
    Response response = exceptionMapper.toResponse(new NotFoundApiException(exception));
    assertThat(response.getStatus()).isEqualTo(404);
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    assertThat(response.getEntity()).isEqualTo(exception);
  }

  @Test
  public void toResponse_otherException() {
    InternalServerErrorException exception = new InternalServerErrorException()
          .errorCode("InternalError")
          .message("Internal Server Error.");
    Response response = exceptionMapper.toResponse(new IllegalArgumentException());
    assertThat(response.getStatus()).isEqualTo(500);
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    assertThat(response.getEntity()).isEqualTo(exception);
  }

}

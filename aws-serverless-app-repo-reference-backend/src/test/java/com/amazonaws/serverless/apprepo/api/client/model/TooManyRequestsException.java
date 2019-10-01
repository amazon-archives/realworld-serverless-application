/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model;

import javax.annotation.Generated;

/**
 *
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TooManyRequestsException extends AWSSarBackendException {
  private static final long serialVersionUID = 1L;

  private String errorCode;

  /**
   * Constructs a new TooManyRequestsException with the specified error message.
   *
   * @param message
   *        Describes the error encountered.
   */
  public TooManyRequestsException(String message) {
    super(message);
  }

  /**
   * @param errorCode
   */

  @com.fasterxml.jackson.annotation.JsonProperty("errorCode")
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @return
   */

  @com.fasterxml.jackson.annotation.JsonProperty("errorCode")
  public String getErrorCode() {
    return this.errorCode;
  }

  /**
   * @param errorCode
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public TooManyRequestsException errorCode(String errorCode) {
    setErrorCode(errorCode);
    return this;
  }

}

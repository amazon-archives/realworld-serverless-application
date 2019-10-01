/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model;

import com.amazonaws.auth.RequestSigner;
import com.amazonaws.opensdk.protect.auth.RequestSignerAware;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/UpdateApplication" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class UpdateApplicationRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable, RequestSignerAware {

  private String applicationId;

  private UpdateApplicationInput updateApplicationInput;

  /**
   * @param applicationId
   */

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  /**
   * @return
   */

  public String getApplicationId() {
    return this.applicationId;
  }

  /**
   * @param applicationId
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public UpdateApplicationRequest applicationId(String applicationId) {
    setApplicationId(applicationId);
    return this;
  }

  /**
   * @param updateApplicationInput
   */

  public void setUpdateApplicationInput(UpdateApplicationInput updateApplicationInput) {
    this.updateApplicationInput = updateApplicationInput;
  }

  /**
   * @return
   */

  public UpdateApplicationInput getUpdateApplicationInput() {
    return this.updateApplicationInput;
  }

  /**
   * @param updateApplicationInput
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public UpdateApplicationRequest updateApplicationInput(UpdateApplicationInput updateApplicationInput) {
    setUpdateApplicationInput(updateApplicationInput);
    return this;
  }

  /**
   * Returns a string representation of this object; useful for testing and debugging.
   *
   * @return A string representation of this object.
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    if (getApplicationId() != null)
      sb.append("ApplicationId: ").append(getApplicationId()).append(",");
    if (getUpdateApplicationInput() != null)
      sb.append("UpdateApplicationInput: ").append(getUpdateApplicationInput());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (obj instanceof UpdateApplicationRequest == false)
      return false;
    UpdateApplicationRequest other = (UpdateApplicationRequest) obj;
    if (other.getApplicationId() == null ^ this.getApplicationId() == null)
      return false;
    if (other.getApplicationId() != null && other.getApplicationId().equals(this.getApplicationId()) == false)
      return false;
    if (other.getUpdateApplicationInput() == null ^ this.getUpdateApplicationInput() == null)
      return false;
    if (other.getUpdateApplicationInput() != null && other.getUpdateApplicationInput().equals(this.getUpdateApplicationInput()) == false)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = 1;

    hashCode = prime * hashCode + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
    hashCode = prime * hashCode + ((getUpdateApplicationInput() == null) ? 0 : getUpdateApplicationInput().hashCode());
    return hashCode;
  }

  @Override
  public UpdateApplicationRequest clone() {
    return (UpdateApplicationRequest) super.clone();
  }

  @Override
  public Class<? extends RequestSigner> signerType() {
    return com.amazonaws.serverless.apprepo.api.client.auth.CognitoAuthorizer.class;
  }

  /**
   * Set the configuration for this request.
   *
   * @param sdkRequestConfig
   *        Request configuration.
   * @return This object for method chaining.
   */
  public UpdateApplicationRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
    super.sdkRequestConfig(sdkRequestConfig);
    return this;
  }

}

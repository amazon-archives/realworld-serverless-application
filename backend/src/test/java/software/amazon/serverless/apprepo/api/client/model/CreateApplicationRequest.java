/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model;

import com.amazonaws.auth.RequestSigner;
import com.amazonaws.opensdk.protect.auth.RequestSignerAware;

import java.io.Serializable;
import javax.annotation.Generated;

import software.amazon.serverless.apprepo.api.client.auth.CognitoAuthorizer;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/CreateApplication" target="_top">AWS
 *      API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class CreateApplicationRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable, RequestSignerAware {

  private CreateApplicationInput createApplicationInput;

  /**
   * @param createApplicationInput
   */

  public void setCreateApplicationInput(CreateApplicationInput createApplicationInput) {
    this.createApplicationInput = createApplicationInput;
  }

  /**
   * @return
   */

  public CreateApplicationInput getCreateApplicationInput() {
    return this.createApplicationInput;
  }

  /**
   * @param createApplicationInput
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public CreateApplicationRequest createApplicationInput(CreateApplicationInput createApplicationInput) {
    setCreateApplicationInput(createApplicationInput);
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
    if (getCreateApplicationInput() != null)
      sb.append("CreateApplicationInput: ").append(getCreateApplicationInput());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (obj instanceof CreateApplicationRequest == false)
      return false;
    CreateApplicationRequest other = (CreateApplicationRequest) obj;
    if (other.getCreateApplicationInput() == null ^ this.getCreateApplicationInput() == null)
      return false;
    if (other.getCreateApplicationInput() != null && other.getCreateApplicationInput().equals(this.getCreateApplicationInput()) == false)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = 1;

    hashCode = prime * hashCode + ((getCreateApplicationInput() == null) ? 0 : getCreateApplicationInput().hashCode());
    return hashCode;
  }

  @Override
  public CreateApplicationRequest clone() {
    return (CreateApplicationRequest) super.clone();
  }

  @Override
  public Class<? extends RequestSigner> signerType() {
    return CognitoAuthorizer.class;
  }

  /**
   * Set the configuration for this request.
   *
   * @param sdkRequestConfig
   *        Request configuration.
   * @return This object for method chaining.
   */
  public CreateApplicationRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
    super.sdkRequestConfig(sdkRequestConfig);
    return this;
  }

}

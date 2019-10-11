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
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/ListApplications" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ListApplicationsRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable, RequestSignerAware {

  private String maxItems;

  private String nextToken;

  /**
   * @param maxItems
   */

  public void setMaxItems(String maxItems) {
    this.maxItems = maxItems;
  }

  /**
   * @return
   */

  public String getMaxItems() {
    return this.maxItems;
  }

  /**
   * @param maxItems
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ListApplicationsRequest maxItems(String maxItems) {
    setMaxItems(maxItems);
    return this;
  }

  /**
   * @param nextToken
   */

  public void setNextToken(String nextToken) {
    this.nextToken = nextToken;
  }

  /**
   * @return
   */

  public String getNextToken() {
    return this.nextToken;
  }

  /**
   * @param nextToken
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ListApplicationsRequest nextToken(String nextToken) {
    setNextToken(nextToken);
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
    if (getMaxItems() != null)
      sb.append("MaxItems: ").append(getMaxItems()).append(",");
    if (getNextToken() != null)
      sb.append("NextToken: ").append(getNextToken());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (obj instanceof ListApplicationsRequest == false)
      return false;
    ListApplicationsRequest other = (ListApplicationsRequest) obj;
    if (other.getMaxItems() == null ^ this.getMaxItems() == null)
      return false;
    if (other.getMaxItems() != null && other.getMaxItems().equals(this.getMaxItems()) == false)
      return false;
    if (other.getNextToken() == null ^ this.getNextToken() == null)
      return false;
    if (other.getNextToken() != null && other.getNextToken().equals(this.getNextToken()) == false)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = 1;

    hashCode = prime * hashCode + ((getMaxItems() == null) ? 0 : getMaxItems().hashCode());
    hashCode = prime * hashCode + ((getNextToken() == null) ? 0 : getNextToken().hashCode());
    return hashCode;
  }

  @Override
  public ListApplicationsRequest clone() {
    return (ListApplicationsRequest) super.clone();
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
  public ListApplicationsRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
    super.sdkRequestConfig(sdkRequestConfig);
    return this;
  }

}

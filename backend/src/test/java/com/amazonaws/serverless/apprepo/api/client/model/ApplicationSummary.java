/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model;

import com.amazonaws.protocol.ProtocolMarshaller;
import com.amazonaws.protocol.StructuredPojo;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/ApplicationSummary" target="_top">AWS
 *      API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ApplicationSummary implements Serializable, Cloneable, StructuredPojo {

  private String applicationId;

  private String creationTime;

  private String description;

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

  public ApplicationSummary applicationId(String applicationId) {
    setApplicationId(applicationId);
    return this;
  }

  /**
   * @param creationTime
   */

  public void setCreationTime(String creationTime) {
    this.creationTime = creationTime;
  }

  /**
   * @return
   */

  public String getCreationTime() {
    return this.creationTime;
  }

  /**
   * @param creationTime
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ApplicationSummary creationTime(String creationTime) {
    setCreationTime(creationTime);
    return this;
  }

  /**
   * @param description
   */

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return
   */

  public String getDescription() {
    return this.description;
  }

  /**
   * @param description
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ApplicationSummary description(String description) {
    setDescription(description);
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
    if (getCreationTime() != null)
      sb.append("CreationTime: ").append(getCreationTime()).append(",");
    if (getDescription() != null)
      sb.append("Description: ").append(getDescription());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (obj instanceof ApplicationSummary == false)
      return false;
    ApplicationSummary other = (ApplicationSummary) obj;
    if (other.getApplicationId() == null ^ this.getApplicationId() == null)
      return false;
    if (other.getApplicationId() != null && other.getApplicationId().equals(this.getApplicationId()) == false)
      return false;
    if (other.getCreationTime() == null ^ this.getCreationTime() == null)
      return false;
    if (other.getCreationTime() != null && other.getCreationTime().equals(this.getCreationTime()) == false)
      return false;
    if (other.getDescription() == null ^ this.getDescription() == null)
      return false;
    if (other.getDescription() != null && other.getDescription().equals(this.getDescription()) == false)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = 1;

    hashCode = prime * hashCode + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
    hashCode = prime * hashCode + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
    hashCode = prime * hashCode + ((getDescription() == null) ? 0 : getDescription().hashCode());
    return hashCode;
  }

  @Override
  public ApplicationSummary clone() {
    try {
      return (ApplicationSummary) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
    }
  }

  @com.amazonaws.annotation.SdkInternalApi
  @Override
  public void marshall(ProtocolMarshaller protocolMarshaller) {
    com.amazonaws.serverless.apprepo.api.client.model.transform.ApplicationSummaryMarshaller.getInstance().marshall(this, protocolMarshaller);
  }
}

/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model;

import com.amazonaws.protocol.ProtocolMarshaller;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.serverless.apprepo.api.client.model.transform.ApplicationListMarshaller;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/ApplicationList" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ApplicationList implements Serializable, Cloneable, StructuredPojo {

  private java.util.List<ApplicationSummary> applications;

  private String nextToken;

  /**
   * @return
   */

  public java.util.List<ApplicationSummary> getApplications() {
    return applications;
  }

  /**
   * @param applications
   */

  public void setApplications(java.util.Collection<ApplicationSummary> applications) {
    if (applications == null) {
      this.applications = null;
      return;
    }

    this.applications = new java.util.ArrayList<ApplicationSummary>(applications);
  }

  /**
   * <p>
   * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
   * {@link #setApplications(java.util.Collection)} or {@link #withApplications(java.util.Collection)} if you want to
   * override the existing values.
   * </p>
   *
   * @param applications
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ApplicationList applications(ApplicationSummary... applications) {
    if (this.applications == null) {
      setApplications(new java.util.ArrayList<ApplicationSummary>(applications.length));
    }
    for (ApplicationSummary ele : applications) {
      this.applications.add(ele);
    }
    return this;
  }

  /**
   * @param applications
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ApplicationList applications(java.util.Collection<ApplicationSummary> applications) {
    setApplications(applications);
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

  public ApplicationList nextToken(String nextToken) {
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
    if (getApplications() != null)
      sb.append("Applications: ").append(getApplications()).append(",");
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

    if (obj instanceof ApplicationList == false)
      return false;
    ApplicationList other = (ApplicationList) obj;
    if (other.getApplications() == null ^ this.getApplications() == null)
      return false;
    if (other.getApplications() != null && other.getApplications().equals(this.getApplications()) == false)
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

    hashCode = prime * hashCode + ((getApplications() == null) ? 0 : getApplications().hashCode());
    hashCode = prime * hashCode + ((getNextToken() == null) ? 0 : getNextToken().hashCode());
    return hashCode;
  }

  @Override
  public ApplicationList clone() {
    try {
      return (ApplicationList) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
    }
  }

  @com.amazonaws.annotation.SdkInternalApi
  @Override
  public void marshall(ProtocolMarshaller protocolMarshaller) {
    ApplicationListMarshaller.getInstance().marshall(this, protocolMarshaller);
  }
}

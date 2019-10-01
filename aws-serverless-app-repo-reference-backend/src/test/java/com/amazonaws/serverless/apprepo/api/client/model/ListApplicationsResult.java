/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/ListApplications" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ListApplicationsResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

  private ApplicationList applicationList;

  /**
   * @param applicationList
   */

  public void setApplicationList(ApplicationList applicationList) {
    this.applicationList = applicationList;
  }

  /**
   * @return
   */

  public ApplicationList getApplicationList() {
    return this.applicationList;
  }

  /**
   * @param applicationList
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public ListApplicationsResult applicationList(ApplicationList applicationList) {
    setApplicationList(applicationList);
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
    if (getApplicationList() != null)
      sb.append("ApplicationList: ").append(getApplicationList());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (obj instanceof ListApplicationsResult == false)
      return false;
    ListApplicationsResult other = (ListApplicationsResult) obj;
    if (other.getApplicationList() == null ^ this.getApplicationList() == null)
      return false;
    if (other.getApplicationList() != null && other.getApplicationList().equals(this.getApplicationList()) == false)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = 1;

    hashCode = prime * hashCode + ((getApplicationList() == null) ? 0 : getApplicationList().hashCode());
    return hashCode;
  }

  @Override
  public ListApplicationsResult clone() {
    try {
      return (ListApplicationsResult) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
    }
  }

}

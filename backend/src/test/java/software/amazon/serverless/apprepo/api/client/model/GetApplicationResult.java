/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/GetApplication" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetApplicationResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

  private Application application;

  /**
   * @param application
   */

  public void setApplication(Application application) {
    this.application = application;
  }

  /**
   * @return
   */

  public Application getApplication() {
    return this.application;
  }

  /**
   * @param application
   * @return Returns a reference to this object so that method calls can be chained together.
   */

  public GetApplicationResult application(Application application) {
    setApplication(application);
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
    if (getApplication() != null)
      sb.append("Application: ").append(getApplication());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (obj instanceof GetApplicationResult == false)
      return false;
    GetApplicationResult other = (GetApplicationResult) obj;
    if (other.getApplication() == null ^ this.getApplication() == null)
      return false;
    if (other.getApplication() != null && other.getApplication().equals(this.getApplication()) == false)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = 1;

    hashCode = prime * hashCode + ((getApplication() == null) ? 0 : getApplication().hashCode());
    return hashCode;
  }

  @Override
  public GetApplicationResult clone() {
    try {
      return (GetApplicationResult) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
    }
  }

}

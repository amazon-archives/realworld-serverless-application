/**

*/
package software.amazon.serverless.apprepo.api.client.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/k9zlvhl83e-1570924800000/CreateApplicationInput"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class CreateApplicationInput implements Serializable, Cloneable, StructuredPojo {

    private String applicationId;

    private String author;

    private String description;

    private String homePageUrl;

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

    public CreateApplicationInput applicationId(String applicationId) {
        setApplicationId(applicationId);
        return this;
    }

    /**
     * @param author
     */

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return
     */

    public String getAuthor() {
        return this.author;
    }

    /**
     * @param author
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateApplicationInput author(String author) {
        setAuthor(author);
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

    public CreateApplicationInput description(String description) {
        setDescription(description);
        return this;
    }

    /**
     * @param homePageUrl
     */

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    /**
     * @return
     */

    public String getHomePageUrl() {
        return this.homePageUrl;
    }

    /**
     * @param homePageUrl
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateApplicationInput homePageUrl(String homePageUrl) {
        setHomePageUrl(homePageUrl);
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
        if (getAuthor() != null)
            sb.append("Author: ").append(getAuthor()).append(",");
        if (getDescription() != null)
            sb.append("Description: ").append(getDescription()).append(",");
        if (getHomePageUrl() != null)
            sb.append("HomePageUrl: ").append(getHomePageUrl());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof CreateApplicationInput == false)
            return false;
        CreateApplicationInput other = (CreateApplicationInput) obj;
        if (other.getApplicationId() == null ^ this.getApplicationId() == null)
            return false;
        if (other.getApplicationId() != null && other.getApplicationId().equals(this.getApplicationId()) == false)
            return false;
        if (other.getAuthor() == null ^ this.getAuthor() == null)
            return false;
        if (other.getAuthor() != null && other.getAuthor().equals(this.getAuthor()) == false)
            return false;
        if (other.getDescription() == null ^ this.getDescription() == null)
            return false;
        if (other.getDescription() != null && other.getDescription().equals(this.getDescription()) == false)
            return false;
        if (other.getHomePageUrl() == null ^ this.getHomePageUrl() == null)
            return false;
        if (other.getHomePageUrl() != null && other.getHomePageUrl().equals(this.getHomePageUrl()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        hashCode = prime * hashCode + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        hashCode = prime * hashCode + ((getDescription() == null) ? 0 : getDescription().hashCode());
        hashCode = prime * hashCode + ((getHomePageUrl() == null) ? 0 : getHomePageUrl().hashCode());
        return hashCode;
    }

    @Override
    public CreateApplicationInput clone() {
        try {
            return (CreateApplicationInput) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        software.amazon.serverless.apprepo.api.client.model.transform.CreateApplicationInputMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}

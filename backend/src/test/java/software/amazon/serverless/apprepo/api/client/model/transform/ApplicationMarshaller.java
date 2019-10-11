/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import com.amazonaws.SdkClientException;
import com.amazonaws.annotation.SdkInternalApi;
import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolMarshaller;
import software.amazon.serverless.apprepo.api.client.model.Application;

import javax.annotation.Generated;

/**
 * ApplicationMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class ApplicationMarshaller {

  private static final MarshallingInfo<String> APPLICATIONID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("applicationId").build();
  private static final MarshallingInfo<String> AUTHOR_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
          .marshallLocationName("author").build();
  private static final MarshallingInfo<String> CREATIONTIME_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("creationTime").build();
  private static final MarshallingInfo<String> DESCRIPTION_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("description").build();
  private static final MarshallingInfo<String> HOMEPAGEURL_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("homePageUrl").build();

  private static final ApplicationMarshaller instance = new ApplicationMarshaller();

  public static ApplicationMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(Application application, ProtocolMarshaller protocolMarshaller) {

    if (application == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(application.getApplicationId(), APPLICATIONID_BINDING);
      protocolMarshaller.marshall(application.getAuthor(), AUTHOR_BINDING);
      protocolMarshaller.marshall(application.getCreationTime(), CREATIONTIME_BINDING);
      protocolMarshaller.marshall(application.getDescription(), DESCRIPTION_BINDING);
      protocolMarshaller.marshall(application.getHomePageUrl(), HOMEPAGEURL_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

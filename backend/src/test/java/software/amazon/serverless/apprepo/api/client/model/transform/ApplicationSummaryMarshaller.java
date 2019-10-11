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
import software.amazon.serverless.apprepo.api.client.model.ApplicationSummary;

import javax.annotation.Generated;

/**
 * ApplicationSummaryMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class ApplicationSummaryMarshaller {

  private static final MarshallingInfo<String> APPLICATIONID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("applicationId").build();
  private static final MarshallingInfo<String> CREATIONTIME_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("creationTime").build();
  private static final MarshallingInfo<String> DESCRIPTION_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("description").build();

  private static final ApplicationSummaryMarshaller instance = new ApplicationSummaryMarshaller();

  public static ApplicationSummaryMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(ApplicationSummary applicationSummary, ProtocolMarshaller protocolMarshaller) {

    if (applicationSummary == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(applicationSummary.getApplicationId(), APPLICATIONID_BINDING);
      protocolMarshaller.marshall(applicationSummary.getCreationTime(), CREATIONTIME_BINDING);
      protocolMarshaller.marshall(applicationSummary.getDescription(), DESCRIPTION_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

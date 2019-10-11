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
import com.amazonaws.protocol.StructuredPojo;
import software.amazon.serverless.apprepo.api.client.model.CreateApplicationRequest;

import javax.annotation.Generated;

/**
 * CreateApplicationRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class CreateApplicationRequestMarshaller {

  private static final MarshallingInfo<StructuredPojo> CREATEAPPLICATIONINPUT_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
          .marshallLocation(MarshallLocation.PAYLOAD).isExplicitPayloadMember(true).build();

  private static final CreateApplicationRequestMarshaller instance = new CreateApplicationRequestMarshaller();

  public static CreateApplicationRequestMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(CreateApplicationRequest createApplicationRequest, ProtocolMarshaller protocolMarshaller) {

    if (createApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(createApplicationRequest.getCreateApplicationInput(), CREATEAPPLICATIONINPUT_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

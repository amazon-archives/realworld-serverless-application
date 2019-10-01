/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model.transform;

import com.amazonaws.SdkClientException;
import com.amazonaws.annotation.SdkInternalApi;
import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolMarshaller;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationRequest;

import javax.annotation.Generated;

/**
 * UpdateApplicationRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class UpdateApplicationRequestMarshaller {

  private static final MarshallingInfo<String> APPLICATIONID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
        .marshallLocation(MarshallLocation.PATH).marshallLocationName("applicationId").build();
  private static final MarshallingInfo<StructuredPojo> UPDATEAPPLICATIONINPUT_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
        .marshallLocation(MarshallLocation.PAYLOAD).isExplicitPayloadMember(true).build();

  private static final UpdateApplicationRequestMarshaller instance = new UpdateApplicationRequestMarshaller();

  public static UpdateApplicationRequestMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(UpdateApplicationRequest updateApplicationRequest, ProtocolMarshaller protocolMarshaller) {

    if (updateApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(updateApplicationRequest.getApplicationId(), APPLICATIONID_BINDING);
      protocolMarshaller.marshall(updateApplicationRequest.getUpdateApplicationInput(), UPDATEAPPLICATIONINPUT_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

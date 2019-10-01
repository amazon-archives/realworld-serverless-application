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
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;

import javax.annotation.Generated;

/**
 * GetApplicationRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetApplicationRequestMarshaller {

  private static final MarshallingInfo<String> APPLICATIONID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
        .marshallLocation(MarshallLocation.PATH).marshallLocationName("applicationId").build();

  private static final GetApplicationRequestMarshaller instance = new GetApplicationRequestMarshaller();

  public static GetApplicationRequestMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(GetApplicationRequest getApplicationRequest, ProtocolMarshaller protocolMarshaller) {

    if (getApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(getApplicationRequest.getApplicationId(), APPLICATIONID_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

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
import com.amazonaws.serverless.apprepo.api.client.model.ApplicationList;

import java.util.List;
import javax.annotation.Generated;

/**
 * ApplicationListMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class ApplicationListMarshaller {

  private static final MarshallingInfo<List> APPLICATIONS_BINDING = MarshallingInfo.builder(MarshallingType.LIST).marshallLocation(MarshallLocation.PAYLOAD)
        .marshallLocationName("applications").build();
  private static final MarshallingInfo<String> NEXTTOKEN_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
        .marshallLocationName("nextToken").build();

  private static final ApplicationListMarshaller instance = new ApplicationListMarshaller();

  public static ApplicationListMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(ApplicationList applicationList, ProtocolMarshaller protocolMarshaller) {

    if (applicationList == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(applicationList.getApplications(), APPLICATIONS_BINDING);
      protocolMarshaller.marshall(applicationList.getNextToken(), NEXTTOKEN_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

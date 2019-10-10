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
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationInput;

import javax.annotation.Generated;

/**
 * CreateApplicationInputMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class CreateApplicationInputMarshaller {

  private static final MarshallingInfo<String> APPLICATIONID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("applicationId").build();
  private static final MarshallingInfo<String> AUTHOR_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
          .marshallLocationName("author").build();
  private static final MarshallingInfo<String> DESCRIPTION_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("description").build();
  private static final MarshallingInfo<String> HOMEPAGEURL_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("homePageUrl").build();

  private static final CreateApplicationInputMarshaller instance = new CreateApplicationInputMarshaller();

  public static CreateApplicationInputMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(CreateApplicationInput createApplicationInput, ProtocolMarshaller protocolMarshaller) {

    if (createApplicationInput == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(createApplicationInput.getApplicationId(), APPLICATIONID_BINDING);
      protocolMarshaller.marshall(createApplicationInput.getAuthor(), AUTHOR_BINDING);
      protocolMarshaller.marshall(createApplicationInput.getDescription(), DESCRIPTION_BINDING);
      protocolMarshaller.marshall(createApplicationInput.getHomePageUrl(), HOMEPAGEURL_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

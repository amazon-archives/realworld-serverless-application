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
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsRequest;

import javax.annotation.Generated;

/**
 * ListApplicationsRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class ListApplicationsRequestMarshaller {

  private static final MarshallingInfo<String> MAXITEMS_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.QUERY_PARAM).marshallLocationName("maxItems").build();
  private static final MarshallingInfo<String> NEXTTOKEN_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
          .marshallLocation(MarshallLocation.QUERY_PARAM).marshallLocationName("nextToken").build();

  private static final ListApplicationsRequestMarshaller instance = new ListApplicationsRequestMarshaller();

  public static ListApplicationsRequestMarshaller getInstance() {
    return instance;
  }

  /**
   * Marshall the given parameter object.
   */
  public void marshall(ListApplicationsRequest listApplicationsRequest, ProtocolMarshaller protocolMarshaller) {

    if (listApplicationsRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      protocolMarshaller.marshall(listApplicationsRequest.getMaxItems(), MAXITEMS_BINDING);
      protocolMarshaller.marshall(listApplicationsRequest.getNextToken(), NEXTTOKEN_BINDING);
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

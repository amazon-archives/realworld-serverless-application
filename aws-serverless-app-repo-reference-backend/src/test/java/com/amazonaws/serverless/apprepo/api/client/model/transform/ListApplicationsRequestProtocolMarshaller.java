/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model.transform;

import com.amazonaws.Request;
import com.amazonaws.SdkClientException;
import com.amazonaws.annotation.SdkInternalApi;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.protocol.OperationInfo;
import com.amazonaws.protocol.Protocol;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsRequest;
import com.amazonaws.transform.Marshaller;

import javax.annotation.Generated;

/**
 * ListApplicationsRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class ListApplicationsRequestProtocolMarshaller implements Marshaller<Request<ListApplicationsRequest>, ListApplicationsRequest> {

  private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY).requestUri("/Prod/applications")
        .httpMethodName(HttpMethodName.GET).hasExplicitPayloadMember(false).hasPayloadMembers(false).serviceName("AWSSarBackend").build();

  private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

  public ListApplicationsRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
    this.protocolFactory = protocolFactory;
  }

  public Request<ListApplicationsRequest> marshall(ListApplicationsRequest listApplicationsRequest) {

    if (listApplicationsRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      final ProtocolRequestMarshaller<ListApplicationsRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
            listApplicationsRequest);

      protocolMarshaller.startMarshalling();
      ListApplicationsRequestMarshaller.getInstance().marshall(listApplicationsRequest, protocolMarshaller);
      return protocolMarshaller.finishMarshalling();
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

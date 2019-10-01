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
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;
import com.amazonaws.transform.Marshaller;

import javax.annotation.Generated;

/**
 * GetApplicationRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetApplicationRequestProtocolMarshaller implements Marshaller<Request<GetApplicationRequest>, GetApplicationRequest> {

  private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
        .requestUri("/Prod/applications/{applicationId}").httpMethodName(HttpMethodName.GET).hasExplicitPayloadMember(false).hasPayloadMembers(false)
        .serviceName("AWSSarBackend").build();

  private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

  public GetApplicationRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
    this.protocolFactory = protocolFactory;
  }

  public Request<GetApplicationRequest> marshall(GetApplicationRequest getApplicationRequest) {

    if (getApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      final ProtocolRequestMarshaller<GetApplicationRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
            getApplicationRequest);

      protocolMarshaller.startMarshalling();
      GetApplicationRequestMarshaller.getInstance().marshall(getApplicationRequest, protocolMarshaller);
      return protocolMarshaller.finishMarshalling();
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

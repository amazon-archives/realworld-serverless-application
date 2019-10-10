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
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import com.amazonaws.transform.Marshaller;

import javax.annotation.Generated;

/**
 * UpdateApplicationRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class UpdateApplicationRequestProtocolMarshaller implements Marshaller<Request<UpdateApplicationRequest>, UpdateApplicationRequest> {

  private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
          .requestUri("/Prod/applications/{applicationId}").httpMethodName(HttpMethodName.PATCH).hasExplicitPayloadMember(true).hasPayloadMembers(true)
          .serviceName("AWSServerlessApplicationRepository").build();

  private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

  public UpdateApplicationRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
    this.protocolFactory = protocolFactory;
  }

  public Request<UpdateApplicationRequest> marshall(UpdateApplicationRequest updateApplicationRequest) {

    if (updateApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      final ProtocolRequestMarshaller<UpdateApplicationRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
              updateApplicationRequest);

      protocolMarshaller.startMarshalling();
      UpdateApplicationRequestMarshaller.getInstance().marshall(updateApplicationRequest, protocolMarshaller);
      return protocolMarshaller.finishMarshalling();
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

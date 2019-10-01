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
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationRequest;
import com.amazonaws.transform.Marshaller;

import javax.annotation.Generated;

/**
 * CreateApplicationRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class CreateApplicationRequestProtocolMarshaller implements Marshaller<Request<CreateApplicationRequest>, CreateApplicationRequest> {

  private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY).requestUri("/Prod/applications")
        .httpMethodName(HttpMethodName.POST).hasExplicitPayloadMember(true).hasPayloadMembers(true).serviceName("AWSServerlessApplicationRepository")
        .build();

  private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

  public CreateApplicationRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
    this.protocolFactory = protocolFactory;
  }

  public Request<CreateApplicationRequest> marshall(CreateApplicationRequest createApplicationRequest) {

    if (createApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      final ProtocolRequestMarshaller<CreateApplicationRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
            createApplicationRequest);

      protocolMarshaller.startMarshalling();
      CreateApplicationRequestMarshaller.getInstance().marshall(createApplicationRequest, protocolMarshaller);
      return protocolMarshaller.finishMarshalling();
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

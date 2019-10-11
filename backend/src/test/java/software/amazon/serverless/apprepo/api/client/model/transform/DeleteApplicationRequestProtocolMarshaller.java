/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import com.amazonaws.Request;
import com.amazonaws.SdkClientException;
import com.amazonaws.annotation.SdkInternalApi;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.protocol.OperationInfo;
import com.amazonaws.protocol.Protocol;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import com.amazonaws.transform.Marshaller;

import javax.annotation.Generated;

/**
 * DeleteApplicationRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class DeleteApplicationRequestProtocolMarshaller implements Marshaller<Request<DeleteApplicationRequest>, DeleteApplicationRequest> {

  private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
          .requestUri("/Prod/applications/{applicationId}").httpMethodName(HttpMethodName.DELETE).hasExplicitPayloadMember(false).hasPayloadMembers(false)
          .serviceName("AWSServerlessApplicationRepository").build();

  private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

  public DeleteApplicationRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
    this.protocolFactory = protocolFactory;
  }

  public Request<DeleteApplicationRequest> marshall(DeleteApplicationRequest deleteApplicationRequest) {

    if (deleteApplicationRequest == null) {
      throw new SdkClientException("Invalid argument passed to marshall(...)");
    }

    try {
      final ProtocolRequestMarshaller<DeleteApplicationRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
              deleteApplicationRequest);

      protocolMarshaller.startMarshalling();
      DeleteApplicationRequestMarshaller.getInstance().marshall(deleteApplicationRequest, protocolMarshaller);
      return protocolMarshaller.finishMarshalling();
    } catch (Exception e) {
      throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
    }
  }

}

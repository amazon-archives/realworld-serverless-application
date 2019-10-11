/**

*/
package software.amazon.serverless.apprepo.api.client.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import software.amazon.serverless.apprepo.api.client.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * DeleteApplicationRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class DeleteApplicationRequestMarshaller {

    private static final MarshallingInfo<String> APPLICATIONID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PATH).marshallLocationName("applicationId").build();

    private static final DeleteApplicationRequestMarshaller instance = new DeleteApplicationRequestMarshaller();

    public static DeleteApplicationRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(DeleteApplicationRequest deleteApplicationRequest, ProtocolMarshaller protocolMarshaller) {

        if (deleteApplicationRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(deleteApplicationRequest.getApplicationId(), APPLICATIONID_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}

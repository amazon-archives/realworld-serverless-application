/**

*/
package software.amazon.serverless.apprepo.api.client.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import software.amazon.serverless.apprepo.api.client.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * UpdateApplicationInputMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class UpdateApplicationInputMarshaller {

    private static final MarshallingInfo<String> AUTHOR_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("author").build();
    private static final MarshallingInfo<String> DESCRIPTION_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("description").build();
    private static final MarshallingInfo<String> HOMEPAGEURL_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("homePageUrl").build();

    private static final UpdateApplicationInputMarshaller instance = new UpdateApplicationInputMarshaller();

    public static UpdateApplicationInputMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(UpdateApplicationInput updateApplicationInput, ProtocolMarshaller protocolMarshaller) {

        if (updateApplicationInput == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(updateApplicationInput.getAuthor(), AUTHOR_BINDING);
            protocolMarshaller.marshall(updateApplicationInput.getDescription(), DESCRIPTION_BINDING);
            protocolMarshaller.marshall(updateApplicationInput.getHomePageUrl(), HOMEPAGEURL_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}

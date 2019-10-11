/**

*/
package software.amazon.serverless.apprepo.api.client.model.transform;

import java.math.*;

import javax.annotation.Generated;

import software.amazon.serverless.apprepo.api.client.model.*;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.*;
import com.amazonaws.transform.*;

import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * DeleteApplicationResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class DeleteApplicationResultJsonUnmarshaller implements Unmarshaller<DeleteApplicationResult, JsonUnmarshallerContext> {

    public DeleteApplicationResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        DeleteApplicationResult deleteApplicationResult = new DeleteApplicationResult();

        return deleteApplicationResult;
    }

    private static DeleteApplicationResultJsonUnmarshaller instance;

    public static DeleteApplicationResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new DeleteApplicationResultJsonUnmarshaller();
        return instance;
    }
}

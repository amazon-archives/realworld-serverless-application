/**

*/
package software.amazon.serverless.apprepo.api.client.model.transform;

import java.math.*;

import javax.annotation.Generated;

import software.amazon.serverless.apprepo.api.client.model.*;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.*;
import com.amazonaws.transform.*;

import com.fasterxml.jackson.core.JsonToken;
import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * CreateApplicationResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class CreateApplicationResultJsonUnmarshaller implements Unmarshaller<CreateApplicationResult, JsonUnmarshallerContext> {

    public CreateApplicationResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        CreateApplicationResult createApplicationResult = new CreateApplicationResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return createApplicationResult;
        }

        while (true) {
            if (token == null)
                break;

            createApplicationResult.setApplication(ApplicationJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return createApplicationResult;
    }

    private static CreateApplicationResultJsonUnmarshaller instance;

    public static CreateApplicationResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new CreateApplicationResultJsonUnmarshaller();
        return instance;
    }
}

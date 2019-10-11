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
 * UpdateApplicationInput JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class UpdateApplicationInputJsonUnmarshaller implements Unmarshaller<UpdateApplicationInput, JsonUnmarshallerContext> {

    public UpdateApplicationInput unmarshall(JsonUnmarshallerContext context) throws Exception {
        UpdateApplicationInput updateApplicationInput = new UpdateApplicationInput();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return null;
        }

        while (true) {
            if (token == null)
                break;

            if (token == FIELD_NAME || token == START_OBJECT) {
                if (context.testExpression("author", targetDepth)) {
                    context.nextToken();
                    updateApplicationInput.setAuthor(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("description", targetDepth)) {
                    context.nextToken();
                    updateApplicationInput.setDescription(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("homePageUrl", targetDepth)) {
                    context.nextToken();
                    updateApplicationInput.setHomePageUrl(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return updateApplicationInput;
    }

    private static UpdateApplicationInputJsonUnmarshaller instance;

    public static UpdateApplicationInputJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new UpdateApplicationInputJsonUnmarshaller();
        return instance;
    }
}

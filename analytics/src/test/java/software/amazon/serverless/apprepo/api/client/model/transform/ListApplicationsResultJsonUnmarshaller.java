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
 * ListApplicationsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ListApplicationsResultJsonUnmarshaller implements Unmarshaller<ListApplicationsResult, JsonUnmarshallerContext> {

    public ListApplicationsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        ListApplicationsResult listApplicationsResult = new ListApplicationsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return listApplicationsResult;
        }

        while (true) {
            if (token == null)
                break;

            listApplicationsResult.setApplicationList(ApplicationListJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return listApplicationsResult;
    }

    private static ListApplicationsResultJsonUnmarshaller instance;

    public static ListApplicationsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new ListApplicationsResultJsonUnmarshaller();
        return instance;
    }
}

/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import software.amazon.serverless.apprepo.api.client.model.ListApplicationsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

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

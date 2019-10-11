/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

/**
 * UpdateApplicationResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class UpdateApplicationResultJsonUnmarshaller implements Unmarshaller<UpdateApplicationResult, JsonUnmarshallerContext> {

  public UpdateApplicationResult unmarshall(JsonUnmarshallerContext context) throws Exception {
    UpdateApplicationResult updateApplicationResult = new UpdateApplicationResult();

    int originalDepth = context.getCurrentDepth();
    String currentParentElement = context.getCurrentParentElement();
    int targetDepth = originalDepth + 1;

    JsonToken token = context.getCurrentToken();
    if (token == null)
      token = context.nextToken();
    if (token == VALUE_NULL) {
      return updateApplicationResult;
    }

    while (true) {
      if (token == null)
        break;

      updateApplicationResult.setApplication(ApplicationJsonUnmarshaller.getInstance().unmarshall(context));
      token = context.nextToken();
    }

    return updateApplicationResult;
  }

  private static UpdateApplicationResultJsonUnmarshaller instance;

  public static UpdateApplicationResultJsonUnmarshaller getInstance() {
    if (instance == null)
      instance = new UpdateApplicationResultJsonUnmarshaller();
    return instance;
  }
}

/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import software.amazon.serverless.apprepo.api.client.model.CreateApplicationResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

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

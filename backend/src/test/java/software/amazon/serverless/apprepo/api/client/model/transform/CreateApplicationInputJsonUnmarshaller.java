/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import software.amazon.serverless.apprepo.api.client.model.CreateApplicationInput;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

/**
 * CreateApplicationInput JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class CreateApplicationInputJsonUnmarshaller implements Unmarshaller<CreateApplicationInput, JsonUnmarshallerContext> {

  public CreateApplicationInput unmarshall(JsonUnmarshallerContext context) throws Exception {
    CreateApplicationInput createApplicationInput = new CreateApplicationInput();

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
        if (context.testExpression("applicationId", targetDepth)) {
          context.nextToken();
          createApplicationInput.setApplicationId(context.getUnmarshaller(String.class).unmarshall(context));
        }
        if (context.testExpression("author", targetDepth)) {
          context.nextToken();
          createApplicationInput.setAuthor(context.getUnmarshaller(String.class).unmarshall(context));
        }
        if (context.testExpression("description", targetDepth)) {
          context.nextToken();
          createApplicationInput.setDescription(context.getUnmarshaller(String.class).unmarshall(context));
        }
        if (context.testExpression("homePageUrl", targetDepth)) {
          context.nextToken();
          createApplicationInput.setHomePageUrl(context.getUnmarshaller(String.class).unmarshall(context));
        }
      } else if (token == END_ARRAY || token == END_OBJECT) {
        if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
          if (context.getCurrentDepth() <= originalDepth)
            break;
        }
      }
      token = context.nextToken();
    }

    return createApplicationInput;
  }

  private static CreateApplicationInputJsonUnmarshaller instance;

  public static CreateApplicationInputJsonUnmarshaller getInstance() {
    if (instance == null)
      instance = new CreateApplicationInputJsonUnmarshaller();
    return instance;
  }
}

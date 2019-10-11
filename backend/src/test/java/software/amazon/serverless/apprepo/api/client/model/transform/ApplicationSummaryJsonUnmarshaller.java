/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import software.amazon.serverless.apprepo.api.client.model.ApplicationSummary;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

/**
 * ApplicationSummary JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ApplicationSummaryJsonUnmarshaller implements Unmarshaller<ApplicationSummary, JsonUnmarshallerContext> {

  public ApplicationSummary unmarshall(JsonUnmarshallerContext context) throws Exception {
    ApplicationSummary applicationSummary = new ApplicationSummary();

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
          applicationSummary.setApplicationId(context.getUnmarshaller(String.class).unmarshall(context));
        }
        if (context.testExpression("creationTime", targetDepth)) {
          context.nextToken();
          applicationSummary.setCreationTime(context.getUnmarshaller(String.class).unmarshall(context));
        }
        if (context.testExpression("description", targetDepth)) {
          context.nextToken();
          applicationSummary.setDescription(context.getUnmarshaller(String.class).unmarshall(context));
        }
      } else if (token == END_ARRAY || token == END_OBJECT) {
        if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
          if (context.getCurrentDepth() <= originalDepth)
            break;
        }
      }
      token = context.nextToken();
    }

    return applicationSummary;
  }

  private static ApplicationSummaryJsonUnmarshaller instance;

  public static ApplicationSummaryJsonUnmarshaller getInstance() {
    if (instance == null)
      instance = new ApplicationSummaryJsonUnmarshaller();
    return instance;
  }
}

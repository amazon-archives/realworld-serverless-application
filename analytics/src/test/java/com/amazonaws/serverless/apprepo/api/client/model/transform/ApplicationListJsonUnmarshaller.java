/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import com.amazonaws.serverless.apprepo.api.client.model.ApplicationList;
import com.amazonaws.serverless.apprepo.api.client.model.ApplicationSummary;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

/**
 * ApplicationList JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ApplicationListJsonUnmarshaller implements Unmarshaller<ApplicationList, JsonUnmarshallerContext> {

  public ApplicationList unmarshall(JsonUnmarshallerContext context) throws Exception {
    ApplicationList applicationList = new ApplicationList();

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
        if (context.testExpression("applications", targetDepth)) {
          context.nextToken();
          applicationList.setApplications(new ListUnmarshaller<ApplicationSummary>(ApplicationSummaryJsonUnmarshaller.getInstance())
                  .unmarshall(context));
        }
        if (context.testExpression("nextToken", targetDepth)) {
          context.nextToken();
          applicationList.setNextToken(context.getUnmarshaller(String.class).unmarshall(context));
        }
      } else if (token == END_ARRAY || token == END_OBJECT) {
        if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
          if (context.getCurrentDepth() <= originalDepth)
            break;
        }
      }
      token = context.nextToken();
    }

    return applicationList;
  }

  private static ApplicationListJsonUnmarshaller instance;

  public static ApplicationListJsonUnmarshaller getInstance() {
    if (instance == null)
      instance = new ApplicationListJsonUnmarshaller();
    return instance;
  }
}

/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client.model.transform;

import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.core.JsonToken;

import javax.annotation.Generated;

/**
 * GetApplicationResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetApplicationResultJsonUnmarshaller implements Unmarshaller<GetApplicationResult, JsonUnmarshallerContext> {

  public GetApplicationResult unmarshall(JsonUnmarshallerContext context) throws Exception {
    GetApplicationResult getApplicationResult = new GetApplicationResult();

    int originalDepth = context.getCurrentDepth();
    String currentParentElement = context.getCurrentParentElement();
    int targetDepth = originalDepth + 1;

    JsonToken token = context.getCurrentToken();
    if (token == null)
      token = context.nextToken();
    if (token == VALUE_NULL) {
      return getApplicationResult;
    }

    while (true) {
      if (token == null)
        break;

      getApplicationResult.setApplication(ApplicationJsonUnmarshaller.getInstance().unmarshall(context));
      token = context.nextToken();
    }

    return getApplicationResult;
  }

  private static GetApplicationResultJsonUnmarshaller instance;

  public static GetApplicationResultJsonUnmarshaller getInstance() {
    if (instance == null)
      instance = new GetApplicationResultJsonUnmarshaller();
    return instance;
  }
}

/**
 *
 */
package software.amazon.serverless.apprepo.api.client.model.transform;

import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

import javax.annotation.Generated;

/**
 * DeleteApplicationResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class DeleteApplicationResultJsonUnmarshaller implements Unmarshaller<DeleteApplicationResult, JsonUnmarshallerContext> {

  public DeleteApplicationResult unmarshall(JsonUnmarshallerContext context) throws Exception {
    DeleteApplicationResult deleteApplicationResult = new DeleteApplicationResult();

    return deleteApplicationResult;
  }

  private static DeleteApplicationResultJsonUnmarshaller instance;

  public static DeleteApplicationResultJsonUnmarshaller getInstance() {
    if (instance == null)
      instance = new DeleteApplicationResultJsonUnmarshaller();
    return instance;
  }
}

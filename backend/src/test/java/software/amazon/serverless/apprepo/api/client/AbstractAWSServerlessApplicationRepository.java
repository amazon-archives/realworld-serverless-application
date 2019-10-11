/**
 *
 */
package software.amazon.serverless.apprepo.api.client;

import software.amazon.serverless.apprepo.api.client.model.CreateApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.CreateApplicationResult;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationResult;
import software.amazon.serverless.apprepo.api.client.model.GetApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.GetApplicationResult;
import software.amazon.serverless.apprepo.api.client.model.ListApplicationsRequest;
import software.amazon.serverless.apprepo.api.client.model.ListApplicationsResult;
import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationResult;

import javax.annotation.Generated;

/**
 * Abstract implementation of {@code AWSServerlessApplicationRepository}.
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class AbstractAWSServerlessApplicationRepository implements AWSServerlessApplicationRepository {

  protected AbstractAWSServerlessApplicationRepository() {
  }

  @Override
  public CreateApplicationResult createApplication(CreateApplicationRequest request) {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public DeleteApplicationResult deleteApplication(DeleteApplicationRequest request) {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public GetApplicationResult getApplication(GetApplicationRequest request) {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public ListApplicationsResult listApplications(ListApplicationsRequest request) {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public UpdateApplicationResult updateApplication(UpdateApplicationRequest request) {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void shutdown() {
    throw new java.lang.UnsupportedOperationException();
  }

}

/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client;

import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.DeleteApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsRequest;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsResult;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationResult;

import javax.annotation.Generated;

/**
 * Abstract implementation of {@code AWSSarBackend}.
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class AbstractAWSSarBackend implements AWSSarBackend {

  protected AbstractAWSSarBackend() {
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

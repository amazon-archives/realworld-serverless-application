package com.amazonaws.serverless.apprepo.cucumber.steps;

import com.amazonaws.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationInput;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.DeleteApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsRequest;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsResult;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationInput;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Client implementation that wraps {@link AWSServerlessApplicationRepository} and records results to {@link TestEnv}.
 */
@RequiredArgsConstructor
@Slf4j
public class AWSServerlessApplicationRepositoryRecordingClient implements AWSServerlessApplicationRepository {
  private final AWSServerlessApplicationRepository delegate;

  @Override
  public CreateApplicationResult createApplication(CreateApplicationRequest createApplicationRequest) {
    TestEnv.setLastException(null);
    try {
      CreateApplicationInput input = createApplicationRequest.getCreateApplicationInput();
      TestEnv.setApplicationId(input.getApplicationId());
      TestEnv.setApplicationDescription(input.getDescription());
      TestEnv.setAuthor(input.getAuthor());
      TestEnv.setHomePageUrl(input.getHomePageUrl());

      CreateApplicationResult result = delegate.createApplication(createApplicationRequest);
      TestEnv.setApplication(result.getApplication());
      TestEnv.addApplication(result.getApplication());
      return result;
    } catch (Throwable t) {
      log.info("Exception is thrown in CreateApplication", t);
      TestEnv.setLastException(t);
      throw t;
    }
  }

  @Override
  public DeleteApplicationResult deleteApplication(DeleteApplicationRequest deleteApplicationRequest) {
    TestEnv.setLastException(null);
    try {
      return delegate.deleteApplication(deleteApplicationRequest);
    } catch (Throwable t) {
      log.info("Exception is thrown in DeleteApplication", t);
      TestEnv.setLastException(t);
      throw t;
    }
  }

  @Override
  public GetApplicationResult getApplication(GetApplicationRequest getApplicationRequest) {
    TestEnv.setLastException(null);
    try {
      return delegate.getApplication(getApplicationRequest);
    } catch (Throwable t) {
      log.info("Exception is thrown in GetApplication", t);
      TestEnv.setLastException(t);
      throw t;
    }
  }

  @Override
  public ListApplicationsResult listApplications(ListApplicationsRequest listApplicationsRequest) {
    TestEnv.setLastException(null);
    try {
      ListApplicationsResult listApplicationsResult = delegate.listApplications(listApplicationsRequest);
      TestEnv.setApplicationList(listApplicationsResult.getApplicationList());
      return listApplicationsResult;
    } catch (Throwable t) {
      log.info("Exception is thrown in listApplications", t);
      TestEnv.setLastException(t);
      throw t;
    }
  }

  @Override
  public UpdateApplicationResult updateApplication(UpdateApplicationRequest updateApplicationRequest) {
    TestEnv.setLastException(null);
    try {
      UpdateApplicationInput input = updateApplicationRequest.getUpdateApplicationInput();
      TestEnv.setApplicationDescription(input.getDescription());
      TestEnv.setAuthor(input.getAuthor());
      TestEnv.setHomePageUrl(input.getHomePageUrl());
      UpdateApplicationResult result = delegate.updateApplication(updateApplicationRequest);
      TestEnv.setApplication(result.getApplication());
      return result;
    } catch (Throwable t) {
      log.info("Exception is thrown in UpdateApplication", t);
      TestEnv.setLastException(t);
      throw t;
    }
  }

  @Override
  public void shutdown() {
    delegate.shutdown();
  }
}

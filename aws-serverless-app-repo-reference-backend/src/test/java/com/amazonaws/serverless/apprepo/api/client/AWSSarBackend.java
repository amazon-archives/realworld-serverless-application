/**
 *
 */
package com.amazonaws.serverless.apprepo.api.client;

import com.amazonaws.serverless.apprepo.api.client.model.BadRequestException;
import com.amazonaws.serverless.apprepo.api.client.model.ConflictException;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.CreateApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.DeleteApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.GetApplicationResult;
import com.amazonaws.serverless.apprepo.api.client.model.InternalServerErrorException;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsRequest;
import com.amazonaws.serverless.apprepo.api.client.model.ListApplicationsResult;
import com.amazonaws.serverless.apprepo.api.client.model.NotFoundException;
import com.amazonaws.serverless.apprepo.api.client.model.TooManyRequestsException;
import com.amazonaws.serverless.apprepo.api.client.model.UnauthorizedException;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import com.amazonaws.serverless.apprepo.api.client.model.UpdateApplicationResult;

import javax.annotation.Generated;

/**
 * Interface for accessing AWSSarBackend.
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public interface AWSSarBackend {

  /**
   * @param createApplicationRequest
   * @return Result of the CreateApplication operation returned by the service.
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @throws ConflictException
   * @sample AWSSarBackend.CreateApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/CreateApplication" target="_top">AWS
   *      API Documentation</a>
   */
  CreateApplicationResult createApplication(CreateApplicationRequest createApplicationRequest);

  /**
   * @param deleteApplicationRequest
   * @return Result of the DeleteApplication operation returned by the service.
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @throws NotFoundException
   * @throws TooManyRequestsException
   * @throws ConflictException
   * @sample AWSSarBackend.DeleteApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/DeleteApplication" target="_top">AWS
   *      API Documentation</a>
   */
  DeleteApplicationResult deleteApplication(DeleteApplicationRequest deleteApplicationRequest);

  /**
   * @param getApplicationRequest
   * @return Result of the GetApplication operation returned by the service.
   * @throws NotFoundException
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @sample AWSSarBackend.GetApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/GetApplication" target="_top">AWS API
   *      Documentation</a>
   */
  GetApplicationResult getApplication(GetApplicationRequest getApplicationRequest);

  /**
   * @param listApplicationsRequest
   * @return Result of the ListApplications operation returned by the service.
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @sample AWSSarBackend.ListApplications
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/ListApplications" target="_top">AWS
   *      API Documentation</a>
   */
  ListApplicationsResult listApplications(ListApplicationsRequest listApplicationsRequest);

  /**
   * @param updateApplicationRequest
   * @return Result of the UpdateApplication operation returned by the service.
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @throws NotFoundException
   * @throws TooManyRequestsException
   * @throws ConflictException
   * @sample AWSSarBackend.UpdateApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-07-01/UpdateApplication" target="_top">AWS
   *      API Documentation</a>
   */
  UpdateApplicationResult updateApplication(UpdateApplicationRequest updateApplicationRequest);

  /**
   * @return Create new instance of builder with all defaults set.
   */
  public static AWSSarBackendClientBuilder builder() {
    return new AWSSarBackendClientBuilder();
  }

  /**
   * Shuts down this client object, releasing any resources that might be held open. This is an optional method, and
   * callers are not expected to call it, but can if they want to explicitly release any open resources. Once a client
   * has been shutdown, it should not be used to make any more requests.
   */
  void shutdown();

}

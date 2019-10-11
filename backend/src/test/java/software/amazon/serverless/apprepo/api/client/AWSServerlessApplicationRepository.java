/**
 *
 */
package software.amazon.serverless.apprepo.api.client;

import software.amazon.serverless.apprepo.api.client.model.BadRequestException;
import software.amazon.serverless.apprepo.api.client.model.ConflictException;
import software.amazon.serverless.apprepo.api.client.model.CreateApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.CreateApplicationResult;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.DeleteApplicationResult;
import software.amazon.serverless.apprepo.api.client.model.GetApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.GetApplicationResult;
import software.amazon.serverless.apprepo.api.client.model.InternalServerErrorException;
import software.amazon.serverless.apprepo.api.client.model.ListApplicationsRequest;
import software.amazon.serverless.apprepo.api.client.model.ListApplicationsResult;
import software.amazon.serverless.apprepo.api.client.model.NotFoundException;
import software.amazon.serverless.apprepo.api.client.model.TooManyRequestsException;
import software.amazon.serverless.apprepo.api.client.model.UnauthorizedException;
import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationRequest;
import software.amazon.serverless.apprepo.api.client.model.UpdateApplicationResult;

import javax.annotation.Generated;

/**
 * Interface for accessing AWSServerlessApplicationRepository.
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public interface AWSServerlessApplicationRepository {

  /**
   * @param createApplicationRequest
   * @return Result of the CreateApplication operation returned by the service.
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @throws ConflictException
   * @sample AWSServerlessApplicationRepository.CreateApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/CreateApplication"
   *      target="_top">AWS API Documentation</a>
   */
  CreateApplicationResult createApplication(CreateApplicationRequest createApplicationRequest);

  /**
   * @param deleteApplicationRequest
   * @return Result of the DeleteApplication operation returned by the service.
   * @throws NotFoundException
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @sample AWSServerlessApplicationRepository.DeleteApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/DeleteApplication"
   *      target="_top">AWS API Documentation</a>
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
   * @sample AWSServerlessApplicationRepository.GetApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/GetApplication" target="_top">AWS
   *      API Documentation</a>
   */
  GetApplicationResult getApplication(GetApplicationRequest getApplicationRequest);

  /**
   * @param listApplicationsRequest
   * @return Result of the ListApplications operation returned by the service.
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @sample AWSServerlessApplicationRepository.ListApplications
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/ListApplications" target="_top">AWS
   *      API Documentation</a>
   */
  ListApplicationsResult listApplications(ListApplicationsRequest listApplicationsRequest);

  /**
   * @param updateApplicationRequest
   * @return Result of the UpdateApplication operation returned by the service.
   * @throws NotFoundException
   * @throws TooManyRequestsException
   * @throws BadRequestException
   * @throws UnauthorizedException
   * @throws InternalServerErrorException
   * @sample AWSServerlessApplicationRepository.UpdateApplication
   * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/xxx-2019-10-13/UpdateApplication"
   *      target="_top">AWS API Documentation</a>
   */
  UpdateApplicationResult updateApplication(UpdateApplicationRequest updateApplicationRequest);

  /**
   * @return Create new instance of builder with all defaults set.
   */
  public static AWSServerlessApplicationRepositoryClientBuilder builder() {
    return new AWSServerlessApplicationRepositoryClientBuilder();
  }

  /**
   * Shuts down this client object, releasing any resources that might be held open. This is an optional method, and
   * callers are not expected to call it, but can if they want to explicitly release any open resources. Once a client
   * has been shutdown, it should not be used to make any more requests.
   */
  void shutdown();

}

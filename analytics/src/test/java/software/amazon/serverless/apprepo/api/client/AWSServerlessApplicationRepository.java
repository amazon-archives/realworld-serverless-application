/**

*/
package software.amazon.serverless.apprepo.api.client;

import javax.annotation.Generated;

import com.amazonaws.*;
import com.amazonaws.opensdk.*;
import com.amazonaws.opensdk.model.*;
import com.amazonaws.regions.*;

import software.amazon.serverless.apprepo.api.client.model.*;

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
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/k9zlvhl83e-1570924800000/CreateApplication"
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
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/k9zlvhl83e-1570924800000/DeleteApplication"
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
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/k9zlvhl83e-1570924800000/GetApplication" target="_top">AWS
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
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/k9zlvhl83e-1570924800000/ListApplications" target="_top">AWS
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
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/k9zlvhl83e-1570924800000/UpdateApplication"
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

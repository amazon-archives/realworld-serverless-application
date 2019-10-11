/**
 *
 */
package software.amazon.serverless.apprepo.api.client;

import com.amazonaws.SdkBaseException;
import com.amazonaws.annotation.ThreadSafe;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.client.ClientExecutionParams;
import com.amazonaws.client.ClientHandler;
import com.amazonaws.client.ClientHandlerParams;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.opensdk.protect.client.SdkClientHandler;
import com.amazonaws.protocol.json.JsonClientMetadata;
import com.amazonaws.protocol.json.JsonErrorResponseMetadata;
import com.amazonaws.protocol.json.JsonErrorShapeMetadata;
import com.amazonaws.protocol.json.JsonOperationMetadata;
import software.amazon.serverless.apprepo.api.client.model.BadRequestException;
import software.amazon.serverless.apprepo.api.client.model.AWSServerlessApplicationRepositoryException;
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
import software.amazon.serverless.apprepo.api.client.model.transform.CreateApplicationRequestProtocolMarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.CreateApplicationResultJsonUnmarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.DeleteApplicationRequestProtocolMarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.DeleteApplicationResultJsonUnmarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.GetApplicationRequestProtocolMarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.GetApplicationResultJsonUnmarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.ListApplicationsRequestProtocolMarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.ListApplicationsResultJsonUnmarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.UpdateApplicationRequestProtocolMarshaller;
import software.amazon.serverless.apprepo.api.client.model.transform.UpdateApplicationResultJsonUnmarshaller;

import java.util.Arrays;
import javax.annotation.Generated;

/**
 * Client for accessing AWSServerlessApplicationRepository. All service calls made using this client are blocking, and
 * will not return until the service call completes.
 * <p>
 *
 */
@ThreadSafe
@Generated("com.amazonaws:aws-java-sdk-code-generator")
class AWSServerlessApplicationRepositoryClient implements AWSServerlessApplicationRepository {

  private final ClientHandler clientHandler;

  private static final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory = new com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl(
          new JsonClientMetadata()
                  .withProtocolVersion("1.1")
                  .withSupportsCbor(false)
                  .withSupportsIon(false)
                  .withContentTypeOverride("application/json")
                  .addErrorMetadata(
                          new JsonErrorShapeMetadata().withErrorCode("ConflictException").withModeledClass(
                                  ConflictException.class))
                  .addErrorMetadata(
                          new JsonErrorShapeMetadata().withErrorCode("NotFoundException").withModeledClass(
                                  NotFoundException.class))
                  .addErrorMetadata(
                          new JsonErrorShapeMetadata().withErrorCode("TooManyRequestsException").withModeledClass(
                                  TooManyRequestsException.class))
                  .addErrorMetadata(
                          new JsonErrorShapeMetadata().withErrorCode("UnauthorizedException").withModeledClass(
                                  UnauthorizedException.class))
                  .addErrorMetadata(
                          new JsonErrorShapeMetadata().withErrorCode("BadRequestException").withModeledClass(
                                  BadRequestException.class))
                  .addErrorMetadata(
                          new JsonErrorShapeMetadata().withErrorCode("InternalServerErrorException").withModeledClass(
                                  InternalServerErrorException.class))
                  .withBaseServiceExceptionClass(AWSServerlessApplicationRepositoryException.class));

  /**
   * Constructs a new client to invoke service methods on AWSServerlessApplicationRepository using the specified
   * parameters.
   *
   * <p>
   * All service calls made using this new client object are blocking, and will not return until the service call
   * completes.
   *
   * @param clientParams
   *        Object providing client parameters.
   */
  AWSServerlessApplicationRepositoryClient(AwsSyncClientParams clientParams) {
    this.clientHandler = new SdkClientHandler(new ClientHandlerParams().withClientParams(clientParams));
  }

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
  @Override
  public CreateApplicationResult createApplication(CreateApplicationRequest createApplicationRequest) {
    HttpResponseHandler<CreateApplicationResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
            .withHasStreamingSuccessResponse(false), new CreateApplicationResultJsonUnmarshaller());

    HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler(
            new JsonErrorShapeMetadata().withModeledClass(TooManyRequestsException.class).withHttpStatusCode(429), new JsonErrorShapeMetadata()
                    .withModeledClass(BadRequestException.class).withHttpStatusCode(400),
            new JsonErrorShapeMetadata().withModeledClass(UnauthorizedException.class).withHttpStatusCode(401), new JsonErrorShapeMetadata()
                    .withModeledClass(InternalServerErrorException.class).withHttpStatusCode(500),
            new JsonErrorShapeMetadata().withModeledClass(ConflictException.class).withHttpStatusCode(409));

    return clientHandler.execute(new ClientExecutionParams<CreateApplicationRequest, CreateApplicationResult>()
            .withMarshaller(new CreateApplicationRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
            .withErrorResponseHandler(errorResponseHandler).withInput(createApplicationRequest));
  }

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
  @Override
  public DeleteApplicationResult deleteApplication(DeleteApplicationRequest deleteApplicationRequest) {
    HttpResponseHandler<DeleteApplicationResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
            .withHasStreamingSuccessResponse(false), new DeleteApplicationResultJsonUnmarshaller());

    HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler(
            new JsonErrorShapeMetadata().withModeledClass(NotFoundException.class).withHttpStatusCode(404),
            new JsonErrorShapeMetadata().withModeledClass(TooManyRequestsException.class).withHttpStatusCode(429), new JsonErrorShapeMetadata()
                    .withModeledClass(BadRequestException.class).withHttpStatusCode(400),
            new JsonErrorShapeMetadata().withModeledClass(UnauthorizedException.class).withHttpStatusCode(401), new JsonErrorShapeMetadata()
                    .withModeledClass(InternalServerErrorException.class).withHttpStatusCode(500));

    return clientHandler.execute(new ClientExecutionParams<DeleteApplicationRequest, DeleteApplicationResult>()
            .withMarshaller(new DeleteApplicationRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
            .withErrorResponseHandler(errorResponseHandler).withInput(deleteApplicationRequest));
  }

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
  @Override
  public GetApplicationResult getApplication(GetApplicationRequest getApplicationRequest) {
    HttpResponseHandler<GetApplicationResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
            .withHasStreamingSuccessResponse(false), new GetApplicationResultJsonUnmarshaller());

    HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler(
            new JsonErrorShapeMetadata().withModeledClass(NotFoundException.class).withHttpStatusCode(404),
            new JsonErrorShapeMetadata().withModeledClass(TooManyRequestsException.class).withHttpStatusCode(429), new JsonErrorShapeMetadata()
                    .withModeledClass(BadRequestException.class).withHttpStatusCode(400),
            new JsonErrorShapeMetadata().withModeledClass(UnauthorizedException.class).withHttpStatusCode(401), new JsonErrorShapeMetadata()
                    .withModeledClass(InternalServerErrorException.class).withHttpStatusCode(500));

    return clientHandler.execute(new ClientExecutionParams<GetApplicationRequest, GetApplicationResult>()
            .withMarshaller(new GetApplicationRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
            .withErrorResponseHandler(errorResponseHandler).withInput(getApplicationRequest));
  }

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
  @Override
  public ListApplicationsResult listApplications(ListApplicationsRequest listApplicationsRequest) {
    HttpResponseHandler<ListApplicationsResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
            .withHasStreamingSuccessResponse(false), new ListApplicationsResultJsonUnmarshaller());

    HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler(
            new JsonErrorShapeMetadata().withModeledClass(TooManyRequestsException.class).withHttpStatusCode(429), new JsonErrorShapeMetadata()
                    .withModeledClass(BadRequestException.class).withHttpStatusCode(400),
            new JsonErrorShapeMetadata().withModeledClass(UnauthorizedException.class).withHttpStatusCode(401), new JsonErrorShapeMetadata()
                    .withModeledClass(InternalServerErrorException.class).withHttpStatusCode(500));

    return clientHandler.execute(new ClientExecutionParams<ListApplicationsRequest, ListApplicationsResult>()
            .withMarshaller(new ListApplicationsRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
            .withErrorResponseHandler(errorResponseHandler).withInput(listApplicationsRequest));
  }

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
  @Override
  public UpdateApplicationResult updateApplication(UpdateApplicationRequest updateApplicationRequest) {
    HttpResponseHandler<UpdateApplicationResult> responseHandler = protocolFactory.createResponseHandler(new JsonOperationMetadata().withPayloadJson(true)
            .withHasStreamingSuccessResponse(false), new UpdateApplicationResultJsonUnmarshaller());

    HttpResponseHandler<SdkBaseException> errorResponseHandler = createErrorResponseHandler(
            new JsonErrorShapeMetadata().withModeledClass(NotFoundException.class).withHttpStatusCode(404),
            new JsonErrorShapeMetadata().withModeledClass(TooManyRequestsException.class).withHttpStatusCode(429), new JsonErrorShapeMetadata()
                    .withModeledClass(BadRequestException.class).withHttpStatusCode(400),
            new JsonErrorShapeMetadata().withModeledClass(UnauthorizedException.class).withHttpStatusCode(401), new JsonErrorShapeMetadata()
                    .withModeledClass(InternalServerErrorException.class).withHttpStatusCode(500));

    return clientHandler.execute(new ClientExecutionParams<UpdateApplicationRequest, UpdateApplicationResult>()
            .withMarshaller(new UpdateApplicationRequestProtocolMarshaller(protocolFactory)).withResponseHandler(responseHandler)
            .withErrorResponseHandler(errorResponseHandler).withInput(updateApplicationRequest));
  }

  /**
   * Create the error response handler for the operation.
   *
   * @param errorShapeMetadata
   *        Error metadata for the given operation
   * @return Configured error response handler to pass to HTTP layer
   */
  private HttpResponseHandler<SdkBaseException> createErrorResponseHandler(JsonErrorShapeMetadata... errorShapeMetadata) {
    return protocolFactory.createErrorResponseHandler(new JsonErrorResponseMetadata().withErrorShapes(Arrays.asList(errorShapeMetadata)));
  }

  @Override
  public void shutdown() {
    clientHandler.shutdown();
  }

}

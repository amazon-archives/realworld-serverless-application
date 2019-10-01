package com.amazonaws.serverless.apprepo.api;

import com.amazonaws.serverless.apprepo.api.model.Application;
import com.amazonaws.serverless.apprepo.api.model.ApplicationList;
import com.amazonaws.serverless.apprepo.api.model.BadRequestException;
import com.amazonaws.serverless.apprepo.api.model.ConflictException;
import com.amazonaws.serverless.apprepo.api.model.CreateApplicationInput;
import com.amazonaws.serverless.apprepo.api.model.InternalServerErrorException;
import com.amazonaws.serverless.apprepo.api.model.NotFoundException;
import com.amazonaws.serverless.apprepo.api.model.TooManyRequestsException;
import com.amazonaws.serverless.apprepo.api.model.UnauthorizedException;
import com.amazonaws.serverless.apprepo.api.model.UpdateApplicationInput;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/applications")
@Api(description = "the applications API")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-09-30T22:23:31.487-07:00")
public interface ApplicationsApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", authorizations = {
        @Authorization(value = "cognitoAuthorizer")
    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "", response = Application.class),
        @ApiResponse(code = 400, message = "", response = BadRequestException.class),
        @ApiResponse(code = 401, message = "", response = UnauthorizedException.class),
        @ApiResponse(code = 409, message = "", response = ConflictException.class),
        @ApiResponse(code = 429, message = "", response = TooManyRequestsException.class),
        @ApiResponse(code = 500, message = "", response = InternalServerErrorException.class) })
    Application createApplication(@Valid CreateApplicationInput createApplicationInput);

    @DELETE
    @Path("/{applicationId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", authorizations = {
        @Authorization(value = "cognitoAuthorizer")
    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Delete an application", response = Void.class),
        @ApiResponse(code = 400, message = "", response = BadRequestException.class),
        @ApiResponse(code = 401, message = "", response = UnauthorizedException.class),
        @ApiResponse(code = 404, message = "", response = NotFoundException.class),
        @ApiResponse(code = 409, message = "", response = ConflictException.class),
        @ApiResponse(code = 429, message = "", response = TooManyRequestsException.class),
        @ApiResponse(code = 500, message = "", response = InternalServerErrorException.class) })
    void deleteApplication(@PathParam("applicationId") @Pattern(regexp="^[a-zA-Z0-9\\-]{3,128}$") String applicationId);

    @GET
    @Path("/{applicationId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", authorizations = {
        @Authorization(value = "cognitoAuthorizer")
    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Application.class),
        @ApiResponse(code = 400, message = "", response = BadRequestException.class),
        @ApiResponse(code = 401, message = "", response = UnauthorizedException.class),
        @ApiResponse(code = 404, message = "", response = NotFoundException.class),
        @ApiResponse(code = 429, message = "", response = TooManyRequestsException.class),
        @ApiResponse(code = 500, message = "", response = InternalServerErrorException.class) })
    Application getApplication(@PathParam("applicationId") @Pattern(regexp="^[a-zA-Z0-9\\-]{3,128}$") String applicationId);

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", authorizations = {
        @Authorization(value = "cognitoAuthorizer")
    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = ApplicationList.class),
        @ApiResponse(code = 400, message = "", response = BadRequestException.class),
        @ApiResponse(code = 401, message = "", response = UnauthorizedException.class),
        @ApiResponse(code = 429, message = "", response = TooManyRequestsException.class),
        @ApiResponse(code = 500, message = "", response = InternalServerErrorException.class) })
    ApplicationList listApplications(@QueryParam("nextToken")    String nextToken,@QueryParam("maxItems") @Min(1) @Max(100)    Integer maxItems);

    @PATCH
    @Path("/{applicationId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", authorizations = {
        @Authorization(value = "cognitoAuthorizer")
    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Application.class),
        @ApiResponse(code = 400, message = "", response = BadRequestException.class),
        @ApiResponse(code = 401, message = "", response = UnauthorizedException.class),
        @ApiResponse(code = 404, message = "", response = NotFoundException.class),
        @ApiResponse(code = 409, message = "", response = ConflictException.class),
        @ApiResponse(code = 429, message = "", response = TooManyRequestsException.class),
        @ApiResponse(code = 500, message = "", response = InternalServerErrorException.class) })
    Application updateApplication(@PathParam("applicationId") @Pattern(regexp="^[a-zA-Z0-9\\-]{3,128}$") String applicationId,@Valid UpdateApplicationInput updateApplicationInput);
}

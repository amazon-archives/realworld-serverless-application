/**

*/
package software.amazon.serverless.apprepo.api.client.auth;

import javax.annotation.Generated;
import com.amazonaws.ImmutableRequest;
import com.amazonaws.SignableRequest;
import com.amazonaws.auth.RequestSigner;
import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepository;
import software.amazon.serverless.apprepo.api.client.AWSServerlessApplicationRepositoryClientBuilder;

/**
 * A default implementation of {@link RequestSigner} that puts a generated token into the header. An implementation of
 * this can to be supplied during construction of a {@link AWSServerlessApplicationRepository} via
 * {@link AWSServerlessApplicationRepositoryClientBuilder#signer(CognitoAuthorizer)} like so
 *
 * <pre>
 * <code>
 *  AWSServerlessApplicationRepository client = AWSServerlessApplicationRepository.builder().signer((CognitoAuthorizer) request -> "some token").build();
 * </code>
 * </pre>
 */
@FunctionalInterface
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public interface CognitoAuthorizer extends RequestSigner {

    /**
     * Generate a token that will be added to Authorization in the header of the request during signing
     * 
     * @param request
     *        an immutable view of the request for which to generate a token
     * @return the token to use for signing
     */
    String generateToken(ImmutableRequest<?> request);

    /**
     * @see RequestSigner#sign(SignableRequest)
     */
    @Override
    default void sign(SignableRequest<?> request) {
        request.addHeader("Authorization", generateToken(request));
    }
}

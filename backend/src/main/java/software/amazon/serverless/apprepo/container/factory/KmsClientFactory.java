package software.amazon.serverless.apprepo.container.factory;

import org.glassfish.hk2.api.Factory;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;

import software.amazon.serverless.apprepo.container.config.Env;

/**
 * Factory for {@link KmsClient} for HK2 DI wiring.
 */
public class KmsClientFactory implements Factory<KmsClient> {
  // Creating the DynamoDB client followed AWS SDK v2 best practice to improve Lambda performance:
  // https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/client-configuration-starttime.html
  private static final KmsClient kmsClient = KmsClient.builder()
        .region(Region.of(Env.getRegion()))
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .httpClientBuilder(UrlConnectionHttpClient.builder()).build();

  @Override
  public KmsClient provide() {
    return kmsClient;
  }

  @Override
  public void dispose(KmsClient kmsClient) {

  }
}

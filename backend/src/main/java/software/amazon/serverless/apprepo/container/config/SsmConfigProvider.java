package software.amazon.serverless.apprepo.container.config;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ssm.model.ParameterNotFoundException;
import software.amazon.serverless.ssmcachingclient.SsmParameterCachingClient;

/**
 * Provide config value from AWS SSM.
 */
@RequiredArgsConstructor
public class SsmConfigProvider implements ConfigProvider {
  private static final Duration DEFAULT_PAGINATION_TOKEN_TTL = Duration.ofHours(1);
  private static final String APPLICATIONS_TABLE_NAME_CONFIG_KEY = "ddb/Applications/TableName";
  private static final String KMS_KEY_ID_CONFIG_KEY = "kms/pagination/KeyId";
  private static final String PAGINATION_TOKEN_TTL_IN_SECONDS_CONFIG_KEY =
        "configuration/pagination/TtlInSeconds";

  private final SsmParameterCachingClient ssm;

  @Override
  public String getApplicationsTableName() {
    return ssm.getAsString(APPLICATIONS_TABLE_NAME_CONFIG_KEY);
  }

  @Override
  public String getKmsKeyId() {
    return ssm.getAsString(KMS_KEY_ID_CONFIG_KEY);
  }

  @Override
  public Duration getPaginationTokenTtl() {
    try {
      String ttlString = ssm.getAsString(PAGINATION_TOKEN_TTL_IN_SECONDS_CONFIG_KEY);
      return Duration.ofSeconds(Long.parseLong(ttlString));
    } catch (ParameterNotFoundException e) {
      return DEFAULT_PAGINATION_TOKEN_TTL;
    }
  }
}
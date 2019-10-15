/* eslint no-template-curly-in-string: 0 */

// This file vends configuration values that vary per stage, e.g., test, production.
// The deployment system does string substitution on variables within ${} prior to
// uploading the website to S3.

export const SAR_API_ENDPOINT = '${SAR_BACKEND_ENDPOINT}';

export const COGNITO_USER_POOL_DATA = {
  userPoolId: '${USER_POOL_ID}',
  userPoolWebClientId: '${USER_POOL_WEB_CLIENT_ID}',
};

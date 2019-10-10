const AWS = require('aws-sdk');
const uuid = require('uuid');

const USER_POOL_ID = process.env.COGNITO_USER_POOL_ID;
const cognitoidentityserviceprovider = new AWS.CognitoIdentityServiceProvider({ region: 'us-east-1' });

const createTestUser = async () => {
  const username = `success+${uuid.v4()}@simulator.amazonses.com`;
  const password = uuid.v4();

  await cognitoidentityserviceprovider.adminCreateUser({
    UserPoolId: USER_POOL_ID,
    Username: username,
  }).promise();

  await cognitoidentityserviceprovider.adminSetUserPassword({
    Password: password,
    UserPoolId: USER_POOL_ID,
    Username: username,
    Permanent: true,
  }).promise();
  return { username, password };
};

module.exports = {
  createTestUser,
};

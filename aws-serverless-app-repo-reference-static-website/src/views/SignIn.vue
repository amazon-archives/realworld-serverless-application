<template>
  <b-jumbotron>
    <div style="text-align: center">
      <amplify-authenticator v-bind:authConfig="authConfig"></amplify-authenticator>
    </div>
  </b-jumbotron>
</template>

<script>
export default {
  name: 'signin',
  props: [],
  data() {
    return {
      authConfig: {
        usernameAttributes: 'email',
        signUpConfig: {
          hideAllDefaults: true,
          signUpFields: [
            {
              label: 'Email',
              key: 'email',
              required: true,
              displayOrder: 1,
              type: 'string',
              signUpWith: true,
            },
            {
              label: 'Password',
              key: 'password',
              required: true,
              displayOrder: 2,
              type: 'password',
            },
          ],
        },
      },
    };
  },
};
</script>

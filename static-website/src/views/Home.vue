<template>
  <b-jumbotron
    header="Welcome!"
    lead="A production grade serverless application"
  >

    <hr class="my-4" />
    <p>This is a cover page for the front end module of the awslabs/realworld-serverless-application project. See more details <a href="https://github.com/awslabs/realworld-serverless-application/wiki">here</a>.</p>

    <b-button id="try-demo" variant="primary" to="/applications">Try the Demo</b-button>
    &nbsp;
    <b-button variant="success" href="https://github.com/awslabs/realworld-serverless-application">View Source Code</b-button>
  </b-jumbotron>
</template>

<script>
export default {
  name: 'home',
  components: {},
};
</script>

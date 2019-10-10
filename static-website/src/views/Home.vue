<template>
  <b-jumbotron
    header="Welcome!"
    lead="An open source example serverless application"
  >

    <hr class="my-4" />
    <p>This is a fully open source example of a serverless application written on AWS... TODO</p>

    <b-button id="try-demo" variant="primary" to="/applications">Try the Demo</b-button>
    &nbsp;
    <b-button variant="success" href="https://github.com/awslabs/TODO">View Source Code</b-button>
  </b-jumbotron>
</template>

<script>
export default {
  name: 'home',
  components: {},
};
</script>

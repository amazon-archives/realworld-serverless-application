<template>
  <b-alert id="error-alert" show variant="danger" class="mb-3" v-if="message">Error: {{ message }}</b-alert>
</template>

<script>
export default {
  name: 'error-alert',
  props: {
    message: {
      type: String,
    },
  },
};
</script>

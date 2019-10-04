<template>
  <div id="app">
    <Nav />
    <router-view :key="$route.fullPath" />
  </div>
</template>

<script>
import Nav from '@/components/Nav.vue';

export default {
  name: 'app',
  components: {
    Nav,
  },
};
</script>

<template>
  <b-breadcrumb :items="items" v-if="items" />
</template>

<script>
export default {
  name: 'breadcrumb',
  data() {
    let items = null;
    const meta = (this.$route || {}).meta || {};
    if (meta.breadcrumb) {
      items = this.$route.meta.breadcrumb;
    }
    return {
      items,
    };
  },
};
</script>

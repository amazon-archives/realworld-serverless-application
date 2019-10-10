<template>
  <b-navbar type="dark" variant="dark">
    <b-navbar-brand to="/">Serverless Application Repository</b-navbar-brand>
    <b-navbar-nav class="ml-auto" id="signed-in-nav-bar" v-if="jwtToken">
        <b-nav-item id="signout" @click="onSignout()">SignOut</b-nav-item>
    </b-navbar-nav>
    <b-navbar-nav class="ml-auto" v-else>
        <b-nav-item id="signin" to="/signin">Sign In</b-nav-item>
    </b-navbar-nav>
  </b-navbar>
</template>

<script>
import { mapGetters } from 'vuex';
import { SIGNOUT } from '../store/actions.type';

export default {
  name: 'navBar',
  computed: {
    ...mapGetters(['jwtToken']),
  },
  methods: {
    onSignout() {
      this.$store
        .dispatch(SIGNOUT)
        .then(() => {
          this.$router.push({ name: 'home' });
        });
    },
  },
};
</script>

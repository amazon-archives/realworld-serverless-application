import Vue from 'vue';
import Vuex from 'vuex';
import auth from './auth.module';
import sar from './sar.module';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    sar,
  },
});

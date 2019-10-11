import '@babel/polyfill';
import 'mutationobserver-shim';
import Vue from 'vue';
import './plugins/bootstrap-vue';
import Amplify, * as AmplifyModules from 'aws-amplify';
import { AmplifyPlugin, AmplifyEventBus } from 'aws-amplify-vue';
import App from './App.vue';
import router from './router';
import store from './store';
import SarApi from './services/sar-api';
import { IS_AUTHENTICATED, SIGNOUT } from './store/actions.type';
import { COGNITO_USER_POOL_DATA } from './config';

Vue.config.productionTip = false;

SarApi.init();

Amplify.configure({
  Auth: {
    ...COGNITO_USER_POOL_DATA,
  },
});

Vue.use(AmplifyPlugin, AmplifyModules);

AmplifyEventBus.$on('authState', async (state) => {
  if (state === 'signedOut') {
    await store.dispatch(SIGNOUT);
    router.push({ name: 'home' });
  } else if (state === 'signedIn') {
    await store.dispatch(IS_AUTHENTICATED);
    router.push({ name: 'applications' });
  }
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(route => route.meta && route.meta.checkAuth)) {
    store.dispatch(IS_AUTHENTICATED)
      .then(() => {
        const token = store.getters.jwtToken;
        if (!token) {
          return next({ name: 'signin' });
        }
        return next();
      })
      .catch(() => next({ name: 'signin' }));
  }
  return next();
});

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');

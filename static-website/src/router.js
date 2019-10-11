import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import SignIn from './views/SignIn.vue';
import Applications from './views/Applications.vue';
import NewApplication from './views/NewApplication.vue';
import ViewApplication from './views/ViewApplication.vue';
import EditApplication from './views/EditApplication.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        checkAuth: false,
      },
    },
    {
      path: '/signin',
      name: 'signin',
      component: SignIn,
    },
    {
      path: '/applications',
      name: 'applications',
      component: Applications,
      meta: {
        breadcrumb: [
          {
            text: 'My applications',
            active: true,
          },
        ],
        checkAuth: true,
      },
    },
    {
      path: '/applications/new',
      name: 'new-application',
      component: NewApplication,
      meta: {
        breadcrumb: [
          {
            text: 'My applications',
            to: { name: 'applications' },
          },
          {
            text: 'Publish an application',
            active: true,
          },
        ],
        checkAuth: true,
      },
    },
    {
      path: '/applications/:id',
      name: 'view-application',
      component: ViewApplication,
      meta: {
        breadcrumb: [
          {
            text: 'My applications',
            to: { name: 'applications' },
          },
          {
            text: 'Application details',
            active: true,
          },
        ],
        checkAuth: true,
      },
    },
    {
      path: '/applications/:id/edit',
      name: 'edit-application',
      component: EditApplication,
      meta: {
        breadcrumb: [
          {
            text: 'My applications',
            to: { name: 'applications' },
          },
          {
            text: 'Edit application',
            active: true,
          },
        ],
        checkAuth: true,
      },
    },
  ],
});

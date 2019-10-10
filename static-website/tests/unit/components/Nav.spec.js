/* eslint no-unused-expressions: 0 */

import Vuex from 'vuex';
import { expect } from 'chai';
import sinon from 'sinon';
import { mount, createLocalVue } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import Nav from '@/components/Nav.vue';

const localVue = createLocalVue();
localVue.use(BootstrapVue);
localVue.use(Vuex);

describe('Nav.vue', () => {
  describe('when a user is signed in', () => {
    let getters;
    let actions;
    let store;

    beforeEach(() => {
      actions = {
        signout: sinon.fake(),
      };
      getters = {
        jwtToken: () => sinon.match.any,
        error: sinon.fake(),
      };
      store = new Vuex.Store({
        modules: {
          auth: {
            actions,
            getters,
          },
        },
      });
    });
    it('renders with a SignOut button', () => {
      const wrapper = mount(Nav, {
        store,
        localVue,
      });
      expect(wrapper.contains('#signout')).to.equal(true);
    });

    it('dispatches SignOut action when button clicked', () => {
      const wrapper = mount(Nav, {
        store,
        localVue,
      });
      expect(wrapper.contains('#signout')).to.equal(true);
      wrapper.find('#signout a').trigger('click');
      expect(actions.signout).to.have.been.called;
    });
  });

  describe('when a user is not signed in', () => {
    it('renders without a SignOut button', () => {
      const getters = {
        jwtToken: () => null,
        error: sinon.fake(),
      };
      const store = new Vuex.Store({
        modules: {
          auth: {
            getters,
          },
        },
      });
      const wrapper = mount(Nav, {
        store,
        localVue,
      });
      expect(wrapper.contains('#signout')).to.equal(false);
      expect(wrapper.contains('#signin')).to.equal(true);
    });
  });
});

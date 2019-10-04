/* eslint no-unused-expressions: 0 */

import Vuex from 'vuex';
import { expect } from 'chai';
import sinon from 'sinon';
import { mount, createLocalVue, RouterLinkStub } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import EditApplication from '@/views/EditApplication.vue';

// render bootstrap components
const localVue = createLocalVue();
localVue.use(BootstrapVue);

const APPLICATION = {
  applicationId: 'test-app-1',
  description: 'test-app-1 description',
  author: 'test-author',
  creationTime: new Date('9/8/2019'),
  homePageUrl: 'https://aws.amazon.com/serverless',
};

let actions;
let store;
describe('EditApplication.vue', () => {
  const route = {
    params: { id: APPLICATION.applicationId },
  };

  it('should render the form', async () => {
    actions = {
      getApplication: sinon.stub().resolves(APPLICATION),
    };
    const wrapper = mount(EditApplication, {
      localVue,
      stubs: {
        RouterLink: RouterLinkStub,
      },
      mocks: {
        $route: route,
        $store: new Vuex.Store({
          actions,
        }),
      },
    });

    await wrapper.vm.$nextTick();
    expect(actions.getApplication).to.have.been.called;
    expect(wrapper.find('h2').text()).to.equal('Edit test-app-1');
    expect(wrapper.contains('#error-alert')).to.equal(false);

    expect(wrapper.contains('#author-group')).to.equal(true);
    expect(wrapper.find('#author-group label').text()).to.equal('Author');
    expect(wrapper.contains('#author-group input#author')).to.equal(true);
    expect(wrapper.find('#author-group input#author').props().value).to.equal(APPLICATION.author);

    expect(wrapper.contains('#homepage-url-group')).to.equal(true);
    expect(wrapper.find('#homepage-url-group label').text()).to.equal('Home page - optional');
    expect(wrapper.contains('#homepage-url-group input#homepage-url')).to.equal(true);
    expect(wrapper.find('#homepage-url-group input#homepage-url').props().value).to.equal(APPLICATION.homePageUrl);

    expect(wrapper.contains('#description-group')).to.equal(true);
    expect(wrapper.find('#description-group label').text()).to.equal('Description');
    expect(wrapper.contains('#description-group textarea#description')).to.equal(true);
    expect(wrapper.find('#description-group textarea#description').props().value).to.equal(APPLICATION.description);
  });

  describe('on successful update', () => {
    beforeEach(() => {
      actions = {
        getApplication: sinon.stub().resolves(APPLICATION),
        updateApplication: sinon.stub().resolves(APPLICATION),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('should redirect to ViewApplication.vue', async () => {
      const router = {
        push: sinon.spy(),
      };
      const wrapper = mount(EditApplication, {
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
        mocks: {
          $router: router,
          $route: route,
          $store: store,
        },
      });

      await wrapper.vm.$nextTick();
      wrapper.find('form').trigger('submit.prevent');

      await wrapper.vm.$nextTick();
      expect(actions.updateApplication).to.have.been.calledWith(sinon.match.any, {
        applicationId: APPLICATION.applicationId,
        author: APPLICATION.author,
        homePageUrl: APPLICATION.homePageUrl,
        description: APPLICATION.description,
      });
      expect(router.push).to.have.been.calledWith({ name: 'view-application', params: { id: 'test-app-1' } });
      expect(wrapper.contains('#error-alert')).to.equal(false);
    });
  });
  describe('on failed update', () => {
    beforeEach(() => {
      actions = {
        getApplication: sinon.stub().resolves(APPLICATION),
        updateApplication: sinon.stub().rejects(new Error('oof!')),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('should display an error', async () => {
      const router = {
        push: sinon.spy(),
      };
      const wrapper = mount(EditApplication, {
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
        mocks: {
          $router: router,
          $route: route,
          $store: store,
        },
      });

      await wrapper.vm.$nextTick();
      wrapper.find('form').trigger('submit.prevent');

      await wrapper.vm.$nextTick();
      expect(actions.updateApplication).to.have.been.calledWith(sinon.match.any, {
        applicationId: APPLICATION.applicationId,
        author: APPLICATION.author,
        homePageUrl: APPLICATION.homePageUrl,
        description: APPLICATION.description,
      });
      expect(router.push).to.not.have.been.called;

      expect(wrapper.contains('#error-alert')).to.equal(true);
      expect(wrapper.find('#error-alert').text()).to.include('oof!');
    });
  });
});

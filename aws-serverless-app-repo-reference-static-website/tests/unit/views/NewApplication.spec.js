/* eslint no-unused-expressions: 0 */

import Vuex from 'vuex';
import { expect } from 'chai';
import sinon from 'sinon';
import { mount, createLocalVue, RouterLinkStub } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import NewApplication from '@/views/NewApplication.vue';

// render bootstrap components
const localVue = createLocalVue();
localVue.use(BootstrapVue);
localVue.use(Vuex);

const APPLICATION = {
  applicationId: 'test-app-1',
  description: 'test-app-1 description',
  author: 'test-author',
  creationTime: new Date('9/8/2019'),
  homePageUrl: 'https://aws.amazon.com/serverless',
};

function submitForm(wrapper) {
  wrapper.find('#application-id').setValue(APPLICATION.applicationId);
  wrapper.find('#description').setValue(APPLICATION.description);
  wrapper.find('#author').setValue(APPLICATION.author);
  wrapper.find('#homepage-url').setValue(APPLICATION.homePageUrl);

  wrapper.find('form').trigger('submit.prevent');
}
let actions;
let store;

describe('NewApplication.vue', () => {
  it('should render the form', () => {
    const wrapper = mount(NewApplication, {
      localVue,
      stubs: {
        RouterLink: RouterLinkStub,
      },
    });

    expect(wrapper.find('h2').text()).to.equal('Publish an application');
    expect(wrapper.contains('#error-alert')).to.equal(false);
    expect(wrapper.find('#cancel').attributes().href).to.equal('/applications');

    expect(wrapper.contains('#application-id-group')).to.equal(true);
    expect(wrapper.find('#application-id-group label').text()).to.equal('Application name');
    expect(wrapper.contains('#application-id-group input#application-id')).to.equal(true);

    expect(wrapper.contains('#author-group')).to.equal(true);
    expect(wrapper.find('#author-group label').text()).to.equal('Author');
    expect(wrapper.contains('#author-group input#author')).to.equal(true);

    expect(wrapper.contains('#homepage-url-group')).to.equal(true);
    expect(wrapper.find('#homepage-url-group label').text()).to.equal('Home page - optional');
    expect(wrapper.contains('#homepage-url-group input#homepage-url')).to.equal(true);

    expect(wrapper.contains('#description-group')).to.equal(true);
    expect(wrapper.find('#description-group label').text()).to.equal('Description');
    expect(wrapper.contains('#description-group textarea#description')).to.equal(true);
  });

  describe('on successful create', () => {
    beforeEach(() => {
      actions = {
        createApplication: sinon.stub().resolves(APPLICATION),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('should redirect to ViewApplication.vue', async () => {
      const router = {
        push: sinon.spy(),
      };
      const wrapper = mount(NewApplication, {
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
        mocks: {
          $router: router,
          $store: store,
        },
      });

      submitForm(wrapper);

      await wrapper.vm.$nextTick();

      expect(actions.createApplication).to.have.been.calledWith(sinon.match.any, {
        applicationId: APPLICATION.applicationId,
        author: APPLICATION.author,
        homePageUrl: APPLICATION.homePageUrl,
        description: APPLICATION.description,
      });
      expect(router.push).to.have.been.calledWith({ name: 'view-application', params: { id: 'test-app-1' } });
      expect(wrapper.contains('#error-alert')).to.equal(false);
    });
  });
  describe('on failed create', () => {
    beforeEach(() => {
      actions = {
        createApplication: sinon.stub().rejects(new Error('nope!')),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('should display an error', async () => {
      const router = {
        push: sinon.spy(),
      };
      const wrapper = mount(NewApplication, {
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
        mocks: {
          $router: router,
          $store: store,
        },
      });

      submitForm(wrapper);

      await wrapper.vm.$nextTick();

      expect(actions.createApplication).to.have.been.calledWith(sinon.match.any, {
        applicationId: APPLICATION.applicationId,
        author: APPLICATION.author,
        homePageUrl: APPLICATION.homePageUrl,
        description: APPLICATION.description,
      });
      expect(router.push).to.not.have.been.called;

      expect(wrapper.contains('#error-alert')).to.equal(true);
      expect(wrapper.find('#error-alert').text()).to.include('nope!');
    });
  });
});

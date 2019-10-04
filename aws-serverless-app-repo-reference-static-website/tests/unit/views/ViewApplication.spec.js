/* eslint no-unused-expressions: 0 */

import Vuex from 'vuex';
import { expect } from 'chai';
import sinon from 'sinon';
import { mount, createLocalVue, RouterLinkStub } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import SarApi from '@/services/sar-api';
import ViewApplication from '@/views/ViewApplication.vue';

// render bootstrap components
const localVue = createLocalVue();
localVue.use(BootstrapVue);

const VALID_APPLICATION_ID = 'test-app-1';
const INVALID_APPLICATION_ID = 'test-app-notfound';
const APPLICATION = {
  applicationId: VALID_APPLICATION_ID,
  description: 'test-app-1 description',
  author: 'test-author',
  creationTime: new Date('9/8/2019'),
  homePageUrl: 'https://aws.amazon.com/serverless',
};

let actions;
let store;

describe('ViewApplication.vue', () => {
  describe('with a valid application', () => {
    const applicationId = VALID_APPLICATION_ID;
    const route = {
      params: { id: applicationId },
    };

    beforeEach(() => {
      sinon.stub(SarApi, 'getApplication').resolves(APPLICATION);
      sinon.stub(SarApi, 'deleteApplication');
      actions = {
        getApplication: sinon.stub().resolves(APPLICATION),
        deleteApplication: sinon.stub(),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('should render the application details', async () => {
      const wrapper = mount(ViewApplication, {
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
        mocks: {
          $route: route,
          $store: store,
        },
      });

      await wrapper.vm.$nextTick();
      expect(actions.getApplication).to.have.been.calledWith(sinon.match.any, applicationId);
      expect(actions.deleteApplication).to.not.have.been.called;

      expect(wrapper.find('h2').text()).to.equal(applicationId);
      expect(wrapper.contains('#error-alert')).to.equal(false);

      expect(wrapper.find('#delete').text()).to.equal('Delete');
      expect(wrapper.find('#edit').text()).to.equal('Edit');

      expect(wrapper.text()).to.include(APPLICATION.author);
      expect(wrapper.text()).to.include(APPLICATION.homePageUrl);
      expect(wrapper.text()).to.include(APPLICATION.creationTime.toLocaleString('en-US'));
      expect(wrapper.text()).to.include(APPLICATION.description);
    });
  });
});

describe('with an invalid application', () => {
  const applicationId = INVALID_APPLICATION_ID;
  const route = {
    params: { id: applicationId },
  };

  beforeEach(() => {
    actions = {
      getApplication: sinon.stub().rejects(new Error('what application?')),
      deleteApplication: sinon.stub(),
    };
    store = new Vuex.Store({
      actions,
    });
  });

  it('should display an error', async () => {
    const wrapper = mount(ViewApplication, {
      localVue,
      stubs: {
        RouterLink: RouterLinkStub,
      },
      mocks: {
        $route: route,
        $store: store,
      },
    });

    await wrapper.vm.$nextTick();
    expect(actions.deleteApplication).to.not.have.been.called;

    expect(wrapper.contains('#error-alert')).to.equal(true);
    expect(wrapper.find('#error-alert').text()).to.include('what application?');

    expect(wrapper.find('#error').exists()).to.equal(false);
    expect(wrapper.find('#delete').exists()).to.equal(false);
  });
});

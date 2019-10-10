/* eslint no-unused-expressions: 0 */

import Vuex from 'vuex';
import { expect } from 'chai';
import sinon from 'sinon';
import {
  mount, createLocalVue, RouterLinkStub,
} from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import Applications from '@/views/Applications.vue';

// render bootstrap components
const localVue = createLocalVue();
localVue.use(BootstrapVue);
localVue.use(Vuex);

const APPLICATIONS = [
  {
    applicationId: 'test-app-1',
    description: 'test-app-1 description',
    author: 'test-author',
    creationTime: new Date('9/8/2019'),
    homePageUrl: 'https://aws.amazon.com/serverless',
  },
  {
    applicationId: 'test-app-2',
    description: 'test-app-2 description',
    author: 'test-author',
    creationTime: new Date('3/25/2019'),
    homePageUrl: 'https://aws.amazon.com/serverless',
  },
  {
    applicationId: 'test-app-3',
    description: 'test-app-3 description',
    author: 'test-author',
    creationTime: new Date('8/28/2019'),
    homePageUrl: 'https://aws.amazon.com/serverless',
  },
];

let actions;
let store;
describe('Applications.vue', () => {
  describe('with no applications', () => {
    beforeEach(() => {
      actions = {
        listApplications: sinon.stub().resolves([]),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('renders a message to the user', async () => {
      const wrapper = mount(Applications, {
        localVue,
        store,
        stubs: {
          RouterLink: RouterLinkStub,
        },
      });

      await wrapper.vm.$nextTick();

      expect(actions.listApplications).to.have.been.called;
      expect(wrapper.contains('#error-alert')).to.equal(false);

      expect(wrapper.text()).to.include('My applications');
      expect(wrapper.text()).to.include("Once you publish an application, you'll see it here");
    });
  });
  describe('with applications', () => {
    beforeEach(() => {
      actions = {
        listApplications: sinon.stub().resolves(APPLICATIONS),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('renders the applications table', async () => {
      const wrapper = mount(Applications, {
        store,
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
      });

      await wrapper.vm.$nextTick();

      expect(actions.listApplications).to.have.been.called;
      expect(wrapper.contains('#error-alert')).to.equal(false);

      expect(wrapper.text()).to.include('My applications');
      expect(wrapper.text()).not.to.include("Once you publish an application, you'll see it here");

      const rows = wrapper.findAll('tr');
      expect(rows.length).to.equal(4);

      const headerRow = rows.at(0);
      const headerCols = headerRow.findAll('th');
      expect(headerCols.length).to.equal(3);
      expect(headerCols.at(0).text()).to.equal('Application name');
      expect(headerCols.at(1).text()).to.equal('Description');
      expect(headerCols.at(2).text()).to.equal('Creation time');

      const app1Row = rows.at(1);
      const app1Cols = app1Row.findAll('td');
      expect(app1Cols.at(0).text()).to.equal('test-app-1');
      expect(app1Cols.at(0).find(RouterLinkStub).props().to).to.deep.include({ name: 'view-application', params: { id: 'test-app-1' } });
      expect(app1Cols.at(1).text()).to.equal('test-app-1 description');
      expect(app1Cols.at(2).text()).to.equal('9/8/2019');

      const app2Row = rows.at(2);
      const app2Cols = app2Row.findAll('td');
      expect(app2Cols.at(0).text()).to.equal('test-app-2');
      expect(app2Cols.at(0).find(RouterLinkStub).props().to).to.deep.include({ name: 'view-application', params: { id: 'test-app-2' } });
      expect(app2Cols.at(1).text()).to.equal('test-app-2 description');
      expect(app2Cols.at(2).text()).to.equal('3/25/2019');

      const app3Row = rows.at(3);
      const app3Cols = app3Row.findAll('td');
      expect(app3Cols.at(0).text()).to.equal('test-app-3');
      expect(app3Cols.at(0).find(RouterLinkStub).props().to).to.deep.include({ name: 'view-application', params: { id: 'test-app-3' } });
      expect(app3Cols.at(1).text()).to.equal('test-app-3 description');
      expect(app3Cols.at(2).text()).to.equal('8/28/2019');
    });
  });
  describe('on error', () => {
    beforeEach(() => {
      actions = {
        listApplications: sinon.stub().rejects(new Error('boom!')),
      };
      store = new Vuex.Store({
        actions,
      });
    });

    it('renders the error message', async () => {
      const wrapper = mount(Applications, {
        store,
        localVue,
        stubs: {
          RouterLink: RouterLinkStub,
        },
      });

      await wrapper.vm.$nextTick();
      expect(actions.listApplications).to.have.been.called;

      expect(wrapper.contains('#error-alert')).to.equal(true);
      expect(wrapper.find('#error-alert').text()).to.include('boom!');

      expect(wrapper.text()).to.include('My applications');
      expect(wrapper.text()).not.to.include("Once you publish an application, you'll see it here");

      const rows = wrapper.findAll('tr');
      expect(rows.length).to.equal(1);

      const headerRow = rows.at(0);
      const headerCols = headerRow.findAll('th');
      expect(headerCols.length).to.equal(3);
      expect(headerCols.at(0).text()).to.equal('Application name');
      expect(headerCols.at(1).text()).to.equal('Description');
      expect(headerCols.at(2).text()).to.equal('Creation time');
    });
  });
});

/* eslint no-unused-expressions: 0 */

import { expect } from 'chai';
import { mount, createLocalVue } from '@vue/test-utils';
import BootstrapVue from 'bootstrap-vue';
import Home from '@/views/Home.vue';

// render bootstrap components
const localVue = createLocalVue();
localVue.use(BootstrapVue);

describe('Home.vue', () => {
  it('renders', () => {
    const wrapper = mount(Home, {
      localVue,
      propsData: {},
    });
    expect(wrapper.text()).to.include('Welcome!');
  });
});

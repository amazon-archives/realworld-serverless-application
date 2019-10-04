import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import { SAR_API_ENDPOINT } from '../config';

const SarApi = {
  init() {
    Vue.use(VueAxios, axios);
    Vue.axios.defaults.baseURL = SAR_API_ENDPOINT;
  },
  listApplications(jwtToken) {
    return Vue.axios.get('/applications', {
      headers: {
        Authorization: jwtToken,
      },
    })
      .then(response => response.data.applications);
  },
  createApplication(jwtToken, request) {
    return Vue.axios.post('/applications', {
      applicationId: request.applicationId,
      author: request.author,
      description: request.description,
      homePageUrl: request.homePageUrl,
    }, {
      headers: {
        Authorization: jwtToken,
      },
    })
      .then(response => response.data);
  },
  getApplication(jwtToken, applicationId) {
    return Vue.axios.get(`/applications/${applicationId}`, {
      headers: {
        Authorization: jwtToken,
      },
    }).then(response => response.data);
  },
  updateApplication(jwtToken, request) {
    return Vue.axios.patch(`/applications/${request.applicationId}`, {
      author: request.author,
      description: request.description,
      homePageUrl: request.homePageUrl,
    }, {
      headers: {
        Authorization: jwtToken,
      },
    })
      .then(response => response.data);
  },
  deleteApplication(jwtToken, applicationId) {
    return Vue.axios.delete(`/applications/${applicationId}`, {
      headers: {
        Authorization: jwtToken,
      },
    })
      .then(response => response.data);
  },
};

export default SarApi;

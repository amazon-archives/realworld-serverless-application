import {
  LIST_APPLICATIONS, GET_APPLICATION, CREATE_APPLICATION, UPDATE_APPLICATION,
  DELETE_APPLICATION,
} from './actions.type';
import SarApi from '../services/sar-api';

const actions = {
  [LIST_APPLICATIONS](context) {
    return SarApi.listApplications(context.getters.jwtToken);
  },
  [GET_APPLICATION](context, applicationId) {
    return SarApi.getApplication(context.getters.jwtToken, applicationId);
  },
  [CREATE_APPLICATION](context, request) {
    return SarApi.createApplication(context.getters.jwtToken, request);
  },
  [UPDATE_APPLICATION](context, request) {
    return SarApi.updateApplication(context.getters.jwtToken, request);
  },
  [DELETE_APPLICATION](context, applicationId) {
    return SarApi.deleteApplication(context.getters.jwtToken, applicationId);
  },
};

export default {
  actions,
};

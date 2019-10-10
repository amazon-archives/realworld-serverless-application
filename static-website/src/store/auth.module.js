/* eslint no-shadow: ["error", { "allow": ["state"] }] */

import { AuthClass } from '@aws-amplify/auth';
import {
  SIGNOUT, IS_AUTHENTICATED,
} from './actions.type';
import { SET_ERROR, SET_TOKEN } from './mutations.type';
import { COGNITO_USER_POOL_DATA } from '../config';


const Auth = new AuthClass(COGNITO_USER_POOL_DATA);

const state = {
  error: null,
  jwtToken: null,
};

const getters = {
  jwtToken(state) {
    return state.jwtToken;
  },
  error(state) {
    return state.error;
  },
};

const actions = {
  [IS_AUTHENTICATED](context) {
    return new Promise((resolve, reject) => {
      Auth.currentSession()
        .then((user) => {
          if (user.isValid()) {
            context.commit(SET_TOKEN, user.getIdToken().getJwtToken());
            resolve();
          } else {
            reject();
          }
        })
        .catch((e) => {
          context.commit(SET_TOKEN, null);
          context.commit(SET_ERROR, e);
          reject(e);
        });
    });
  },
  [SIGNOUT](context) {
    return Auth.signOut().then(() => {
      context.commit(SET_TOKEN, null);
      context.commit(SET_ERROR, null);
    });
  },
};

const mutations = {
  [SET_ERROR](state, error) {
    state.error = error;
  },
  [SET_TOKEN](state, token) {
    state.jwtToken = token;
  },
};

export default {
  state,
  actions,
  mutations,
  getters,
};

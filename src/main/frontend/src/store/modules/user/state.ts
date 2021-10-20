/* eslint no-shadow: ["error", { "allow": ["state"] }] */

import { Commit } from 'vuex';

export interface State {
  isAuth: false;
  userData: any;
}

const state: State = {
  isAuth: false,
  userData: {},
};

export default {
  namespaced: true,
  state,
  mutations: {
    SET_USER_TO_STORE: (state: State, value: any) => {
      state.userData = value.profile;
    },
    SET_AUTH_STATE: (state: State, value: any) => {
      state.isAuth = value;
    },
    RESET_USER_STATE: (state: State) => {
      state.userData = {};
    },
    RESET_IS_AUTHENTICATED: (state: State) => {
      state.isAuth = false;
    },
  },
  actions: {
    setUserToStore: (context: { commit: Commit }, value: any): void => {
      context.commit('SET_USER_TO_STORE', value);
    },
    setAuthState: (context: { commit: Commit }, value: boolean): void => {
      context.commit('SET_AUTH_STATE', value);
    },
  },
  getters: {
    isAuthenticated(state: State): boolean {
      return state.isAuth;
    },
    getUserRole(state: State): boolean {
      return state.userData.role;
    },
  },
};

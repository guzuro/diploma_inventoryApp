/* eslint no-shadow: ["error", { "allow": ["state"] }] */

import { Commit } from 'vuex';

export interface UserState {
  isAuth: false;
  userData: any;
}

const state: UserState = {
  isAuth: false,
  userData: null,
};

export default {
  namespaced: true,
  state,
  mutations: {
    SET_USER_TO_STORE: (state: UserState, value: any) => {
      state.userData = value;
    },
    SET_AUTH_STATE: (state: UserState, value: any) => {
      state.isAuth = value;
    },
    RESET_USER_STATE: (state: UserState) => {
      state.userData = null;
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
    isAuthenticated(state: UserState): boolean {
      return state.isAuth;
    },
    getUserRole(state: UserState): boolean {
      return state.userData.role;
    },
  },
};

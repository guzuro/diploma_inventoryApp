/* eslint-disable no-shadow */
import { Commit } from 'vuex';

export interface CompanyState {
  company: null;
}

const state: CompanyState = {
  company: null,
};

export default {
  namespaced: true,
  state,
  mutations: {
    SET_COMPANY_TO_STORE: (state: CompanyState, value: any) => {
      state.company = value;
    },
    RESET_COMPANY_STATE: (state: CompanyState): void => {
      state.company = null;
    },
  },
  actions: {
    setCompanyToStore: (context: { commit: Commit }, value: any): void => {
      context.commit('SET_COMPANY_TO_STORE', value);
    },
  },
  getters: {
    getCompany(): any {
      return state.company;
    },
  },
};

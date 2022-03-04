import Vue from 'vue';
import Vuex from 'vuex';
import userModule from './modules/user/state';
import productsModule from './modules/products/state';

Vue.use(Vuex);

export default new Vuex.Store({
  state: { },
  mutations: {},
  actions: {},
  modules: {
    userModule,
    productsModule,
  },
});

import Vue from 'vue';
import Vuex from 'vuex';
import userModule from './modules/user/state';
import productsModule from './modules/products/state';
import companyModule from './modules/company/state';
import configModule from './modules/config/state';
import suppliersModule from './modules/suppliers/state';

Vue.use(Vuex);

export default new Vuex.Store({
  state: { },
  mutations: {},
  actions: {},
  modules: {
    userModule,
    productsModule,
    companyModule,
    configModule,
    suppliersModule,
  },
});

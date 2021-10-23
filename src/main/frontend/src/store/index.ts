import Vue from 'vue';
import Vuex from 'vuex';
import userModule from './modules/user/state';
import productsModule from './modules/products/state';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    defaultUsers: [
      {
        login: 'admin',
        password: 'admin',
        profile: {
          name: 'Jora',
          phone: '+79994524548',
          email: 'admin@email.ru',
          company: 'OOO "STROIM DOMA"',
          role: 'admin',
          orders: [],
        },
      },
    ],
    userState: {},
  },
  mutations: {},
  actions: {},
  modules: {
    userModule,
    productsModule,
  },
});

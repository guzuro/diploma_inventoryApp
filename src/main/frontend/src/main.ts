import Buefy from 'buefy';
import VueApexCharts from 'vue-apexcharts';
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import { extend } from 'vee-validate';
import { email, required, min } from 'vee-validate/dist/rules';
import App from './App.vue';
import router from './router/router';
import store from './store';
import 'buefy/dist/buefy.css';
import './assets/styles.scss';

Vue.use(Buefy);
Vue.use(VueApexCharts);
Vue.use(VueAxios, axios);

extend('email', email);
extend('required', required);
extend('min', min);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');

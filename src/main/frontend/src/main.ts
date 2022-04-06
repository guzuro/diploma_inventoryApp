import VueApexCharts from 'vue-apexcharts';
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import { extend } from 'vee-validate';
import { email, required, min } from 'vee-validate/dist/rules';
import Antd from 'ant-design-vue';
import { initializeApp } from 'firebase/app';
import App from './App.vue';
import router from './router/router';
import store from './store';
import './assets/styles.scss';

import 'ant-design-vue/dist/antd.css';
import '@/assets/tailwind.css';

// Import the functions you need from the SDKs you need

Vue.use(Antd);

Vue.use(VueApexCharts);
Vue.use(VueAxios, axios);

extend('email', email);
extend('required', required);
extend('min', min);
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: 'AIzaSyDWebP0d5OJ2Z20XXYOO3Vkd2yqP9nEegE',
  authDomain: 'diploma-main.firebaseapp.com',
  projectId: 'diploma-main',
  storageBucket: 'diploma-main.appspot.com',
  messagingSenderId: '1095685828601',
  appId: '1:1095685828601:web:7f43f6a47dc0424d361728',
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');

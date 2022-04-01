import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from '@/router/routes';
import store from '@/store';
import AuthService from '@/services/AuthService';

Vue.use(VueRouter);

const router = new VueRouter({
  linkExactActiveClass: 'is-active',
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.name !== 'Login' && to.name !== 'Main') {
    if ((store.state as any).userModule.userData === null) {
      AuthService.checkLogin()
        .then(() => {
          if ((store.state as any).configModule.config.sale === null) {
            store.dispatch('configModule/REQUEST_SALES');
          }
          if ((store.state as any).configModule.config.category === null) {
            store.dispatch('configModule/REQUEST_CATEGORIES');
          }
          if ((store.state as any).configModule.config.warehouse === null) {
            store.dispatch('configModule/REQUEST_WAREHOUSE');
          }
        })
        .then(next)
        .catch((e: any) => {
          next({ name: 'Login' });
        });
    }
    // if ((store.state as any).configModule.config.sale === null) {
    //   store.dispatch('configModule/REQUEST_SALES');
    // }
    // if ((store.state as any).configModule.config.category === null) {
    //   store.dispatch('configModule/REQUEST_CATEGORIES');
    // }
    // if ((store.state as any).configModule.config.warehouse === null) {
    //   store.dispatch('configModule/REQUEST_WAREHOUSE');
    // }
    next();
  } else {
    next();
  }
});

export default router;

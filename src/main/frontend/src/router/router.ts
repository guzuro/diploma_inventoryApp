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
router.afterEach((to, from) => {
  console.log(to);
});

router.beforeEach((to, from, next) => {
  if (to.name !== 'Login' && to.name !== 'Main') {
    if ((store.state as any).userModule.userData === null) {
      AuthService.checkLogin()
        .then(() => next())
        .catch((e: any) => {
          next({ name: 'Login' });
        });
    }
  }
  next();
});

export default router;

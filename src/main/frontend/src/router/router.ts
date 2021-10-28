import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from '@/router/routes';

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
  next();
  // TODO
  // const routeMeta = to.meta;
  // if (to.name !== 'Login' && to.name !== 'Main') {
  //   if (routeMeta && routeMeta.authRole && store.getters['userModule/isAuthenticated']
  //   && routeMeta.authRole.includes(store.getters['userModule/getUserRole'])) {
  //     next();
  //   } else {
  //     next({ path: '/login' });
  //   }
  // } else {
  //   next();
  // }

  // if (!to.path.includes('dashboard')) {
  //   store.commit('userModule/RESET_IS_AUTHENTICATED');
  //   store.commit('userModule/RESET_USER_STATE');
  //   next();
  // }
});

export default router;

import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from '@/router/routes';
import store from '@/store';
import AuthService from '@/services/AuthService';
import SalesService from '@/services/Config/SalesService';
import { Sale } from '@/types/Sale';

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
        .then(() => {
          if ((store.state as any).configModule.config.sale === null) {
            // TODO MOVE TO STORE ACTION
            SalesService.getSales({ company_id: (store.state as any).companyModule.company.id }).then((response: Array<Sale>) => {
              store.commit('configModule/UPDATE_CONFIG_FIELDS', { field: 'sale', value: response });
              next();
            });
          } else {
            next();
          }
        })
        .catch((e: any) => {
          next({ name: 'Login' });
        });
    } else if ((store.state as any).configModule.config.sale === null) {
      // TODO MOVE TO STORE ACTION
      SalesService.getSales({ company_id: (store.state as any).companyModule.company.id }).then((response: Array<Sale>) => {
        store.commit('configModule/UPDATE_CONFIG_FIELDS', { field: 'sale', value: response });
        next();
      });
    } else {
      next();
    }
  }
  next();
});

export default router;

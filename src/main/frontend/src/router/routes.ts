import { RouteConfig } from 'vue-router';

const routes: Array<RouteConfig> = [
  {
    component: () => import('../views/auth.vue'),
    path: '/login',
    name: 'Login',
  },
  {
    component: () => import('../layouts/DashboardLayout.vue'),
    path: '',
    children: [
      {
        component: () => import('../views/dashboard.vue'),
        path: '/office/:userId/dashboard/',
        name: 'Dashboard',
      },
      {
        component: () => import('../views/general/profile.vue'),
        path: '/office/:userId/profile',
        name: 'Profile',
      },
      {
        component: () => import('../views/general/company-profile.vue'),
        path: '/office/:userId/company-profile',
        name: 'CompanyProfile',
      },
      // {
      //   component: () => import('../views/categories.vue'),
      //   path: 'categories',
      //   name: 'Categories',
      // },
      // {
      //   component: () => import('../views/product-list.vue'),
      //   path: 'products',
      //   name: 'Products',
      // },
      // {
      //   component: () => import('../views/product.vue'),
      //   path: 'products/:actionType',
      //   name: 'Product',
      // },
    ],
  },
];

export default routes;

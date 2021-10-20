import { RouteConfig } from 'vue-router';

const routes: Array<RouteConfig> = [
  {
    component: () => import('../views/auth.vue'),
    path: '/login',
    name: 'Login',
  },
  {
    component: () => import('../layouts/DashboardLayout.vue'),
    path: '/:role/dashboard/',
    children: [
      {
        component: () => import('../views/dashboard.vue'),
        path: '',
        name: 'Dashboard',
      },
      {
        component: () => import('../views/categories.vue'),
        path: 'categories',
        name: 'Categories',
      },
      {
        component: () => import('../views/items.vue'),
        path: 'items',
        name: 'Items',
      },
    ],
  },
];

export default routes;

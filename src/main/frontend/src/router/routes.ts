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
      {
        component: () => import('../views/employes/employes.vue'),
        path: '/office/:userId/employes',
        name: 'Employes',
      },
      {
        component: () => import('../views/config/warehouses.vue'),
        path: '/office/:userId/warehouses',
        name: 'Warehouses',
      },
      {
        component: () => import('../views/config/categories.vue'),
        path: '/office/:userId/categories',
        name: 'Categories',
      },
      {
        component: () => import('../views/config/sales.vue'),
        path: '/office/:userId/sales',
        name: 'Sales',
      },
      {
        component: () => import('../views/config/employee-roles.vue'),
        path: '/office/:userId/employee-roles',
        name: 'EmployeeRoles',
      },
      {
        component: () => import('../views/product-list.vue'),
        path: '/office/:userId/products',
        name: 'Products',
      },
      {
        component: () => import('../views/product.vue'),
        path: '/office/:userId/products/:actionType',
        name: 'Product',
      },
    ],
  },
];

export default routes;

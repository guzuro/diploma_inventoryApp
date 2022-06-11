import { RouteConfig } from 'vue-router';

const routes: Array<RouteConfig> = [
  {
    component: () => import('../views/auth.vue'),
    path: '/login',
    name: 'Login',
  },
  {
    component: () => import('../layouts/OfficeLayout.vue'),
    path: '/office/',
    children: [
      {
        component: () => import('../views/general/profile.vue'),
        path: ':userId/profile',
        name: 'Profile',
      },
      {
        component: () => import('../views/general/company-profile.vue'),
        path: ':userId/company-profile',
        name: 'CompanyProfile',
      },
      {
        component: () => import('../views/employes/employes.vue'),
        path: ':userId/employes',
        name: 'Employes',
      },
      {
        component: () => import('../views/config/warehouses.vue'),
        path: ':userId/warehouses',
        name: 'Warehouses',
      },
      {
        component: () => import('../views/config/categories.vue'),
        path: ':userId/categories',
        name: 'Categories',
      },
      {
        component: () => import('../views/config/sales.vue'),
        path: ':userId/sales',
        name: 'Sales',
      },
      {
        component: () => import('../views/config/employee-roles.vue'),
        path: ':userId/employee-roles',
        name: 'EmployeeRoles',
      },
    ],
  },
  {
    component: () => import('../layouts/DashboardLayout.vue'),
    path: '',
    children: [
      {
        component: () => import('../views/dashboard.vue'),
        path: '',
        name: 'Dashboard',
      },
      {
        component: () => import('../views/product-list.vue'),
        path: '/products',
        name: 'Products',
      },
      {
        component: () => import('../views/product.vue'),
        path: '/product/:actionType',
        name: 'Product',
      },
      {
        component: () => import('../views/suppliers-list.vue'),
        path: '/suppliers',
        name: 'Suppliers',
      },
      {
        component: () => import('../views/supplier.vue'),
        path: '/supplier/:actionType',
        name: 'Supplier',
      },
      {
        component: () => import('../views/income-docs.vue'),
        path: '/income-docs',
        name: 'IncomeDocs',
      },
      {
        component: () => import('../views/income-doc.vue'),
        path: '/income-doc/:actionType',
        name: 'IncomeDoc',
      },
      {
        component: () => import('../views/sale-docs.vue'),
        path: '/sale-docs',
        name: 'SaleDocs',
      },
      {
        component: () => import('../views/sale-doc.vue'),
        path: '/sale-doc/:actionType',
        name: 'SaleDoc',
      },
    ],
  },
  {
    component: () => import('../layouts/App.vue'),
    path: '',
    children: [
      {
        component: () => import('../views/catalog/catalog.vue'),
        path: '/catalog/:companyId/products',
        name: 'Catalog',
      },
      {
        component: () => import('../views/catalog/product.vue'),
        path: '/catalog/:companyId/product/:id',
        name: 'CatalogProduct',
      },
      {
        component: () => import('../views/catalog/cart.vue'),
        path: '/catalog/:companyId/cart',
        name: 'CatalogCart',
      },
    ],
  },
];

export default routes;

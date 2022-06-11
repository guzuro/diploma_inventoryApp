/* eslint-disable */
import Vue from 'vue';
import Vuex from 'vuex';
import userModule from './modules/user/state';
import productsModule from './modules/products/state';
import companyModule from './modules/company/state';
import configModule from './modules/config/state';
import suppliersModule from './modules/suppliers/state';
import ProductApiService from '@/services/ProductApiService';
import { Product } from '@/types/Product';

Vue.use(Vuex);
type Cart = Array<{sku:number, quantity: number}>

export default new Vuex.Store({
  state: {
    cart: [] as Cart,
    products: [] as Array<Product>,
  },
  getters: {
    PRODUCTS: (state): Array<Product> => state.products,
    PRODUCT_BY_ID: (state) => (sku: number): Product | undefined => state.products.find((p) => p.sku === sku),
    CART_ITEMS_LENGTH: (state): number => state.cart.length,
    CART_LINES: (state): Array<{ product: Product | undefined; quantity: number }> =>
      state.cart.map(({ sku, quantity }) => {
        const product = state.products.find((product) => product.sku === sku);

        return {
          product,
          quantity,
        };
      }),
  },
  mutations: {
    SET_PRODUCTS: (state, products: Array<Product>) => {
      state.products = products;
    },
    CLEAR_CART: (state) => {
      state.cart = [];
    },
    UPDATE_CART: (state, product: Product) => {     
      const productInCart = state.cart.find((value: any) => product.sku === value.sku);

      if (productInCart) {
        productInCart.quantity++;
      } else {
        state.cart.push({
          sku: product.sku,
          quantity: 1,
        });
      }
    },
    UPDATE_CART_LINE_QUANTITY: (state, payload: { quantity: number; product: Product }) => {
      const productInCart = state.cart.find((value) => payload.product.sku === value.sku);
      if (payload.quantity === 0) {
        state.cart = state.cart.filter((value) => value.sku !== payload.product.sku);
      } else if (productInCart) {
        productInCart.quantity = payload.quantity;
      }
    },
  },
  actions: {
    ADD_PRODUCT_TO_CART: ({ commit }, product: Product) => {
      commit('UPDATE_CART', product);
    },
    REQUEST_PRODUCTS: ({ commit }, companyId: number): Promise<Array<Product>> =>
      new Promise(async (resolve) => {
        const products = await ProductApiService.getProducts(companyId);

        commit('SET_PRODUCTS', products);

        resolve(products);
      }),
  },
  modules: {
    userModule,
    productsModule,
    companyModule,
    configModule,
    suppliersModule,
  },
});

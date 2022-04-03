/* eslint no-shadow: ["error", { "allow": ["state"] }] */
import { Commit } from 'vuex';
import { Product } from '@/types/Product';
import ProductApiService from '@/services/ProductApiService';

export interface State {
  products: Product[];
}

const state: State = {
  products: [],
};

export default {
  namespaced: true,
  state,
  mutations: {
    SET_PRODUCTS_FROM_API_TO_STORE: (state: State, value: any) => {
      state.products = value;
    },
    ADD_PRODUCT: (state: State, value: Product) => {
      state.products.push(value);
    },
    UPDATE_PRODUCT: (state: State, value: Product) => {
      const index = state.products.findIndex((p: Product) => p.sku === value.sku);
      state.products.splice(index, 1, value);
    },
    REMOVE_PRODUCT: (state: State, value: Product) => {
      const index = state.products.findIndex((p: Product) => p.sku === value.sku);
      state.products.splice(index, 1);
    },
  },
  actions: {
    setProductsFromApiToStore: (context: { commit: Commit }, value: any): void => {
      context.commit('SET_PRODUCTS_FROM_API_TO_STORE', value);
    },
    addProduct: (context: { commit: Commit }, product: Product): Promise<void> => new Promise((resolve, reject) => {
      ProductApiService.addProduct(product)
        .then(resolve)
        .catch(reject);
    }),
    updateProduct: (context: { commit: Commit }, product: Product): Promise<void> => new Promise((resolve, reject) => {
      ProductApiService.updateProduct(product)
        .then(resolve)
        .catch(reject);
    }),
    removeProduct: (context: { commit: Commit }, product: Product): void => {
      context.commit('REMOVE_PRODUCT', product);
    },
  },
  getters: {
    getProducts(state: State): any[] {
      return state.products;
    },
  },
};

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
    addProduct: (context: { commit: Commit }, product: Product): void => {
      // console.log('add prouct to db logic later');
      // console.log('......');
      // console.log('add to store...');

      const fd = new FormData();
      console.log(product);

      Object.entries(product).forEach(([key, value]) => {
        if (key === 'photos') {
          // fd.append('photos[]', value);
          // }
          value!.forEach((element: any) => {
            fd.append('photos[]', element.originFileObj);
          });
        }
        if (key !== 'photos') {
          fd.append(key, value.toString());
        }
      });

      console.log(fd);

      ProductApiService.addProduct(fd);

      context.commit('ADD_PRODUCT', product);
    },
    updateProduct: (context: { commit: Commit }, product: Product): void => {
      console.log('update product in db logic later');
      console.log('......');
      console.log('updating product in store...');
      context.commit('UPDATE_PRODUCT', product);
    },
    removeProduct: (context: { commit: Commit }, product: Product): void => {
      console.log('remove product in db logic later');
      console.log('......');
      console.log('removing product in store...');
      context.commit('REMOVE_PRODUCT', product);
    },
  },
  getters: {
    getProducts(state: State): any[] {
      return state.products;
    },
    getProductByCode: (state: State) => (productCode: string): Product | null => {
      const pCode = Number.parseInt(productCode, 10);
      const product = state.products.find((p: Product) => p.code === pCode);
      return product !== undefined ? product : null;
    },
  },
};

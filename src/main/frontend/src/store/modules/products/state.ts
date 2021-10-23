/* eslint no-shadow: ["error", { "allow": ["state"] }] */
import { Commit } from 'vuex';
import { Product } from '@/types/Product';

export interface State {
  products: Product[];
}

const state: State = {
  products: [{
    code: '1',
    name: 'Молоко',
    category: 'Кисломолочка',
    quantity: 12,
    price: 350,
    description: 'qweqweqwe',
    images: [],
    imagesRemoved: [],
    unit: 'шт',
    price_old: 450,
  }, {
    code: '2',
    name: 'Хлеб',
    category: 'Выпечка',
    quantity: 112,
    price: 400,
    description: 'фвыаваполропа',
    images: [],
    imagesRemoved: [],
    unit: 'шт',
    price_old: 550,
  }, {
    code: '3',
    name: 'Сосиски',
    category: 'Полуфабрикаты',
    quantity: 66,
    price: 8000,
    description: 'птимсчя',
    images: [],
    imagesRemoved: [],
    unit: 'шт',
    price_old: 9999,
  }, {
    code: '4',
    name: 'Пельмени',
    category: 'Полуфабрикаты',
    quantity: 4,
    price: 45,
    description: 'гшлшглошг',
    images: [],
    imagesRemoved: [],
    unit: 'шт',
    price_old: 999,
  }, {
    code: '5',
    name: 'Макароны',
    category: 'Макароны и крупы',
    quantity: 6,
    price: 60,
    description: 'ьтимсчя',
    images: [],
    imagesRemoved: [],
    unit: 'шт',
    price_old: 880,
  }],
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
  },
  actions: {
    setProductsFromApiToStore: (context: { commit: Commit }, value: any): void => {
      context.commit('SET_PRODUCTS_FROM_API_TO_STORE', value);
    },
    addProduct: (context: { commit: Commit }, product: Product): void => {
      console.log('add prouct to db logic');
      console.log('......');
      console.log('but not my db is store....');
      console.log('add to store...');
      context.commit('ADD_PRODUCT', product);
    },
  },
  getters: {
    getProducts(state: State): any[] {
      return state.products;
    },
    getProductByCode(state: State, productCode: string): any {
      return state.products.find((p: Product) => p.code === productCode);
    },
  },
};

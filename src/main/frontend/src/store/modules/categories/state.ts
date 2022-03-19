import { Category } from '@/types/Category';

export interface State {
  categories: Category[];
}

const state: State = {
  categories: [{
    id: 1,
    name: 'Кисломолочка',
  }, {
    id: 2,
    name: 'Выпечка',
  }, {
    id: 3,
    name: 'Полуфабрикаты',
  }, {
    id: 4,
    name: 'Полуфабрикаты',
  }, {
    id: 5,
    name: 'Макароны и крупы',
  }],
};

export default {
  namespaced: true,
  state,
  mutations: {
  },
  actions: {
  },
  getters: {},
};

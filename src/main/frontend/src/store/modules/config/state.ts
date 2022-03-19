/* eslint-disable no-shadow */

import { Category } from '@/types/Category';
import { EmployeeRole } from '@/types/EmployeeRoles';
import { Sale } from '@/types/Sale';
import { Warehouse } from '@/types/Warehouse';

export type ConfigField = Array<Sale> | Array<Category> | Array<Warehouse> | Array<EmployeeRole>;

export type ConfigState = {
  config: Record<string, ConfigField | null>;
};

const state: ConfigState = {
  config: {
    sale: null,
    category: null,
    warehouse: null,
    employeeRoles: null,
  },
};

export default {
  namespaced: true,
  state,
  mutations: {
    UPDATE_CONFIG_FIELDS: (state: ConfigState, payload: { field: string; value: ConfigField }): void => {
      state.config[payload.field] = payload.value;
    },
  },
  actions: {},
  getters: {},
};

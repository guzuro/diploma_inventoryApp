/* eslint-disable no-shadow */

import { Commit } from 'vuex';
import SalesService from '@/services/Config/SalesService';
import { Category } from '@/types/Category';
import { EmployeeRole } from '@/types/EmployeeRoles';
import { Sale } from '@/types/Sale';
import { Warehouse } from '@/types/Warehouse';
import CategoryService from '@/services/Config/CategoryService';
import WarehouseService from '@/services/Config/WarehouseService';

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
  actions: {
    REQUEST_SALES(context: { commit: Commit; state: ConfigState, rootState:any }): Promise<void> {
      return new Promise((resolve, reject) => {
        SalesService.getSales({ company_id: context.rootState.companyModule.company.id })
          .then((response: Array<Sale>) => {
            context.commit('UPDATE_CONFIG_FIELDS', { field: 'sale', value: response });
            resolve();
          })
          .catch(reject);
      });
    },
    REQUEST_CATEGORIES(context: { commit: Commit; state: ConfigState, rootState:any }): Promise<void> {
      return new Promise((resolve, reject) => {
        CategoryService.getCategory({ company_id: context.rootState.companyModule.company.id })
          .then((response: Array<Category>) => {
            context.commit('UPDATE_CONFIG_FIELDS', { field: 'category', value: response });
            resolve();
          })
          .catch(reject);
      });
    },
    REQUEST_WAREHOUSE(context: { commit: Commit; state: ConfigState, rootState:any }): Promise<void> {
      return new Promise((resolve, reject) => {
        WarehouseService.getWarehouses({ company_id: context.rootState.companyModule.company.id })
          .then((response: Array<Warehouse>) => {
            context.commit('UPDATE_CONFIG_FIELDS', { field: 'warehouse', value: response });
            resolve();
          })
          .catch(reject);
      });
    },
  },
  getters: {},
};

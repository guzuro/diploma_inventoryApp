/* eslint-disable no-shadow */

import { Commit } from 'vuex';
import SalesService from '@/services/Config/SalesService';
import { Category } from '@/types/Category';
import { EmployeeRole } from '@/types/EmployeeRoles';
import { Sale } from '@/types/Sale';
import { Warehouse } from '@/types/Warehouse';
import CategoryService from '@/services/Config/CategoryService';
import WarehouseService from '@/services/Config/WarehouseService';
import { Supplier } from '@/types/Supplier';
import SupplierService from '@/services/SupplierService';

export type ConfigField = Array<Sale> | Array<Category> | Array<Warehouse> | Array<EmployeeRole>;

export type SupplierState = {
  suppliers: Array<Supplier> | null;
};

const state: SupplierState = {
  suppliers: null,
};

export default {
  namespaced: true,
  state,
  mutations: {
    UPDATE_SUPPLIERS: (state: SupplierState, payload: Array<Supplier>): void => {
      state.suppliers = [...payload];
    },
  },
  actions: {
    REQUEST_SUPPLIERS(context: { commit: Commit; state: SupplierState; rootState: any }): Promise<void> {
      return new Promise((resolve, reject) => {
        SupplierService.getSuppliers({ company_id: context.rootState.companyModule.company.id })
          .then((response: Array<Supplier>) => {
            context.commit('UPDATE_SUPPLIERS', response);
            resolve();
          })
          .catch(reject);
      });
    },
  },
  getters: {},
};

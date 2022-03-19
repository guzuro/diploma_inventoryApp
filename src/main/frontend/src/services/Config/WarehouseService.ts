import BaseApi from '../BaseApi';
import store from '@/store';
import { errorNotification } from '../NotificationService';
import { Warehouse } from '@/types/Warehouse';

export default class WarehouseService {
  static BASE_PATH = '/warehouse';

  static async addWarehouse(reqBody: { warehouse: Omit<Warehouse, 'id'>; company_id: number }): Promise<Warehouse> {
    try {
      const { data } = await BaseApi.sendRequest(`${WarehouseService.BASE_PATH}/add`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getWarehouses(reqBody: { company_id: number }): Promise<Array<Warehouse>> {
    try {
      const { data } = await BaseApi.sendRequest(`${WarehouseService.BASE_PATH}/get`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async updateWarehouse(reqBody: { warehouse: Warehouse }): Promise<Warehouse> {
    try {
      const { data } = await BaseApi.sendRequest(`${WarehouseService.BASE_PATH}/update`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }
}

import { Sale } from '@/types/Sale';
import BaseApi from '../BaseApi';
import { errorNotification } from '../NotificationService';

export default class SalesService {
  static BASE_PATH = '/config/sales';

  static async addSale(reqBody: { sale: Omit<Sale, 'id'>; company_id: number }): Promise<Sale> {
    try {
      const { data } = await BaseApi.sendRequest(`${SalesService.BASE_PATH}/add`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getSales(reqBody: { company_id: number }): Promise<Array<Sale>> {
    try {
      const { data } = await BaseApi.sendRequest(`${SalesService.BASE_PATH}/get`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async updateWarehouse(reqBody: { sale: Sale }): Promise<Sale> {
    try {
      const { data } = await BaseApi.sendRequest(`${SalesService.BASE_PATH}/update`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }
}

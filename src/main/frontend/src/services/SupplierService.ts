import { ISupplier } from '@/types/Supplier';
import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class SupplierService {
  static BASE_PATH = '/suppliers';

  static async addSupplier(reqBody: { supplier: Omit<ISupplier, 'id'>; company_id: number }): Promise<ISupplier> {
    try {
      const { data } = await BaseApi.sendRequest(`${SupplierService.BASE_PATH}/add`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async updateSupplier(reqBody: { supplier: ISupplier;}): Promise<ISupplier> {
    try {
      const { data } = await BaseApi.sendRequest(`${SupplierService.BASE_PATH}/update`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getSuppliers(reqBody: { company_id: number }): Promise<Array<ISupplier>> {
    try {
      const { data } = await BaseApi.sendRequest(`${SupplierService.BASE_PATH}/getSuppliers`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getSupplier(reqBody: { supplier_id: number }): Promise<ISupplier> {
    try {
      const { data } = await BaseApi.sendRequest(`${SupplierService.BASE_PATH}/getSupplier`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async deleteSupplier(reqBody: { supplier_id:number }): Promise<void> {
    try {
      await BaseApi.sendRequest(`${SupplierService.BASE_PATH}/delete`, reqBody);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

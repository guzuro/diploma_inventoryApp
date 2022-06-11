import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class SaleDocumentService {
  static BASE_PATH = '/saledoc';

  static async add(reqBody: { doc: any, company_id: number }): Promise<any> {
    try {
      const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/add`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getAll(reqBody: { company_id: number }): Promise<any> {
    try {
      const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/getAll`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getSaleDocument(reqBody: { saleDocId: number }): Promise<any> {
    try {
      const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/get`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async paySaleDocument(reqBody: { saleDocId: number, company_id:number }): Promise<any> {
    try {
      const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/pay`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  //   static async updateSupplier(reqBody: { supplier: ISupplier;}): Promise<ISupplier> {
  //     try {
  //       const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/update`, reqBody);
  //       return data;
  //     } catch (e:any) {
  //       errorNotification(e.message);
  //       return e;
  //     }
  //   }

  //   static async getSuppliers(reqBody: { company_id: number }): Promise<Array<ISupplier>> {
  //     try {
  //       const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/getSuppliers`, reqBody);
  //       return data;
  //     } catch (e:any) {
  //       errorNotification(e.message);
  //       return e;
  //     }
  //   }

  //   static async getSupplier(reqBody: { supplier_id: number }): Promise<ISupplier> {
  //     try {
  //       const { data } = await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/getSupplier`, reqBody);
  //       return data;
  //     } catch (e:any) {
  //       errorNotification(e.message);
  //       return e;
  //     }
  //   }

  //   static async deleteSupplier(reqBody: { supplier_id:number }): Promise<void> {
  //     try {
  //       await BaseApi.sendRequest(`${SaleDocumentService.BASE_PATH}/delete`, reqBody);
  //     } catch (e:any) {
  //       errorNotification(e.message);
  //     }
  //   }
}

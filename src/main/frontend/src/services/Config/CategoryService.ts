import { Category } from '@/types/Category';
import { Sale } from '@/types/Sale';
import BaseApi from '../BaseApi';
import { errorNotification } from '../NotificationService';

export default class CategoryService {
  static BASE_PATH = '/config/category';

  static async addCategory(reqBody: { category: Omit<Category, 'id'>; company_id: number }): Promise<Sale> {
    try {
      const { data } = await BaseApi.sendRequest(`${CategoryService.BASE_PATH}/add`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getCategory(reqBody: { company_id: number }): Promise<Array<Sale>> {
    try {
      const { data } = await BaseApi.sendRequest(`${CategoryService.BASE_PATH}/get`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async updateCategory(reqBody: { category: Category }): Promise<Sale> {
    try {
      const { data } = await BaseApi.sendRequest(`${CategoryService.BASE_PATH}/update`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async deleteCategory(reqBody: { category_id:number }): Promise<void> {
    try {
      await BaseApi.sendRequest(`${CategoryService.BASE_PATH}/delete`, reqBody);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

import store from '@/store';
import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class CompanyService {
  static BASE_PATH = '/company';

  static async getCompany(owner: number): Promise<void> {
    try {
      const response = await BaseApi.sendRequest(`${CompanyService.BASE_PATH}/get`, { owner });
      await store.dispatch('companyModule/setCompanyToStore', response.data);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }

  static async updateCompanyInfo(companyData: any): Promise<void> {
    try {
      const response = await BaseApi.sendRequest(`${CompanyService.BASE_PATH}/update`, companyData);
      await store.dispatch('companyModule/setCompanyToStore', response.data);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

import store from '@/store';
import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class CompanyService {
  static BASE_PATH = '/company';

  static async updateCompanyInfo(companyData: any): Promise<void> {
    try {
      const { data } = await BaseApi.sendRequest(`${CompanyService.BASE_PATH}/update`, companyData);
      await store.dispatch('companyModule/setCompanyToStore', data);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

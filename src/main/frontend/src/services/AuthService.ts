import { LoginData } from '@/types/LoginData';
import { User } from '@/types/User';
import BaseApi from './BaseApi';
import store from '@/store';
import { errorNotification } from './NotificationService';

export default class AuthService {
  static BASE_PATH = '/auth';

  static async register(reqBody: { user: User; company: string }): Promise<any> {
    return BaseApi.sendRequest(`${AuthService.BASE_PATH}/register`, reqBody);
  }

  static async login(reqBody: LoginData): Promise<void> {
    try {
      const { data } = await BaseApi.sendRequest(`${AuthService.BASE_PATH}/login`, reqBody);
      await AuthService.handleResponseUserData(data);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }

  static async logout(): Promise<void> {
    await BaseApi.sendRequest(`${AuthService.BASE_PATH}/logout`);
    await store.dispatch('userModule/setAuthState', false);
    await store.commit('userModule/RESET_USER_STATE');
  }

  static async checkLogin(): Promise<void> {
    // eslint-disable-next-line no-useless-catch
    try {
      const { data } = await BaseApi.sendRequest(`${AuthService.BASE_PATH}/isauth`);
      await AuthService.handleResponseUserData(data);
    } catch (e:any) {
      if (e.message === 'Unauthorized') {
        // eslint-disable-next-line no-throw-literal
        throw 401;
      }
    }
  }

  private static handleResponseUserData = async (data: any): Promise<void> => {
    await store.dispatch('userModule/setAuthState', true);
    const userData = { ...data };
    delete userData.company;

    await store.dispatch('companyModule/setCompanyToStore', data.company);
    await store.dispatch('userModule/setUserToStore', userData);
  };
}

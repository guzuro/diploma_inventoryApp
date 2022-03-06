import { LoginData } from '@/types/LoginData';
import { User } from '@/types/User';
import BaseApi from './BaseApi';
import store from '@/store';

export default class AuthService {
  static BASE_PATH = '/auth';

  static async register(reqBody: User): Promise<any> {
    return BaseApi.sendRequest(`${AuthService.BASE_PATH}/register`, reqBody);
  }

  static async login(reqBody: LoginData): Promise<void> {
    const { data } = await BaseApi.sendRequest(`${AuthService.BASE_PATH}/login`, reqBody);
    await store.dispatch('userModule/setAuthState', true);
    await store.dispatch('userModule/setUserToStore', data);
  }

  static async logout(): Promise<void> {
    BaseApi.sendRequest(`${AuthService.BASE_PATH}/logout`);
    await store.dispatch('userModule/setAuthState', false);
    await store.commit('userModule/RESET_USER_STATE');
  }

  static async checkLogin(): Promise<void> {
    // eslint-disable-next-line no-useless-catch
    try {
      const response = await BaseApi.sendRequest(`${AuthService.BASE_PATH}/isauth`);
      await store.dispatch('userModule/setAuthState', true);
      await store.dispatch('userModule/setUserToStore', response.data);
      return response;
    } catch (e:any) {
      throw e;
    }
  }
}

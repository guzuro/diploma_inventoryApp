import { LoginData } from '@/types/LoginData';
import { User } from '@/types/User';
import BaseApi from './BaseApi';

export default class AuthService {
  static BASE_PATH = '/auth';

  static async register(reqBody: User): Promise<User> {
    return BaseApi.sendRequest(`${AuthService.BASE_PATH}/register`, reqBody);
  }

  static async login(reqBody: LoginData): Promise<any> {
    return BaseApi.sendRequest(`${AuthService.BASE_PATH}/login`, reqBody);
  }

  static async logout(): Promise<any> {
    const logoutPath = '/logout';
    return BaseApi.sendRequest(AuthService.BASE_PATH + logoutPath);
  }

  static async checkLogin(): Promise<void> {
    const checkLoginPath = '/checklogin';
    return BaseApi.sendRequest(AuthService.BASE_PATH + checkLoginPath);
  }
}

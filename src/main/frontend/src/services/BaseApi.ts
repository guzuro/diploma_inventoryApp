import Vue from 'vue';
import { errorNotification } from './NotificationService';

export default class BaseApi {
  static BASE_API = 'http://localhost:8888';

  static async sendRequest(method: string, body: any = {}, headers: any = {}): Promise<any> {
    try {
      const response = await Vue.axios.post(BaseApi.BASE_API + method, body, {
        withCredentials: true,
        ...headers,
      });
      return response;
    } catch (error:any) {
      // erorNotification(error.response.message);
      throw error.response.status;
    }
  }
}

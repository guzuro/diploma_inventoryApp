import Vue from 'vue';

export default class BaseApi {
  static BASE_API = 'http://localhost:8888';

  static async sendRequest(method: string, body: any = {}, headers: any = {}): Promise<any> {
    try {
      return Vue.axios.post(BaseApi.BASE_API + method, body, {
        withCredentials: true,
        ...headers,
      });
    } catch (error) {
      console.error(error);
      return error;
    }
  }
}

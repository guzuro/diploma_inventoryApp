import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class StatisticsService {
  static BASE_PATH = '/statistics';

  static async getStatistics(reqBody: { company_id: number }): Promise<any> {
    try {
      const { data } = await BaseApi.sendRequest(`${StatisticsService.BASE_PATH}/getstatistics`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }
}

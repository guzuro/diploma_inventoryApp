import axios from 'axios';
import { errorNotification } from './NotificationService';
import BaseApi from '@/services/BaseApi';

export default class UploadFileService {
  static async uploadFile(file: any): Promise<Array<string>> {
    try {
      const { data } = await axios.post(`${BaseApi.BASE_API}/uploadfile`, file, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return (data as Array<string>);
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }
}

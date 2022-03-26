import axios from 'axios';
import { Product } from '@/types/Product';
import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class ProductApiService {
  static benzinBaseURL = 'https://api.benzin.io/v1/removeBackground';

  static BASE_PATH = '/products';

  static ApiKey = '*';

  static removeBackground(data: File): Promise<any> {
    const fd = new FormData();
    fd.append('image_file', data);
    fd.append('size', 'preview');
    return axios.post(ProductApiService.benzinBaseURL, fd, {
      headers: {
        'X-Api-Key': ProductApiService.ApiKey,
        'Content-Type': 'multipart/form-data',
      },
      responseType: 'blob',
    });
  }

  static async getProducts(company_id: number): Promise<void> {
    try {
      const { data } = await BaseApi.sendRequest(`${ProductApiService.BASE_PATH}/getproducts`, { company_id });
      console.log(data);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

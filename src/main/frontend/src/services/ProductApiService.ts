import axios from 'axios';
import { Product } from '@/types/Product';
import BaseApi from './BaseApi';
import { errorNotification } from './NotificationService';

export default class ProductApiService {
  static benzinBaseURL = 'https://api.benzin.io/v1/removeBackground';

  static BASE_PATH = '/products';

  static ApiKey = '6c840b5686a94ce9aa2896222e245f75';

  static removeBackground(data: File): Promise<any> {
    console.log(data);
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

  static async getProducts(company_id: number): Promise<Array<Product>> {
    try {
      const { data } = await BaseApi.sendRequest(`${ProductApiService.BASE_PATH}/getproducts`, { company_id });
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async addProduct(product: any): Promise<void> {
    try {
      await axios.post(`http://localhost:8888${ProductApiService.BASE_PATH}/add`, product, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
    } catch (e:any) {
      errorNotification(e.message);
    }
  }

  static async deleteProduct(sku:number): Promise<void> {
    try {
      await BaseApi.sendRequest(`${ProductApiService.BASE_PATH}/removeproduct`, { sku });
    } catch (e:any) {
      errorNotification(e.message);
    }
  }

  static async getProduct(sku:number): Promise<void> {
    try {
      await BaseApi.sendRequest(`${ProductApiService.BASE_PATH}/getproduct`, { sku });
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

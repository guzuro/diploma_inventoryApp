import axios from 'axios';
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

  static async getProducts(company_id: number): Promise<void> {
    try {
      const { data } = await BaseApi.sendRequest(`${ProductApiService.BASE_PATH}/getproducts`, { company_id });
      console.log(data);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }

  static async addProduct(product:any): Promise<void> {
    const response = await axios.post(`http://localhost:8888${ProductApiService.BASE_PATH}/add`, product, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    console.log(response);

    // try {
    //   const { data } = await BaseApi.sendRequest(`${ProductApiService.BASE_PATH}/add`, product);
    //   console.log(data);
    // } catch (e:any) {
    //   errorNotification(e.message);
    // }
  }
}

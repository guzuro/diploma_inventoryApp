import axios from 'axios';

export default class ProductApiService {
  static benzinBaseURL = 'https://api.benzin.io/v1/removeBackground';

  static ApiKey = '*';

  static removeBackground(data: File):Promise<any> {
    const fd = new FormData();
    fd.append('image_file', data);
    fd.append('size', 'preview');
    return axios.post(ProductApiService.benzinBaseURL, fd,
      {
        headers: {
          'X-Api-Key': ProductApiService.ApiKey,
          'Content-Type': 'multipart/form-data',
        },
        responseType: 'blob',
      });
  }
}

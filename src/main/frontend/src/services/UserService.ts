import store from '@/store';
import BaseApi from './BaseApi';
import { errorNotification, successNotification } from './NotificationService';

export default class UserService {
  static BASE_PATH = '/user';

  static async updateUser(userInfo: any, newPassword: { password: string; approved: string }): Promise<void> {
    try {
      let reqUser = null;
      if (userInfo.password) {
        if (newPassword.approved && newPassword.password && newPassword.password === newPassword.approved) {
          reqUser = { ...userInfo };
        } else if (!newPassword.approved && !newPassword.password) {
          reqUser = { ...userInfo };
        } else {
          throw new Error('Новые пароли не совпадают');
        }
      } else {
        throw new Error('Введите пароль учетной записи');
      }

      const reqBody = {
        id: reqUser.id,
        email: reqUser.email,
        password: reqUser.password,
        first_name: reqUser.first_name,
        last_name: reqUser.last_name,
        phone: reqUser.phone,
        company: reqUser.company,
        role: reqUser.role,
      };

      const { data } = await BaseApi.sendRequest(`${UserService.BASE_PATH}/update`, reqBody);
      await store.dispatch('userModule/setUserToStore', data);
      await successNotification('Успешно');
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

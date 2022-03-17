import { EmployeeRole } from '@/types/EmployeeRoles';
import BaseApi from '../BaseApi';
import { errorNotification } from '../NotificationService';

export default class EmployeeRolesService {
  static BASE_PATH = '/config/employeeroles';

  static async addEmployeeRole(reqBody: { employee_role: Omit<EmployeeRole, 'id'>; company_id: number }): Promise<EmployeeRole> {
    try {
      const { data } = await BaseApi.sendRequest(`${EmployeeRolesService.BASE_PATH}/add`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async getEmployeeRoles(reqBody: { company_id: number }): Promise<Array<EmployeeRole>> {
    try {
      const { data } = await BaseApi.sendRequest(`${EmployeeRolesService.BASE_PATH}/get`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async updateEmployeeRole(reqBody: { employee_role: EmployeeRole }): Promise<EmployeeRole> {
    try {
      const { data } = await BaseApi.sendRequest(`${EmployeeRolesService.BASE_PATH}/update`, reqBody);
      return data;
    } catch (e:any) {
      errorNotification(e.message);
      return e;
    }
  }

  static async deleteEmployeeRole(reqBody: { role_id:number }): Promise<void> {
    try {
      await BaseApi.sendRequest(`${EmployeeRolesService.BASE_PATH}/delete`, reqBody);
    } catch (e:any) {
      errorNotification(e.message);
    }
  }
}

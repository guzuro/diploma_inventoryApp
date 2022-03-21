<template>
  <div class="employes">
    <a-row>
      <a-col :span="7" class="mr-2">
        Фильтр по должности
        <div v-if="employeeRoles.length">
          <div v-for="(role, index) in employeeRoles" :key="index">
            <a-button @click="doFilter(role.value)" class="w-full mb-2" type="dashed"> {{ role.label }} </a-button>
          </div>
          <a-button @click="doFilter('reset')" class="w-full mb-2" type="dashed"> Сброс </a-button>
        </div>
      </a-col>
      <a-col :span="16">
        <a-list :loading="loading" item-layout="horizontal" :data-source="filteredUsers">
          <div slot="header">
            <a-button
              icon="plus"
              type="primary"
              @click="
                employeModalForm = true;
                modalMode = 'create';
              "
              >Добавить сотрудника</a-button
            >
          </div>
          <a-list-item slot="renderItem" slot-scope="item">
            <a slot="actions"><a-icon @click="getEmployeeInfo(item)" type="edit" /></a>

            <a slot="actions">
              <a-popconfirm title="Действительно удалить польователя?" ok-text="Удалить" cancel-text="Отмена" @confirm="removeEmployee(item)"> <a-icon type="delete" /> </a-popconfirm
            ></a>

            <a-list-item-meta v-if="$store.state.configModule.config.employeeRoles" :description="resolveRole(item.role)">
              <div slot="title">{{ getUserName(item) }}</div>
            </a-list-item-meta>
          </a-list-item>
        </a-list>
      </a-col>
    </a-row>
    <ValidationObserver v-slot="{ invalid }">
      <a-modal v-model="employeModalForm" title="Сотрудник">
        <a-tabs>
          <a-tab-pane key="main" tab="Основное"
            ><div class="flex flex-col">
              <main-user-info-form v-model="user" />
              <field-wrapper class="mt-2" :fieldTitle="'Должность'" v-if="$store.state.configModule.config.employeeRoles !== null">
                <a-select class="w-full" v-model="user.role" :default-value="Number.parseInt(user.role)" @change="(value) => (user.role = value)">
                  <a-select-option v-for="(role, index) in employeeRoles" :key="index" :value="role.value">
                    {{ role.label }}
                  </a-select-option>
                </a-select>
              </field-wrapper>
            </div>
          </a-tab-pane>
          <a-tab-pane key="employment" tab="Трудоустройство">
            <div v-if="user.employement">
              <field-wrapper :fieldTitle="'Дата устройства'">
                <a-date-picker format="YYYY-MM-DD" :show-time="false" v-model="date" />
              </field-wrapper>
              <field-wrapper class="mt-2" :fieldTitle="'Зарплата'">
                <a-input-number class="w-full" v-model="user.employement.salary" />
              </field-wrapper>
            </div>
            <div v-else>
              <a-button
                @click="
                  user.employement = {
                    employement_date: null,
                    salary: 0,
                  }
                "
                >Добавить трудоустройство</a-button
              >
            </div>
          </a-tab-pane>
        </a-tabs>

        <template slot="footer">
          <a-button :disabled="invalid" class="ml-auto mt-5" @click="handleOk"> Добавить </a-button>
        </template>
      </a-modal>
    </ValidationObserver>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { ValidationObserver, ValidationProvider } from 'vee-validate';
import moment from 'moment';
import { Watch } from 'vue-property-decorator';
import { cloneDeep } from 'lodash';
import MainUserInfoForm from '@/components/MainUserInfoForm.vue';
import UserService from '@/services/UserService';
import FieldWrapper from '@/components/FieldWrapper.vue';
import { EmployeeRole } from '@/types/EmployeeRoles';
import EmployeeRolesService from '@/services/Config/EmployeeRolesService';

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
    MainUserInfoForm,
    FieldWrapper,
  },
})
export default class Employes extends Vue {
  loading = false;

  filteredUsers: any[] = [];

  doFilter(role: string) {
    if (role === 'reset') {
      this.filteredUsers = this.originalUsersList;
    } else {
      this.filteredUsers = cloneDeep(this.originalUsersList.filter((e) => e.role === role));
    }
  }

  user: any = {
    email: '',
    password: '',
    first_name: '',
    last_name: '',
    phone: '',
    role: '',
    employement: {
      employement_date: null,
      salary: 0,
      user_id: null,
    },
  };

  employeModalForm = false;

  resolveRole(roleId: string): string | undefined {
    return this.employeeRoles.find((r: any) => r.value === roleId)?.label;
  }

  @Watch('employeModalForm')
  onModalFormChange(state: boolean): void {
    if (!state) {
      this.user = {
        email: '',
        password: '',
        first_name: '',
        last_name: '',
        phone: '',
        role: '',
        employement: {
          employement_date: null,
          salary: 0,
        },
      };
    }
  }

  get employeeRoles(): Array<{ label: string; value: string }> {
    if (this.$store.state.configModule.config.employeeRoles !== null) {
      return this.$store.state.configModule.config.employeeRoles.map((i: EmployeeRole) => ({
        label: i.name,
        value: i.id?.toString(),
      }));
    }
    return [];
  }

  modalMode = 'create';

  get date(): any {
    return this.user.employement.employement_date;
  }

  set date(value: any) {
    this.user.employement.employement_date = moment(value).format('YYYY-MM-DDT00:00:00');
  }

  // eslint-disable-next-line class-methods-use-this
  getEmployeeInfo(user: any): void {
    this.user = { ...user };
    this.employeModalForm = true;
    this.modalMode = 'edit';
  }

  getUserName = (item: any): string => `${item.last_name} ${item.first_name}`;

  handleOk(): void {
    if (this.modalMode === 'create') {
      UserService.addUser({ user: this.user, employment: this.user.employement, company_id: this.$store.getters['companyModule/getCompany'].id }).then(this.getEmployees);
    }
    if (this.modalMode === 'edit') {
      UserService.updateEmployee(this.user, this.user.employement).then(this.getEmployees);
    }

    this.employeModalForm = false;
  }

  originalUsersList: any[] = [];

  getEmployees(): void {
    UserService.getUsers({ company_id: this.$store.getters['companyModule/getCompany'].id }).then((response) => {
      this.originalUsersList = response;
      this.filteredUsers = this.originalUsersList;
    });
  }

  async removeEmployee(item: any): Promise<void> {
    await UserService.deleteUser({ user_id: item.id });
    this.getEmployees();
  }

  async created(): Promise<void> {
    this.getEmployees();
    const list = await EmployeeRolesService.getEmployeeRoles({ company_id: this.$store.getters['companyModule/getCompany'].id });
    await this.$store.commit('configModule/UPDATE_CONFIG_FIELDS', { field: 'employeeRoles', value: list });
  }
}
</script>

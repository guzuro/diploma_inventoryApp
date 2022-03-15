<template>
  <div class="employes">
    <a-row>
      <a-col :span="7" class="mr-2">Lorem ipsum dolor, sit amet consectetur adipisicing elit. Quisquam, debitis!</a-col>
      <a-col :span="16">
        <a-list :loading="loading" item-layout="horizontal" :data-source="usersList">
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
              <a-popconfirm title="Действительно удалить польователя?" ok-text="Удалить" cancel-text="Отмена" @confirm="removeEmployee(item)">
                <a-icon type="delete" />
                </a-popconfirm></a>

            <a-list-item-meta :description="item.role">
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
              <main-user-info-form v-model="user" /></div
          ></a-tab-pane>
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
import MainUserInfoForm from '@/components/MainUserInfoForm.vue';
import UserService from '@/services/UserService';
import FieldWrapper from '@/components/FieldWrapper.vue';

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

  user: any = {
    email: '',
    password: '',
    first_name: '',
    last_name: '',
    phone: '',
    employement: {
      employement_date: null,
      salary: 0,
      user_id: null,
    },
  };

  employeModalForm = false;

  @Watch('employeModalForm')
  onModalFormChange(state: boolean): void {
    if (!state) {
      this.user = {
        email: '',
        password: '',
        first_name: '',
        last_name: '',
        phone: '',
        employement: {
          employement_date: null,
          salary: 0,
        },
      };
    }
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

  usersList: any[] = [];

  getEmployees(): void {
    UserService.getUsers({ company_id: this.$store.getters['companyModule/getCompany'].id }).then((response) => {
      this.usersList = response;
    });
  }

  async removeEmployee(item: any): Promise<void> {
    await UserService.deleteUser({ user_id: item.id });
    this.getEmployees();
  }

  created(): void {
    this.getEmployees();
  }
}
</script>

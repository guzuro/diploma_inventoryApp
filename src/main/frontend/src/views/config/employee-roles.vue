<template>
  <a-spin :spinning="spinning">
    <div class="employee-roles">
      <a-button
        @click="
          isEmployeeRolesModalOpen = true;
          modalMode = 'new';
        "
        class="mb-5"
        >Добавить роль</a-button
      >
      <div v-if="employeeRolesList && employeeRolesList.length" class="flex flex-wrap">
        <div v-for="(employeeRole, index) in employeeRolesList" :key="index" class="mb-2 md:mb-0 md:mr-2 w-full md:w-1/3">
          <a-card hoverable>
            <template slot="actions" class="ant-card-actions">
              <a-icon key="edit" type="edit" @click="doEdit(employeeRole)" />
              <a-popconfirm title="Действительно удалить роль?" ok-text="Удалить" cancel-text="Отмена" @confirm="removeRole(employeeRole.id)">
                <a-icon key="remove" type="delete" />
              </a-popconfirm>
            </template>
            <a-card-meta :title="employeeRole.name" />
          </a-card>
        </div>
      </div>
      <div v-else>'empty '</div>
      <a-modal v-model="isEmployeeRolesModalOpen" :title="modalMode === 'new' ? 'Добавить' : 'Редактировать'">
        <field-wrapper fieldTitle="Название">
          <a-input v-model="employeeRole.name" />
        </field-wrapper>

        <template slot="footer">
          <a-button key="submit" @click="modalMode === 'new' ? addEmployeeRole() : updateEmployeeRole()">Сохранить</a-button>
        </template>
      </a-modal>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { Watch } from 'vue-property-decorator';
import { snakeCase } from 'lodash';
import FieldWrapper from '@/components/FieldWrapper.vue';
import EmployeeRolesService from '@/services/Config/EmployeeRolesService';
import { EmployeeRole } from '@/types/EmployeeRoles';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class EmployeeRoles extends Vue {
  employeeRolesList: Array<EmployeeRole> | null = null;

  spinning = false;

  isEmployeeRolesModalOpen = false;

  modalMode = 'new';

  employeeRole: EmployeeRole = {
    name: '',
  };

  @Watch('isEmployeeRolesModalOpen')
  onModalChange(state: boolean): void {
    if (!state) {
      this.employeeRole = {
        name: '',
      };
    }
  }

  get companyId(): number {
    return this.$store.getters['companyModule/getCompany'].id;
  }

  async getEmployeeRoles(): Promise<void> {
    this.spinning = true;
    this.employeeRolesList = await EmployeeRolesService.getEmployeeRoles({ company_id: this.companyId });
    this.$store.commit('configModule/UPDATE_CONFIG_FIELDS', { field: 'employeeRoles', value: this.employeeRolesList });
    this.spinning = false;
  }

  addEmployeeRole(): void {
    EmployeeRolesService.addEmployeeRole({ employee_role: this.employeeRole, company_id: this.companyId }).then(() => {
      this.getEmployeeRoles();
      this.isEmployeeRolesModalOpen = false;
    });
  }

  updateEmployeeRole(): void {
    EmployeeRolesService.updateEmployeeRole({ employee_role: this.employeeRole }).then(() => {
      this.getEmployeeRoles();
      this.isEmployeeRolesModalOpen = false;
    });
  }

  doEdit(employeeRole: EmployeeRole): void {
    this.modalMode = 'edit';
    this.employeeRole = { ...employeeRole };
    this.isEmployeeRolesModalOpen = true;
  }

  async removeRole(id: number): Promise<void> {
    await EmployeeRolesService.deleteEmployeeRole({ role_id: id });
    this.getEmployeeRoles();
  }

  created(): void {
    this.getEmployeeRoles();
  }
}
</script>

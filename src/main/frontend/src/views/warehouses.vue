<template>
  <div class="warehouses">
    <a-button
      @click="
        isWarehouseModalOpen = true;
        modalMode = 'new';
      "
      class="mb-5"
      >Добавить склад</a-button
    >
    <div v-if="warehouses.length">
      <div v-for="(warehouse, index) in warehouses" :key="index">
        <a-card hoverable style="width: 300px">
          <template slot="actions" class="ant-card-actions">
            <a-icon key="edit" type="edit" @click="editWarehouse(warehouse)" />
          </template>
          <a-card-meta :title="warehouse.title" :description="warehouse.address" />
        </a-card>
      </div>
    </div>
    <div v-else>'empty blyat`'</div>
    <a-modal v-model="isWarehouseModalOpen" :title="modalMode === 'new' ? 'Добавить' : 'Редактировать'">
      <field-wrapper fieldTitle="Название склада">
        <a-input v-model="warehouse.title" />
      </field-wrapper>
      <field-wrapper class="mt-2" fieldTitle="Адрес склада">
        <a-input v-model="warehouse.address" />
      </field-wrapper>
      <template slot="footer">
        <a-button key="submit" @click="modalMode === 'new' ? addWarehouse() : updateWarehouse()">Сохранить</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { Warehouse } from '@/types/Warehouse';
import WarehouseService from '@/services/WarehouseService';
import FieldWrapper from '@/components/FieldWrapper.vue';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class Warehouses extends Vue {
  isWarehouseModalOpen = false;

  modalMode = 'new';

  warehouses: Array<Warehouse> = [];

  warehouse: Warehouse = {
    title: '',
    address: '',
  };

  get companyId(): number {
    return this.$store.getters['companyModule/getCompany'].id;
  }

  editWarehouse(item: Warehouse): void {
    this.warehouse = { ...item };
    this.modalMode = 'edit';
    this.isWarehouseModalOpen = true;
  }

  async updateWarehouse(): Promise<void> {
    await WarehouseService.updateWarehouse({ warehouse: this.warehouse });
    await this.getWarehouses();
  }

  async addWarehouse(): Promise<void> {
    await WarehouseService.addWarehouse({
      warehouse: this.warehouse,
      company_id: this.companyId,
    });
    await this.getWarehouses();
  }

  async getWarehouses(): Promise<void> {
    this.isWarehouseModalOpen = false;
    this.warehouses = await WarehouseService.getWarehouses({ company_id: this.companyId });
    this.warehouse = {
      title: '',
      address: '',
    };
  }

  created(): void {
    this.getWarehouses();
  }
}
</script>

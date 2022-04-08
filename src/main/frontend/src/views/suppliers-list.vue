<template>
  <div class="suppliers-list">
    <div class="flex justify-between">
      <h1>Поставщики</h1>
      <a-button @click="supplierPage(null, 'new')">Добавить</a-button>
    </div>

    <div v-if="suppliers && suppliers.length">
      <div v-for="(supplier, index) in suppliers" :key="index">
        <a-card hoverable style="width: 300px">
          <template slot="actions" class="ant-card-actions">
            <a-icon key="edit" type="edit" @click="supplierPage(supplier.id, 'edit')" />
            <a-icon key="eye" type="eye" @click="supplierPage(supplier.id, 'watch')" />
            <a-icon key="delete" type="delete" @click="deleteSupplier(supplier.id)" />
          </template>
          <b>Наименование: </b>
          <p class="supplier_name">{{ supplier.name }}</p>
          <b>Адрес: </b>
          <p class="supplier_address">{{ supplier.address }}</p>
          <b>ИНН: </b>
          <p class="supplier_inn">{{ supplier.inn }}</p>
          <b>Номер: </b>
          <p class="supplier_inn">{{ supplier.phone }}</p>
        </a-card>
      </div>
    </div>
    <a-empty v-else></a-empty>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import Vue from 'vue';
import { ISupplier } from '@/types/Supplier';
import SupplierService from '@/services/SupplierService';

@Component
export default class SuppliersList extends Vue {
  data: Array<ISupplier> = [];

  get suppliers(): Array<ISupplier> | null {
    return this.$store.state.suppliersModule.suppliers;
  }

  deleteSupplier(supplier_id: number): void {
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const self = this;
    this.$confirm({
      title: 'Удалить поставщика?',
      content: 'Вы Действительно хотите удалить поставщика?',
      async onOk() {
        await SupplierService.deleteSupplier({ supplier_id });
        await self.getSuppliers();
      },
    });
  }

  supplierPage(supplierId: number | null, actionType: string): void {
    this.$router.push({
      name: 'Supplier',
      params: {
        actionType,
      },
      query: {
        ...(supplierId ? { supplierId: supplierId.toString() } : {}),
      },
    });
  }

  getSuppliers(): void {
    this.$store.dispatch('suppliersModule/REQUEST_SUPPLIERS');
  }

  created(): void {
    this.getSuppliers();
  }
}
</script>

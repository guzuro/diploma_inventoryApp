<template>
  <div class="suppliers-list">
    <div v-if="suppliers.length">
      <div v-for="(supplier, index) in suppliers" :key="index">
        <a-card hoverable style="width: 300px">
          <template slot="actions" class="ant-card-actions">
            <a-icon key="setting" type="setting" />
            <a-icon key="edit" type="edit" />
          </template>
          <p class="supplier_name">{{ supplier.name }}</p>
          <p class="supplier_address">{{ supplier.address }}</p>
          <p class="supplier_inn">{{ supplier.inn }}</p>
        </a-card>
      </div>
    </div>
    <a-empty v-else></a-empty>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import Vue from 'vue';
import { Supplier } from '@/types/Supplier';

@Component
export default class SuppliersList extends Vue {
  data: Array<Supplier> = [];

  get suppliers(): Array<Supplier> | null {
    return this.$store.state.suppliersModule.suppliers;
  }

  getSuppliers(): void {
    this.$store.dispatch('suppliersModule/REQUEST_SUPPLIERS');
  }

  created(): void {
    this.getSuppliers();
  }
}
</script>

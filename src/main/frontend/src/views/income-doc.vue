<template>
  <div class="income-doc">
    <field-wrapper fieldTitle="Поставшик">
      <a-select @change="handleChange">
        <a-select-option v-for="(supplier, index) in suppliersOptions" :key="index" :value="supplier.value">
          {{ supplier.label }}
        </a-select-option>
      </a-select>
    </field-wrapper>
    <div v-if="supplier.id" class="mt-2 supplier-info">
      <h3>Данные поставщика:</h3>
      <div class="mt-2 supplier-info__inn"><b>ИНН:</b> {{ supplier.inn }}</div>
      <div class="mt-2 supplier-info__address"><b>Адрес: </b> {{ supplier.address }}</div>
      <div class="mt-2 supplier-info__phone"><b>Номер телефона: </b> {{ supplier.phone }}</div>
    </div>
    <a-card class="order mt-5" title="Позиции к заказу">
      <span slot="extra"><b>Итого:</b> {{ orderTotal }}</span>

      <a-empty v-if="!order.orderLines.length" />
      <div v-else class="order__lines"></div>
    </a-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import FieldWrapper from '@/components/FieldWrapper.vue';
import { ISupplier } from '@/types/Supplier';
import Supplier from './supplier.vue';
import ProductApiService from '@/services/ProductApiService';
import { Product } from '@/types/Product';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class IncomeDoc extends Vue {
  incomeDocument: any = {
    company_id: this.$store.state.companyModule.company.id,
  };

  products: Array<Product> = [];

  selectedSupplier: number | null = null;

  supplier: Supplier = {} as Supplier;

  order = {
    total: 0,
    orderLines: [],
  };

  get orderTotal(): number {
    if (this.order.orderLines.length) {
      return this.order.orderLines.reduce((accumulator, currentValue) => accumulator + (currentValue as any).line_total, 0);
    }
    return 0.0;
  }

  setCurrentSupplier(supplierId: number): void {
    this.supplier = this.$store.state.suppliersModule.suppliers.find((s: ISupplier) => s.id === supplierId);
  }

  handleChange(value: number): void {
    this.selectedSupplier = value;
    this.setCurrentSupplier(value);
  }

  get productsOptions(): Array<any> {
    return this.products.slice().map((s: Product) => ({
      label: s.name,
      value: s.sku,
    }));
  }

  get suppliersOptions(): Array<any> {
    return this.$store.state.suppliersModule.suppliers.slice().map((s: ISupplier) => ({
      label: s.name,
      value: s.id,
    }));
  }

  created(): void {
    ProductApiService.getProducts(this.$store.state.companyModule.company.id).then((res) => {
      this.products = res;
    });
  }
}
</script>

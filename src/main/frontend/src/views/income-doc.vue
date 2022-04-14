<template>
  <div class="income-doc">
    <field-wrapper fieldTitle="Поставшик">
      <a-select :disabled="disabled" v-model="selectedSupplier" @change="handleChange">
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
      <span slot="extra"> <a-button :disabled="disabled" @click="addOrderLine" shape="circle" icon="plus" /> <b class="ml-5">Итого:</b> {{ orderTotal }}</span>

      <a-empty v-if="!order.orderLines.length" />
      <div v-else class="order__lines">
        <a-table :disabled="disabled" :columns="columns" :data-source="order.orderLines">
          <div slot="product-select" slot-scope="record">
            <a-select :disabled="disabled" v-model="record.product.sku" class="w-full" @change="(value) => handleProductChange(value, record.id)">
              <a-select-option v-for="(product, index) in products" :key="index" :value="product.sku">
                {{ product.name }}
              </a-select-option>
            </a-select>
          </div>
          <span slot="quantity-select" slot-scope="record">
            <a-input-number :disabled="disabled" v-model="record.quantity" :min="1" />
          </span>
          <span slot="price_base" slot-scope="record">
            <div :class="{ 'line-through': record.product.price_sale > 0 }" class="price_base" v-if="record.product.price_base">{{ record.product.price_base }}</div>
          </span>
          <span slot="price_sale" slot-scope="record">
            <div class="price_sale" v-if="record.product.price_sale && record.product.price_sale > 0">{{ record.product.price_sale }}</div>
          </span>
          <span slot="total" slot-scope="record">
            <a-input-number :disabled="disabled" :value="lineTotal(record, record.id)" :min="1" />
          </span>
          <span slot="action" slot-scope="text, record">
            <a-tooltip><template #title>Удалить</template><a-button type="danger" :disabled="disabled" icon="delete" @click="deleteRow(record.id)"></a-button></a-tooltip>
          </span>
        </a-table>
      </div>
    </a-card>
    <div class="flex mt-5">
      <a-button v-if="$route.params.actionType !== 'watch'" @click="saveOrder">Сохранить</a-button>
      <a-button class="ml-2" @click="$router.back()">Назад</a-button>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { cloneDeep, uniqueId } from 'lodash';
import moment from 'moment';
import FieldWrapper from '@/components/FieldWrapper.vue';
import { ISupplier } from '@/types/Supplier';
import Supplier from './supplier.vue';
import ProductApiService from '@/services/ProductApiService';
import { Product } from '@/types/Product';
import IncomeDocumentService from '@/services/IncomeDocumentService';

const columns = [
  { title: 'Наименование', key: 'product', scopedSlots: { customRender: 'product-select' } },
  { title: 'Количество', scopedSlots: { customRender: 'quantity-select' } },
  { title: 'Цена', scopedSlots: { customRender: 'price_base' } },
  { title: 'Цена со скидкой', scopedSlots: { customRender: 'price_sale' } },
  { title: 'Всего', scopedSlots: { customRender: 'total' } },
  { title: 'Действия', key: 'action', scopedSlots: { customRender: 'action' } },
];

@Component({
  components: {
    FieldWrapper,
  },
  data() {
    return {
      columns,
    };
  },
})
export default class IncomeDoc extends Vue {
  order: any = {
    total: 0,
    orderLines: [],
  };

  doc: any = {
    created_at: moment().format('YYYY-MM-DD'),
  };

  products: Array<Product> = [];

  selectedSupplier: number | null = null;

  supplier: Supplier = {} as Supplier;

  addOrderLine(): void {
    this.order.orderLines.push({
      id: uniqueId(),
      product: {},
      quantity: 0,
      line_total: 0,
    });
  }

  get orderTotal(): number {
    if (this.order.orderLines.length) {
      const total = this.order.orderLines.reduce((accumulator, currentValue) => accumulator + (currentValue as any).line_total, 0);
      this.order.total = total;
      console.log(total, 'total');

      return total;
    }
    return 0.0;
  }

  deleteRow(rowIndex: string): void {
    const prIdx = this.order.orderLines.findIndex((l: any) => l.id === rowIndex);
    this.order.orderLines.splice(prIdx, 1);
  }

  lineTotal(line: any, idx: string): number {
    let total = 0;

    if (typeof line.product.price_sale === 'number' || typeof line.product.price_base === 'number') {
      if (line.product.price_sale > 0) {
        total = line.product.price_sale * line.quantity;
      } else {
        total = line.product.price_base * line.quantity;
      }
      const prIdx = this.order.orderLines.findIndex((l: any) => l.id === idx);
      this.order.orderLines[prIdx].line_total = total;
    }

    return total;
  }

  setCurrentSupplier(supplierId: number): void {
    this.supplier = this.$store.state.suppliersModule.suppliers.find((s: ISupplier) => s.id === supplierId);
  }

  handleChange(value: number): void {
    this.selectedSupplier = value;
    this.setCurrentSupplier(value);
  }

  handleProductChange(value: number, idx: number): void {
    const product = this.products.find((p) => p.sku === value);
    this.order.orderLines = this.order.orderLines.map((l: any) => {
      if (l.id === idx) {
        // eslint-disable-next-line no-param-reassign
        l.product = product;
        return l;
      }
      return l;
    });
  }

  saveOrder(): void {
    const orderCopy = cloneDeep(this.order);

    orderCopy.orderLines = orderCopy.orderLines.slice().map((l: any) => {
      // eslint-disable-next-line no-param-reassign
      delete l.id;
      return l;
    });

    const doc = {
      order: orderCopy,
      supplier: this.supplier,
      ...this.doc,
    };

    IncomeDocumentService.add({ doc, company_id: this.$store.state.companyModule.company.id });
  }

  get productsOptions(): Array<any> {
    return this.products.slice().map((s: Product) => ({
      label: s.name,
      value: s.sku,
    }));
  }

  get suppliersOptions(): Array<any> {
    return this.$store.state.suppliersModule.suppliers.map((s: ISupplier) => ({
      label: s.name,
      value: s.id,
    }));
  }

  disabled = false;

  created(): void {
    ProductApiService.getProducts(this.$store.state.companyModule.company.id).then((res) => {
      this.products = res;
    });

    if (this.$route.params.actionType === 'watch') {
      this.disabled = true;
      IncomeDocumentService.getIncomeDocument({ incomeDocId: Number.parseInt(this.$route.query.docId.toString(), 10) }).then((res) => {
        this.order = res.order;
        this.order.total = res.order.total;
        this.supplier = res.supplier;
        this.doc.created_at = res.created_at;
        this.selectedSupplier = res.supplier.id;
      });
    }
    if (this.$route.params.actionType === 'edit') {
      IncomeDocumentService.getIncomeDocument({ incomeDocId: Number.parseInt(this.$route.query.docId.toString(), 10) }).then((res) => {
        this.order = res.order;
        this.supplier = res.supplier;
        this.doc.created_at = res.created_at;
        this.selectedSupplier = res.supplier.id;
        this.supplier = res.supplier;
      });
    }
  }
}
</script>

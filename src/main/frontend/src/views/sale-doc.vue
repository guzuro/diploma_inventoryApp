<template>
  <a-spin :spinning="spinning">
    <div class="income-doc">
      <a-input v-model="client_name"/>
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
              <a-input-number disabled :value="lineTotal(record, record.id)" :min="1" />
            </span>
            <span slot="action" slot-scope="text, record">
              <a-tooltip>
                <template #title>Удалить</template>
                <a-button type="danger" :disabled="disabled" icon="delete" @click="deleteRow(record.id)" />
              </a-tooltip>
            </span>
          </a-table>
        </div>
      </a-card>
      <div class="flex mt-5">
        <a-button v-if="$route.params.actionType !== 'watch'" @click="saveOrder">Сохранить</a-button>
        <a-button class="ml-2" @click="$router.back()">Назад</a-button>
      </div>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { cloneDeep, uniqueId } from 'lodash';
import moment from 'moment';
import FieldWrapper from '@/components/FieldWrapper.vue';
import ProductApiService from '@/services/ProductApiService';
import { Product } from '@/types/Product';
import SaleDocumentService from '@/services/SaleDocumentService';

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

  spinning = false;

  doc: any = {
    created_at: moment().format('YYYY-MM-DD'),
  };

  products: Array<Product> = [];

  client_name = ''

  addOrderLine(): void {
    this.order.orderLines.push({
      id: uniqueId(),
      product: {},
      quantity: 1,
      line_total: 0,
    });
  }

  get orderTotal(): number {
    const total = this.order.orderLines.reduce((accumulator: any, currentValue: any) => accumulator + (currentValue as any).line_total, 0);
    this.order.total = total;

    return total;
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
      client_name: this.client_name,
      ...this.doc,
    };

    SaleDocumentService.add({ doc, company_id: this.$store.state.companyModule.company.id });
  }

  get productsOptions(): Array<any> {
    return this.products.slice().map((s: Product) => ({
      label: s.name,
      value: s.sku,
    }));
  }

  loadIncomeDocument(): void {
    SaleDocumentService.getSaleDocument({ saleDocId: Number.parseInt(this.$route.query.docId.toString(), 10) })
      .then((res) => {
        this.order = res.order;
        this.order.total = res.order.total;
        this.client_name = res.client_name;
        this.doc.created_at = res.created_at;
      })
      .finally(() => {
        this.spinning = false;
      });
  }

  disabled = false;

  created(): void {
    this.spinning = true;
    ProductApiService.getProducts(this.$store.state.companyModule.company.id).then((res) => {
      this.products = res;
    });

    if (this.$route.params.actionType === 'watch') {
      this.disabled = true;
      this.loadIncomeDocument();
    }
    if (this.$route.params.actionType === 'edit') {
      this.loadIncomeDocument();
    }
    if (this.$route.params.actionType === 'new') {
      this.spinning = false;
    }
  }
}
</script>

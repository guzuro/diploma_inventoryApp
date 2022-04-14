<template>
  <div class="items p-2">
    <div class="mb-5 flex justify-between">
      <h1>Все товары</h1>
      <a-button @click="onAddButtonClick"> Добавить </a-button>
    </div>

    <a-table :row-key="(record) => record.sku" :columns="columns" :data-source="data" :loading="loading">
      <div style="width: 100px" slot="product-image" slot-scope="record">
        <img v-if="record.photos[0]" :src="`${BaseApi.BASE_API}/assets/static/${record.photos[0]}`" />
      </div>
      <span slot="quantity" slot-scope="quantity"> {{ !!quantity ? quantity : '-' }} </span>
      <span slot="warehouse_id" slot-scope="warehouse_id">{{ !!warehouse_id ? warehouseName(warehouse_id) : '-' }} </span>
      <span slot="action" slot-scope="record">
        <a-tooltip
          ><template #title>Просмотр</template><a-icon :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-pink-900" type="eye" @click="watchProduct(record.sku)"></a-icon
        ></a-tooltip>
        <a-tooltip
          ><template #title>Редактировать</template><a-icon :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-purple-900" type="edit" @click="editProduct(record.sku)"></a-icon
        ></a-tooltip>
        <a-tooltip
          ><template #title>Удалить</template><a-icon :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-red-900" type="delete" @click="deleteProduct(record.sku)"></a-icon
        ></a-tooltip>
      </span>
    </a-table>

    <a-modal v-model="isCardModalActive">
      <div class="card">
        <div class="card-content">
          <div class="content">
            <p>Действительно удалить номенклатуру {{ selectedProduct.name }} (#{{ selectedProduct.code }})?</p>
            <div class="has-text-right">
              <a-button type="is-success" @click="onRemoveButtonClick">Удалить</a-button>
              <a-button class="ml-2" type="is-danger" @click="isCardModalActive = !isCardModalActive">Отмена</a-button>
            </div>
          </div>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Product } from '@/types/Product';
import { successNotification } from '@/services/NotificationService';
import ProductApiService from '@/services/ProductApiService';
import { Warehouse } from '@/types/Warehouse';
import BaseApi from '@/services/BaseApi';

const columns = [
  { key: 'image', scopedSlots: { customRender: 'product-image' } },
  { title: 'ШК', dataIndex: 'sku', key: 'sku' },
  { title: 'Наименование', dataIndex: 'name', key: 'name' },
  {
    title: 'Количество',
    dataIndex: 'quantity',
    key: 'quantity',
    scopedSlots: { customRender: 'quantity' },
  },
  {
    title: 'Склад',
    dataIndex: 'warehouse_id',
    key: 'warehouse_id',
    scopedSlots: { customRender: 'warehouse_id' },
  },
  { title: 'Действия', key: 'action', scopedSlots: { customRender: 'action' } },
];

@Component({
  data() {
    return { columns, BaseApi };
  },
})
export default class Items extends Vue {
  data: Array<Product> = [];

  checkedRows = [];

  loading = false;

  watchProduct(sku: number): void {
    this.$router.push({
      name: 'Product',
      params: {
        actionType: 'watch',
      },
      query: {
        sku: sku.toString(),
      },
    });
  }

  editProduct(sku: number): void {
    this.$router.push({
      name: 'Product',
      params: {
        actionType: 'edit',
      },
      query: {
        sku: sku.toString(),
      },
    });
  }

  deleteProduct(sku: number): void {
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const self = this;
    this.$confirm({
      title: 'Удалить позицию?',
      content: 'Вы Действительно хотите удалить номенклатуру?',
      async onOk() {
        await ProductApiService.deleteProduct(sku);
        await self.getProducts();
      },
    });
  }

  // eslint-disable-next-line class-methods-use-this
  editIconHandler(item: Product): void {
    this.$router.push({
      name: 'Product',
      params: {
        actionType: 'edit',
      },
      query: {
        product: item.sku.toString(),
      },
    });
  }

  isCardModalActive = false;

  selectedProduct: Product = {} as Product;

  // eslint-disable-next-line class-methods-use-this
  removeIconHandler(item: any): void {
    this.selectedProduct = item;
    this.isCardModalActive = true;
    console.log(item, 'remove');
  }

  onRemoveButtonClick(): void {
    this.$store.dispatch('productsModule/removeProduct', this.selectedProduct).then(() => {
      this.isCardModalActive = false;
      successNotification('Удалено');
    });
  }

  async getProducts(): Promise<void> {
    this.loading = true;
    this.data = await ProductApiService.getProducts(this.$store.getters['companyModule/getCompany'].id);
    this.loading = false;
  }

  warehouseName(id: number): string {
    return this.$store.state.configModule.config.warehouse.find((w: Warehouse) => w.id === id).title;
  }

  mounted(): void {
    this.getProducts();
  }

  onAddButtonClick(): void {
    this.$router.push({
      name: 'Product',
      params: {
        actionType: 'new',
      },
    });
  }
}
</script>

<style scoped>
</style>

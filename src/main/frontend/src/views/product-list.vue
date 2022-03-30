<template>
  <div class="items p-2">
    <div class="is-flex is-justify-content-space-between">
      <h1>Все товары</h1>
      <a-button class="is-right" @click="onAddButtonClick"> Добавить </a-button>
    </div>

    <a-table :columns="columns" :data-source="data">
      <span slot="action" slot-scope="text, record">
        <a>Invite 一 {{ record }}</a>
        <a-divider type="vertical" />
        <a>Delete</a>
        <a-divider type="vertical" />
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

const columns = [
  { title: 'ШК', dataIndex: 'sku', key: 'sku' },
  { title: 'Наименование', dataIndex: 'name', key: 'name' },
];

@Component({
  data() {
    return { columns };
  },
})
export default class Items extends Vue {
  data: Array<Product> = [];

  checkedRows = [];

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

  async mounted(): Promise<void> {
    this.data = await ProductApiService.getProducts(this.$store.getters['companyModule/getCompany'].id);
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

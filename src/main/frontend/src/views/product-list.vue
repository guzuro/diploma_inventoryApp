<template>
  <div class="items p-2">
    <div class="is-flex is-justify-content-space-between">
      <h1>
        Все товары
      </h1>
      <a-button class="is-right" @click="onAddButtonClick">
        Добавить
      </a-button>
    </div>

  <a-table :columns="columns" :data-source="data">
    <a slot="name" slot-scope="text">{{ text }}</a>
    <span slot="customTitle"><a-icon type="smile-o" /> Name</span>
    <span slot="tags" slot-scope="tags">
      <a-tag
        v-for="tag in tags"
        :key="tag"
        :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
      >
        {{ tag.toUpperCase() }}
      </a-tag>
    </span>
    <span slot="action" slot-scope="text, record">
      <a>Invite 一 {{ record }}</a>
      <a-divider type="vertical" />
      <a>Delete</a>
      <a-divider type="vertical" />
    </span>
  </a-table>

    <b-modal v-model="isCardModalActive" :width="350">
      <div class="card">
        <div class="card-content">
          <div class="content">
          <p>
            Действительно удалить номенклатуру {{ selectedProduct.name }} (#{{selectedProduct.code}})?
          </p>
          <div class="has-text-right">
            <a-button type="is-success" @click="onRemoveButtonClick">Удалить</a-button>
            <a-button class="ml-2" type="is-danger" @click="isCardModalActive = !isCardModalActive">Отмена</a-button>
          </div>
          </div>
        </div>
      </div>
    </b-modal>

  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Product } from '@/types/Product';
import { successNotification } from '@/services/NotificationService';
import ProductApiService from '@/services/ProductApiService';

@Component
export default class Items extends Vue {
  data = [];

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

  selectedProduct:Product = {} as Product;

  // eslint-disable-next-line class-methods-use-this
  removeIconHandler(item: any): void {
    this.selectedProduct = item;
    this.isCardModalActive = true;
    console.log(item, 'remove');
  }

  onRemoveButtonClick():void {
    this.$store.dispatch('productsModule/removeProduct', this.selectedProduct)
      .then(() => {
        this.isCardModalActive = false;
        successNotification('Удалено');
      });
  }

  mounted(): void {
    ProductApiService.getProducts(this.$store.getters['companyModule/getCompany'].id);
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

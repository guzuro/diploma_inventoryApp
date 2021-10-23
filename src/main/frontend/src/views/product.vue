<template>
  <div class="item p-2">
    <h1 class="page-title"> {{ pageTitle }} </h1>
    <product-form :product="product" @update="updateProduct"/>

    <b-button type="is-success" @click="onSaveButtonClick">Сохранить</b-button>
  </div>
</template>

<script lang="ts">

import Component from 'vue-class-component';
import Vue from 'vue';
import ProductForm from '@/components/ProducForm.vue';
import { Product } from '@/types/Product';

@Component({
  components: {
    ProductForm,
  },
})
export default class Item extends Vue {
  productActionType = '';

  product: Product = {
    name: '', category: '', quantity: 0, price: 0, description: '', images: [], imagesRemoved: [], unit: 'шт', price_old: 0,
  };

  updateProduct(product: Product): void {
    this.product = product;
  }

  onSaveButtonClick() {
    if (this.productActionType === 'new') {
      this.$store.dispatch('productsModule/addProduct', this.product);
    }
    if (this.productActionType === 'edit') {
      console.log('edit product action');
    }
  }

  get pageTitle(): string {
    if (this.productActionType === 'new') {
      return 'Добавить номенклатуру';
    }
    return 'Редактировать номенклатуру';
  }

  created(): void {
    if (this.$route.query.product) {
      this.product = this.$store.getters['productsModule/getProductByCode'](this.$route.query.product);
    }
    this.productActionType = this.$route.params.actionType;
  }
}
</script>

<style scoped>

</style>

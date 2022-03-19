<!--<template>-->
<!--  <div class="item p-2">-->
<!--    <h1 class="page-title"> {{ pageTitle }} </h1>-->
<!--    <product-form :product="product" @update="updateProduct"/>-->

<!--    <div class="mt-5 has-text-right">-->
<!--      <b-button class="mr-2" type="is-success" @click="onSaveButtonClick">Сохранить</b-button>-->
<!--      <b-button type="is-danger" @click="onCancelButtonClick">Отмена</b-button>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script lang="ts">-->

<!--import Component from 'vue-class-component';-->
<!--import Vue from 'vue';-->
<!--import ProductForm from '@/components/ProducForm.vue';-->
<!--import { Product } from '@/types/Product';-->
<!--import { successNotification } from '@/services/NotificationService';-->

<!--@Component({-->
<!--  components: {-->
<!--    ProductForm,-->
<!--  },-->
<!--})-->
<!--export default class Item extends Vue {-->
<!--  productActionType = '';-->

<!--  product: Product = {-->
<!--    name: '', currency: 'RUB', category: '', quantity: 0, price: 0, description: '', images: [], imagesRemoved: [], unit: 'шт', price_old: 0,-->
<!--  };-->

<!--  updateProduct(product: Product): void {-->
<!--    this.product = product;-->
<!--  }-->

<!--  onCancelButtonClick(): void {-->
<!--    this.$router.back();-->
<!--  }-->

<!--  onSaveButtonClick(): void {-->
<!--    if (this.productActionType === 'new') {-->
<!--      this.$store.dispatch('productsModule/addProduct', this.product)-->
<!--        .then(() => {-->
<!--          successNotification('Номенклатура добавлена');-->
<!--        });-->
<!--    }-->
<!--    if (this.productActionType === 'edit') {-->
<!--      this.$store.dispatch('productsModule/updateProduct', this.product)-->
<!--        .then(() => {-->
<!--          successNotification('Номенклатура обновлена');-->
<!--        });-->
<!--    }-->
<!--  }-->

<!--  get pageTitle(): string {-->
<!--    console.log(this.product);-->
<!--    if (this.productActionType === 'new') {-->
<!--      return 'Добавить номенклатуру';-->
<!--    }-->
<!--    return `Редактировать номенклатуру #${this.product.code}`;-->
<!--  }-->

<!--  created(): void {-->
<!--    if (this.$route.query.product) {-->
<!--      this.product = this.$store.getters['productsModule/getProductByCode'](this.$route.query.product);-->
<!--    }-->
<!--    this.productActionType = this.$route.params.actionType;-->
<!--  }-->

<!--  // eslint-disable-next-line class-methods-use-this-->
<!--  // getProductByCode(productCode:any): Product {-->
<!--  //   console.log(productCode);-->
<!--  //   throw new Error('Method not implemented.');-->
<!--  // }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->

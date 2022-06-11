<template>
  <div class="product">
    <b>Информация о товаре #</b> {{ product.id }}
    <br />
    <b> Наименование: </b>{{ product.name }}
    <br />
    <b> Цена: </b>{{ product.price }}
    <br />
    <b> Ед.измерения:</b> {{ product.unit }}
    <br />
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';

@Component
export default class ProductInfo extends Vue {
  get product(): any {
    return this.$store.getters.PRODUCT_BY_ID(this.productId);
  }

  get productId(): number {
    return +this.$route.params.id.toString();
  }
}
</script>

<style scoped></style>

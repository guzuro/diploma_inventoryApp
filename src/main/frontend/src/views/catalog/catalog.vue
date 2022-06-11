<template>
  <div class="catalog">
    <a-row type="flex" :gutter="16">
      <a-col v-for="product in productsCopy" class="gutter-row" :key="product.sku" :xs="24" :md="8">
        <product-card :product="product" @addToCart="addToCart" @cardClick="goToProductInfo" />
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts">
/* eslint-disable class-methods-use-this */
/* eslint-disable @typescript-eslint/no-explicit-any */ /* eslint-disable no-return-assign */
import Component from 'vue-class-component';
import Vue from 'vue';
import ProductCard from '@/components/ProductCard.vue';

@Component({
  components: { ProductCard },
})
export default class Catalog extends Vue {
  productsCopy: any[] = [];

  // products(): void {
  // ProductApiService.getProducts(Number.parseInt(this.$route.params.companyId, 10));
  // }

  loading = false;

  addToCart(product: any): void {
    this.$store
      .dispatch('ADD_PRODUCT_TO_CART', product)
      .then(() => {
        this.$message.success(`${product.name} добавлен в корзину`);
      })
      .catch(this.$message.error);
  }

  goToProductInfo(productId: number): void {
    this.$router.push({
      name: 'ProductInfo',
      params: {
        id: productId.toString(),
      },
    });
  }

  created(): void {
    this.loading = true;

    this.$store
      .dispatch('REQUEST_PRODUCTS', Number.parseInt(this.$route.params.companyId, 10))
      .then((response: any) => (this.productsCopy = response))
      .catch(console.error)
      .finally(() => (this.loading = false));
  }
}
</script>

<style scoped>
.catalog {
  padding: 0 10px;
}
</style>

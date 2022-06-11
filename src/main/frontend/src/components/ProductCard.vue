<template>
  <a-card class="product-card" :title="product.name">
    <img slot="cover" :src="`${BaseApi.BASE_API}/assets/static/${product.photos[0]}`" />
    <div class="body">
      {{ product.price_base }} , {{ product.currency }}
      <div v-if="product.price_sale" class="price-sale">{{ product.price_sale }}, {{ product.currency }}</div>
      <br />
      <div v-if="product.quantity"><b>Количество: </b> {{ product.quantity }}</div>
    </div>
    <template slot="actions" class="ant-card-actions">
      <a-icon key="shop" type="plus" @click="addToCart" />
      <a-icon key="eye" type="eye" @click="onCardClick" />
    </template>
  </a-card>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Emit, Prop } from 'vue-property-decorator';
import BaseApi from '@/services/BaseApi';

@Component({
  data() {
    return {
      BaseApi,
    };
  },
})
export default class ProductCard extends Vue {
  @Prop({
    type: Object,
    required: true,
  })
  product!: any;

  @Emit('addToCart')
  addToCart(): any {
    return this.product;
  }

  @Emit('cardClick')
  onCardClick(): number {
    return this.product.id;
  }
}
</script>

<style scoped>
.product-card {
  max-width: 500px;
  width: 100%;
  margin: 0 auto 5px auto;
}

.price-sale {
  color: red;
  font-size: 25px;
}
</style>

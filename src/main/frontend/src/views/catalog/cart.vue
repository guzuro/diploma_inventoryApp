<template>
  <a-spin :spinning="spinner">
    <div class="cart">
      <h1>Корзина</h1>
      <a-list item-layout="horizontal" :data-source="cartProducts">
        <a-list-item slot="renderItem" slot-scope="item" class="cart">
          <div class="cart-data">
            <div class="cart-header">
              <div class="cart-name">
                {{ item.product.name }}
              </div>
              <div class="cart-price">
                {{ item.product.price_sale ? item.product.price_sale : item.product.price_base }}, {{item.product.currency}} <span v-if="item.product.unit"> / {{ item.product.unit }} </span>
              </div>
            </div>
            <div class="cart-info">
              <a-input-number id="inputNumber" v-model="item.quantity" :min="0" @change="(value) => onChange(value, item)" />
            </div>
          </div>
        </a-list-item>
      </a-list>
      <div class="total">
        <p><b>Тотал:</b> {{ total }}</p>
        <a-button :disabled="!cartProducts.length" @click="doPayment">
          Заказать
        </a-button>
      </div>
    </div>
  </a-spin>
</template>

<script lang="ts">
/* eslint-disable class-methods-use-this */
/* eslint-disable @typescript-eslint/no-explicit-any */

import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class Cart extends Vue {
  spinner = false;

  getPrice(p: any): number {
    if (p.product.price_sale) {
      return p.product.price_sale;
    }
    return p.product.price_base;
  }

  get total(): number {
    const result = this.cartProducts.reduce((accumulator, p) => accumulator + this.getPrice(p) * p.quantity, 0);
    return +result.toFixed(2);
  }

  get cartProducts(): Array<{ product: any; quantity: number }> {
    return this.$store.getters.CART_LINES;
  }

  doPayment(): void {
    this.spinner = true;

    this.$notification.open({
      message: 'Успешно',
      description: 'Ващ заказ успешно сформирован! Вы будите перенаправлены в каталог...',
      duration: 2.5,
    });

    setTimeout(() => {
      this.$store.commit('CLEAR_CART');
      this.$router.push({ name: 'Catalog' });
    }, 3000);
  }

  onChange(value: number, line: { quantity: number; product: any }): void {
    this.$store.commit('UPDATE_CART_LINE_QUANTITY', line);
  }
}
</script>

<style scoped>
.cart-data {
  width: 100%;
  padding: 0px 25px;
  display: flex;
  justify-content: space-between;
}

.total {
  width: 100%;
  text-align: right;
  font-size: 1.2rem;
}
</style>

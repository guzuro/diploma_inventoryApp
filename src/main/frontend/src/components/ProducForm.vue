<template>
  <div class="item-form">
    <div class="card">
      <header class="card-header">
        <p class="card-header-title">
          Общая информация
        </p>
      </header>
      <div class="card-content">
        <b-field label="Наименование">
          <b-input v-model="productCopy.name"></b-input>
        </b-field>
        <b-field label="Описание">
          <b-input v-model="productCopy.description" type="textarea"></b-input>
        </b-field>
      </div>
    </div>
    <div class="card">
      <header class="card-header">
        <p class="card-header-title">
          Цена
        </p>
      </header>
      <div class="card-content">
        <b-field grouped>
          <b-field label="Текущая цена">
            <b-input v-model="productCopy.price" type="number"></b-input>
          </b-field>
          <b-field label="Старая цена">
            <b-input v-model="productCopy.price_old" type="number"></b-input>
          </b-field>
        </b-field>
      </div>
    </div>
    <div class="card">
      <header class="card-header">
        <p class="card-header-title">
          Учетная информация
        </p>
      </header>
      <div class="card-content">
        <b-field label="Артикул">
          <b-input v-model="productCopy.code"></b-input>
        </b-field>
        <b-field grouped>
          <b-field label="Текущее количество">
            <b-input v-model="productCopy.quantity" type="number"></b-input>
          </b-field>
          <b-field label="ед.">
            <b-input v-model="productCopy.unit"></b-input>
          </b-field>
        </b-field>
      </div>
    </div>
    <div class="card">
      <header class="card-header">
        <p class="card-header-title">
          Фотографии товара
        </p>
      </header>
      <div class="card-content">
      </div>
    </div>
    <b-field class="file is-primary" :class="{'has-name': !!productCopy.images}">
      <img :src="productCopy.images" alt="">
      <img :src="productCopy.imagesRemoved" alt="">
      <b-upload v-model="productCopy.images" class="file-label">
            <span class="file-cta">
                <b-icon class="file-icon" icon="upload"></b-icon>
                <span class="file-label">Click to upload</span>
            </span>
        <span class="file-name" v-if="productCopy.images">
                {{ productCopy.images.name }}
            </span>
      </b-upload>
      <b-button @click="onRemoveBgButtonClick">удалить фон</b-button>
    </b-field>
  </div>
</template>

<script lang="ts">

import Component from 'vue-class-component';
import Vue from 'vue';
import { Prop, Watch } from 'vue-property-decorator';
import ProductApiService from '@/services/ProductApiService';

@Component
export default class ProductForm extends Vue {
  @Prop() product!: any;

  productCopy: any = {};

  @Watch('productCopy', { deep: true }) onProductCopyChange(itemValue: any): void {
    this.$emit('update', itemValue);
  }

  // eslint-disable-next-line class-methods-use-this
  onRemoveBgButtonClick(): void {
    ProductApiService.removeBackground(this.productCopy.images)
      .then(({ data }) => {
        const urlCreator = window.URL || window.webkitURL;
        const imageUrl = urlCreator.createObjectURL(data);
        this.productCopy.imagesRemoved = imageUrl;
      });
  }

  mounted(): void {
    this.productCopy = this.product;
  }
}
</script>

<style scoped>

</style>

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
          <b-field label="Валюта">
            <b-select placeholder="Валюта"
                      v-model="productCopy.currency"
            >
              <option value="RUB">Рубль</option>
              <option value="USD">Доллар</option>
              <option value="EUR">Евро</option>
            </b-select>
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
          <b-input type="number" v-model="productCopy.code"></b-input>
        </b-field>
        <b-field grouped>
          <b-field label="Текущее количество">
            <b-input v-model="productCopy.quantity" type="number"></b-input>
          </b-field>
          <b-field label="ед.">
            <b-select placeholder="ед."
                      v-model="productCopy.unit"
            >
              <option value="кг">кг</option>
              <option value="шт">шт</option>
              <option value="л">л</option>
            </b-select>
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
        <b-upload v-model="productCopy.images" class="file-label" multiple @input="onFileChange">
            <span class="file-cta">
                <b-icon class="file-icon" icon="upload"></b-icon>
                <span class="file-label">Загрузить изображения</span>
            </span>
        </b-upload>
        <div v-if="previews.length" class="mt-5">
          <div class="previews"
               v-for="(preview, index) in previews"
               :key="index"
          >
            <div
              class="mb-2 preview-wrapper is-flex is-justify-content-space-between is-align-items-center">
              <img :src="preview.clearBlobUrl ? preview.clearBlobUrl : preview.blobLink" alt="">
              <div>
                <div class="is-clickable" @click="onRemoveBgButtonClick(preview.file, index)">
                  <b-icon icon="eraser-variant"></b-icon>
                </div>
                <div class="is-clickable" @click="onDeleteImageButtonClick(index)">
                  <b-icon icon="delete" type="is-danger"></b-icon>
                </div>
              </div>
            </div>
          </div>
        </div>
        <p class="has-text-centered mt-2 is-size-4" v-else> Изображения пока не загружены...</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Prop, Watch } from 'vue-property-decorator';
import { cloneDeep } from 'lodash';
import ProductApiService from '@/services/ProductApiService';
import { Product } from '@/types/Product';

@Component
export default class ProductForm extends Vue {
  @Prop() product!: any;

  productCopy: Product = {} as Product;

  previews: any[] = [];

  @Watch('previews') // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types,class-methods-use-this
  onPreviewsChange(value: any[]) {
    const valueCopy = cloneDeep(value);
    this.productCopy.images = valueCopy.map((p: any) => {
    // eslint-disable-next-line no-param-reassign
      delete p.clearBlobUrl;
      // eslint-disable-next-line no-param-reassign
      delete p.blobLink;
      return p;
    });
  }

  previewsWithoutBg: any[] = [];

  @Watch('productCopy', { deep: true }) onProductCopyChange(itemValue: any): void {
    this.$emit('update', itemValue);
  }

  // eslint-disable-next-line class-methods-use-this
  onRemoveBgButtonClick(image: File, index: number): void {
    ProductApiService.removeBackground(image)
      .then(({ data }) => {
        const urlCreator = window.URL || window.webkitURL;
        const imageUrl = urlCreator.createObjectURL(data);
        this.previews = this.previews.map((p: any, idx: number): any => {
          if (idx === index) {
            // eslint-disable-next-line no-param-reassign
            p.clearBlobUrl = imageUrl;
            // eslint-disable-next-line no-param-reassign
            p.clearBlob = data;
          }
          return p;
        });
      });
  }

  onDeleteImageButtonClick(index: number): void {
    this.previews.splice(index, 1);
  }

  onFileChange(e: any) {
    e.forEach((img: File) => {
      this.previews.push({ file: img, blobLink: URL.createObjectURL(img) });
    });
    this.productCopy.images = [];
  }

  created(): void {
    this.productCopy = this.product;
    if (this.productCopy.images) {
      console.log(1);
      const urlCreator = window.URL || window.webkitURL;
      this.previews = this.productCopy.images.map((i:any):any => {
        if (i.file) {
          console.log(2);
          this.$set(i, 'blobLink', urlCreator.createObjectURL(i.file));
        }
        if (i.clearBlob) {
          console.log(3);
          // eslint-disable-next-line no-param-reassign
          this.$set(i, 'clearBlobUrl', urlCreator.createObjectURL(i.clearBlob));
          // i.clearBlobUrl = urlCreator.createObjectURL(i.clearBlob);
        }
        return i;
      });
      console.log(this.productCopy);
    }
  }
}
</script>

<style scoped lang="scss">
.previews img {
  height: 150px;
  width: 150px;
  transition: 400ms all;

  &:hover {
    transform: scale(3);
  }
}
</style>

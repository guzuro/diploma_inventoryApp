<template>
  <div class="item-form">
    <a-card title="Общая информация">
      <field-wrapper fieldTitle="Наименование">
        <a-input v-model="productCopy.name"></a-input>
      </field-wrapper>
      <field-wrapper class="mt-2" fieldTitle="Описание">
        <a-input v-model="productCopy.description" type="textarea"></a-input>
      </field-wrapper>
    </a-card>
    <a-card class="mt-5" title="Цена">
      <div class="flex">
        <field-wrapper class="mt-2 w-full" fieldTitle="Обычная цена">
          <a-input v-model="productCopy.price_base" type="number"></a-input>
        </field-wrapper>
        <field-wrapper class="mt-2 ml-2 w-full" fieldTitle="Цена со скидкой">
          <a-input disabled :value="priceSale" type="number"></a-input>
        </field-wrapper>
      </div>
      <div class="flex">
        <field-wrapper class="mt-2 w-full" fieldTitle="Сумма скидки">
          <a-input v-model="productCopy.sale_value" type="number"></a-input>
        </field-wrapper>
        <field-wrapper class="mt-2 ml-2 w-full" fieldTitle="Выбрать скидку">
          <a-select allowClear placeholder="Выберите скидку к товару" @change="handleSaleSelect">
            <a-select-option v-for="(sale, index) in salesOptions" :key="index" :value="sale.value">
              {{ sale.label }}
            </a-select-option>
          </a-select>
        </field-wrapper>
      </div>
      <field-wrapper class="mt-2" fieldTitle="Валюта">
        <a-select placeholder="Валюта" v-model="productCopy.currency">
          <a-select-option value="RUB">Рубль</a-select-option>
          <a-select-option value="USD">Доллар</a-select-option>
          <a-select-option value="EUR">Евро</a-select-option>
        </a-select>
      </field-wrapper>
    </a-card>
    <a-card class="mt-5" title="Учетная информация">
      <field-wrapper fieldTitle="Артикул">
        <a-input type="number" v-model="productCopy.code"></a-input>
      </field-wrapper>
      <field-wrapper class="mt-2" fieldTitle="Текущее количество">
        <a-input v-model="productCopy.quantity" type="number"></a-input>
      </field-wrapper>
      <field-wrapper class="mt-2" fieldTitle="ед.">
        <a-select placeholder="ед." v-model="productCopy.unit">
          <a-select-option value="кг">кг</a-select-option>
          <a-select-option value="шт">шт</a-select-option>
          <a-select-option value="л">л</a-select-option>
        </a-select>
      </field-wrapper>
    </a-card>
    <a-upload :showUploadList="false" list-type="picture" :file-list="productCopy.photos" :before-upload="beforeUpload" :remove="handleRemove" @change="onFileChange">
      <a-button> <a-icon type="upload" /> Select File </a-button>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewVisible = false">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>

    <div v-if="previews.length" class="mt-5">
      <div class="previews" v-for="(preview, index) in previews" :key="index">
        <div class="mb-2 preview-wrapper is-flex is-justify-content-space-between is-align-items-center">
          <img :src="preview.clearBlobUrl ? preview.clearBlobUrl : preview.blobLink" alt="" />
          <div>
            <div class="cursor-pointer" @click="onRemoveBgButtonClick(preview.file, index)">
              <a-icon type="bg-colors" :style="{ fontSize: '18px' }" />
            </div>
            <div class="cursor-pointer mt-2" @click="onDeleteImageButtonClick(index)">
              <a-icon type="delete" :style="{ fontSize: '18px' }" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <p v-else class="has-text-centered mt-2 is-size-4">Изображения пока не загружены...</p>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Prop, Watch } from 'vue-property-decorator';
import { cloneDeep } from 'lodash';
import ProductApiService from '@/services/ProductApiService';
import FieldWrapper from './FieldWrapper.vue';
import { Sale } from '@/types/Sale';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class ProductForm extends Vue {
  @Prop() product!: any;

  productCopy: any = {
    photos: [],
    sale_id: 0,
    sale_value: null,
    price_base: 0,
    price_sale: 0,
  } as any;

  get priceBase(): number {
    return this.productCopy.price_base;
  }

  get priceSale(): number | undefined {
    if (!this.productCopy.sale_id && this.productCopy.sale_value > 0) {
      this.productCopy.price_sale = this.productCopy.price_base - this.productCopy.sale_value;
      return this.productCopy.price_base - this.productCopy.sale_value;
    }
    if (this.productCopy.sale_id) {
      this.productCopy.sale_value = null;
      const sale: Sale = this.$store.state.configModule.config.sale.find((s: Sale) => s.id === this.productCopy.sale_id);
      if (sale.type === 'procent') {
        this.productCopy.price_sale = this.productCopy.price_base - (this.productCopy.price_base * sale.value) / 100;
        return this.productCopy.price_base - (this.productCopy.price_base * sale.value) / 100;
      }
      this.productCopy.price_sale = this.productCopy.price_base - sale.value;
      return this.productCopy.price_base - sale.value;
    }
    return undefined;
  }

  handleSaleSelect(value: number | undefined): void {
    if (value !== undefined) {
      this.$set(this.productCopy, 'sale_id', value);
    } else {
      this.$set(this.productCopy, 'sale_id', null);
    }
  }

  previews: any[] = [];

  previewVisible = false;

  previewImage = '';

  previewsWithoutBg: any[] = [];

  @Watch('previews')
  onPreviewsChange(value: any[]): void {
    const valueCopy = cloneDeep(value);
    this.productCopy.images = valueCopy.map((p: any) => {
      // eslint-disable-next-line no-param-reassign
      delete p.clearBlobUrl;
      // eslint-disable-next-line no-param-reassign
      delete p.blobLink;
      return p;
    });
  }

  handleRemove(file: File): void {
    const index = this.productCopy.photos.indexOf(file);
    const newFileList = this.productCopy.photos.slice();
    newFileList.splice(index, 1);
    this.productCopy.photos = newFileList;
  }

  beforeUpload = (): boolean => false;

  @Watch('productCopy', { deep: true })
  onProductCopyChange(itemValue: any): void {
    this.$emit('update', itemValue);
  }

  onRemoveBgButtonClick(image: File, index: number): void {
    ProductApiService.removeBackground(image).then(({ data }) => {
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

  onFileChange({ fileList }: any): boolean {
    console.log(fileList);
    // this.productCopy.photos.push();
    fileList.forEach((img: any) => {
      this.previews.push({ file: img, blobLink: URL.createObjectURL(img.originFileObj) });
    });
    // this.productCopy.photos = [];
    return false;
  }

  get salesOptions(): Array<{ label: string; value: number }> {
    return this.$store.state.configModule.config.sale.slice().map((s: Sale) => ({
      label: `${s.name}, ${s.value} ${s.type === 'procent' ? '%' : this.$store.state.companyModule.company.currency}`,
      value: s.id,
    }));
  }

  created(): void {
    this.productCopy = this.product;
    if (this.productCopy.images) {
      console.log(1);
      const urlCreator = window.URL || window.webkitURL;
      this.previews = this.productCopy.images.map((i: any): any => {
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
.preview-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.previews img {
  width: 100%;
  max-width: 100px;
  height: 100px;

  //   &:hover {
  //     transform: scale(3);
  //   }
}
</style>

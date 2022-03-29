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
        <a-input type="number" v-model="productCopy.sku"></a-input>
      </field-wrapper>
      <field-wrapper class="mt-2 w-full" fieldTitle="Выбрать категорию">
        <a-select placeholder="Выберите категорию товара" @change="(value) => (productCopy.category = value)">
          <a-select-option v-for="(category, index) in categoryOptions" :key="index" :value="category.value">
            {{ category.label }}
          </a-select-option>
        </a-select>
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
    <a-upload :showUploadList="false" list-type="picture" :file-list="list" :before-upload="beforeUpload" :remove="handleRemove" @change="onFileChange">
      <a-button> <a-icon type="upload" /> Выбрать файл </a-button>
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
import ProductApiService from '@/services/ProductApiService';
import FieldWrapper from './FieldWrapper.vue';
import { Sale } from '@/types/Sale';
import { Product } from '@/types/Product';
import { Category } from '@/types/Category';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class ProductForm extends Vue {
  @Prop() product!: Product;

  productCopy = {} as Product;

  get priceBase(): number {
    return this.productCopy.price_base;
  }

  get priceSale(): number | undefined {
    if (!this.productCopy.sale_id && this.productCopy.sale_value && this.productCopy.sale_value > 0) {
      this.productCopy.price_sale = this.productCopy.price_base - this.productCopy.sale_value;
      return this.productCopy.price_base - this.productCopy.sale_value;
    }
    if (this.productCopy.sale_id) {
      this.productCopy.sale_value = 0;
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

  list = [];

  @Watch('previews', { deep: true })
  onPreviewsChange(): void {
    this.list.forEach((f: any) => this.productCopy.photos!.push(f));
  }

  handleRemove(file: File): void {
    const index = this.productCopy.photos!.indexOf(file);
    const newFileList = this.productCopy.photos!.slice();
    newFileList.splice(index, 1);
    this.productCopy.photos = newFileList;
  }

  beforeUpload = (): boolean => false;

  @Watch('productCopy', { deep: true })
  onProductCopyChange(itemValue: any): void {
    this.$emit('update', itemValue);
  }

  onRemoveBgButtonClick(image: any, index: number): void {
    ProductApiService.removeBackground(image.originFileObj).then(({ data }) => {
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
    this.list = fileList;
    fileList.forEach((img: any) => {
      this.previews.push({ file: img, blobLink: URL.createObjectURL(img.originFileObj) });
    });
    return false;
  }

  get categoryOptions(): Array<{ label: string; value: number }> {
    return this.$store.state.configModule.config.category.map((c: Category) => ({
      label: c.name,
      value: c.id,
    }));
  }

  get salesOptions(): Array<{ label: string; value: number }> {
    return this.$store.state.configModule.config.sale.map((s: Sale) => ({
      label: `${s.name}, ${s.value} ${s.type === 'procent' ? '%' : this.$store.state.companyModule.company.currency}`,
      value: s.id,
    }));
  }

  created(): void {
    this.productCopy = this.product;
    // if (this.productCopy.photos) {
    //   const urlCreator = window.URL || window.webkitURL;
    //   this.previews = this.productCopy.photos.map((i: any): any => {
    //     if (i.file) {
    //       this.$set(i, 'blobLink', urlCreator.createObjectURL(i.file));
    //     }
    //     if (i.clearBlob) {
    //       // eslint-disable-next-line no-param-reassign
    //       this.$set(i, 'clearBlobUrl', urlCreator.createObjectURL(i.clearBlob));
    //       // i.clearBlobUrl = urlCreator.createObjectURL(i.clearBlob);
    //     }
    //     return i;
    //   });
    //   console.log(this.productCopy);
    // }
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

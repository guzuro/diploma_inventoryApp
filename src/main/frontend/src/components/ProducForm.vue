<template>
  <div class="item-form">
    <a-card title="Общая информация">
      <field-wrapper fieldTitle="Наименование">
        <a-input :disabled="disabled" v-model="productCopy.name"></a-input>
      </field-wrapper>
      <field-wrapper class="mt-2" fieldTitle="Описание">
        <a-input :disabled="disabled" v-model="productCopy.description" type="textarea"></a-input>
      </field-wrapper>
    </a-card>
    <a-card class="mt-5" title="Цена">
      <div class="flex">
        <field-wrapper class="mt-2 w-full" fieldTitle="Обычная цена">
          <a-input :disabled="disabled" v-model="productCopy.price_base" type="number"></a-input>
        </field-wrapper>
        <field-wrapper class="mt-2 ml-2 w-full" fieldTitle="Цена со скидкой">
          <a-input disabled :value="priceSale" type="number"></a-input>
        </field-wrapper>
      </div>
      <div class="flex">
        <field-wrapper class="mt-2 w-full" fieldTitle="Сумма скидки">
          <a-input :disabled="disabled" v-model="productCopy.sale_value" type="number"></a-input>
        </field-wrapper>
        <field-wrapper class="mt-2 ml-2 w-full" fieldTitle="Выбрать скидку">
          <a-select :disabled="disabled" :defaultValue="productCopy.sale_id" allowClear placeholder="Выберите скидку к товару" @change="handleSaleSelect">
            <a-select-option v-for="(sale, index) in salesOptions" :key="index" :value="sale.value">
              {{ sale.label }}
            </a-select-option>
          </a-select>
        </field-wrapper>
      </div>
      <field-wrapper class="mt-2" fieldTitle="Валюта">
        <a-select :disabled="disabled" placeholder="Валюта" v-model="productCopy.currency">
          <a-select-option value="RUB">Рубль</a-select-option>
          <a-select-option value="USD">Доллар</a-select-option>
          <a-select-option value="EUR">Евро</a-select-option>
        </a-select>
      </field-wrapper>
    </a-card>
    <a-card class="mt-5" title="Учетная информация">
      <field-wrapper fieldTitle="Артикул">
        <a-input :disabled="disabled" type="number" v-model="productCopy.sku"></a-input>
      </field-wrapper>
      <field-wrapper v-if="!!productCopy.category" class="mt-2 w-full" fieldTitle="Выбрать категорию">
        <a-select :disabled="disabled" :defaultValue="productCopy.category" placeholder="Выберите категорию товара" @change="(value) => (productCopy.category = value)">
          <a-select-option v-for="(category, index) in categoryOptions" :key="index" :value="category.value">
            {{ category.label }}
          </a-select-option>
        </a-select>
      </field-wrapper>
      <field-wrapper v-if="!!productCopy.warehouse_id" class="mt-2 w-full" fieldTitle="Хранение (склад)">
        <a-select :disabled="disabled" :defaultValue="productCopy.warehouse_id" placeholder="Выберите склад" @change="(value) => (productCopy.warehouse_id = value)">
          <a-select-option v-for="(warehouse, index) in warehouseOptions" :key="index" :value="warehouse.value">
            {{ warehouse.label }}
          </a-select-option>
        </a-select>
      </field-wrapper>
      <field-wrapper v-if="!!productCopy.quantity" class="mt-2" fieldTitle="Текущее количество">
        <a-input :disabled="disabled" v-model="productCopy.quantity" type="number"></a-input>
      </field-wrapper>
      <field-wrapper v-if="!!productCopy.unit" class="mt-2" fieldTitle="ед.">
        <a-select :disabled="disabled" placeholder="ед." v-model="productCopy.unit">
          <a-select-option value="кг">кг</a-select-option>
          <a-select-option value="шт">шт</a-select-option>
          <a-select-option value="л">л</a-select-option>
        </a-select>
      </field-wrapper>
      <a-button :disabled="disabled" class="mt-2" @click="visible = true">Дополнительные параметры</a-button>
    </a-card>
    <a-upload :showUploadList="false" :action="uploadFile" list-type="picture" :file-list="productCopy.photos" :remove="handleRemove">
      <a-button class="mt-5" :disabled="disabled"> <a-icon type="upload" /> Выбрать файл </a-button>
    </a-upload>
    <!-- <a-modal :visible="previewVisible" :footer="null" @cancel="previewVisible = false">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal> -->
    <div v-if="productCopy.photos.length" class="mt-5">
      <div class="previews" v-for="(preview, index) in productCopy.photos" :key="index">
        <div class="mb-2 preview-wrapper is-flex is-justify-content-space-between is-align-items-center">
          <img :src="`${BaseApi.BASE_API}/assets/static/${preview}`" alt="" />
          <div>
            <div class="cursor-pointer" @click="onRemoveBgButtonClick(preview.file, index)">
              <a-button :disabled="disabled" icon="bg-colors" :style="{ fontSize: '18px' }" />
            </div>
            <div class="cursor-pointer mt-2" @click="onDeleteImageButtonClick(index)">
              <a-button :disabled="disabled" icon="delete" :style="{ fontSize: '18px' }" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <a-empty v-else class="mt-2"><span slot="description">Изображения пока не загружены...</span></a-empty>
    <a-drawer title="Дополнительные поля" placement="right" :closable="true" :visible="visible" @close="visible = false">
      <a-checkbox @change="handleCategoryAvailabilityChange" :checked="!!productCopy.category"> Категория </a-checkbox>
      <a-checkbox @change="handleQuantityAvailabilityChange" :checked="!!productCopy.quantity"> Количество </a-checkbox>
      <a-checkbox @change="handleUnitAvailabilityChange" :checked="!!productCopy.unit"> Ед. измерения </a-checkbox>
      <a-checkbox @change="handleWarehouseAvailabilityChange" :checked="!!productCopy.warehouse_id"> Склад хранения </a-checkbox>
    </a-drawer>
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
import { Product } from '@/types/Product';
import { Category } from '@/types/Category';
import { Warehouse } from '@/types/Warehouse';
import UploadFileService from '@/services/UploadFileService';
import BaseApi from '@/services/BaseApi';

@Component({
  components: {
    FieldWrapper,
  },
  data() {
    return {
      BaseApi,
    };
  },
})
export default class ProductForm extends Vue {
  @Prop() product!: Product;

  @Prop({ default: false }) disabled!: boolean;

  visible = false;

  productCopy = {} as Product;

  @Watch('productCopy', { deep: true })
  onProductCopyChange(itemValue: Product): void {
    this.$emit('update', itemValue);
  }

  uploadFile(file: File): void {
    const fd = new FormData();
    fd.append('photos[]', file);
    UploadFileService.uploadFile(fd).then((response) => {
      this.productCopy.photos.push(...response);
    });
  }

  get priceBase(): number {
    return this.productCopy.price_base;
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

  get warehouseOptions(): Array<{ label: string; value: number }> {
    return this.$store.state.configModule.config.warehouse.map((w: Warehouse) => ({
      label: w.title,
      value: w.id,
    }));
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

  handleRemove(file: string): void {
    const index = this.productCopy.photos!.indexOf(file);
    this.productCopy.photos.splice(index, 1);
  }

  onRemoveBgButtonClick(image: any, index: number): void {
    ProductApiService.removeBackground(image.originFileObj).then(({ data }) => {
      const urlCreator = window.URL || window.webkitURL;
      const imageUrl = urlCreator.createObjectURL(data);
      console.log(this.product);

      // this.previews = this.previews.map((p: any, idx: number): any => {
      //   if (idx === index) {
      //     // eslint-disable-next-line no-param-reassign
      //     p.clearBlobUrl = imageUrl;
      //     // eslint-disable-next-line no-param-reassign
      //     p.clearBlob = data;
      //   }
      //   return p;
      // });
    });
  }

  onDeleteImageButtonClick(index: number): void {
    this.productCopy.photos.splice(index, 1);
  }

  handleCategoryAvailabilityChange(value: Event): void {
    if ((value.target as HTMLInputElement).checked && this.categoryOptions.length) {
      this.productCopy.category = this.categoryOptions[0].value;
    }
    if (!(value.target as HTMLInputElement).checked) {
      this.productCopy.category = null;
    }
  }

  handleQuantityAvailabilityChange(value: Event): void {
    if ((value.target as HTMLInputElement).checked) {
      this.productCopy.quantity = 1;
    }
    if (!(value.target as HTMLInputElement).checked) {
      this.productCopy.quantity = null;
    }
  }

  handleUnitAvailabilityChange(value: Event): void {
    if ((value.target as HTMLInputElement).checked) {
      this.productCopy.unit = 'шт';
    } else {
      this.productCopy.unit = null;
    }
  }

  handleWarehouseAvailabilityChange(value: Event): void {
    if ((value.target as HTMLInputElement).checked && this.warehouseOptions.length) {
      this.productCopy.warehouse_id = this.warehouseOptions[0].value;
    }
    if (!(value.target as HTMLInputElement).checked) {
      this.productCopy.warehouse_id = null;
    }
  }

  created(): void {
    this.productCopy = cloneDeep(this.product);
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
}
</style>

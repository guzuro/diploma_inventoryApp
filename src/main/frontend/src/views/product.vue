<template>
  <div class="item p-2" v-if="product">
    <h1 class="page-title mb-5">{{ pageTitle }}</h1>
    <product-form :product="product" @update="updateProduct" :disabled="disabled" />
    <div class="mt-5 has-text-right">
      <a-button :disabled="disabled" class="mr-2" type="is-success" @click="onSaveButtonClick">Сохранить</a-button>
      <a-button :disabled="disabled" type="is-danger" @click="onCancelButtonClick">Отмена</a-button>
    </div>
  </div>
</template>
<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import ProductForm from '@/components/ProducForm.vue';
import { Product } from '@/types/Product';
import { successNotification } from '@/services/NotificationService';
import ProductApiService from '@/services/ProductApiService';

@Component({
  components: {
    ProductForm,
  },
})
export default class Item extends Vue {
  productActionType = '';

  product: Product | null = null;

  disabled = false;

  updateProduct(product: Product): void {
    this.product = product;
  }

  onCancelButtonClick(): void {
    this.$router.back();
  }

  onSaveButtonClick(): void {
    if (this.productActionType === 'new') {
      this.$store.dispatch('productsModule/addProduct', this.product).then(() => {
        successNotification('Номенклатура добавлена');
        this.$router.push({
          name: 'Products',
        });
      });
    }
    if (this.productActionType === 'edit') {
      this.$store.dispatch('productsModule/updateProduct', this.product).then(() => {
        successNotification('Номенклатура обновлена');
        this.$router.push({
          name: 'Products',
        });
      });
    }
  }

  get pageTitle(): string {
    const productSku = this.product!.sku;

    if (this.productActionType === 'new') {
      return 'Добавить номенклатуру';
    }
    if (this.productActionType === 'watch') {
      return `Просмотр номенклатуры #${productSku}`;
    }
    return `Редактировать номенклатуру #${productSku}`;
  }

  created(): void {
    this.productActionType = this.$route.params.actionType;

    if (this.productActionType === 'watch') {
      this.getProductBySku(Number.parseInt(this.$route.query.sku.toString(), 10));
      this.disabled = true;
    }
    if (this.productActionType === 'edit') {
      this.getProductBySku(Number.parseInt(this.$route.query.sku.toString(), 10));
    }
    if (this.productActionType === 'new') {
      this.product = {
        sku: 0,
        name: '',
        description: '',
        price_base: 0,
        price_sale: 0,
        sale_value: 0,
        sale_id: null,
        currency: 'RUB',
        category: null,
        quantity: null,
        unit: null,
        warehouse_id: null,
        company_id: this.$store.state.companyModule.company.id,
        photos: [],
      };
    }
  }

  async getProductBySku(sku: number): Promise<void> {
    this.product = await ProductApiService.getProduct(sku);
  }
}
</script>
<style scoped>
</style>

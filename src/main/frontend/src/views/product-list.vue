<template>
  <div class="items p-2">
    <div class="is-flex is-justify-content-space-between">
      <h1>
        Все товары
      </h1>
      <b-button class="is-right" type="is-warning" @click="onAddButtonClick">
        Добавить
      </b-button>
    </div>

    <b-table
      :data="data"
      :checked-rows.sync="checkedRows"
      checkable
      :checkbox-position="'left'"
      :pagination-simple="true"
      :paginated="true"
      :per-page="20"
      :sort-icon="'arrow-up'"
      :sort-icon-size="'is-small'"
    >
      <b-table-column field="code" label="Артикул" width="40" numeric searchable sortable
                      v-slot="props">
        {{ props.row.code }}
      </b-table-column>

      <b-table-column field="name" label="Наименование" searchable sortable v-slot="props">
        {{ props.row.name }}
      </b-table-column>

      <b-table-column field="category" label="Категория" sortable v-slot="props">
        {{ props.row.category }}
      </b-table-column>

      <b-table-column field="quantity" label="Количество" sortable v-slot="props">
        {{ props.row.quantity }}
      </b-table-column>

      <b-table-column field="price" label="Стоимость" sortable v-slot="props">
        {{ props.row.price }}
      </b-table-column>

      <b-table-column field="action" label="Действие" v-slot="props">
        <div>
          <div class="is-inline-block is-clickable" @click="editIconHandler(props.row)">
            <b-tooltip type="is-warning" label="Изменить">
              <b-icon icon="pencil" type="is-warning"></b-icon>
            </b-tooltip>
          </div>
          <div class="is-inline-block is-clickable" @click="removeIconHandler(props.row)">
            <b-tooltip type="is-danger" label="Удалить">
              <b-icon icon="delete" type="is-danger"></b-icon>
            </b-tooltip>
          </div>
        </div>
      </b-table-column>
    </b-table>
    <b-modal v-model="isCardModalActive" :width="350">
      <div class="card">
        <div class="card-content">
          <div class="content">
          <p>
            Действительно удалить номенклатуру {{ selectedProduct.name }} (#{{selectedProduct.code}})?
          </p>
          <div class="has-text-right">
            <b-button type="is-success" @click="onRemoveButtonClick">Удалить</b-button>
            <b-button class="ml-2" type="is-danger" @click="isCardModalActive = !isCardModalActive">Отмена</b-button>
          </div>
          </div>
        </div>
      </div>
    </b-modal>

  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Product } from '@/types/Product';
import { successNotification } from '@/services/NotificationService';

@Component
export default class Items extends Vue {
  data = [];

  checkedRows = [];

  // eslint-disable-next-line class-methods-use-this
  editIconHandler(item: Product): void {
    this.$router.push({
      name: 'Product',
      params: {
        actionType: 'edit',
      },
      query: {
        product: item.code!.toString(),
      },
    });
  }

  isCardModalActive = false;

  selectedProduct:Product = {} as Product;

  // eslint-disable-next-line class-methods-use-this
  removeIconHandler(item: any): void {
    this.selectedProduct = item;
    this.isCardModalActive = true;
    console.log(item, 'remove');
  }

  onRemoveButtonClick():void {
    this.$store.dispatch('productsModule/removeProduct', this.selectedProduct)
      .then(() => {
        this.isCardModalActive = false;
        successNotification('Удалено');
      });
  }

  mounted(): void {
    this.data = this.$store.getters['productsModule/getProducts'];
  }

  onAddButtonClick(): void {
    this.$router.push({
      name: 'Product',
      params: {
        actionType: 'new',
      },
    });
  }
}
</script>

<style scoped>

</style>

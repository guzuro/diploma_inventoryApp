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
      :columns="columns"
      :checked-rows.sync="checkedRows"
      checkable
      :checkbox-position="'left'"
      :pagination-simple="true"
      :paginated="true"
      :per-page="3"
      :sort-icon="'arrow-up'"
      :sort-icon-size="'is-small'"
    >

      <template #bottom-left>
        <b>Total checked</b>: {{ checkedRows.length }}
      </template>
    </b-table>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';

@Component
export default class Items extends Vue {
  data = [];

  checkedRows = [];

  columns = [
    {
      field: 'code',
      label: 'Артикул',
      width: '40',
      numeric: true,
      searchable: true,
    },
    {
      field: 'name',
      label: 'Наименование',
      searchable: true,
      sortable: true,
    },
    {
      field: 'category',
      label: 'Категория',
      sortable: true,
    },
    {
      field: 'quantity',
      label: 'Количество',
      centered: true,
      numeric: true,
      sortable: true,
    },
    {
      field: 'price',
      label: 'Стоимость',
      sortable: true,
    },
  ];

  mounted():void {
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

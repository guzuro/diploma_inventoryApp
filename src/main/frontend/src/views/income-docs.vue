<template>
  <div class="income-docs">
    <div class="mb-5 flex justify-between">
      <h1>Все товары</h1>
      <a-button @click="onAddButtonClick"> Сформировать накладную </a-button>
    </div>

    <a-table :row-key="(record) => record.id" :columns="columns" :data-source="docs" :loading="loading">
      <span slot="isPayed" slot-scope="isPayed">
        {{ isPayed ? 'Оплачен' : 'Не оплачен' }}
      </span>
      <span slot="action" slot-scope="record">
        <a-tooltip
          ><template #title>Просмотр</template><a-icon :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-pink-900" type="eye" @click="watchIncomeDoc(record.id)"></a-icon
        ></a-tooltip>
        <a-tooltip
          ><template #title>Редактировать</template><a-icon :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-purple-900" type="edit" @click="editIncomeDoc(record.id)"></a-icon
        ></a-tooltip>
        <a-tooltip
          ><template #title>Удалить</template><a-icon :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-red-900" type="delete" @click="deleteIncomeDoc(record.id)"></a-icon
        ></a-tooltip>
      </span>
    </a-table>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import IncomeDocumentService from '@/services/IncomeDocumentService';

const columns = [
  { title: '№', dataIndex: 'id', key: 'id' },
  { title: 'Поставщик', dataIndex: 'supplier_name', key: 'supplier_name' },
  { title: 'Дата форм-я', dataIndex: 'created_at', key: 'created_at' },
  {
    title: 'Статус оплаты',
    dataIndex: 'is_payed',
    key: 'is_payed',
    scopedSlots: { customRender: 'isPayed' },
  },
  { title: 'Всего', dataIndex: 'total', key: 'total' },
  { title: 'Действия', key: 'action', scopedSlots: { customRender: 'action' } },
];

@Component({
  data() {
    return { columns };
  },
})
export default class IncomeDocs extends Vue {
  docs: any[] = [];

  loading = false;

  get companyId(): number {
    return this.$store.getters['companyModule/getCompany'].id;
  }

  //   deleteIncomeDoc(docId: number): void {
  // eslint-disable-next-line @typescript-eslint/no-this-alias
  // const self = this;
  // this.$confirm({
  //   title: 'Удалить позицию?',
  //   content: 'Вы Действительно хотите удалить номенклатуру?',
  //   async onOk() {
  //     await ProductApiService.deleteProduct(docId);
  //     await self.getProducts();
  //   },
  // });
  //   }

  editIncomeDoc(docId: number): void {
    this.$router.push({
      name: 'IncomeDoc',
      params: {
        actionType: '',
      },
      query: {
        docId: docId.toString(),
      },
    });
  }

  watchIncomeDoc(docId: number): void {
    this.$router.push({
      name: 'IncomeDoc',
      params: {
        actionType: 'watch',
      },
      query: {
        docId: docId.toString(),
      },
    });
  }

  onAddButtonClick(): void {
    console.log(this);
  }

  created(): void {
    this.loading = true;
    IncomeDocumentService.getAll({ company_id: this.companyId })
      .then((response) => {
        this.docs = response;
      })
      .finally(() => {
        this.loading = false;
      });
  }
}
</script>

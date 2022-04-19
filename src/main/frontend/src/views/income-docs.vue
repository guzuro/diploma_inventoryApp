<template>
  <div class="income-docs">
    <div class="mb-5 flex flex-col md:flex-row justify-between">
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
          ><template #title>Редактировать</template
          ><a-icon v-if="!record.is_payed"  :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-purple-900" type="edit" @click="editIncomeDoc(record.id)"></a-icon
        ></a-tooltip>
        <a-tooltip
          ><template #title>Оплатить</template>
          <a-icon v-if="!record.is_payed" :style="{ fontSize: '19px' }" class="ml-2 cursor-pointer hover:text-green-900" type="dollar" @click="payIncomeDoc(record.id)"></a-icon
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
  { title: 'Поставщик', dataIndex: 'name', key: 'name' },
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

  payIncomeDoc(docId: number): void {
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const self = this;
    this.$confirm({
      title: 'Оплатить приходную номенклатуру?',
      content: 'Вы Действительно хотите Оплатить приходную номенклатуру?',
      async onOk() {
        await IncomeDocumentService.paytIncomeDocument({ incomeDocId: docId, company_id: self.$store.state.companyModule.company.id });
        self.$notification.success({
          message: 'Оплата прошла успешно',
          description: 'Оплата прошла успешно, единицы товара обновлены в соответствии с накладной.',
        });

        await self.getAllIncomeDocs();
      },
    });
  }

  editIncomeDoc(docId: number): void {
    this.$router.push({
      name: 'IncomeDoc',
      params: {
        actionType: 'edit',
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
    this.$router.push({
      name: 'IncomeDoc',
      params: {
        actionType: 'new',
      },
    });
  }

  getAllIncomeDocs(): void {
    IncomeDocumentService.getAll({ company_id: this.companyId })
      .then((response) => {
        this.docs = response;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  created(): void {
    this.loading = true;
    this.getAllIncomeDocs();
  }
}
</script>

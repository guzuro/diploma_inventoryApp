<template>
  <a-spin :spinning="spinning">
    <div class="sales">
      <a-button
        @click="
          isSaleModalOpen = true;
          modalMode = 'new';
        "
        class="mb-5"
        >Добавить скидку</a-button
      >
      <div v-if="sales && sales.length" class="flex flex-wrap">
        <div v-for="(sale, index) in sales" :key="index" class="mb-2 md:mb-0 md:mr-2 w-full md:w-1/3">
          <a-card hoverable :class="{ 'border-green-500': sale.is_active, 'border-red-500': !sale.is_active }">
            <template slot="actions" class="ant-card-actions">
              <a-icon key="edit" type="edit" @click="doEdit(sale)" />
            </template>
            <a-card-meta :title="sale.name" :description="`${sale.value} ${sale.type === 'fixed' ? 'руб.' : '%'}`" />
          </a-card>
        </div>
      </div>
      <div v-else>'empty '</div>
      <a-modal v-model="isSaleModalOpen" :title="modalMode === 'new' ? 'Добавить' : 'Редактировать'">
        <field-wrapper fieldTitle="Название">
          <a-input v-model="sale.name" />
        </field-wrapper>

        <field-wrapper class="mt-2" fieldTitle="Тип скидки">
          <a-select class="w-full" :default-value="saleTypeDefault" @change="(value) => (sale.type = value)">
            <a-select-option value="fixed"> Фиксированная </a-select-option>
            <a-select-option value="procent"> Процент </a-select-option>
          </a-select>
        </field-wrapper>
        <field-wrapper class="mt-2" fieldTitle="Значение">
          <a-input-number class="w-full" v-model="sale.value" :min="0" :max="getMax" step="0.10" />
        </field-wrapper>
        <field-wrapper class="mt-2" fieldTitle="Активна">
          <a-switch checked-children="Активна" un-checked-children="Не активна" v-model="sale.is_active" />
        </field-wrapper>

        <template slot="footer">
          <a-button key="submit" @click="modalMode === 'new' ? addSale() : updateSale()">Сохранить</a-button>
        </template>
      </a-modal>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { Watch } from 'vue-property-decorator';
import { Sale } from '@/types/Sale';
import FieldWrapper from '@/components/FieldWrapper.vue';
import SalesService from '@/services/Config/SalesService';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class Sales extends Vue {
  sales: Array<Sale> | null = null;

  spinning = false;

  isSaleModalOpen = false;

  modalMode = 'new';

  sale: Sale = {
    name: '',
    type: 'fixed',
    value: 0.0,
    is_active: false,
  };

  @Watch('isSaleModalOpen')
  onModalChange(state: boolean): void {
    if (!state) {
      this.sale = {
        name: '',
        type: 'fixed',
        value: 0.0,
        is_active: false,
      };
    }
  }

  get saleTypeDefault(): string {
    return this.sale.type;
  }

  get getMax(): number {
    if (this.sale.type === 'fixed') {
      return Number.MAX_SAFE_INTEGER;
    }
    return 100.0;
  }

  get companyId(): number {
    return this.$store.getters['companyModule/getCompany'].id;
  }

  getSales(): void {
    this.spinning = true;

    this.$store
      .dispatch('configModule/REQUEST_SALES')
      .then(() => {
        this.sales = this.$store.state.configModule.sales.slice();
      })
      .finally(() => {
        this.spinning = false;
      });
  }

  addSale(): void {
    SalesService.addSale({ sale: this.sale, company_id: this.companyId }).then(() => {
      this.getSales();
      this.isSaleModalOpen = false;
    });
  }

  updateSale(): void {
    SalesService.updateSale({ sale: this.sale }).then(() => {
      this.getSales();
      this.isSaleModalOpen = false;
    });
  }

  doEdit(sale: Sale): void {
    this.modalMode = 'edit';
    this.sale = { ...sale };
    this.isSaleModalOpen = true;
  }

  created(): void {
    this.getSales();
  }
}
</script>

<template>
  <div class="supplier">
    <a-skeleton :loading="loading">
      <field-wrapper class="mt-2" fieldTitle="Наименование поставщика">
        <a-input v-model="supplierModel.name" :disabled="disabled" />
      </field-wrapper>

      <field-wrapper class="mt-2" fieldTitle="Адрес">
        <a-input v-model="supplierModel.address" :disabled="disabled" />
      </field-wrapper>

      <field-wrapper class="mt-2" fieldTitle="Номер телефона">
        <a-input v-model="supplierModel.phone" :disabled="disabled" />
      </field-wrapper>

      <field-wrapper class="mt-2" fieldTitle="ИНН">
        <a-input v-model="supplierModel.inn" :disabled="disabled" />
      </field-wrapper>

      <a-button class="mt-5" @click="$router.back()">Назад</a-button>
      <a-button class="mt-5 ml-2" @click="saveSupplier" :disabled="disabled">Сохранить</a-button>
    </a-skeleton>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import FieldWrapper from '@/components/FieldWrapper.vue';
import SupplierService from '@/services/SupplierService';
import { ISupplier } from '@/types/Supplier';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class Supplier extends Vue {
  supplierModel: ISupplier = {
    name: '',
    address: '',
    phone: '',
    inn: 0,
  };

  loading = false;

  disabled = false;

  saveSupplier(): void {
    if (this.$route.params.actionType === 'edit') {
      SupplierService.updateSupplier({ supplier: this.supplierModel }).then(() => {
        this.$router.push({
          name: 'Suppliers',
        });
      });
    }
    if (this.$route.params.actionType === 'new') {
      SupplierService.addSupplier({ supplier: this.supplierModel, company_id: this.$store.state.companyModule.company.id }).then(() => {
        this.$router.push({
          name: 'Suppliers',
        });
      });
    }
  }

  created(): void {
    if (['edit', 'watch'].includes(this.$route.params.actionType)) {
      this.loading = true;

      SupplierService.getSupplier({ supplier_id: Number.parseInt(this.$route.query.supplierId.toString(), 10) })
        .then((res) => {
          this.supplierModel = res;
        })
        .finally(() => {
          this.loading = false;
        });
    }
    if (this.$route.params.actionType === 'watch') {
      this.disabled = true;
    }
  }
}
</script>

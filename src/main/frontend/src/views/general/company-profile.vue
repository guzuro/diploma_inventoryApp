<template>
  <a-spin :spinning="spinning">
    <div class="company-profile" v-if="companyCopy">
      <ValidationObserver v-slot="{ invalid }">
        <a-card title="Основное" class="w-full">
          <ValidationProvider name="Навание компании" immediate rules="required" v-slot="{ errors }">
            <field-wrapper :fieldTitle="'Навание компании'">
              <a-input type="text" v-model="companyCopy.name" />
              <span class="text-red-900"> {{ errors[0] }} </span>
            </field-wrapper>
          </ValidationProvider>
          <field-wrapper class="mt-2" :fieldTitle="'ИНН'">
            <a-input-number :min="1" :max="999999999999" class="w-full" v-model="companyCopy.inn" />
          </field-wrapper>
          <field-wrapper class="mt-2" :fieldTitle="'Номер телефона'">
            <a-input type="text" v-model="companyCopy.phone" />
          </field-wrapper>
          <field-wrapper class="mt-2" :fieldTitle="'Email'">
            <a-input type="text" v-model="companyCopy.email" />
          </field-wrapper>
        </a-card>
        <a-card title="Дополнительно" class="w-full mt-2">
          <field-wrapper :fieldTitle="'Страна'">
            <a-input type="text" v-model="companyCopy.country" />
          </field-wrapper>
          <field-wrapper class="mt-2" :fieldTitle="'Валюта'" @change="(value) => (companyCopy.currency = value)">
            <a-select class="w-full" :defaultValue="companyCopy.currency">
              <a-select-option v-for="(option, index) in currencies" :key="index" :value="option.value">
                {{ option.label }}
              </a-select-option>
            </a-select>
          </field-wrapper>
        </a-card>

        <div class="button-wrapper mt-5 flex justify-end">
          <a-button @click="updateCompanySettings" :disabled="invalid">Обновить настройки</a-button>
        </div>
      </ValidationObserver>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { ValidationObserver, ValidationProvider } from 'vee-validate';
import CompanyService from '@/services/CompanyService';
import FieldWrapper from '@/components/FieldWrapper.vue';
import currencies from '@/mixins/CurrencyList';

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
    FieldWrapper,
  },
  data() {
    return {
      currencies,
    };
  },
})
export default class CompanyProfile extends Vue {
  companyCopy = null;

  spinning = false;

  updateCompanySettings(): void {
    this.spinning = true;
    CompanyService.updateCompanyInfo(this.companyCopy)
      .then(() => {
        this.companyCopy = { ...this.$store.getters['companyModule/getCompany'] };
      })
      .finally(() => {
        this.spinning = false;
      });
  }

  created(): void {
    this.spinning = true;
    this.companyCopy = { ...this.$store.getters['companyModule/getCompany'] };
    this.spinning = false;
  }
}
</script>

<style scoped>
.spin-content {
  border: 1px solid #91d5ff;
  background-color: #e6f7ff;
  padding: 30px;
}
</style>

<template>
  <div class="main-user-info-form">
    <ValidationProvider name="E-mail" immediate rules="required|email" v-slot="{ errors }">
      <field-wrapper class="mt-5" :fieldTitle="'E-mail'">
        <a-input placeholder="Email" maxlength="30" v-model="user.email"> </a-input>
        <span class="text-red-900"> {{ errors[0] }} </span>
      </field-wrapper>
    </ValidationProvider>
    <ValidationProvider name="Фамилия" immediate rules="required" v-slot="{ errors }">
      <field-wrapper class="mt-5" :fieldTitle="'Фамилия'">
        <a-input placeholder="Фамилия" type="text" v-model="user.last_name"> </a-input>
        <span class="text-red-900"> {{ errors[0] }} </span>
      </field-wrapper>
    </ValidationProvider>
    <ValidationProvider name="Номер телефона" immediate rules="required" v-slot="{ errors }">
      <field-wrapper :fieldTitle="'Номер телефона'" class="mt-5">
        <a-input placeholder="Номер телефона" type="text" v-model="user.phone"> </a-input>
        <span class="text-red-900"> {{ errors[0] }} </span>
      </field-wrapper>
    </ValidationProvider>
    <ValidationProvider name="Пароль" immediate rules="required|min:7" v-slot="{ errors }">
      <field-wrapper class="mt-5" :fieldTitle="'Пароль'">
        <a-input placeholder="Пароль" type="password" password-reveal v-model="user.password"> </a-input>
        <span class="text-red-900"> {{ errors[0] }} </span>
      </field-wrapper>
    </ValidationProvider>
    <ValidationProvider name="Имя" immediate rules="required" v-slot="{ errors }">
      <field-wrapper class="mt-5" :fieldTitle="'Имя'">
        <a-input placeholder="Имя" type="text" v-model="user.first_name"> </a-input>
        <span class="text-red-900"> {{ errors[0] }} </span>
      </field-wrapper>
    </ValidationProvider>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, VModel } from 'vue-property-decorator';
import { ValidationProvider } from 'vee-validate';
import { User } from '@/types/User';
import FieldWrapper from './FieldWrapper.vue';

@Component({
  components: {
    FieldWrapper,
    ValidationProvider,
  },
})
export default class MainUserInfoForm extends Vue {
  @VModel() user!: User;
}
</script>

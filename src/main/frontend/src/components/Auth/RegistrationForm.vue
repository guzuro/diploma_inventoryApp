<template>
  <div class="register-form">
    <p class="is-size-3 has-text-centered mt-5">Регистрация</p>
    <ValidationObserver v-slot="{ invalid }">
      <div class="flex flex-col">
        <main-user-info-form v-model="user" />

        <ValidationProvider name="Название компании / ИП" immediate rules="required" v-slot="{ errors }">
          <a-input placeholder="Название компании / ИП" type="text" class="mt-5" v-model="company" />
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <a-button :disabled="invalid" class="ml-auto mt-5" @click="register"> Регистрация </a-button>
      </div>
    </ValidationObserver>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { ValidationObserver, ValidationProvider } from 'vee-validate';
import { User } from '@/types/User';
import AuthService from '@/services/AuthService';
import MainUserInfoForm from '../MainUserInfoForm.vue';
import { errorNotification, successNotification } from '@/services/NotificationService';

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
    MainUserInfoForm,
  },
})
export default class RegistrationForm extends Vue {
  user: User = {
    email: '',
    password: '',
    first_name: '',
    last_name: '',
    phone: '',
  };

  company = '';

  resetForm():void {
    this.user = {
      email: '',
      password: '',
      first_name: '',
      last_name: '',
      phone: '',
    };
    this.company = '';
  }

  async register(): Promise<void> {
    try {
      await AuthService.register({ user: this.user, company: this.company });
      this.resetForm();
      successNotification('Пользователь зарегистрирован');
    } catch (error:any) {
      errorNotification(error);
    }
  }
}
</script>

<style scoped land="scss">
.register-form__field {
  margin-top: 10px;
}
</style>

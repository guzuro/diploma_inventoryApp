<template>
  <div class="register-form">
    <p class="is-size-3 has-text-centered mt-5">Регистрация</p>
    <ValidationObserver v-slot="{ invalid }">
      <div class="flex flex-col">
        <ValidationProvider
          name="E-mail"
          immediate
          rules="required|email"
          v-slot="{ errors }"
        >
          <a-input
            class="mt-5"
            placeholder="Email"
            maxlength="30"
            v-model="user.email"
          >
          </a-input>
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <ValidationProvider
          name="Фамилия"
          immediate
          rules="required"
          v-slot="{ errors }"
        >
          <a-input
            class="mt-5"
            placeholder="Фамилия"
            type="text"
            v-model="user.last_name"
          >
          </a-input>
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <ValidationProvider
          name="Номер телефона"
          immediate
          rules="required"
          v-slot="{ errors }"
        >
          <a-input
            placeholder="Номер телефона"
            class="mt-5"
            type="text"
            v-model="user.phone"
          >
          </a-input>
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <ValidationProvider
          name="Пароль"
          immediate
          rules="required|min:7"
          v-slot="{ errors }"
        >
          <a-input
            class="mt-5"
            placeholder="Пароль"
            type="password"
            password-reveal
            v-model="user.password"
          >
          </a-input>
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <ValidationProvider
          name="Имя"
          immediate
          rules="required"
          v-slot="{ errors }"
        >
          <a-input
            class="mt-5"
            placeholder="Имя"
            type="text"
            v-model="user.first_name"
          >
          </a-input>
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>

        <ValidationProvider
          name="Название компании / ИП"
          immediate
          rules="required"
          v-slot="{ errors }"
        >
          <a-input
            placeholder="Название компании / ИП"
            type="text"
            class="mt-5"
            v-model="user.company"
          />
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <a-button :disabled="invalid" class="ml-auto mt-5" @click="register">
          Регистрация
        </a-button>
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

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
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

  company = ''

  async register(): Promise<void> {
    const res = await AuthService.register({ user: this.user, company: this.company });
    console.log(res);
  }
}
</script>

<style scoped land="scss">
.register-form__field {
  margin-top: 10px;
}
</style>

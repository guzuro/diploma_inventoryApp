<template>
  <div class="register-form">
    <p class="is-size-3 has-text-centered mt-5">Регистрация</p>
    <ValidationObserver v-slot="{ invalid }">
      <div class="columns">
        <div class="column is-half">
          <ValidationProvider
            name="E-mail"
            immediate
            rules="required|email"
            v-slot="{ errors }"
          >
            <b-field
              class="register-form__field"
              :message="errors[0]"
              label="Email"
              :type="errors[0] ? 'is-danger' : 'is-success'"
            >
              <b-input maxlength="30" v-model="user.email"> </b-input>
            </b-field>
          </ValidationProvider>
          <ValidationProvider
            name="Фамилия"
            immediate
            rules="required"
            v-slot="{ errors }"
          >
            <b-field
              class="register-form__field"
              label="Фамилия"
              :type="errors[0] ? 'is-danger' : 'is-success'"
              :message="errors[0]"
            >
              <b-input type="text" v-model="user.last_name"> </b-input>
            </b-field>
          </ValidationProvider>
          <ValidationProvider
            name="Номер телефона"
            immediate
            rules="required"
            v-slot="{ errors }"
          >
            <b-field
              class="register-form__field"
              label="Номер телефона"
              :type="errors[0] ? 'is-danger' : 'is-success'"
              :message="errors[0]"
            >
              <b-input type="text" v-model="user.phone"> </b-input>
            </b-field>
          </ValidationProvider>
        </div>
        <div class="column is-half">
          <ValidationProvider
            name="Пароль"
            immediate
            rules="required|min:7"
            v-slot="{ errors }"
          >
            <b-field
              class="register-form__field"
              label="Пароль"
              :type="errors[0] ? 'is-danger' : 'is-success'"
              :message="errors[0]"
            >
              <b-input type="password" password-reveal v-model="user.password">
              </b-input>
            </b-field>
          </ValidationProvider>
          <ValidationProvider
            name="Имя"
            immediate
            rules="required"
            v-slot="{ errors }"
          >
            <b-field
              class="register-form__field"
              label="Имя"
              :type="errors[0] ? 'is-danger' : 'is-success'"
              :message="errors[0]"
            >
              <b-input type="text" v-model="user.first_name"> </b-input>
            </b-field>
          </ValidationProvider>

          <ValidationProvider
            name="Название компании / ИП"
            immediate
            rules="required"
            v-slot="{ errors }"
          >
            <b-field
              class="register-form__field"
              label="Название компании / ИП"
              :type="errors[0] ? 'is-danger' : 'is-success'"
              :message="errors[0]"
            >
              <b-input type="text" v-model="user.company"> </b-input>
            </b-field>
          </ValidationProvider>
        </div>
      </div>
      <b-button
        :disabled="invalid"
        class="is-block ml-auto mt-5"
        type="is-success"
        @click="register"
      >
        Регистрация
      </b-button>
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
    company: '',
  };

  async register(): Promise<void> {
    const res = await AuthService.register(this.user);
    console.log(res);
  }
}
</script>

<style scoped land="scss">
.register-form__field {
  margin-top: 10px;
}
</style>

<template>
  <div class="login-form">
    <p class="is-size-3 has-text-centered">Войти в учетную запись</p>
    <ValidationObserver v-slot="{ invalid }">
      <ValidationProvider
        name="E-mail"
        immediate
        rules="required|email"
        v-slot="{ errors }"
      >
        <b-field
          :message="errors[0]"
          label="Email"
          :type="errors[0] ? 'is-danger' : 'is-success'"
        >
          <b-input maxlength="30" v-model="loginUser.email"> </b-input>
        </b-field>
      </ValidationProvider>
      <ValidationProvider
        name="Password"
        immediate
        rules="required|min:7"
        v-slot="{ errors }"
      >
        <b-field
          label="Password"
          :type="errors[0] ? 'is-danger' : 'is-success'"
          :message="errors[0]"
        >
          <b-input type="password" password-reveal v-model="loginUser.password">
          </b-input>
        </b-field>
      </ValidationProvider>
      <b-button
        :disabled="invalid"
        class="is-block ml-auto mt-5"
        type="is-success"
        @click="login"
      >
        Войти
      </b-button>
    </ValidationObserver>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { ValidationObserver, ValidationProvider } from 'vee-validate';
import AuthService from '@/services/AuthService';

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
  },
})
export default class LoginForm extends Vue {
  loginUser = {
    email: 'admin',
    password: 'admin',
  };

  async login(): Promise<void> {
    const response = await AuthService.login(this.loginUser);
    console.log(response);
  }
}
</script>

<style scoped>
</style>

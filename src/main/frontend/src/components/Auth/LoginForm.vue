<template>
  <div class="login-form">
    <p class="is-size-3 text-center mb-2">Войти в учетную запись</p>
    <ValidationObserver v-slot="{ invalid }">
      <div class="flex flex-col">
        <ValidationProvider
          name="E-mail"
          immediate
          rules="required|email"
          v-slot="{ errors }"
        >
          <a-input
            type="email"
            v-model="loginUser.email"
            placeholder="Email..."
          />
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <ValidationProvider
          name="Password"
          immediate
          rules="required|min:7"
          v-slot="{ errors }"
        >
          <a-input
            class="mt-5"
            type="password"
            v-model="loginUser.password"
            placeholder="Password..."
          />
          <span class="text-red-900"> {{ errors[0] }} </span>
        </ValidationProvider>
        <a-button :disabled="invalid" class="ml-auto mt-5" @click="login">
          Войти
        </a-button>
      </div>
    </ValidationObserver>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { ValidationObserver, ValidationProvider } from 'vee-validate';
import AuthService from '@/services/AuthService';
import { errorNotification } from '@/services/NotificationService';

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
  },
})
export default class LoginForm extends Vue {
  loginUser = {
    email: '',
    password: '',
  };

  async login(): Promise<void> {
    try {
      await AuthService.login(this.loginUser);
      await this.$router.push({
        name: 'Dashboard',
        params: {
          userId: this.$store.state.userModule.userData.id.toString(),
        },
      });
    } catch (e: any) {
      errorNotification(e);
    }
  }
}
</script>

<style scoped>
</style>

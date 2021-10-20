<template>
  <div class="login-form">
    <p class="is-size-3 has-text-centered">Войти в учетную запись</p>
    <b-field label="Email"
             type="is-danger"
             message="This email is invalid"
    >
      <b-input type="email"
               value="john@"
               maxlength="30"
               v-model="login"
      >
      </b-input>
    </b-field>
    <b-field label="Password">
      <b-input type="password"
               value="iwantmytreasure"
               password-reveal
               v-model="password"
      >
      </b-input>
    </b-field>
    <b-button class="is-block ml-auto" type="is-success" @click="submit">Войти</b-button>
    <b-button class="is-block ml-auto mt-2" type="is-primary">Регистрация</b-button>
  </div>

</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';

@Component
export default class LoginForm extends Vue {
  login = 'admin';

  password = 'admin';

  submit(): void {
    let validData = false;
    let foundUser = null;

    this.$store.state.defaultUsers.forEach((df: any) => {
      if (this.login === df.login && this.password === df.password) {
        validData = true;
        foundUser = df;
      }
    });

    if (validData && foundUser !== null) {
      this.$store.dispatch('userModule/setUserToStore', foundUser);
      this.$store.dispatch('userModule/setAuthState', true);
      this.$router.push({ name: 'Dashboard', params: { role: (foundUser as any).profile.role } });
    } else {
      this.login = '';
      this.password = '';
    }
  }
}
</script>

<style scoped>

</style>

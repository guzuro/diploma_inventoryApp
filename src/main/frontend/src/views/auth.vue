<template>
  <div class="auth">
    <div class="auth-wrapper">
      <div class="auth-right__content px-5">
        <component :is="currentForm[form].view" />
      </div>
      <div class="has-text-centered">
        <p>
          Уже есть аккаунт?
          <span
            @click="form = 'login'"
            class="
              auth-change-view
              is-uppercase
              has-text-weight-bold
              is-clickable
            "
            >Войти</span
          >
        </p>
        <hr />
        <p
          @click="form = 'registration'"
          class="
            auth-change-view
            is-uppercase
            has-text-weight-bold
            is-clickable
          "
        >
          Регистрация
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import LoginForm from '@/components/Auth/LoginForm.vue';

@Component({
  components: { LoginForm },
})
export default class Auth extends Vue {
  form = 'login';

  currentForm = {
    login: {
      view: () => import('../components/Auth/LoginForm.vue'),
    },
    registration: {
      view: () => import('../components/Auth/RegistrationForm.vue'),
    },
  };
}
</script>

<style lang="scss">
.auth {
  background-image: url('../assets/images/login-right.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;

  &-wrapper {
    width: 100%;
    margin:0 auto;
    max-width: 933px;
    padding: 10px;

    background-color: white;
  }
  .auth-change-view {
    &:hover {
      color: hsl(204, 86%, 53%);
    }
  }
}
</style>

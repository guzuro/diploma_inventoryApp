<template>
  <div class="auth">
    <div class="columns">
      <div class="column is-two-fifths auth-left is-align-self-center has-shadow">
        <div class="auth-right__content px-5">
          <LoginForm/>
        </div>
      </div>
      <div class="column auth-right">
        <div class="auth-right__content px-5">
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import LoginForm from '@/components/LoginForm.vue';

@Component({
  components: { LoginForm },
})
export default class Auth extends Vue {
}
</script>

<style lang="scss">
.auth {
  height: 100%;
  &-right {
    background-image: url('../assets/images/login-right.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    position: relative;
  }
}
</style>

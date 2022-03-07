<template>
  <a-spin :spinning="spinning">
    <div class="profile" v-if="userCopy">
      <a-card title="Мой профиль" class="w-full">
        <div class="flex flex-col md:flex-row md:justify-between">
          <a-list-item>
            <a-list-item-meta :description="userCompany">
              <div slot="title">Название компании / ИП</div>
            </a-list-item-meta>
          </a-list-item>
          <a-list-item>
            <a-list-item-meta :description="userCopy.role">
              <div slot="title">Должность</div>
            </a-list-item-meta>
          </a-list-item>
        </div>
        <ValidationObserver v-slot="{ invalid }">
          <div class="flex flex-col">
            <ValidationProvider name="Имя" immediate rules="required" v-slot="{ errors }">
              <field-wrapper class="mt-5" :fieldTitle="'Имя'">
                <a-input placeholder="Имя" type="text" v-model="userCopy.first_name"> </a-input>
                <span class="text-red-900"> {{ errors[0] }} </span>
              </field-wrapper>
            </ValidationProvider>
            <ValidationProvider name="Фамилия" immediate rules="required" v-slot="{ errors }">
              <field-wrapper class="mt-5" :fieldTitle="'Фамилия'">
                <a-input placeholder="Фамилия" type="text" v-model="userCopy.last_name"> </a-input>
                <span class="text-red-900"> {{ errors[0] }} </span>
              </field-wrapper>
            </ValidationProvider>
            <ValidationProvider name="E-mail" immediate rules="required|email" v-slot="{ errors }">
              <field-wrapper class="mt-5" :fieldTitle="'Email'">
                <a-input placeholder="Email" maxlength="30" v-model="userCopy.email"> </a-input>
                <span class="text-red-900"> {{ errors[0] }} </span>
              </field-wrapper>
            </ValidationProvider>

            <ValidationProvider name="Номер телефона" immediate rules="required" v-slot="{ errors }">
              <field-wrapper class="mt-5" :fieldTitle="'Номер телефона'">
                <a-input placeholder="Номер телефона" type="text" v-model="userCopy.phone"> </a-input>
                <span class="text-red-900"> {{ errors[0] }} </span>
              </field-wrapper>
            </ValidationProvider>
            <field-wrapper class="mt-5" :fieldTitle="'Пароль'">
              <a-input placeholder="Пароль" type="password" password-reveal v-model="userCopy.password"> </a-input>
            </field-wrapper>
            <field-wrapper class="mt-5 ml-2" :fieldTitle="'Новый пароль'">
              <a-input placeholder="Новый пароль" type="password" password-reveal v-model="newPassword.password"> </a-input>
            </field-wrapper>
            <field-wrapper class="mt-5 ml-2" :fieldTitle="'Повторите новый пароль'">
              <a-input placeholder="Повторите новый пароль" type="password" password-reveal v-model="newPassword.approved"> </a-input>
            </field-wrapper>

            <a-button :disabled="invalid" class="ml-auto mt-5" @click="updateUser"> Сохранить </a-button>
          </div>
        </ValidationObserver>
      </a-card>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { ValidationObserver, ValidationProvider } from 'vee-validate';
import FieldWrapper from '@/components/FieldWrapper.vue';
import UserService from '@/services/UserService';

@Component({
  components: {
    ValidationObserver,
    ValidationProvider,
    FieldWrapper,
  },
})
export default class Profile extends Vue {
  userCopy: any = null;

  spinning = false;

  newPassword = {
    password: '',
    approved: '',
  };

  get userCompany(): string {
    return this.$store.state.companyModule.company.name;
  }

  updateUser(user: any) {
    this.spinning = true;
    UserService.updateUser(this.userCopy, this.newPassword)
      .then(() => {
        this.userCopy = { ...this.$store.state.userModule.userData };
      })
      .finally(() => {
        this.spinning = false;
      });
  }

  created(): void {
    this.userCopy = { ...this.$store.state.userModule.userData };
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

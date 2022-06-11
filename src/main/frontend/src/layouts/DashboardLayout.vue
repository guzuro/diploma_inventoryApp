<template>
  <a-layout id="components-layout-demo-responsive">
    <a-layout-sider breakpoint="lg" collapsed-width="0">
      <div class="logo" />
      <a-menu theme="dark" mode="inline" :default-selected-keys="activeKey" :style="{ lineHeight: '64px' }">
        <a-menu-item key="Dashboard" @click="goto('Dashboard')"> <a-icon type="appstore" />Dashboard </a-menu-item>
        <a-menu-item key="Products" @click="goto('Products')"> <a-icon type="shop" />Продукты </a-menu-item>
        <a-sub-menu>
          <span slot="title" class="submenu-title-wrapper"><a-icon type="setting" />Закупки</span>
          <a-menu-item-group>
            <a-menu-item key="setting:1" @click="goto('Suppliers')"> Поставщики </a-menu-item>
            <a-menu-item key="setting:2" @click="goto('IncomeDocs')"> Приходные накладные </a-menu-item>
          </a-menu-item-group>
        </a-sub-menu>
         <a-sub-menu>
          <span slot="title" class="submenu-title-wrapper"><a-icon type="setting" />Продажи</span>
          <a-menu-item-group>
            <a-menu-item key="setting:3" @click="goto('SaleDocs')"> Расходная накладная </a-menu-item>
          </a-menu-item-group>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header :style="{ background: '#fff', padding: 0 }">
        <div class="navbar flex h-full justify-end items-center">
          <a-button type="primary" icon="user" @click="handleUserIconClick" />
        </div>
      </a-layout-header>
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div :style="{ padding: '24px', background: '#fff', minHeight: '100vh' }">
          <router-view></router-view>
        </div>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts">
/* eslint-disable */
import moment from 'moment';
import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class DashboardLayout extends Vue {
  currentTime = moment().format('YYYY-MM-DD, HH:mm');

  get activeKey(): Array<string | undefined | null> {
    return [this.$route.name];
  }

  goto(name: string): void {
    this.$router.push({
      name,
      params: {
        userId: this.$store.state.userModule.userData.id.toString(),
      },
    });
  }

  handleUserIconClick(): void {
    this.$router.push({
      name: 'Profile',
      params: {
        userId: this.$store.state.userModule.userData.id,
      },
    });
  }

  created(): void {
    setInterval(() => {
      this.currentTime = moment().format('YYYY-MM-DD, HH:mm');
    }, 5000);
  }
}
</script>

<style lang="scss">
.navbar {
  padding: 0px 16px;
}
// .ant-layout {
//   height: 100vh;
// }
// .ant-layout-header {
//   height: px;
// }
</style>

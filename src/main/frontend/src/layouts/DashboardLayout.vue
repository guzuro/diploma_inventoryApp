<template>
  <div class="dashboard-layout">
    <a-layout id="components-layout-demo-top" class="layout">
      <a-layout-header>
        <div class="logo" />
        <a-menu theme="dark" mode="horizontal" :default-selected-keys="['2']" :style="{ lineHeight: '42px' }">
          <a-menu-item key="mail"> <a-icon type="mail" />Navigation One </a-menu-item>
          <a-sub-menu>
            <span slot="title" class="submenu-title-wrapper"><a-icon type="setting" />Navigation Three - Submenu</span>
            <a-menu-item-group title="Item 1">
              <a-menu-item key="setting:1"> Option 1 </a-menu-item>
              <a-menu-item key="setting:2"> Option 2 </a-menu-item>
            </a-menu-item-group>
            <a-menu-item-group title="Item 2">
              <a-menu-item key="setting:3"> Option 3 </a-menu-item>
              <a-menu-item key="setting:4"> Option 4 </a-menu-item>
            </a-menu-item-group>
          </a-sub-menu>
          <a-menu-item key="alipay">
            <a href="https://antdv.com" target="_blank" rel="noopener noreferrer">Navigation Four - Link</a>
          </a-menu-item>
        </a-menu>
      </a-layout-header>
      <a-layout-content class="mt-5" style="padding: 0 50px">
        <a-breadcrumb>
          <a-breadcrumb-item class="mt-5 mb-5">{{ currentTime }}</a-breadcrumb-item>
        </a-breadcrumb>
        <div :style="{ background: '#fff', padding: '24px', minHeight: '280px' }">
          <router-view></router-view>
        </div>
      </a-layout-content>
      <a-layout-footer style="text-align: center"> Per aspera ad astra </a-layout-footer>
    </a-layout>
  </div>
</template>

<script lang="ts">
import moment from 'moment';
import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class DashboardLayout extends Vue {
  currentTime = moment().format('YYYY-MM-DD HH:mm');

  created(): void {
    setInterval(() => {
      this.currentTime = moment().format('YYYY-MM-DD HH:mm');
    }, 5000);
  }
}
</script>

<style lang="scss">
.ant-layout {
  height: 100vh;
}
.ant-layout-header {
  height: 45px;
}
</style>

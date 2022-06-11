<template>
  <div id="app">
    <a-layout id="components-layout-demo-responsive">
      <a-layout-sider breakpoint="lg" collapsed-width="0">
        <div class="logo" />
        <a-menu theme="dark" mode="inline">
          <a-menu-item @click="navigateTo('Catalog')">
            <a-icon type="user" />
            <span class="nav-text">Каталог</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0 }">
          <div class="header__inner">
            <a-badge :count="$store.getters.CART_ITEMS_LENGTH">
              <div>
                <a-icon type="shopping-cart" :style="{ fontSize: '28px' }" @click="navigateTo('CatalogCart')" />
              </div>
            </a-badge>
          </div>
        </a-layout-header>
        <a-layout-content :style="{ margin: '24px 16px 0' }">
          <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
            <router-view />
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';

@Component
export default class App extends Vue {
  navigateTo(routeName: string): void {
    this.$router.push({
      name: routeName,
    });
  }
}
</script>

<style>
.header__inner {
  width: 100%;
  margin-top: 15px;
  padding: 0px 30px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

body,
html {
  height: 100%;
}

#app,
.ant-layout {
  height: 100vh;
}

/*#nav {*/
/*    padding: 30px;*/
/*}*/

/*#nav a {*/
/*    font-weight: bold;*/
/*    color: #2c3e50;*/
/*}*/

/*#nav a.router-link-exact-active {*/
/*    color: #42b983;*/
/*}*/
</style>

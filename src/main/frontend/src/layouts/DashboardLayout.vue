<template>
  <a-layout id="components-layout-demo-custom-trigger">
    <a-layout-sider breakpoint="md" collapsed-width="0" @collapse="onCollapse" @breakpoint="onBreakpoint">
      <div class="logo" />
      <a-menu mode="inline" :default-selected-keys="activeKey" :default-open-keys="activeGroup">
        <template v-for="item in navItems">
          <a-menu-item v-if="!item.children" :key="item.path" @click="navigateTo(item.path)">
            <a-icon :type="item.icon" />
            <span>{{ item.name }}</span>
          </a-menu-item>

          <a-sub-menu v-else :key="item.guid">
            <span slot="title">
              <a-icon :type="item.icon" />
              <span>{{ item.name }} </span>
            </span>
            <a-menu-item v-for="menuChildren in item.children" :key="menuChildren.path" @click="navigateTo(menuChildren.path)">
              <a-icon :type="menuChildren.icon" />
              <span>
                {{ menuChildren.name }}
              </span>
            </a-menu-item>
          </a-sub-menu>
        </template>
        <a-menu-item @click="doLogout()">
          <a-icon type="close" />
          <span>Выход</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-content
        :style="{
          margin: '24px 16px',
          padding: '24px',
          background: '#fff',
          minHeight: '280px',
        }"
      >
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import { Mixins } from 'vue-property-decorator';
import AuthService from '@/services/AuthService';
import NavigationItems from '@/mixins/NavigationItems.mixin';

@Component
export default class DashboardLayout extends Mixins<NavigationItems>(NavigationItems) {
  activeGroup: Array<string> = [];

  navigateTo(path: string): void {
    if (this.$route.path !== path) this.$router.push(path);
  }

  created(): void {
    // eslint-disable-next-line no-restricted-syntax
    for (const item of this.navItems) {
      if (item.children) {
        // eslint-disable-next-line no-restricted-syntax
        for (const ch of item.children) {
          if (this.$route.path === ch.path) {
            this.activeGroup.push(item.guid);
          }
        }
      }
    }
  }

  get activeKey(): Array<string> {
    return [this.$route.path];
  }

  onCollapse = (collapsed: any, type: any) => {
    console.log(collapsed, type);
  };

  onBreakpoint = (broken: any) => {
    console.log(broken);
  };

  async doLogout(): Promise<void> {
    await AuthService.logout();
    this.$router.push({
      name: 'Login',
    });
  }
}
</script>

<style lang="scss">
.ant-layout {
  height: 100%;
}
</style>

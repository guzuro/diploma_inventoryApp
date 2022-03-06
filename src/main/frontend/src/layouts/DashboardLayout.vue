<template>
  <a-layout id="components-layout-demo-custom-trigger">
    <a-layout-sider
      breakpoint="md"
      collapsed-width="0"
      @collapse="onCollapse"
      @breakpoint="onBreakpoint"
    >
      <div class="logo" />
      <a-menu
        mode="inline"
        :default-selected-keys="activeKey"
        :default-open-keys="activeGroup"
      >
        <template v-for="item in navItems">
          <a-menu-item
            v-if="!item.children"
            :key="item.path"
            @click="$router.push(item.path)"
          >
            <span>{{ item.name }}</span>
          </a-menu-item>

          <a-sub-menu v-else :key="item.guid">
            <span slot="title">
              <span>{{ item.name }} </span>
            </span>
            <a-menu-item
              v-for="menuChildren in item.children"
              :key="menuChildren.path"
              @click="$router.push(menuChildren.path)"
              >{{ menuChildren.name }}
            </a-menu-item>
          </a-sub-menu>
        </template>
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
import Vue from 'vue';
import Component from 'vue-class-component';
import AuthService from '@/services/AuthService';

@Component
export default class DashboardLayout extends Vue {
  collapsed = false;

  activeGroup: any[] = [];

  created() {
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

  get navItems(): any[] {
    console.log(this.collapsed);
    return [
      {
        path: this.$router.resolve({
          name: 'Dashboard',
          params: {
            userId: this.$store.state.userModule.userData.id.toString(),
          },
        }).href,
        name: 'Dashboard',
      },
      {
        name: 'Основная информация',
        guid: '123',
        children: [
          {
            name: 'Мой профиль',
            path: this.$router.resolve({
              name: 'Profile',
              params: {
                userId: this.$store.state.userModule.userData.id.toString(),
              },
            }).href,
          },
          {
            name: 'Профиль компании',
            path: this.$router.resolve({
              name: 'CompanyProfile',
              params: {
                userId: this.$store.state.userModule.userData.id.toString(),
              },
            }).href,
          },
        ],
      },
    ];
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

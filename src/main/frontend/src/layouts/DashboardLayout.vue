<template>
  <div class="sidebar-page">
    <section class="sidebar-layout">
      <b-sidebar
        type="is-light"
        :fullheight="true"
        :overlay="false"
        :right="false"
        v-model="open"
        position="static"
        :mobile="mobile"
        :expand-on-hover="expandOnHover"
        :reduce="reduce"
        :delay="expandWithDelay ? 500 : null"
      >
        <div class="p-1">
          <div class="is-flex is-align-items-end mb-5">
            <img style="height: 65px; width: 65px"
                 src="../assets/images/logo-icon.svg"
            />
            <p class="sidebar-title has-text-weight-bold is-size-5">
              Управление <br>
              продажами
            </p>
          </div>
          <b-menu>
            <b-menu-list label="Навигация">
              <b-menu-list>
                <b-menu-item label="Dashboard"
                             icon="link"
                             tag="router-link"
                             :to="{name:'Dashboard'}"></b-menu-item>
              </b-menu-list>

              <b-menu-item icon="database" label="Каталог">
                <b-menu-item icon="cart" label="Номенклатура"
                             tag="router-link"
                             :to="{name: 'Products'}"/>
                <b-menu-item icon="format-list-bulleted-square"
                             label="Категории"
                             tag="router-link"
                             :to="{name: 'Categories'}"/>
              </b-menu-item>
            </b-menu-list>

            <b-menu-list label="Actions">
              <b-menu-item label="Logout"></b-menu-item>
            </b-menu-list>
          </b-menu>
        </div>
      </b-sidebar>
      <div class="content">
        <router-view></router-view>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class DashboardLayout extends Vue {
  expandOnHover = false;

  expandWithDelay = false;

  mobile = 'reduce';

  reduce = false;

  open = true;
}
</script>

<style lang="scss">
@import "../assets/styles/bulma/sidebar.scss";

.dashboard-layout {
  height: 100%;

}

.menu-list a:hover {
  background-color: #dbdbdb;
  color: #363636;
}

.content {
  width: 100%;
  margin: 10px;
}
</style>

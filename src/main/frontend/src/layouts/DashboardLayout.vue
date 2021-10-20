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
            <p class="has-text-weight-bold is-size-5">
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

              <b-menu-item active expanded icon="database" label="Каталог">
                <b-menu-item icon="cart" label="Товары"
                             tag="router-link"
                             :to="{name: 'Items'}"/>
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
.dashboard-layout {
  height: 100%;

}

.menu-list a:hover {
  background-color: #dbdbdb;
  color: #363636;
}

.sidebar-page {
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 100%;
  height: 100%;
  // min-height: 100vh;
  .sidebar-layout {
    display: flex;
    flex-direction: row;
    min-height: 100%;
    // min-height: 100vh;
  }
}

@media screen and (max-width: 768px) {
  .b-sidebar {
    .sidebar-content {
      &.is-mini-mobile {
        &:not(.is-mini-expand),
        &.is-mini-expand:not(:hover):not(.is-mini-delayed) {
          .menu-list {
            li {
              a {
                span:nth-child(2) {
                  display: none;
                }
              }

              ul {
                padding-left: 0;

                li {
                  a {
                    display: inline-block;
                  }
                }
              }
            }
          }

          .menu-label:not(:last-child) {
            margin-bottom: 0;
          }
        }
      }
    }
  }
}

@media screen and (min-width: 1024px) {
  .b-sidebar {
    .sidebar-content {
      &.is-mini {
        &:not(.is-mini-expand),
        &.is-mini-expand:not(:hover):not(.is-mini-delayed) {
          .menu-list {
            li {
              a {
                span:nth-child(2) {
                  display: none;
                }
              }

              ul {
                padding-left: 0;

                li {
                  a {
                    display: inline-block;
                  }
                }
              }
            }
          }

          .menu-label:not(:last-child) {
            margin-bottom: 0;
          }
        }
      }
    }
  }
}

.is-mini-expand {
  .menu-list a {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

</style>

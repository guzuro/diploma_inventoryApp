import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class NavigationItems extends Vue {
  get navItems(): any[] {
    if (this.$store.state.userModule.userData !== null) {
      return [
        {
          path: this.$router.resolve({
            name: 'Dashboard',
            params: {
              userId: this.$store.state.userModule.userData.id.toString(),
            },
          }).href,
          name: 'Dashboard',
          icon: 'appstore',
        },
        {
          name: 'Основная информация',
          icon: 'idcard',
          guid: '123',
          children: [
            {
              name: 'Мой профиль',
              icon: 'user',
              path: this.$router.resolve({
                name: 'Profile',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
            {
              name: 'Профиль компании',
              icon: 'trademark',
              path: this.$router.resolve({
                name: 'CompanyProfile',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
          ],
        },
        {
          name: 'Сотрудники и должности',
          guid: '124',
          icon: 'folder-open',
          children: [
            {
              name: 'Сотрудники',
              icon: 'team',
              path: this.$router.resolve({
                name: 'Employes',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
            {
              name: 'Должности',
              icon: 'folder',
              path: this.$router.resolve({
                name: 'EmployeeRoles',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
          ],
        },
        {
          name: 'Продукты',
          guid: '125',
          icon: 'database',
          children: [
            {
              name: 'Склады',
              icon: 'shop',
              path: this.$router.resolve({
                name: 'Warehouses',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
            {
              name: 'Категории',
              icon: 'filter',
              path: this.$router.resolve({
                name: 'Categories',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
            {
              name: 'Скидки',
              icon: 'gift',
              path: this.$router.resolve({
                name: 'Sales',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
          ],
        },
      ];
    }
    return [];
  }
}

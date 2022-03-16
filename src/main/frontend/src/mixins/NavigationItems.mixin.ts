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

        {
          name: 'Сотрудники',
          path: this.$router.resolve({
            name: 'Employes',
            params: {
              userId: this.$store.state.userModule.userData.id.toString(),
            },
          }).href,
        },
        {
          name: 'Продукты',
          guid: '124',
          children: [
            {
              name: 'Склады',
              path: this.$router.resolve({
                name: 'Warehouses',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
            {
              name: 'Категории',
              path: this.$router.resolve({
                name: 'Categories',
                params: {
                  userId: this.$store.state.userModule.userData.id.toString(),
                },
              }).href,
            },
            {
              name: 'Скидки',
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

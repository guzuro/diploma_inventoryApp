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
      ];
    }
    return [];
  }
}

<template>
  <div>
    <!-- <div v-if="page === 'home'">
      <div class="page-wrapper">
        <HomeNavigation />
        <router-view :key="$route.path" />
        <HomeFooter />
      </div>
    </div>
    <div v-if="page === 'meeting'">
      <groups>
        <router-view :key="$route.path" />
      </groups>
    </div> -->
    <div v-if="isAdminPage">
      <dashboard />
    </div>
    <div v-else>
      <router-view name="navigation" />
      <router-view />
      <router-view name="footer" />
    </div>
  </div>
</template>

<script>
import Dashboard from "./views/Dashboard.vue";
// import HomeNavigation from './components/layout/home/HomeNavigation.vue'
// import HomeFooter from './components/layout/home/HomeFooter.vue'
// import Groups from './components/layout/teams/Groups.vue'
// import './assets/css/style.css'
// import './assets/css/home.css'

export default {
  name: "App",
  components: {
    Dashboard,
  },
  data() {
    return {
      isAdminPage: false,
    };
  },
  created() {
    this.checkAdminPage();
  },
  methods: {
    checkAdminPage() {
      const url = window.location.href;
      if (url.search("/admin") !== -1) this.isAdminPage = true;
    },
  },

  // components: {
  // HomeNavigation,
  // HomeFooter,
  // Groups,
  // },

  // data() {
  //     return {
  //         page: null
  //     }
  // },

  // created() {
  // const route = this.$route.name;
  // const nameRoute = ['Teams', 'Meeting'];
  // if (nameRoute.includes(route)) {
  //     this.page = 'meeting';
  // } if ('RoomDetail' === route) {
  //     this.page = 'meeting';
  // }
  // else {
  //     this.page = 'home'
  // }
  // }
};
</script>

<style>
</style>

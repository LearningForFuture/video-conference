<template>
  <div>
    <div v-if="isPageHome">
      <div class="page-wrapper">
        <HomeNavigation />
        <router-view :key="$route.path" />
        <HomeFooter />
      </div>
    </div>
    <div v-if="!isPageHome">
      <groups>
        <router-view :key="$route.path" />
      </groups>
    </div>
  </div>
</template>

<script>
import HomeNavigation from './components/layout/home/HomeNavigation.vue'
import HomeFooter from './components/layout/home/HomeFooter.vue'
import Groups from './components/layout/teams/Groups.vue'
import './assets/css/style.css'
import './assets/css/home.css'

export default {
    name: 'App',
    components: {
        HomeNavigation,
        HomeFooter,
        Groups,
    },

    data() {
        return {
            isPageHome: null
        }
    },

    created() {
        const route = this.$route.name;
        const nameRoute = ['Teams', 'Groups', 'Meeting'];
        if (nameRoute.includes(route)) {
            this.isPageHome = false
        } else {
            this.isPageHome = true
        }
    }
}
</script>

<style lang="css">
</style>

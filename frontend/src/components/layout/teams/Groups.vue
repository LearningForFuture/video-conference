
<template lang="">
  <div class="layout">
    <teams-nav-bar />
    <teams-side-bar />
    <slot />
  </div>
</template>

<script>

import TeamsNavBar from './TeamsNavBar.vue'
import TeamsSideBar from './TeamsSideBar.vue'
import 'jquery/src/jquery.js'
import 'bootstrap/dist/js/bootstrap.min.js'
import '../../../assets/css/classroom.css'

export default{
    name: 'Groups',
    components: {
        TeamsNavBar,
        TeamsSideBar,
    }
}
</script>
<style scoped>
.layout {
    overflow: hidden !important;
}
</style>

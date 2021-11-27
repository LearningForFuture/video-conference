<template lang="">
  <div class="layout">
    <teams-nav-bar />
    <teams-side-bar />
    <router-view />
  </div>
</template>

<script>
import TeamsNavBar from "../components/layout/teams/TeamsNavBar.vue";
import TeamsSideBar from "../components/layout/teams/TeamsSideBar.vue";
import "jquery/src/jquery.js";
import "bootstrap/dist/js/bootstrap.min.js";
import "../assets/css/classroom.css";

export default {
  name: "Conversations",
  components: {
    TeamsNavBar,
    TeamsSideBar
  }
};
</script>
<style scoped>
.layout {
    overflow: hidden !important;
}
</style>

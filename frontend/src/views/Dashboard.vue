<template>
  <div class="flex">
    <SideBarAdmin />
    <router-view />
  </div>
</template>

<script>
// import "../assets/css/app.css";
import SideBarAdmin from "../components/admin/SideBarAdmin.vue";
export default {
  name: "Dashboard",
  components: {
    SideBarAdmin,
  },
};
</script>

<style>
</style>
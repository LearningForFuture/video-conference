<template>
  <div class="flex">
    <SideBarAdmin />
    <router-view />
  </div>
</template>

<script>
import SideBarAdmin from "../components/admin/SideBarAdmin.vue";
export default {
  name: "Dashboard",
  components: {
    SideBarAdmin,
  },
  mounted() {
    document.body.classList.add("home_croll");
    document.body.style.backgroundColor = "#2e51bb";
  },
};
</script>

<style src="../assets/css/app.css" scoped>
</style>
<template>
  <!-- BEGIN: Side Menu -->
  <nav class="side-nav">
    <a href="" class="intro-x flex items-center pl-5 pt-4">
      <img
        alt="Midone Tailwind HTML Admin Template"
        class="w-6"
        src="@/assets/images/logo.svg"
      />
      <span class="hidden xl:block text-white text-lg ml-3">
        Mid<span class="font-medium">one</span>
      </span>
    </a>
    <div class="side-nav__devider my-6" />
    <ul>
      <li>
        <a class="side-menu side-menu--active" @click="navigateHomePage">
          <div class="side-menu__icon">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feather feather-home"
            >
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              <polyline points="9 22 9 12 15 12 15 22" />
            </svg>
          </div>
          <div class="side-menu__title">User</div>
        </a>
      </li>
      <li>
        <a href="javascript:;" class="side-menu" @click="navigateRoomPage">
          <div class="side-menu__icon">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feather feather-box"
            >
              <path
                d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"
              />
              <polyline points="3.27 6.96 12 12.01 20.73 6.96" />
              <line x1="12" y1="22.08" x2="12" y2="12" />
            </svg>
          </div>
          <div class="side-menu__title">
            Room
            <div class="side-menu__sub-icon">
              <i data-feather="chevron-down" />
            </div>
          </div>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
// import "../../assets/css/app.css";
export default {
  name: "SideBarAdmin",
  created() {
    this.navigateHomePage();
  },
  methods: {
    checkNavigationDuplicated(path) {
      return this.$router.currentRoute.fullPath !== path;
    },
    navigateHomePage() {
      let userPagePath = "/admin/users";
      if (this.checkNavigationDuplicated(userPagePath)) {
        this.$router.push({ path: userPagePath });
      }
    },
    navigateRoomPage() {
      let roomPagePath = "/admin/rooms";
      if (this.checkNavigationDuplicated(roomPagePath)) {
        this.$router.push({ path: roomPagePath });
      }
    },
  },
};
</script>

<style src="../../assets/css/app.css" scoped>
</style>
<template>
  <!-- header layout -->
  <div class="header-layout w-100">
    <a
      class="menu"
      href="#"
    ><i class="ti-layout-grid3" /></a>
    <div class="wrapper-header">
      <div class="logo">
        <a
          class=""
          href="#"
        >
          <div class="container">
            <div class="row">
              <img
                src="@/assets/images/Video-Conferencing-App-Logo.png"
                class="lazyload m-0"
                alt="..."
              >
              <div class="text-logo">Video Conference</div>
            </div>
          </div>
        </a>
      </div>
      <form
        action="#"
        method=""
      >
        <div class="input-wrapper">
          <input
            class="input-search form-control"
            type="text"
            placeholder="search"
          >
        </div>
      </form>
      <div class="avatar">
        <span class="text-muted mr-1">{{ full_name }}</span>
        <a href="#"><img
          :src="'https://eu.ui-avatars.com/api/?name=' + full_name + '&size=40&background=random&rounded=true&quot;'"
          alt=""
        ></a>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'TeamsNavBar',
  data() {
    return {
      full_name: localStorage.getItem('full_name'),
    }
  }
}
</script>
<style scoped>
</style>
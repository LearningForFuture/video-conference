<template lang="">
  <!-- <div class="page-wrapper"> -->
  <!-- <Navigation/> -->
  <div>
    <HomeBanner />
    <HomeTextAnimation />
    <HomeBody />
    <HomeMember />
  </div>
  <!-- <FooterHome/> -->
  <!-- </div> -->
</template>

<script>

import HomeBanner from '../components/layout/home/HomeBanner.vue'
import HomeTextAnimation from '../components/layout/home/HomeTextAnimation.vue'
import HomeBody from '../components/layout/home/HomeBody.vue'
import HomeMember from '../components/layout/home/HomeMember.vue'
import '../assets/css/style.css'
import '../assets/css/home.css'

export default{
  name: 'HomePage',
  components: {
    // Navigation,
    HomeBanner,
    HomeTextAnimation,
    HomeBody,
    HomeMember,
    // FooterHome,
  },
  mounted() {
    document.body.classList.add('croll')
  }
}
</script>
<style src="../assets/css/home.css">
</style>
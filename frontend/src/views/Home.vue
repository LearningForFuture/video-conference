<template lang="">
    <div class="page-wrapper">
        <Navigation/>
        <BannerHome/>
        <TextAnimationHome/>
        <BodyHome/>
        <MemberHome/>
        <FooterHome/>
    </div>
</template>

<script>

import Navigation from '../components/Navigation.vue'
import BannerHome from '../components/BannerHome.vue'
import TextAnimationHome from '../components/TextAnimationHome.vue'
import BodyHome from '../components/BodyHome.vue'
import MemberHome from '../components/MemberHome.vue'
import FooterHome from '../components/FooterHome.vue'
import '../assets/css/style.css'
import '../assets/css/home.css'

export default{
    name: 'HomePage',
    components: {
        Navigation,
        BannerHome,
        TextAnimationHome,
        BodyHome,
        MemberHome,
        FooterHome,
    },
    mounted() {
        document.body.classList.add('croll')
    }
}
</script>
<style src="../assets/css/home.css">
</style>
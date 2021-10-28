
<template lang="">
    <div class="layout">
        <nav-bar></nav-bar>
        <side-bar></side-bar>
        <slot name="allgroups"></slot>
        <slot name="callvideo"></slot>
        <slot name="viewgroups"></slot>
    </div>
</template>

<script>

import NavBar from './layout/partials/NavBarLayout.vue'
import SideBar from './layout/partials/SideBarLayout.vue'
import 'jquery/src/jquery.js'
import 'bootstrap/dist/js/bootstrap.min.js'
import '../assets/css/classroom.css'

export default{
    name: 'Groups',
    components: {
        NavBar,
        SideBar,
    }
}
</script>
<style scoped>
.layout {
    overflow: hidden !important;
}
</style>


<template lang="">
    <groups>
        <!-- <BodyLayout v-slot:allgroups /> -->
        <template v-slot:allgroups ><BodyLayout/></template>
    </groups>
</template>

<script>


import Groups from '../components/Groups.vue'
import BodyLayout from '../components/layout/BodyLayout.vue'
import 'jquery/src/jquery.js'
import 'bootstrap/dist/js/bootstrap.min.js'
import '../assets/css/classroom.css'

export default{
    name: 'MainApp',
    components: {
        Groups,
        BodyLayout,
    }
}
</script>
<style scoped>
.layout {
    overflow: hidden !important;
}
</style>

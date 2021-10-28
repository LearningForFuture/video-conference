<template>
    <groups>
        <template v-slot:callvideo ><CallVideoGroups/></template>
    </groups>
</template>

<script>
    import Groups from '../components/Groups.vue'
    import CallVideoGroups from '../components/layout/CallVideoGroups.vue'
    import 'jquery/src/jquery.js'
    import 'bootstrap/dist/js/bootstrap.min.js'
    import '../assets/css/classroom.css'
    import 'webrtc-adapter'
    export default {
        name: 'CallVideo',
        components: {
            CallVideoGroups,
            Groups,
        },

        data() {
            return {
                
            };
        },

        methods: {
            
        },
    };
</script>

<style lang="scss" scoped>

</style>
<template>
    <div class="wrapper-video">
        <header-video-call></header-video-call>
        <body-video-call></body-video-call>
    </div>
</template>

<script>
    import HeaderVideoCall from './partials/HeaderVideoCall.vue';
    import BodyVideoCall from './partials/BodyVideoCall.vue';

    export default {
        name: 'CallVideoGroups',

        components: {
            HeaderVideoCall,
            BodyVideoCall,
        },

        data() {
            return {
                
            };
        },

        mounted() {
            
        },

        methods: {
            
        },
    };
</script>

<style lang="css" scoped>
.wrapper-video {
    overflow: hidden;
    height: calc(100vh - 45px);
    max-height: calc(100vh - 45px);
    display: flex;
    flex-direction: column;
    flex: 1;
}
</style>
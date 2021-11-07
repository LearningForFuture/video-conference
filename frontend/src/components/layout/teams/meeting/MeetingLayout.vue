<template>
  <div class="wrapper-video">
    <meeting-header />
    <meeting-body />
  </div>
</template>

<script>
import MeetingHeader from './MeetingHeader.vue';
import MeetingBody from './MeetingBody.vue';

export default {
    name: 'MeetingLayout',

    components: {
        MeetingHeader,
        MeetingBody,
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
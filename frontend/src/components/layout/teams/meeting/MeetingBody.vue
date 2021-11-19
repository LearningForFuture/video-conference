<template>
  <section>
    <MeetingVideoArea />
    <meeting-chat />
  </section>
</template>

<script>
import MeetingVideoArea from './MeetingVideoArea.vue';
import MeetingChat from './MeetingChat.vue';
export default {
  name: 'MeetingBody',

  components: {
    MeetingVideoArea,
    MeetingChat,
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
    section {
        display: flex;
        flex-flow: row nowrap;
        justify-content: center;
        align-items: center;
        height: 100%;
        background-color: #292d32;
    }
</style>
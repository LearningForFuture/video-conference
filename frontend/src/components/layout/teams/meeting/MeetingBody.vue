<template>
  <section>
    <MeetingVideoArea />
    <meeting-chat v-if="getShowChat" />
    <meeting-participants v-if="getShowParticipants" />
  </section>
</template>

<script>
import MeetingVideoArea from './MeetingVideoArea.vue';
import MeetingChat from './MeetingChat.vue';
import MeetingParticipants from './MeetingParticipants.vue';
import {mapGetters} from 'vuex';

export default {
  name: 'MeetingBody',

  components: {
    MeetingVideoArea,
    MeetingChat,
    MeetingParticipants
  },

  data() {
    return {
            
    };
  },

  computed: {
    ...mapGetters('meeting', ['getShowChat', 'getShowParticipants'])
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
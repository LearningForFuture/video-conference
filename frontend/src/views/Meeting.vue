<template v-slot:callvideo>
  <!-- <groups> -->
  <!-- <template v-slot:callvideo ><CallVideoGroups/></template> -->
  <!-- </groups> -->
  <MeetingLayout />
</template>

<script>
// import Groups from '../components/Groups.vue'
import MeetingLayout from '../components/layout/teams/meeting/MeetingLayout.vue'
import 'jquery/src/jquery.js'
import 'bootstrap/dist/js/bootstrap.min.js'
// import '../assets/css/classroom.css'
import 'webrtc-adapter'

export default {
  name: 'Meeting',
  components: {
    MeetingLayout,
    // Groups,
  },

  data() {
    return {
                
    };
  },

  methods: {
            
  },
};
</script>

<style lang="css" scoped>
</style>
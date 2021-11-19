
<template
  lang=""
  v-slot:allgroups
>
  <!-- <groups> -->
  <!-- <BodyLayout v-slot:allgroups /> -->
  <!-- <template v-slot:allgroups ><BodyLayout/></template> -->
  <!-- </groups> -->
  <TeamsLayout />
</template>

<script>
// import Groups from '../components/Groups.vue'
import TeamsLayout from '../components/layout/teams/TeamsLayout.vue'
import 'jquery/src/jquery.js'
import 'bootstrap/dist/js/bootstrap.min.js'
import '../assets/css/classroom.css'

export default{
  name: 'Teams',
  components: {
    // Groups,
    TeamsLayout,
  }
}
</script>
<style scoped>
.layout {
    overflow: hidden !important;
}
</style>

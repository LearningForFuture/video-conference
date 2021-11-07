<template>
  <div class="content">
    <teams-header />
    <teams-body />
  </div>
</template>
<script>

import TeamsHeader from './TeamsHeader.vue'
import TeamsBody from './TeamsBody.vue'

export default {
    name: 'TeamsLayout',
    components: {
        TeamsHeader,
        TeamsBody,
    },
}
</script>
<style>

</style>
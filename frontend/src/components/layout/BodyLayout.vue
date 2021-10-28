<template>
    <div class="content">
        <group-header></group-header>
        <group-body></group-body>
    </div>
</template>
<script>

import GroupHeader from './partials/GroupHeader.vue'
import GroupBody from './partials/GroupBody.vue'

export default {
    name: 'BodyLayout',
    components: {
        GroupHeader,
        GroupBody,
    },
}
</script>
<style>

</style>
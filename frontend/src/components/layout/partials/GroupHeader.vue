<template >
    <!-- teams header-->
    <div class="wrapper-teams-header">
        <h5>Teams</h5>
        <div class="join-teams" @click="showJoinedGroup">
            <a data-navigation-target="#jointeam" href="javascript:void(0)">
                <input type="button" class="" value="Tham gia hoặc tạo nhóm">
            </a>
        </div>
    </div>
</template>
<script>
    import { mapActions } from 'vuex'

    export default {
        name: 'GroupHeader',
        methods: {
            ...mapActions('groups', ['changeStateJoined']),
            showJoinedGroup() {
                this.changeStateJoined(true)
            }
        },
    }
</script>
<style>

</style>
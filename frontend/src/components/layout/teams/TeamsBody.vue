<template>
  <div class="wrapper-teams">
    <div class="container-teams">
      <teams-list-groups :is-joined="isJoined" />
      <div v-show="isJoined">
        <a
          class="text-primary"
          @click="backLayoutTeams"
        ><span>&gt;&nbsp;Back</span></a>
        <div class="row-team h-auto row-join-group">
          <div class="team-grid">
            <div class="group-wrapper">
              <a
                class=""
                href="javascript:void(0)"
              >
                <div class="wrapper-avatar-group mw-30">
                  <img
                    class="img-responsive group-avatar mw-100"
                    src="https://www.pngfind.com/pngs/m/343-3436560_our-team-european-joint-programme-on-rare-diseases.png"
                    alt="group-avatar"
                  >
                </div>
                <div class="group-name">
                  <h5 class="text-center">Create new group</h5>
                </div>
                <button class="btn btn-secondary"><i class="fa fa-users mr-1 text-white" />Create group</button>
              </a>
            </div>
          </div>
          <div class="team-grid">
            <div class="group-wrapper">
              <a
                class=""
                href="javascript:void(0)"
              >
                <div class="wrapper-avatar-group">
                  <img
                    class="img-responsive group-avatar mw-100"
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcnSCYFR1gIyd8UK1Kg3objNNdZRpx_eVkWg&usqp=CAU"
                    alt="group-avatar"
                  >
                </div>
                <div class="group-name">
                  <h5 class="text-center">Join group</h5>
                </div>
                <form>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      placeholder="Enter code"
                    >
                  </div>
                  <input
                    type="submit"
                    class="btn-join"
                    value="Join"
                  >
                </form>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import TeamsListGroups from './TeamsListGroups.vue'

export default {
    name: 'TeamsBody',
    components: {
        TeamsListGroups,
    },
    computed: {
        ...mapState({
            isJoined: state => state.rooms.isJoined
        }),
    },
    methods: {
        ...mapActions('rooms', ['changeStateJoined']),
        backLayoutTeams() {
            this.changeStateJoined(false);
        },
            
    }
}
</script>

<style scoped>
.row-join-group .group-wrapper .wrapper-avatar-group {
    max-width: 30% !important;
}

.row-join-group .group-wrapper .group-name h5 {
    font-size: 20px
}

.btn-join {
    outline: none;
    font-size: 16px;
    padding: 4px 8px;
    border: 1px solid rgb(106, 90, 205);
    background-color: rgb(106, 90, 205);
    border-radius: 2px;
    color: #fff;
    float: right
}

.group-wrapper a {
    display: flex;
    flex-flow: column wrap;
    justify-content: center;
    align-items: center
}

a {
    cursor: pointer;
}
</style>

<template>
  <div class="container-room m-0">
    <div class="row-room">
      <div class="side_bar">
        <room-side-bar />
      </div>
      <div class="room_body">
        <room-header />
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import RoomSideBar from '../components/layout/teams/room/RoomSideBar.vue';
// import RoomBody from '../components/layout/teams/room/RoomBody.vue';
import RoomHeader from '../components/layout/teams/room/RoomHeader.vue';
import { mapActions} from 'vuex';

export default {
  name: 'FrontendRoomdetail',
    
  components: {
    RoomSideBar,
    // RoomBody,
    RoomHeader,
  },
    
  props: ['roomId'],

  data() {
    return {
            
    };
  },

  created() {
    this.getDetailRoom(parseInt(this.roomId));
  },

  mounted() {
        
  },

  methods: {
    ...mapActions('rooms', ['getDetailRoom']),
  },
};
</script>

<style lang="css" scoped>
.container-room {
    width: calc(var(--vw, 1vw) * 100 - 64px);
}

.row-room {
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
}

.side_bar {
    width: 25%;
    box-sizing: border-box;
    padding: 0 15px;
}

.room_body {
    width: 75%;
    box-sizing: border-box;
    height: calc(var(--vh, 100vh) * 100 - 45px);
    max-height: calc(var(--vh, 100vh) * 100 - 45px);
    overflow-x: hidden;
    overflow-y: scroll;
    box-shadow: 0px 0px 5px #232931ce;
}
</style>
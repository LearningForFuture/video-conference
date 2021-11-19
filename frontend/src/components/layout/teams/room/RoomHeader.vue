<template>
  <div class="header">
    <div class="container">
      <div class="row">
        <div class="col-7 nav-header-container">
          <div class="img-group">
            <a class="mh-10">
              <img
                :src="'https://eu.ui-avatars.com/api/?name=' + getRoomDetails.roomName + '&size=30&background=random&square=true'"
                alt="img-group"
              >
            </a>
          </div>
          <h6 class="m-0 ml-1">
            Chung
          </h6>
          <ul>
            <li
              v-for="(item, index) in navItems"
              :key="index"
              @click=" currentActive = index"
            >
              <router-link
                tag="a"
                :to="{ path: item.to }"
                :class="{ active: index === currentActive }"
              >
                <span>{{ item.text }}</span>
              </router-link>
            </li>
          </ul>
        </div>
        <div class="col-5">
          <button
            class="btn-light btn-meeting"
            @click="createNewMeeting()"
          >
            <i class="ti-video-camera mr-2" />Họp ngay
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'Roomheader',

  data() {
    return {
      nameGroup: null,
      roomId: null,
      currentActive: 0,
      navItems: [{
        text: "Bài đăng",
        to: "conversations/teams/room/",
        isActive: false
      }, {
        text: "Tệp",
        to: "conversations/teams/room/",
        isActive: true
      }, ]

    };
  },

  computed: { 
    ...mapGetters('rooms', ['getRoomDetails']),
    ...mapGetters('meeting', ['getMeetingId'])
  },

  mounted() {
    this.nameGroup = this.getRoomDetails.roomName;
    this.roomId = this.getRoomDetails.roomId;
  },

  methods: {
    ...mapActions('meeting', ['createMeeting']),

    async createNewMeeting() {
      try {
        const data = { roomId: this.getRoomDetails.roomId, }
        await this.createMeeting(data);
        this.$router.push({ path: `/conversations/teams/room/${this.getRoomDetails.roomId}/meeting/${this.getMeetingId}`});
      } catch (e) {
        // console.log(e.response);
        this.$log.debug(e.response);
      }

      // this.$router.push({ path: "/teams/meeting/121313"});
    }
  },
};
</script>

<style lang="css" scoped>
.nav-header-container {
    display: flex;
    justify-content: flex-start;
    align-items:
        center;
}

.img-group img {
    border-radius: 2px;
}

ul {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin: 0;
}

ul li a {
    padding: 0 15px;
    height: 50px;
    display: block;
    display: flex;
    align-items:
        center;
    color: rgb(16, 91, 190);
    position: relative;
}

ul li a.active::after {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    width: 70%;
    content: '.';
    color: transparent;
    background: linear-gradient(to right, #EE7752,
            #E73C7E, #23A6D5, #23D5AB);
    height: 5px;
}

.header {
    background-color: #eee6e6;
    box-shadow: 0px 0px 2px #77808bce;
}

.btn-meeting {
    outline: none;
    border: none;
    padding: 6px 10px;
    border: 1px solid rgba(17, 17, 17, 0.295);
    border-radius:
        5px;
}

.btn-meeting:hover {
    background-color: rgb(82, 129, 230);
    border: 1px solid rgb(82, 129, 230);
    color: #fff;
}

.col-5 {
    display: flex;
    justify-content:
        flex-end;
    align-items: center;
}
</style>

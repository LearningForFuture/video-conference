<template>
  <div class="side-bar">
    <div class="container p-0">
      <div class="row m-0 h-100">
        <div class="col p-0">
          <h5 class="text-left text-back">
            <router-link
              tag="a"
              to="/conversations/teams"
            >
              <i class="ti-arrow-left mr-1" />
              <span>Tất cả các nhóm</span>
            </router-link>
          </h5>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <a class="mh-100 img-group">
            <img
              :src="'https://eu.ui-avatars.com/api/?name=' + getRoomDetails.roomName + '&size=100&background=random&square=true'"
              alt="img-group"
            >
          </a>
        </div>
      </div>
      <div class="row">
        <div class="col name-group">
          <p>{{ getRoomDetails.roomName }}</p>
          <div class="dropdown float-right">
            <a
              class=""
              href="javascript:void(0)"
              data-toggle="dropdown"
            >
              <i class="ti-more" />
            </a>
            <div class="dropdown-menu">
              <router-link
                tag="a"
                class="dropdown-item"
                to="participant-room"
              >
                <i class="ti-settings mr-1" />Quản lý teams</router-link>
              <a
                class="dropdown-item"
                href="#"
              ><i class="ti-user mr-1" />Thêm thành viên</a>
              <a
                class="dropdown-item"
                href="#"
              ><i class="ti-shift-left mr-1" />Rời khỏi nhóm</a>
              <a
                class="dropdown-item"
                href="#"
              ><i class="ti-link mr-1" />Nhận liên kết đến nhóm</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'Roomsidebar',

  data() {
    return {

    };
  },

  computed: {
    ...mapGetters('rooms', ['getRoomDetails']),
  },

  mounted() {

  },

  methods: {

  },
};
</script>

<style lang="css" scoped>
.img-group img {
    max-height: 200px;
    min-height: 100px;
    border-radius: 5px;
}

h5.text-back {
    font-size: 16px;
    margin-top: 10px;
}

a:hover {
    color: rgb(16, 84, 185);
}

.name-group {
    margin: 10px 0;
    display: flex;
    flex-flow: row nowrap;
    justify-content:space-between;
    align-items: center;
}

.name-group p {
    margin: 0;
    color: #000000cb;
    font-size: 1.2rem;
    white-space: nowrap; 
    overflow: hidden;
    text-overflow: ellipsis; 
    font-weight: bold;
}
</style>

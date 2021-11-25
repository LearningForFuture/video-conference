<template>
  <div class="meeting-participants">
    <div class="participants">
      <div class="header-participant d-flex justify-content-between align-items-center">
        <h5 class="text-light">
          Participants
        </h5>
        <div class="btn-close text-muted">
          <i class="ti-close" />
        </div>
      </div>
      <div class="form-search">
        <div class="form-group mb-2">
          <input
            class="form-control"
            type="text"
            placeholder="Type a name"
          >
        </div>
      </div>
      <div class="form-group">
        <button
          class="btn btn-primary btn-invite"
          type="button"
        >
          <i class="ti-sharethis mr-2" />Share invite
        </button>
      </div>
      <a
        class="text-info mx-2 font-weight-bold"
        data-toggle="collapse"
        href="#participants"
        role="button"
        aria-expanded="false"
        aria-controls="participants"
      ><i class="ti-angle-down mr-1" /> In this meeting</a>
      <ul
        id="participants"
        class="list-user collapse"
      >
        <li
          v-for="(user, userId) in getAllUsersInCall"
          :key="userId"
          class="d-flex justify-content-start align-items-center position-relative"
        >
          <img
            class="avatar-user"
            :src="'https://eu.ui-avatars.com/api/?name=' + user.fullName + '&size=40&background=random&rounded=true&quot;'"
          >
          <div class="username">
            <p class="m-0">
              {{ user.fullName }}
            </p>
            <span
              v-if="user_id && user.userId == user_id"
              class="m-0"
            >Organizater</span>
          </div>
          <div class="btn-mic">
            <svg
              v-if="!user.turnOffMic"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-mic"
              viewBox="0 0 16 16"
            >
              <path d="M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z" />
              <path d="M10 8a2 2 0 1 1-4 0V3a2 2 0 1 1 4 0v5zM8 0a3 3 0 0 0-3 3v5a3 3 0 0 0 6 0V3a3 3 0 0 0-3-3z" />
            </svg>
            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-mic-mute"
              viewBox="0 0 16 16"
            >
              <path d="M13 8c0 .564-.094 1.107-.266 1.613l-.814-.814A4.02 4.02 0 0 0 12 8V7a.5.5 0 0 1 1 0v1zm-5 4c.818 0 1.578-.245 2.212-.667l.718.719a4.973 4.973 0 0 1-2.43.923V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 1 0v1a4 4 0 0 0 4 4zm3-9v4.879l-1-1V3a2 2 0 0 0-3.997-.118l-.845-.845A3.001 3.001 0 0 1 11 3z" />
              <path d="m9.486 10.607-.748-.748A2 2 0 0 1 6 8v-.878l-1-1V8a3 3 0 0 0 4.486 2.607zm-7.84-9.253 12 12 .708-.708-12-12-.708.708z" />
            </svg>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';

export default {
  name: 'Meetingparticipants',

  data() {
    return {
      user_id: localStorage.getItem('user_id'),
    };
  },

  computed: {
    ...mapGetters('meeting', ['getAllUsersInCall'])
  },

  mounted() {
        
  },

  methods: {
        
  },
};
</script>

<style lang="css" scoped>
.meeting-participants {
  flex: 30%;
  height: 100%;
  overflow: hidden;

}

.participants {
  box-shadow: -2px -2px 3px #3e4247, 4px 4px 4px #1d1f23;
  background: #292d32;
  box-sizing: border-box;
  margin: 10px 10px 10px 5px;
  padding: 5px;
  height: calc(100% - 20px);
}

.header-participant h5 {
  font-weight: 600;
  margin-left: 10px;
  font-size: 1.2rem;
}

.btn-close {
  cursor: pointer;
}

.form-search {
  margin: 10px 20px 0 20px;
}

.btn-invite {
  margin: 0 20px ;
}

.avatar-user {
  max-width: 35px;
  border-radius: 50%;
  float: left;
}

.list-user {
  padding: 5px 10px;
}

.username {
  display: flex;
  flex-flow: column;
  align-items: center;
  justify-content: flex-start;
  margin-left: 1rem;
}

.username span {
  font-size: 12px;
  color: #969ba0
}

svg {
  height:1.2rem;
  color: rgb(202, 186, 186);
}

.btn-mic {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}

li {
  margin: 5px;
}
</style>
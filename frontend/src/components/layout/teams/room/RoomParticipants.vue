<template>
  <div class="participants-room">
    <perfect-scrollbar>
      <h5>Members</h5>
      <a
        data-bs-toggle="collapse"
        href="#"
        role="button"
        aria-expanded="false"
        aria-controls="collapseOwners"
      ><svg
        xmlns="http://www.w3.org/2000/svg"
        width="16"
        height="16"
        fill="currentColor"
        class="bi bi-caret-down-fill"
        viewBox="0 0 16 16"
      >
        <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z" />
      </svg>
        Owners
      </a>
      <div
        class=" list-owners"
      >
        <table
          class="table table-hover"
        >
          <tr>
            <th>Name</th>
            <th>Title</th>
            <th>Location</th>
            <th>Tags@</th>
            <th>Role</th>
          </tr>
          <tr 
            v-for="(participant, participantId) in owners" 
            :key="participantId"
          >
            <td>
              <a
                href="#"
                class="avatar-user"
              ><img
                :src="'https://eu.ui-avatars.com/api/?name=' + participant.participant.fullName + '&size=30&background=random&rounded=true&quot;'"
                alt=""
              ></a>
              <span class="ml-2">{{ participant.participant.fullName }}</span>
            </td>
            <td v-text="" />
            <td v-text="" />
            <td v-text="" />
            <td>Owner</td>
          </tr>
        </table>
      </div>
      <a
        data-bs-toggle="collapse"
        href="#"
        role="button"
        aria-expanded="false"
        aria-controls="collapseMembers"
      ><svg
        xmlns="http://www.w3.org/2000/svg"
        width="16"
        height="16"
        fill="currentColor"
        class="bi bi-caret-down-fill"
        viewBox="0 0 16 16"
      >
        <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z" />
      </svg>
        Members and guests
      </a>
      <div class=" list-members">
        <table
          class="table table-hover"
        >
          <tr>
            <th>Name</th>
            <th>Title</th>
            <th>Location</th>
            <th>Tags@</th>
            <th>Role</th>
          </tr>
          <tr 
            v-for="(participant, participantId) in members" 
            :key="participantId"
          >
            <td>
              <a
                href="#"
                class="avatar-user"
              ><img
                :src="'https://eu.ui-avatars.com/api/?name=' + participant.participant.fullName + '&size=30&background=random&rounded=true&quot;'"
                alt=""
              ></a>
              <span class="ml-2">{{ participant.participant.fullName }}</span>
            </td>
            <td v-text="" />
            <td v-text="" />
            <td v-text="" />
            <td>Member</td>
          </tr>
        </table>
      </div>
    </perfect-scrollbar>
  </div>
</template>

<script>
import { PerfectScrollbar } from "vue2-perfect-scrollbar";
import 'vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css'
import {mapGetters, mapActions} from 'vuex';

export default {
  name: 'Roomparticipants',

  props: ['roomId'],

  components: {
    PerfectScrollbar,
  },

  data() {
    return {
      owners: [],
      members: [],
    };
  },

  computed: {
    ...mapGetters('ParticipantRoom', ['getPaticipants']),
  },

  created() {
    this.getParticipantsCurrentRoom();
  },

  mounted() {
    const maxHeight = document.querySelector('nav.navigation').offsetHeight -
      document.querySelector(".header").offsetHeight;
    document.querySelector(".participants-room").style.maxHeight = maxHeight - 20 + 'px';
    document.querySelector(".ps").style.height = maxHeight - 20 + "px"; 
  },

  methods: {
    ...mapActions('ParticipantRoom', ['getAllParticipantsByRoomId']),

    getUserAdmins() {
      this.owners = this.getPaticipants.filter(user => 
        user.isAdmin
      )},

    getUserMembers() {
      this.members = this.getPaticipants.filter(user => 
        !user.isAdmin
      )},
    
    async getParticipantsCurrentRoom() {
      try {
        await this.getAllParticipantsByRoomId(parseInt(this.roomId));
        this.getUserAdmins();
        this.getUserMembers();
      } catch (e) {
        console.log(e)
      }
    }
  },
};
</script>

<style lang="css" scoped>
.participants-room {
  padding: 10px;
  box-sizing: border-box;
  overflow-x: hidden;
  overflow-y: hidden;
  width: 100%;
}

svg {
  height: 1rem;
}

.avatar-user img {
  border-radius: 50%;
}

td {
  background-color: lightcyan
}

</style>
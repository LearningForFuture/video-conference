<template>
  <div
    v-show="!isJoined"
    class="row-team"
  >
    <teams-list-groups-items
      v-for="(room, roomId) in getAll"
      :key="roomId"
      :is-admin="room.isAdmin"
      :name-group="room.roomName"
      :is-public="room.isPublic"
      :room-id="room.roomId"
    />
  </div>
</template>

<script>
import TeamsListGroupsItems from './TeamsListGroupsItems.vue';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'Teamslistgroups',

  components: {
    TeamsListGroupsItems,
  },

  props: {
    isJoined: {
      type: Boolean,
      required: true
    }
  },

  data() {
    return {

    };
  },

  computed: {
    ...mapGetters('rooms', ['getAll'])
  },

  created() {
    this.getRoomByParticipant();
  },

  mounted() { },

  methods: {
    ...mapActions('rooms', ['getRoomList']),
    async getRoomByParticipant() {
      try {
        await this.getRoomList();
      } catch (err) {
        if(err.response.data.error === "Unauthorized" || err.response.status === 401) {
          this.$router.push({ path : "/"});
          this.$toast.error("Access is not permitted! Please try logging in again later!", {
            position: "top-center",
            timeout: 5000,
            closeOnClick: true,
            pauseOnFocusLoss: true,
            pauseOnHover: true,
            draggable: true,
            draggablePercent: 0.6,
            showCloseButtonOnHover: false,
            hideProgressBar: true,
            closeButton: "button",
            icon: true,
            rtl: false
          });
        }
      }
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
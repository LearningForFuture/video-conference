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
    this.getRoomList();
  },

  mounted() { },

  methods: {
    ...mapActions('rooms', ['getRoomList']),
  },
};
</script>

<style lang="scss" scoped>

</style>
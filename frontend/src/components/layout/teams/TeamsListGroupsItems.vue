<template>
  <div class="team-grid">
    <div class="group-wrapper">
      <router-link
        tag="a"
        :to="{ path: `teams/room/${roomId}/post` }"
      >
        <div class="wrapper-avatar-group">
          <div class="avatar bg-success">
            <img
              class="img-responsive group-avatar mw-100"
              :src="'https://eu.ui-avatars.com/api/?name=' + nameGroup + '&size=200&background=random&square=true'"
              alt="group-avatar"
            >
          </div>
        </div>
        <div class="group-name">
          <h5 class="text-center">
            {{ nameGroup }}
          </h5>
        </div>
      </router-link>
    </div>
    <div class="dropdown">
      <a
        class=""
        href="javascript:void(0)"
        data-toggle="dropdown"
      >
        <i class="ti-more" />
      </a>
      <div class="dropdown-menu">
        <a
          class="dropdown-item"
          href="#"
        >Add member</a>
        <a
          class="dropdown-item"
          href="#"
        >Another action</a>
        <a
          class="dropdown-item"
          href="#"
        >Leave at group</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Teamslistgroupsitems',
  props: {
    nameGroup: {
      type: String,
      required: true,
    },

    isPublic: {
      type: Boolean,
      required: true,
    },

    isAdmin: {
      type: Boolean,
      required: true,
    },

    roomId: {
      type: Number,
      required: true,
    }
  },

  data() {
    return {

    };
  },

  mounted() {

  },

  methods: {

  },
};
</script>

<style lang="css" scoped>
.wrapper-teams .team-grid .group-wrapper a {
    width: 100%;
}
</style>

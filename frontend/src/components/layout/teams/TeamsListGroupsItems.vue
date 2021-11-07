<template>
  <!-- <div class="team-grid">
    <div class="group-wrapper">
        <a class="" href="javascript:void(0)">
            <div class="wrapper-avatar-group">
                 <img class="img-responsive group-avatar mw-100" src="./images/banner-team-1.png" alt="group-avatar">
                <div class="avatar bg-primary">
                    <div class="name-avatar">GX</div>
                </div>
            </div>
            <div class="group-name">
                <h5 class="text-center">Group_XOMTRO</h5>
            </div>
        </a>
    </div>
  
</div> -->
  <div class="team-grid">
    <div class="group-wrapper">
      <a
        class=""
        href="#"
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
          <h5 class="text-center">{{ nameGroup }}</h5>
        </div>
      </a>
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

<style lang="scss" scoped>

</style>

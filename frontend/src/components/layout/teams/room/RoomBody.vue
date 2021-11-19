<template>
  <div class="room-body" />
</template>

<script>
export default {
  name: 'Roombody',

  data() {
    return {
      nameGroup: 'group programing',
      roomId: 121231,
      currentActive: 0,
      navItems: [
        {
          text: "Bài đăng",
          to: "/teams/room/",
          isActive: false
        },
        {
          text: "Tệp",
          to: "/teams/room/",
          isActive: true
        },
      ]
    };
  },

  mounted() {

  },

  methods: {

  },
};
</script>

<style lang="css" scoped>

</style>

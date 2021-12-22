<template>
  <div>
    <div class="container">
      <div class="row my-2">
        <slot name="time" />
        <div class="ml-2">
          <div class="icon-meeting">
            <i class="ti-video-camera" />
          </div>
        </div>
        <div class="col">
          <div class="card-header bg-white p-2">
            <slot name="header" />
          </div>
          <div class="card-content bg-white">
            <slot name="content" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Meetingitems',

  data() {
    return {
      expanded: false
    }
  },

  mounted() {
        
  },

  methods: {
    toggleCardState() { this.expanded = !this.expanded; }
  },
};
</script>

<style lang="css" scoped>
.icon-meeting {
  position: relative;
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
  background-color: #fff;
}

.icon-meeting i{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 1.2rem;
}
</style>
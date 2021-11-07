<template>
  <main>
    <div
      v-if="showLinkShare"
      id="introduce"
    >
      <p>
        <small>
          <i>Share this link to start meeting</i><br>
          <span class="roomLink">{{ roomLink }}</span>
          <a
            class="copyURL"
            href="javascript:void(0)"
            @click="copyURL"
          >{{ copyText || "Copy" }}</a>
        </small>
      </p>
    </div>

    <div
      v-if="showSettings"
      id="settings"
    >
      <div class="label">
        Camera&nbsp;📹
      </div>
      <div
        v-for="(videoDevice) in videoDevices"
        :key="videoDevice.deviceId"
      >
        <div
          :class="'link '+(selectedVideoDeviceId === videoDevice.deviceId ? 'active' : '')"
          @click="changeCamera(videoDevice.deviceId)"
        >
          {{ videoDevice.label }}
        </div>
      </div>

      <div class="label">
        Microphone&nbsp;🎙️
      </div>
      <div
        v-for="(audioDevice) in audioDevices"
        :key="audioDevice.deviceId"
      >
        <div
          :class="'link '+(selectedAudioDeviceId === audioDevice.deviceId ? 'active' : '')"
          @click="changeMicrophone(audioDevice.deviceId)"
        >
          {{ audioDevice.label }}
        </div>
      </div>

      <div class="label">
        <i class="ti-user mr-1" /> {{ users.username }}
      </div>

      <div
        class="copy link"
        @click="toggleSelfVideoMirror"
      >
        Mirror / Unmirror
        <small class="light">(self-view)</small>
      </div>
    </div>
  </main>
</template>

<script>
export default {
    name: 'Meetingvideosetting',

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
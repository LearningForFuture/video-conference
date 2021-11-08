<template>
  <div class="container-video-call p-0 m-0">
    <div class="row-video-call mx-auto">
      <div class="wrap-devices">
        <div class="wrapper-icon-show">
          <div class="btn-paticipant">
            <button>
              <font-awesome-icon
                color="orange"
                :icon="['fas', 'user-friends']"
              />
            </button>
          </div>

          <div class="btn-chat">
            <i class="ti-comments text-white" />
          </div>
          <div
            class="btn-setting"
            @click="showSettings"
          >
            <i class="ti-more-alt" />
          </div>
        </div>

        <div class="btn-camera">
          <a
            :class="getVideoEnabled ? '': 'icon-video-off'"
            @click="videoToggle"
          >
            <font-awesome-icon
              v-if="getVideoEnabled"
              color="white"
              :icon="['fas', 'video']"
            />
            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-camera-video-off-fill"
              viewBox="0 0 16 16"
            >
              <path
                color="#696969"
                fill-rule="evenodd"
                d="M10.961 12.365a1.99 1.99 0 0 0 .522-1.103l3.11 1.382A1 1 0 0 0 16 11.731V4.269a1 1 0 0 0-1.406-.913l-3.111 1.382A2 2 0 0 0 9.5 3H4.272l6.69 9.365zm-10.114-9A2.001 2.001 0 0 0 0 5v6a2 2 0 0 0 2 2h5.728L.847 3.366zm9.746 11.925-10-14 .814-.58 10 14-.814.58z"
              />
            </svg>
          </a>
        </div>
        <div class="btn-microphone">
          <a
            :class="getAudioEnabled ? '': 'icon-mic-off'"
            @click="audioToggle"
          >
            <svg
              v-if="getAudioEnabled"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-mic-fill"
              viewBox="0 0 16 16"
            >
              <path
                color="#fff"
                d="M5 3a3 3 0 0 1 6 0v5a3 3 0 0 1-6 0V3z"
              />
              <path
                color="#fff"
                d="M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z"
              />
            </svg>
            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-mic-mute-fill"
              viewBox="0 0 16 16"
            >
              <path
                color="#696969"
                d="M13 8c0 .564-.094 1.107-.266 1.613l-.814-.814A4.02 4.02 0 0 0 12 8V7a.5.5 0 0 1 1 0v1zm-5 4c.818 0 1.578-.245 2.212-.667l.718.719a4.973 4.973 0 0 1-2.43.923V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 1 0v1a4 4 0 0 0 4 4zm3-9v4.879L5.158 2.037A3.001 3.001 0 0 1 11 3z"
              />
              <path
                color="#696969"
                d="M9.486 10.607 5 6.12V8a3 3 0 0 0 4.486 2.607zm-7.84-9.253 12 12 .708-.708-12-12-.708.708z"
              />
            </svg>

          </a>
        </div>
        <div
          class="btn-share-screen"
          @click="screenShareToggle"
        >
          <a>
            <i class="ti-share-alt text-white" />
          </a>
        </div>

        <div class="share-screen">
          <i calss="ti-share-alt text-white" />
        </div>
      </div>
      <div class="btn-leave-meeting">
        <button @click="leaveMeeting">
          Leave
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import {
    mapGetters,
    mapActions,
} from 'vuex';

export default {
    name: 'MeetingHeader',

    data() {
        return {};
    },

    computed: {
        ...mapGetters('meeting', ['getCopyText', 'getVideoDevices',
            'getAudioDevices', 'getAudioEnabled', 'getVideoEnabled',
            'getScreenshareEnabled', 'getShowIntro', 'getShowChat', 'getShowSettings',
            'getSelectedAudioDeviceId', 'getSelectedVideoDeviceId', 'getUsername',
            'getTyping', 'getShats', 'getMeetingId', 'getSender'
        ]),
    },

    mounted() {},

    methods: {
        ...mapActions('meeting', ['setVideoDevices',
            'setAudioDevices', 'setAudioEnabled', 'setVideoEnabled',
            'setScreenshareEnabled', 'setShowIntro', 'setShowChat', 'setShowSettings',
            'setSelectedAudioDeviceId', 'setSelectedVideoDeviceId', 'setUsername',
            'setTyping', 'setShats', 'setAudioToggle', 'setVideoToggle', 'setIsLeave'
        ]),

        videoToggle: function () {
            // e.stopPropagation();
            this.setVideoToggle();
            this.$log.debug(this.getVideoEnabled)
            this.setVideoEnabled(!this.getVideoEnabled);
        },

        audioToggle: function (e) {
            e.stopPropagation();
            this.setAudioToggle();
            this.$log.debug(this.getAudioEnabled)
            this.setAudioEnabled(!this.getAudioEnabled);
        },

        screenShareToggle: function () {},

        showSettings() {},

        leaveMeeting() {
            this.setIsLeave(true);
            this.$router.push({ name: 'Teams' });
        },
    },
};
</script>

<style lang="css" scoped>
.container-video-call {
    background-image: linear-gradient(to left, #1f1e1e, #000000);
    filter: opacity(85%);
    width: 100%;
}

.row-video-call {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-end;
    align-items: center;
    padding: 10px 15px;
}

.wrap-devices {
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;
}

.wrapper-icon-show {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    border: 1px solid rgba(43, 13, 13, 0.842);
    background-color: rgba(43, 13, 13,
            0.842);
    border-radius: 5px;
    height: 100%;
    border-right: 1px solid rgb(77, 18, 18);
    margin-right: 15px;
}

.btn-chat,
.btn-setting {
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
}

.btn-setting i {
    font-size: 1.2rem;
    color: rgba(255, 255, 255, 0.705);
}

.btn-share-screen,
.btn-microphone,
.btn-camera {
    padding: 0 5px;
    margin: 0 5px;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
}

.btn-share-screen,
.btn-chat i,
.btn-microphone i,
.btn-camera i {
    font-size: 1.2rem;
}

.btn-camera,
.btn-microphone {
    position: relative;
}

.btn-paticipant button {
    outline: none;
    border: none;
    padding: 0 15px;
    background-color: transparent;
    /* border: 1px solid rgba(43, 13, 13, 0.842); */
    color: #FFF;
    font-weight: 600;
    /* background-color:rgba(43, 13, 13, 0.842); */
}

.btn-paticipant button svg {
    width: 1.2rem;
}

.btn-leave-meeting button {
    outline: none;
    margin: 0 10px;
    padding: 4px 15px;
    border: 1px solid rgb(216, 67, 67);
    color: #FFF;
    font-weight: 600;
    background-color: rgb(214, 30, 30);
    border-radius: 5px;
    box-shadow: 1px 1px 1px rgb(216, 33, 33);
}

.btn-microphone svg,
.btn-camera svg {
    width: 1.5rem;
}
</style>

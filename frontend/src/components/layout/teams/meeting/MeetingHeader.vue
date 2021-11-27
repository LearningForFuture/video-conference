<template>
  <div class="container-video-call p-0 m-0">
    <div class="row-video-call">
      <div class="wrap-devices">
        <div class="btn-camera">
          <a
            :class="getVideoEnabled ? '' : 'icon-video-off'"
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
            :class="getAudioEnabled ? '' : 'icon-mic-off'"
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
      <div class="wrap-btn-show">
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
            <div class="dropdown">
              <a
                class=""
                href="javascript:void(0)"
                data-toggle="dropdown"
              >
                <i class="ti-more" />
              </a>
              <div class="dropdown-menu dropdown-menu-right">
                <a
                  class="dropdown-item"
                  href="#"
                ><i class="ti-settings text-dark mr-1" />Setting devices</a>
                <a
                  v-if="user_id && getCreatedBy == user_id"
                  class="dropdown-item"
                  href="#"
                ><svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-telephone-x"
                  viewBox="0 0 16 16"
                >
                  <path
                    color="red"
                    d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"
                  />
                  <path
                    color="red"
                    fill-rule="evenodd"
                    d="M11.146 1.646a.5.5 0 0 1 .708 0L13 2.793l1.146-1.147a.5.5 0 0 1 .708.708L13.707 3.5l1.147 1.146a.5.5 0 0 1-.708.708L13 4.207l-1.146 1.147a.5.5 0 0 1-.708-.708L12.293 3.5l-1.147-1.146a.5.5 0 0 1 0-.708z"
                  />
                </svg>

                  Terminating meeting</a>
                <a
                  class="dropdown-item"
                  href="#"
                  @click="copyURL()"
                ><i class="ti-link mr-1 text-primary" />Share link join
                  meeting</a>
              </div>
            </div>
          </div>
        </div>
        <div class="btn-leave-meeting">
          <button @click="leaveMeeting">
            Leave
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapState } from "vuex";

export default {
  name: "MeetingHeader",

  data() {
    return {
      user_id: localStorage.getItem("user_id"),
    };
  },

  computed: {
    ...mapState("meeting", {
      peers: (state) => state.peers,
    }),

    ...mapGetters("meeting", [
      "getCopyText",
      "getVideoDevices",
      "getAudioDevices",
      "getAudioEnabled",
      "getVideoEnabled",
      "getScreenshareEnabled",
      "getShowIntro",
      "getShowChat",
      "getShowSettings",
      "getSelectedAudioDeviceId",
      "getSelectedVideoDeviceId",
      "getUsername",
      "getTyping",
      "getShats",
      "getMeetingId",
      "getSender",
      "getCreatedBy",
      "getRoomLink",
    ]),
  },

  mounted() {},

  methods: {
    ...mapActions("meeting", [
      "setVideoDevices",
      "setAudioDevices",
      "setAudioEnabled",
      "setVideoEnabled",
      "setScreenshareEnabled",
      "setShowIntro",
      "setShowChat",
      "setShowSettings",
      "setSelectedAudioDeviceId",
      "setSelectedVideoDeviceId",
      "setUsername",
      "setTyping",
      "setShats",
      "setAudioToggle",
      "setVideoToggle",
      "setIsLeave",
      "sendMsgChannels",
    ]),

    videoToggle: function () {
      // e.stopPropagation();
      this.setVideoToggle();
      this.$log.debug(this.getVideoEnabled);
      this.setVideoEnabled(!this.getVideoEnabled);
      const msgStatusVideo = {
        type: "refreshStatusVideo",
        peer_id: this.getSender,
        videoEnabled: this.getVideoEnabled,
      };
      this.sendMsgChannels(msgStatusVideo);
    },

    audioToggle: function (e) {
      e.stopPropagation();
      this.setAudioToggle();
      this.$log.debug(this.getAudioEnabled);
      this.setAudioEnabled(!this.getAudioEnabled);
      const msgStatusAudio = {
        type: "refreshStatusAudio",
        peer_id: this.getSender,
        audioEnabled: this.getAudioEnabled,
      };
      this.sendMsgChannels(msgStatusAudio);
    },

    screenShareToggle: function (e) {
      e.stopPropagation();
      let screenMediaPromise;
      if (!this.getScreenshareEnabled) {
        if (navigator.getDisplayMedia) {
          screenMediaPromise = navigator.getDisplayMedia({
            video: true,
          });
        } else if (navigator.mediaDevices.getDisplayMedia) {
          screenMediaPromise = navigator.mediaDevices.getDisplayMedia({
            video: true,
          });
        } else {
          screenMediaPromise = navigator.mediaDevices.getUserMedia({
            video: {
              mediaSource: "screen",
            },
          });
        }
      } else {
        screenMediaPromise = navigator.mediaDevices.getUserMedia({
          video: true,
        });
      }
      screenMediaPromise
        .then((screenStream) => {
          this.setScreenshareEnabled(!this.getScreenshareEnabled);

          for (let peer_id in this.peers) {
            const sender = this.peers[peer_id]
              .getSenders()
              .find((s) => (s.track ? s.track.kind === "video" : false));
            sender.replaceTrack(screenStream.getVideoTracks()[0]);
          }
          screenStream.getVideoTracks()[0].enabled = true;
          const newStream = new MediaStream([
            screenStream.getVideoTracks()[0],
            this.getLocalMediaStream.getAudioTracks()[0],
          ]);
          // localMediaStream = newStream;
          this.setLocalMediaStream(newStream);
          this.attachMediaStream(
            document.getElementById("selfVideo"),
            newStream
          );
          this.toggleSelfVideoMirror();

          screenStream.getVideoTracks()[0].onended = function () {
            if (this.screenshareEnabled) this.screenShareToggle();
          };
        })
        .catch((e) => {
          console.log(
            "Unable to share screen. Please use a supported browser."
          );
          this.$log.debug(e);
        });
    },

    toggleSelfVideoMirror: function () {
      document
        .querySelector("#videos .video #selfVideo")
        .classList.toggle("mirror");
    },

    copyURL: function () {
      this.$log.debug(this.getRoomLink);
      navigator.clipboard.writeText(this.getRoomLink).then(
        () => {
          // this.setCopyText("Copied 👍");
          // setTimeout(() => (this.setCopyText("")), 2000);
        },
        (err) => console.error(err)
      );
    },

    showSettings() {},

    leaveMeeting() {
      this.setIsLeave(true);
      this.$router.push({ name: "Teams" });
    },
  },
};
</script>

<style lang="css" scoped>
.container-video-call {
  background-color: #292d32;
  width: 100%;
}

.row-video-call {
  /* display: flex;
    flex-flow: row nowrap;
    justify-content: center; */
  display: grid;
  grid-template-columns: 3fr 1fr;
  align-items: center;
  padding: 4px 15px;
  margin: 10px;
  background-color: #292d32;
  box-shadow: -3px -3px 4px #3e4247, 5px 5px 5px #1d1f23 !important;
  border-radius: 4px;
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
  /* border: 1px solid rgba(43, 13, 13, 0.842);
    background-color: rgba(43, 13, 13,
            0.842); */
  border-radius: 5px;
  height: 100%;
  border-right: 1px solid rgb(77, 18, 18);
  margin-right: 15px;
}

.wrap-btn-show {
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-end;
  align-items: center;
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
  padding: 0 15px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn-share-screen:hover,
.btn-microphone:hover,
.btn-camera:hover {
  background-color: #272727;
}

.btn-camera:hover svg,
i {
  color: rgb(155, 21, 21);
}

.btn-microphone a:hover svg {
  color: rgb(155, 21, 21);
}

.btn-share-screen a:hover i {
  color: rgb(155, 21, 21);
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
  color: #fff;
  font-weight: 600;
  /* background-color:rgba(43, 13, 13, 0.842); */
}

.btn-paticipant button svg {
  width: 1.2rem;
}

.btn-leave-meeting {
  justify-self: end;
}

.btn-leave-meeting button {
  outline: none;
  margin: 0 10px;
  padding: 4px 15px;
  border: 1px solid rgb(216, 67, 67);
  color: #fff;
  font-weight: 600;
  background-color: rgb(214, 30, 30);
  border-radius: 5px;
  box-shadow: 1px 1px 1px rgb(216, 33, 33);
}

.btn-microphone svg,
.btn-camera svg {
  width: 1.2rem;
}

.dropdown-menu svg {
  width: 16px;
}
</style>

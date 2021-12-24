<template>
  <div id="videos" />
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import configuration from '../../../../utils/config';
import {
  mapGetters,
  mapActions,
  mapState,
  mapMutations
} from 'vuex';
import store from '../../../../store/index.js';

// let localMediaStream = null; /* our own microphone / webcam */
// let peers = {}; /* keep track of our peer connections, indexed by peer_id */
let peerMediaElements = {}; /* keep track of our <video>/<audio> tags, indexed by peer_id */
let dataChannels = {};

export default {
  name: 'MeetingVideoArea',

  data() {
    return {
      screenshareEnabled: false,
      showIntro: true,
      showChat: false,
      // showSettings: false,
      // hideToolbar: false,
      selectedAudioDeviceId: "",
      selectedVideoDeviceId: "",
      name: window.localStorage.name || "",
      typing: "",
      chats: [],
      // meeting_id: null,
      send_message: null,
      // sender: uuid.v1(),
      connected: false,
    };
  },

  computed: {
    ...mapState('meeting', {
      peers: (state) => state.peers,
    }),

    ...mapGetters('meeting', [
      'getRoomLink', 'getCopyText', 'getVideoDevices', 'getAudioDevices',
      'getAudioEnabled', 'getVideoEnabled', 'getScreenshareEnabled',
      'getShowChat', 'getShowSettings', 'getSelectedAudioDeviceId', 
      'getSelectedVideoDeviceId', 'getUsername', 'getTyping', 'getShats',
      'getMeetingId', 'getSender', 'getConnected', 'getLocalMediaStream', 'getIsLeave',
      'getRoomId', 'getShowParticipants',
    ]),
  },

  watch: { 
    getAudioEnabled(audioEnabled) {
      if (audioEnabled) {
        document.getElementById(this.getSender).querySelector(".wrap-status-video svg.bi-mic-mute-fill").setAttribute("class", "bi-mic-fill");
        let path = document.getElementById(this.getSender).querySelectorAll(".wrap-status-video svg.bi-mic-fill path");
        path[0].setAttribute("d", "M5 3a3 3 0 0 1 6 0v5a3 3 0 0 1-6 0V3z");
        path[path.length - 1].setAttribute("d", "M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z");
      } else {
        document.getElementById(this.getSender).querySelector(".wrap-status-video svg.bi-mic-fill").setAttribute("class", "bi-mic-mute-fill");
        let path = document.getElementById(this.getSender).querySelectorAll(".wrap-status-video svg.bi-mic-mute-fill path");
        path[0].setAttribute("d", "M13 8c0 .564-.094 1.107-.266 1.613l-.814-.814A4.02 4.02 0 0 0 12 8V7a.5.5 0 0 1 1 0v1zm-5 4c.818 0 1.578-.245 2.212-.667l.718.719a4.973 4.973 0 0 1-2.43.923V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 1 0v1a4 4 0 0 0 4 4zm3-9v4.879L5.158 2.037A3.001 3.001 0 0 1 11 3z");
        path[path.length - 1].setAttribute("d", "M9.486 10.607 5 6.12V8a3 3 0 0 0 4.486 2.607zm-7.84-9.253 12 12 .708-.708-12-12-.708.708z");
      }
    },

    getVideoEnabled(videoEnabled) {
      document.getElementById(this.getSender).querySelector(".avatar-user").style.visibility = videoEnabled ? "hidden": "visible";
    },

    getShowParticipants(isShow) {
      if (isShow) {
        this.getUserOnline();
      }
    }
  },

  beforeMount() {
    window.addEventListener("beforeunload", () => {
      this.stompClient.send(
        "/app/meeting/" + this.getMeetingId,
        JSON.stringify({
          sender: this.getSender,
          type: 'REMOVE_PEER',
          meetingId: this.getMeetingId
        })
      )
      // call api update datetime leaves meeting
      this.leaveMeeting();
    });
  },

  mounted() {
    this.init();
  },


  beforeDestroy() {
    if (this.getIsLeave) {
      this.stompClient.send(
        "/app/meeting/" + this.getMeetingId, 
        JSON.stringify({ 
          sender: this.getSender, 
          type: 'REMOVE_PEER', 
          meetingId: this.getMeetingId 
        }) )
    }
    this.disconnect();
  },

  beforeRouteLeave (to, from, next) {
    this.stompClient.send(
      "/app/meeting/" + this.getMeetingId, 
      JSON.stringify({ 
        sender: this.getSender, 
        type: 'REMOVE_PEER', 
        meetingId: this.getMeetingId 
      })
    )

    // call api update datetime leaves meeting
    this.leaveMeeting();
    this.disconnect();
    next();
  },

  methods: {
    ...mapActions('meeting', [
      'setRoomLink', 'setCopyText', 'setVideoDevices', 'setAudioDevices',
      'setAudioEnabled', 'setVideoEnabled', 'setScreenshareEnabled', 'setShowIntro',
      'setShowChat', 'setShowSettings', 'setSelectedAudioDeviceId',
      'setSelectedVideoDeviceId', 'setUsername', 'setTyping', 'setShats',
      'setMeetingId', 'setSender', 'setConnected', 'setLocalMediaStream', 'setIsLeave',
      'setPeers', 'addPeers', 'removePeers', 'addIceCandidate', 'sendMsgChannels',
      'setRemoteDescription', 'setLocalDescription', 'closePeers', 'addChannels', 
      'removeChannels', 'findMeetingById', 'closeMediaDevices', 'getUsersInCall',
      'setAudioToggle', 'setVideoToggle'
    ]),

    ...mapActions('ParticipantMeeting', ['joinMeeting', 'leftMeeting']),

    ...mapActions('messages', ['getAllMessageByMeeting']),

    ...mapMutations('meeting', ['REFRESH_AUDIO_STATUS']),

    toggleSelfVideoMirror: function () {
      document.querySelector("#videos .video #selfVideo").classList.toggle("mirror");
    },

    attachMediaStream: (element, stream) => (element.srcObject = stream),

    changeCamera: function (deviceId) {
      navigator.mediaDevices
        .getUserMedia({
          video: {
            deviceId: deviceId
          }
        })
        .then((camStream) => {
          console.log(camStream);
          for (let peer_id in this.peers) {
            const sender = this.peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "video" : false));
            sender.replaceTrack(camStream.getVideoTracks()[0]);
          }
          camStream.getVideoTracks()[0].enabled = true;

          const newStream = new MediaStream([camStream.getVideoTracks()[0], this.getLocalMediaStream.getAudioTracks()[0]]);
          this.setLocalMediaStream(newStream);
          this.attachMediaStream(document.getElementById("selfVideo"), newStream);
          this.selectedVideoDeviceId = deviceId;
        })
        .catch((err) => {
          console.log(err);
          alert("Error while swaping camera");
        });
    },
    changeMicrophone: function (deviceId) {
      navigator.mediaDevices
        .getUserMedia({
          audio: {
            deviceId: deviceId
          }
        })
        .then((micStream) => {
          for (let peer_id in this.peers) {
            const sender = this.peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "audio" : false));
            sender.replaceTrack(micStream.getAudioTracks()[0]);
          }
          micStream.getAudioTracks()[0].enabled = true;

          const newStream = new MediaStream([this.getLocalMediaStream.getVideoTracks()[0], micStream.getAudioTracks()[0]]);
          this.setLocalMediaStream(newStream);
          this.attachMediaStream(document.getElementById("selfVideo"), newStream);
          this.selectedAudioDeviceId = deviceId;
        })
        .catch((err) => {
          console.log(err);
          // alert("Error while swaping microphone");
        });
    },
    sanitizeString: function (str) {
      const tagsToReplace = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;"
      };
      const replaceTag = (tag) => tagsToReplace[tag] || tag;
      const safe_tags_replace = (str) => str.replace(/[&<>]/g, replaceTag);
      return safe_tags_replace(str);
    },
    linkify: function (str) {
      return this.sanitizeString(str).replace(/(?:(?:https?|ftp):\/\/)?[\w/\-?=%.]+\.[\w/\-?=%]+/gi, (match) => {
        let displayURL = match
          .trim()
          .replace("https://", "")
          .replace("https://", "");
        displayURL = displayURL.length > 25 ? displayURL.substr(0, 25) + "&hellip;" : displayURL;
        const url = !/^https?:\/\//i.test(match) ? "http://" + match : match;
        return `<a href="${url}" target="_blank" class="link" rel="noopener">${displayURL}</a>`;
      });
    },
    edit: function (e) {
      this.typing = e.srcElement.textContent;
    },
    paste: function (e) {
      e.preventDefault();
      const clipboardData = e.clipboardData || window.clipboardData;
      const pastedText = clipboardData.getData("Text");
      document.execCommand("inserttext", false, pastedText.replace(/(\r\n\t|\n|\r\t)/gm, " "));
    },
    sendChat: function (e) {
      e.stopPropagation();
      e.preventDefault();
      if (this.typing.length) {
        const composeElement = document.getElementById("compose");
        const chatMessage = {
          type: "chat",
          name: this.name || "Unnamed",
          message: this.typing,
          date: new Date().toISOString(),
        };
        this.chats.push(chatMessage);
        Object.keys(dataChannels).map((peer_id) => dataChannels[peer_id].send(JSON.stringify(chatMessage)));
        this.typing = "";
        composeElement.textContent = "";
        composeElement.blur;
      }
    },

    formatDate: function (dateString) {
      const date = new Date(dateString);
      const hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
      return (
        (hours < 10 ? "0" + hours : hours) +
        ":" +
        (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
        " " +
        (date.getHours() >= 12 ? "PM" : "AM")
      );
    },

    getAppUrl() {
      // const protocol = "http" + (location.hostname == "localhost" ? "" : "s") + "://";
      const protocol = location.protocol + "//";
      return protocol + location.hostname + (location.hostname == "localhost" ? ":8080" : "");
    },

    getServerUrl() {
      return process.env.VUE_APP_SERVICE_ENDPOINT || 'http://localhost:3000';
    },

    connect() {
      this.socket = new SockJS(this.getServerUrl() + "/meeting");
      this.stompClient = Stomp.over(this.socket);
      const header = {
        sender: this.getSender,
        meetingId: this.getMeetingId
      }
      this.stompClient.connect(header, this.onConnected, this.onError);

    },

    onConnected() {
      // Subscribe to the messages Topic
      this.stompClient.subscribe("/topic/messages/" + this.getMeetingId, this.onMessageReceived);
      this.stompClient.subscribe("/user/topic/messages/" + this.getMeetingId, this.onMessageReceived);

      if (this.getLocalMediaStream) {
        this.joinChatChannel(this.roomId());
      } else {
        this.setupLocalMedia(this.roomId(), this.joinChatChannel);
      }
    },

    onError(error) {
      this.$log.error(error);
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      for (let peer_id in peerMediaElements) {
        document.getElementById("videos").removeChild(peerMediaElements[peer_id].parentNode);
        this.resizeVideos();
      }
      
      for (let peer_id in this.peers) {
        // this.peers[peer_id].close();
        this.closePeers(peer_id);
      }

      // peers = {};
      this.setPeers({});
      peerMediaElements = {};
    },

    disconnect() {
      if (this.stompClient) {
        this.leaveMeeting();
        
        for (let peer_id in peerMediaElements) {
          document.getElementById("videos").removeChild(peerMediaElements[peer_id].parentNode);
          this.resizeVideos();
        }

        console.log(this.peers)

        for (let peer_id in this.peers) {
          this.closePeers(peer_id);
        }

        this.closeMediaDevices();
        this.stompClient.disconnect();
      }
      this.connected = false;
    },

    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },

    joinChatChannel(channel) {
      this.stompClient.send(
        "/app/meeting/" + this.getMeetingId,
        JSON.stringify({
          sender: this.getSender,
          type: 'JOIN',
          meetingId: channel
        })
      )
    },

    onMessageReceived(payload) {
      var message = JSON.parse(payload.body);
      switch (message.type) {
      case "ADD_PEER": {
        // console.log("event add peer: " + message);
        this.onAddPeer(message);
        break;
      }
      case "SESSION_DESCRIPTION": {
        // console.log("event session description: " + message);
        this.onSessionDesciption(message);
        break;
      }
      case "ICE_CANDIDATE": {
        // console.log("event ice candidate: " + message);
        this.handleICECandidate(message);
        break;
      }
      case "REMOVE_PEER": {
        this.onRemovePeer(message);
        break;
      }
      default:
      }
    },

    sendMessage() {
      this.stompClient.send("/app/meeting/" + this.getMeetingId, JSON.stringify({
        type: "JOIN",
        // content: "abc",
        sender: this.getSender,
        meetingId: this.getMeetingId,
      }))
    },

    roomId: () => {
      let roomName = location.pathname.substring(location.pathname.search("meeting") + 8);
      const regexp = new RegExp("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})", "g");
      roomName = regexp.exec(roomName)[1];
      if (!roomName) {
        roomName = Math.random().toString(36).substr(2, 6);
        window.history.pushState({
          url: this.getServerUrl() + "/" + roomName,
        }, roomName, this.getServerUrl() + "/" + roomName);
      }
      return roomName;
    },

    setupLocalMedia(roomId, callback, errorback) {
      if (this.getLocalMediaStream != null) {
        if (callback) callback(roomId);
        return;
      }

      navigator.mediaDevices
        .getUserMedia({
          audio: this.getAudioEnabled,
          video: this.getVideoEnabled
        }).then((stream) => {
          this.setLocalMediaStream(stream);
          const localMedia = this.getVideoElement(this.getSender, this.getUsername, this.getVideoEnabled, true);
          this.attachMediaStream(localMedia, stream);
          this.resizeVideos();
          if (callback) callback(roomId);

          navigator.mediaDevices.enumerateDevices().then((devices) => {
            this.setVideoDevices(devices.filter((device) => device.kind === "videoinput" && device.deviceId !== "default"));
            this.setAudioDevices(devices.filter((device) => device.kind === "audioinput" && device.deviceId !== "default"));
          });

        })
        .catch((err) => {
          console.log(err);
          /* user denied access to a/v */
          console.log("This site will not work without camera/microphone access.");
          if (errorback) errorback();
        });
    },

    getVideoElement: (peerId, name, videoEnabled, isLocal) => {
      const videoWrap = document.createElement("div");
      videoWrap.className = "video";
      const media = document.createElement("video");
      media.setAttribute("playsinline", true);
      media.autoplay = true;
      media.controls = false;
      if (isLocal) {
        media.setAttribute("id", "selfVideo");
        media.className = "mirror";
        media.muted = true;
        media.volume = 0;
      } else {
        media.mediaGroup = "remotevideo";
      }
      const fullScreenBtn = document.createElement("button");
      const iconFullScreen = document.createElement("i");
      iconFullScreen.className = "ti-fullscreen";
      fullScreenBtn.className = "icon-maximize";
      fullScreenBtn.appendChild(iconFullScreen);
      fullScreenBtn.addEventListener("click", () => {
        if (videoWrap.requestFullscreen) {
          videoWrap.requestFullscreen();
        } else if (videoWrap.webkitRequestFullscreen) {
          videoWrap.webkitRequestFullscreen();
        }
      });

      const wrapStatusVideo = document.createElement("div");
      const nameVideo =  document.createElement("p");
      const statusMic =  document.createElement("div");
      const svgMic = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      svgMic.setAttribute("width", "16");
      svgMic.setAttribute("height", "16");
      svgMic.setAttribute("fill", "currentColor");
      svgMic.setAttributeNS(null, "class", "bi bi-mic-fill");
      svgMic.setAttribute("viewBox", "0 0 16 16");
      const pathTopMic = document.createElementNS("http://www.w3.org/2000/svg", "path");
      const pathTBottomMic = document.createElementNS("http://www.w3.org/2000/svg", "path");
      pathTopMic.setAttribute("d", "M5 3a3 3 0 0 1 6 0v5a3 3 0 0 1-6 0V3z");
      pathTopMic.setAttribute("color", "#fff");
      pathTBottomMic.setAttribute("d", "M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z");
      pathTBottomMic.setAttribute("color", "#fff");
      svgMic.appendChild(pathTopMic); svgMic.appendChild(pathTBottomMic);
      statusMic.clasName = "status-mic";
      statusMic.appendChild(svgMic);
      nameVideo.innerText = name;
      nameVideo.className = "p-1 m-0";
      wrapStatusVideo.appendChild(nameVideo);
      wrapStatusVideo.appendChild(statusMic);
      wrapStatusVideo.className = "wrap-status-video";
      videoWrap.appendChild(wrapStatusVideo);

      const wrapImage =document.createElement("a");
      const image = document.createElement("img");
      image.src = 'https://eu.ui-avatars.com/api/?size=200&background=random&rounded=true&name=' + name.replace(' ', '%');
      image.className = "avatar-user";
      wrapImage.href ="javascript:void(0)";
      wrapImage.appendChild(image);
      videoWrap.appendChild(wrapImage);
      wrapImage.className = videoEnabled ? '':'video-off';

      videoWrap.setAttribute("id", peerId || "");
      videoWrap.appendChild(media);
      videoWrap.appendChild(fullScreenBtn);
      document.getElementById("videos").appendChild(videoWrap);
      return media;
    },

    resizeVideos: () => {
      // const types = ["100", "", "three", "four", "especially"];
      const len = document.querySelectorAll("#videos .video").length;
      const mw = document.getElementById("videos").offsetWidth - 1;
      const mh = window.innerHeight - document.querySelector(".wrapper-header").offsetHeight - document.querySelector(".container-video-call").offsetHeight;
      let width = (len === 1) ? mw : (len > 2 && len <= 4) ? mw / 2 : (len > 5 && len <= 9) ? mw / 3 : mw / 5;
      let height = (len === 1) ? mh : (len > 2 && len <= 6) ? mh / 2 : mh / 4;
      if (len == 2) {
        width = mw / 2;
      }

      document.querySelectorAll("#videos .video").forEach((v) => {
        // v.className = "video " + typeReponsive;
        v.style.width = width - 10 + "px";
        if (len === 2)  v.style.height = mh - 15 + "px";
        else v.style.height = height - 15 + "px";
      });
    },

    onAddPeer(config) {
      const peer_id = config.peerId;
      if (peer_id in this.peers) return;

      const peerConnection = new RTCPeerConnection({
        iceServers: configuration.ICE_SERVERs
      }, {
        optional: [{
          DtlsSrtpKeyAgreement: true
        }]
      });
      // console.log(peerConnection)

      // peers[peer_id] = peerConnection;
      this.addPeers({ peer_id: peer_id, value: peerConnection });

      this.handleOnIceCandidate(peer_id, this.getMeetingId, this.getSender, this.stompClient);

      this.handleOnTrack(peer_id, config.peerName);
      this.handleRTCDataChannels(peer_id);

      /* Add our local stream */
      peerConnection.addStream(this.getLocalMediaStream);
      // dataChannels[peer_id] = peerConnection.createDataChannel("talk_data_channel");
      const channel = {
        peer_id: peer_id,
        peerConnection: peerConnection.createDataChannel("talk_data_channel")
      }
      this.addChannels(channel);

      if (config.shouldCreateOffer) {
        this.handleRTCOffer(peer_id, this.getMeetingId, this.getSender, this.stompClient);
      }
    },

    handleOnIceCandidate: (peer_id, meeting_id, sender, stompClient) => {
      store.state.meeting.peers[peer_id].onicecandidate = ({
        candidate
      }) => {
        // console.log("send event RELAY_ICE_CANDIDATE to server ");
        stompClient.send("/app/meeting/" + meeting_id, JSON.stringify({
          type: "RELAY_ICE_CANDIDATE",
          peerId: peer_id,
          iceCandidate: {
            sdpMLineIndex: candidate.sdpMLineIndex,
            candidate: candidate.candidate
          },
          sender: sender,
          meetingId: meeting_id,
        }))
      }
    },

    handleRTCDataChannels: (peer_id) => {
      store.state.meeting.peers[peer_id].ondatachannel = (event) => {
        // console.log("Datachannel event" + peer_id, event);
        event.channel.onpend = (event) => {

        }
        event.channel.onmessage = (message) => {
          try {
            let msg = JSON.parse(message.data);
            switch (msg.type) {
            case "chat":
              this.showChat = true;
              this.hideToolbar = false;
              this.chats.push(msg);
              break;
            case "refreshStatusAudio":
              if (msg.audioEnabled) {
                document.getElementById(msg.peer_id).querySelector(".wrap-status-video svg.bi-mic-mute-fill").setAttribute("class", "bi-mic-fill");
                let path = document.getElementById(msg.peer_id).querySelectorAll(".wrap-status-video svg.bi-mic-fill path");
                path[0].setAttribute("d", "M5 3a3 3 0 0 1 6 0v5a3 3 0 0 1-6 0V3z");
                path[path.length - 1].setAttribute("d", "M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z");
              } else {
                document.getElementById(msg.peer_id).querySelector(".wrap-status-video svg.bi-mic-fill").setAttribute("class", "bi-mic-mute-fill");
                let path = document.getElementById(msg.peer_id).querySelectorAll(".wrap-status-video svg.bi-mic-mute-fill path");
                path[0].setAttribute("d", "M13 8c0 .564-.094 1.107-.266 1.613l-.814-.814A4.02 4.02 0 0 0 12 8V7a.5.5 0 0 1 1 0v1zm-5 4c.818 0 1.578-.245 2.212-.667l.718.719a4.973 4.973 0 0 1-2.43.923V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 1 0v1a4 4 0 0 0 4 4zm3-9v4.879L5.158 2.037A3.001 3.001 0 0 1 11 3z");
                path[path.length - 1].setAttribute("d", "M9.486 10.607 5 6.12V8a3 3 0 0 0 4.486 2.607zm-7.84-9.253 12 12 .708-.708-12-12-.708.708z");
              }

              this.REFRESH_AUDIO_STATUS({
                userId: msg.peer_id,
                audioEnabled: msg.audioEnabled
              })

              break;
            case "refreshStatusVideo":
              document.getElementById(msg.peer_id).querySelector(".avatar-user").style.visibility = msg.videoEnabled ? "hidden": "visible";
              break;
            default:
              break;
            }
          } catch (err) {
            console.log(err);
          }
        };
      };
    },

    handleRTCOffer: (peer_id, meeting_id, sender, stompClient) => {
      store.state.meeting.peers[peer_id].createOffer().then(localDescription => {
        store.state.meeting.peers[peer_id].setLocalDescription(localDescription).then(() => {
          stompClient.send("/app/meeting/" + meeting_id, JSON.stringify({
            type: "RELAY_SESSION_DESCRIPTION",
            peerId: peer_id,
            sessionDescription: localDescription,
            sender: sender,
            meetingId: meeting_id,
          }));
          console.log('Offer setLocalDescription done!');
        }).catch((err) => {
          alert('[Error] offer setLocalDescription', err);
        });
      }).catch((err) => {
        console.error('[Error] sending offer', err);
      });
    },

    async onSessionDesciption(config) {
      try {
        const peer_id = config.peerId;
        const remoteDescription = config.sessionDescription;
        const description = new RTCSessionDescription(remoteDescription);
        // console.log(desc); console.log(config);
        // await peers[peer_id].setRemoteDescription(description)
        await this.setRemoteDescription({ peer_id: peer_id, description: description });
        if (remoteDescription.type == "offer") {
          console.log('Creating answer');
          let localDescription = await this.peers[peer_id].createAnswer();
          this.$log.debug('Answer description is: ', localDescription);
          // await peers[peer_id].setLocalDescription(localDescription);
          await this.setLocalDescription({ peer_id: peer_id, description: localDescription});
          this.stompClient.send("/app/meeting/" + this.getMeetingId, JSON.stringify({
            type: "RELAY_SESSION_DESCRIPTION",
            peerId: peer_id,
            sessionDescription: localDescription,
            sender: this.getSender,
            meetingId: this.getMeetingId,
          }))
        }
      } catch (error) {
        console.log(error);
      }
    },

    handleICECandidate: function (config) {
      // console.log(peers[config.peerId])
      // peers[config.peerId].addIceCandidate(new RTCIceCandidate(config.iceCandidate))
      //     .catch((err) => {
      //         console.log('[Error] addIceCandidate', err);
      //         // console.log(err);
      //     });
      this.addIceCandidate(config);
    },

    onRemovePeer(config) {
      const peer_id = config.peerId;
      if (peer_id in peerMediaElements) {
        document.getElementById("videos").removeChild(peerMediaElements[peer_id].parentNode);
        this.resizeVideos();
      }

      if (peer_id in this.peers) {
        // peers[peer_id].close();
        this.closePeers(peer_id);
      }

      // delete dataChannels[peer_id];
      this.removeChannels(peer_id);
      // delete peers[peer_id];
      this.removePeers(peer_id);
      delete peerMediaElements[peer_id];
    },

    handleOnTrack(peer_id, peerName) {
      this.peers[peer_id].ontrack = (event) => {
        // console.log('handleOnTrack', event);
        if (event.track.kind === 'video') {
          this.loadRemoteMediaStream(event.streams[0], peer_id, peerName);
        }
      };
    },

    loadRemoteMediaStream(stream, peer_id, peerName) {
      // console.log("add stream remote" + peer_id)
      const remoteMedia = this.getVideoElement(peer_id, peerName, this.getVideoEnabled);
      peerMediaElements[peer_id] = remoteMedia;
      this.attachMediaStream(remoteMedia, stream);
      this.resizeVideos();
      this.showIntro = false;
    },

    async init() {
      try {
        await Promise.all([
          this.joinMeeting({
            meetingId: this.roomId(),
            participantId: parseInt(this.getSender)
          }),

          this.findMeetingById({
            meetingId: this.roomId(),
            roomId: parseInt(localStorage.getItem("room_id_current"))
          }),

          this.getAllMessageByMeeting({
            meetingId: this.roomId(),
            roomId: parseInt(localStorage.getItem("room_id_current"))
          })
        ]);

        this.connect();
        this.setIsLeave(false);
      } catch (err) {
        console.log(err);
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
    },

    async leaveMeeting() {
      try {
        await this.leftMeeting({
          meetingId: this.getMeetingId,
          participantId: parseInt(this.getSender)
        });
      } catch (e) {
        console.log(e.response);
      }
      
    },

    async getUserOnline() {
      try {
        await this.getUsersInCall({ roomId: this.getRoomId, meetingId: this.getMeetingId })
      } catch (e) {
        this.$log.debug(e.response);
      }
    }
  },
};
</script>

<style lang="css">
#videos {
    flex: 80%;
    box-sizing: border-box;
    display: flex;
    /* height: 100%; */
    flex-wrap: wrap;
    justify-content: flex-start;
}

.light {
  color: #878787;
}

.video {
    overflow: hidden;
    position: relative;
    background-color: #292d32;
    box-shadow: -4px -4px 5px #3e4247, 7px 7px 7px #1d1f23!important;
    box-sizing: border-box;
    border-radius: 4px;
    margin: 5px 5px 5px 5px;
    box-sizing: border-box;
}

.video video {
    width: 100%;
    height: 100%;
    display: block;
    box-sizing: border-box;
    object-fit: cover;
}

.video video.mirror {
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
  -moz-transform: rotateY(180deg);
}

.video:fullscreen video {
  background: #acadd3;
  object-fit: contain;
  border: none;
}

.video a {
    width: auto; height: auto;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 99;
    visibility: hidden;
}

.video a.video-off {
    visibility: visible;
}

.video a img {
    max-height: 50%;
}

.video .wrap-status-video {
    position: absolute;
    bottom: 0;
    left: 0;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;
    z-index: 10;
    background-color: rgb(24, 17, 17);
    filter: opacity(70%);
    border-radius: 0 10px 0px 0;
}

.video .wrap-status-video p {
    text-overflow: ellipsis;
}

.video .wrap-status-video div svg {
    width: 1rem;
    margin: 0 5px;
}

.video button {
    position: absolute;
    top: 0.1rem;
    right: 0px;
    z-index: 10;
    color: white;
    background: none;
    border: none;
    cursor: pointer;
    text-shadow: 2px 2px 5px #989898;
    padding: 0.1rem 0.4rem;
}

.video button i {
    font-size: 16px;
}

.video:fullscreen button {
  display: none;
}

#app {
  display: none;
}

#intro {
  background: #fff;
  position: fixed;
  bottom: 6rem;
  left: 0px;
  right: 0px;
  z-index: 100;
  width: 18rem;
  margin: auto;
  padding: 1rem;
  box-sizing: border-box;
  border-radius: 1rem;
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.16), 0px 1px 4px rgba(0, 0, 0, 0.16);
}

#intro .roomLink {
  text-decoration: underline;
  text-decoration-style: dashed;
  text-underline-offset: 0.2rem;
  word-wrap: break-word;
  word-break: break-word;
}

#intro .copyURL {
  color: inherit;
  text-decoration: none;
  font-weight: bold;
  -webkit-user-select: none;
  user-select: none;
}

#intro .footer {
  display: flex;
  justify-content: space-between;
}

#intro .footer .terms {
  position: relative;
  top: -0.25rem;
}

#chatWrap {
  background: #fff;
  position: fixed;
  bottom: 6rem;
  left: 0px;
  right: 0px;
  z-index: 100;
  width: 18rem;
  margin: auto;
  padding: 1rem;
  box-sizing: border-box;
  border-radius: 1rem;
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.16), 0px 1px 4px rgba(0, 0, 0, 0.16);
}

#chatWrap #chats {
  overflow-y: auto;
  overflow: auto;
  max-height: 240px;
  margin: auto;
  background:
        /* Shadow covers */ linear-gradient(
      white 30%,
      rgba(255, 255, 255, 0)
    ),
    linear-gradient(rgba(255, 255, 255, 0), white 70%) 0 100%,
    /* Shadows */
      radial-gradient(
        50% 0,
        farthest-side,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0)
      ),
    radial-gradient(
        50% 100%,
        farthest-side,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0)
      )
      0 100%;
  background:
        /* Shadow covers */ linear-gradient(
      white 30%,
      rgba(255, 255, 255, 0)
    ),
    linear-gradient(rgba(255, 255, 255, 0), white 70%) 0 100%,
    /* Shadows */
      radial-gradient(
        farthest-side at 50% 0,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0)
      ),
    radial-gradient(
        farthest-side at 50% 100%,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0)
      )
      0 100%;
  background-repeat: no-repeat;
  background-color: white;
  background-size: 100% 40px, 100% 40px, 100% 14px, 100% 14px;

  background-attachment: local, local, scroll, scroll;
}

#chatWrap .chat {
  margin: 0.5rem 0rem;
}

#chatWrap .chat .name {
  font-weight: bold;
}

#chatWrap .chat .date {
  font-size: small;
}

#chatWrap #composeBox {
  position: relative;
  background: #f6f6f6;
  border-radius: 1rem;
  padding: 0.7rem 1rem;
  margin-top: 0.5rem;
  max-height: 4.2rem;
  overflow-y: auto;
}

#chatWrap #composeBox #placeholder {
  position: absolute;
  z-index: 5;
  opacity: 0.5;
}

#chatWrap #composeBox #compose {
  position: relative;
  z-index: 10;
}

#chatWrap #noChat {
  padding: 1rem;
  text-align: center;
}

#settings {
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.16), 0px 1px 4px rgba(0, 0, 0, 0.16);
  position: fixed;
  bottom: 5rem;
  left: 0px;
  right: 0px;
  z-index: 100;
  width: 17rem;
  margin: auto;
  box-sizing: border-box;
  border-radius: 1rem;
  max-height: 26rem;
  overflow-y: auto;
}

#settings .label {
  padding: 1rem 1rem 0.25rem;
  font-weight: bold;
}

#settings .link {
  padding: 0.25rem 1rem;
  white-space: nowrap;
  width: 100%;
  box-sizing: border-box;
  overflow: hidden;
  text-overflow: ellipsis;
  cursor: pointer;
  border-radius: 1rem;
}

#settings .link.active:before {
  content: "✔️";
  margin-right: 0.5rem;
}

#settings .link:hover {
  background: #fff;
}

#settings .link.copy {
  margin-bottom: 0.5rem;
}

#settings input {
  margin: 0rem 1rem 1rem;
  width: calc(100% - 2rem);
}

#actionsWrap {
  position: fixed;
  bottom: 1rem;
  left: 0px;
  right: 0px;
  z-index: 100;
  display: inline-flex;
  transition: all 0.25s ease-out;
  opacity: 1;
  transform: translateY(0rem);
}

#actionsWrap.hidden {
  opacity: 0;
  transform: translateY(1rem);
  z-index: -1;
}

#actions {
  background: rgba(255, 255, 255, 0.5);
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.16), 0px 1px 4px rgba(0, 0, 0, 0.16);
  margin: auto;
  box-sizing: border-box;
  border-radius: 1rem;
  transition: opacity 250ms;
}

#actions button {
  flex: 1;
  background: none;
  border: none;
  font-size: 1.25rem;
  padding: 1rem;
  cursor: pointer;
  margin: auto;
}

#actions button:hover {
  background: rgba(255, 255, 255, 0.8);
  border-radius: 1rem;
}

#actions button.active {
  background: #fafafa;
  border-radius: 1rem;
}

#actions button.icon-mic-off,
#actions button.icon-video-off {
  color: #e74c3c !important;
}

@media only screen and (max-width: 960px) {
  .video {
    float: left;
    width: 50vw;
    height: 25vh;
    overflow: hidden;
  }
}
</style>

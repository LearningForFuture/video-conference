<template>
  <div id="videos" />
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import configuration from '../../../../utils/config';
import {
    mapGetters,
    mapActions
} from 'vuex';

// let localMediaStream = null; /* our own microphone / webcam */
let peers = {}; /* keep track of our peer connections, indexed by peer_id */
let peerMediaElements = {}; /* keep track of our <video>/<audio> tags, indexed by peer_id */
let dataChannels = {};

export default {
    name: 'MeetingVideoArea',

    data() {
        return {
            // roomLink: "",
            // copyText: "",
            // videoDevices: [],
            // audioDevices: [],
            // audioEnabled: true,
            // videoEnabled: true,
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
            connected: false
        };
    },

    computed: {
        ...mapGetters('meeting', [
            'getRoomLink', 'getCopyText', 'getVideoDevices', 'getAudioDevices',
            'getAudioEnabled', 'getVideoEnabled', 'getScreenshareEnabled', 'getShowIntro',
            'getShowChat', 'getShowSettings', 'getSelectedAudioDeviceId',
            'getSelectedVideoDeviceId', 'getUsername', 'getTyping', 'getShats',
            'getMeetingId', 'getSender', 'getConnected', 'getLocalMediaStream', 'getIsLeave'
        ]),
    },

    beforeMount() {
        window.addEventListener("beforeunload", () => {
            this.stompClient.send("/app/chat/" + this.getMeetingId,
                JSON.stringify({
                    sender: this.getSender,
                    type: 'REMOVE_PEER',
                    meetingId: this.getMeetingId
                })
            )
        });
    },

    mounted() {
        this.init();
    },

    beforeDestroy() {
        if (this.getIsLeave) {
            this.stompClient.send("/app/chat/" + this.getMeetingId, 
                JSON.stringify({ sender: this.getSender, 
                    type: 'REMOVE_PEER', 
                    meetingId: this.getMeetingId 
                }) )
        }
    },

    methods: {
        ...mapActions('meeting', [
            'setRoomLink', 'setCopyText', 'setVideoDevices', 'setAudioDevices',
            'setAudioEnabled', 'setVideoEnabled', 'setScreenshareEnabled', 'setShowIntro',
            'setShowChat', 'setShowSettings', 'setSelectedAudioDeviceId',
            'setSelectedVideoDeviceId', 'setUsername', 'setTyping', 'setShats',
            'setMeetingId', 'setSender', 'setConnected', 'setLocalMediaStream', 'setIsLeave'
        ]),

        copyURL: function () {
            navigator.clipboard.writeText(this.getRoomLink()).then(() => {
                this.setCopyText("Copied 👍");
                setTimeout(() => (this.setCopyText("")), 2000);
            }, (err) => console.error(err));
        },

        toggleSelfVideoMirror: function () {
            document.querySelector("#videos .video #selfVideo").classList.toggle("mirror");
        },

        nameToLocalStorage: function () {
            window.localStorage.name = this.name;
        },
        attachMediaStream: (element, stream) => (element.srcObject = stream),

        screenShareToggle: function (e) {
            e.stopPropagation();
            let screenMediaPromise;
            if (!this.screenshareEnabled) {
                if (navigator.getDisplayMedia) {
                    screenMediaPromise = navigator.getDisplayMedia({
                        video: true
                    });
                } else if (navigator.mediaDevices.getDisplayMedia) {
                    screenMediaPromise = navigator.mediaDevices.getDisplayMedia({
                        video: true
                    });
                } else {
                    screenMediaPromise = navigator.mediaDevices.getUserMedia({
                        video: {
                            mediaSource: "screen"
                        },
                    });
                }
            } else {
                screenMediaPromise = navigator.mediaDevices.getUserMedia({
                    video: true
                });
            }
            screenMediaPromise
                .then((screenStream) => {
                    this.screenshareEnabled = !this.screenshareEnabled;

                    for (let peer_id in peers) {
                        const sender = peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "video" : false));
                        sender.replaceTrack(screenStream.getVideoTracks()[0]);
                    }
                    screenStream.getVideoTracks()[0].enabled = true;
                    const newStream = new MediaStream([screenStream.getVideoTracks()[0], this.getLocalMediaStream.getAudioTracks()[0]]);
                    // localMediaStream = newStream;
                    this.setLocalMediaStream(newStream);
                    this.attachMediaStream(document.getElementById("selfVideo"), newStream);
                    this.toggleSelfVideoMirror();

                    screenStream.getVideoTracks()[0].onended = function () {
                        if (this.screenshareEnabled) this.screenShareToggle();
                    };
                })
                .catch((e) => {
                    alert("Unable to share screen. Please use a supported browser.");
                    console.error(e);
                });
        },
        changeCamera: function (deviceId) {
            navigator.mediaDevices
                .getUserMedia({
                    video: {
                        deviceId: deviceId
                    }
                })
                .then((camStream) => {
                    console.log(camStream);
                    for (let peer_id in peers) {
                        const sender = peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "video" : false));
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
                    for (let peer_id in peers) {
                        const sender = peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "audio" : false));
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
        handleIncomingDataChannelMessage: function (chatMessage) {
            switch (chatMessage.type) {
            case "chat":
                this.showChat = true;
                this.hideToolbar = false;
                this.chats.push(chatMessage);
                break;
            default:
                break;
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
            const protocol = "http" + (location.hostname == "localhost" ? "" : "s") + "://";
            return protocol + location.hostname + (location.hostname == "localhost" ? ":8080" : "");
        },

        getServerUrl() {
            return process.env.VUE_APP_SERVICE_ENDPOINT || 'http://localhost:3000';
        },

        connect() {
            this.socket = new SockJS(this.getServerUrl() + "/chat");
            this.stompClient = Stomp.over(this.socket);
            this.stompClient.connect({
                sender: this.getSender
            }, this.onConnected, this.onError);

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
            // console.log(error);
            this.$log.error(error);
            if (this.stompClient) {
                this.stompClient.disconnect();
            }
            this.connected = false;
            for (let peer_id in peerMediaElements) {
                document.getElementById("videos").removeChild(peerMediaElements[peer_id].parentNode);
                this.resizeVideos();
            }
            for (let peer_id in peers) {
                peers[peer_id].close();
            }

            peers = {};
            peerMediaElements = {};
        },

        disconnect() {
            if (this.stompClient) {
                this.stompClient.disconnect();
            }
            this.connected = false;
        },

        tickleConnection() {
            this.connected ? this.disconnect() : this.connect();
        },

        joinChatChannel(channel) {
            this.stompClient.send("/app/chat/" + this.getMeetingId,
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
            this.stompClient.send("/app/chat/" + this.getMeetingId, JSON.stringify({
                type: "JOIN",
                // content: "abc",
                sender: this.getSender,
                meetingId: this.getMeetingId,
            }))
        },

        roomId: () => {
            let roomName = location.pathname.substring(location.pathname.search("meeting") + 8);
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
                    const localMedia = this.getVideoElement(null, true);
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

        getVideoElement: (peerId, isLocal) => {
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
            fullScreenBtn.className = "icon-maximize";
            fullScreenBtn.addEventListener("click", () => {
                if (videoWrap.requestFullscreen) {
                    videoWrap.requestFullscreen();
                } else if (videoWrap.webkitRequestFullscreen) {
                    videoWrap.webkitRequestFullscreen();
                }
            });

            videoWrap.setAttribute("id", peerId || "");
            videoWrap.appendChild(media);
            videoWrap.appendChild(fullScreenBtn);
            document.getElementById("videos").appendChild(videoWrap);
            return media;
        },

        resizeVideos: () => {
            // const types = ["100", "", "three", "four", "especially"];
            const len = document.querySelectorAll("#videos .video").length;
            const mw = document.getElementById("videos").offsetWidth;
            const mh = window.innerHeight - document.querySelector(".wrapper-header").offsetHeight - document.querySelector(".container-video-call").offsetHeight;
            let width = (len === 1) ? mw : (len > 2 && len <= 4) ? mw / 3 : (len <= 5 && len >= 7) ? mw / 4 : mw / 5;
            let height = (len === 1) ? mh : (len > 2 && len <= 4) ? mh / 3 : (len <= 5 && len >= 7) ? mh / 4 : mh / 5;
            if (len == 2) {
                width = mw / 2;
                height = mh;
            }

            document.querySelectorAll("#videos .video").forEach((v) => {
                // v.className = "video " + typeReponsive;
                v.style.width = width + "px";
                v.style.height = height + "px";
            });
        },

        onAddPeer(config) {
            const peer_id = config.peerId;
            if (peer_id in peers) return;

            const peerConnection = new RTCPeerConnection({
                iceServers: configuration.ICE_SERVERs
            }, {
                optional: [{
                    DtlsSrtpKeyAgreement: true
                }]
            });
            // console.log(peerConnection)

            peers[peer_id] = peerConnection;

            this.handleOnIceCandidate(peer_id, this.getMeetingId, this.getSender, this.stompClient);

            this.handleOnTrack(peer_id);
            this.handleRTCDataChannels(peer_id);

            peerConnection.ondatachannel = function (event) {
                console.log("Datachannel event" + peer_id, event);
                event.channel.onmessage = (msg) => {
                    let chatMessage = {};
                    try {
                        chatMessage = JSON.parse(msg.data);
                        this.handleIncomingDataChannelMessage(chatMessage);
                    } catch (err) {
                        console.log(err);
                    }
                };
            };

            /* Add our local stream */
            peerConnection.addStream(this.getLocalMediaStream);
            dataChannels[peer_id] = peerConnection.createDataChannel("talk_data_channel");

            if (config.shouldCreateOffer) {
                this.handleRTCOffer(peer_id, this.getMeetingId, this.getSender, this.stompClient);
            }
        },

        handleOnIceCandidate: (peer_id, meeting_id, sender, stompClient) => {
            peers[peer_id].onicecandidate = ({
                candidate
            }) => {
                console.log("send event RELAY_ICE_CANDIDATE to server ");
                stompClient.send("/app/chat/" + meeting_id, JSON.stringify({
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
            peers[peer_id].ondatachannel = (event) => {
                console.log("Datachannel event" + peer_id, event);
                event.channel.onmessage = (msg) => {
                    let chatMessage = {};
                    try {
                        chatMessage = JSON.parse(msg.data);
                        this.handleIncomingDataChannelMessage(chatMessage);
                    } catch (err) {
                        console.log(err);
                    }
                };
            };
        },

        handleRTCOffer: (peer_id, meeting_id, sender, stompClient) => {
            peers[peer_id].createOffer().then(localDescription => {
                peers[peer_id].setLocalDescription(localDescription).then(() => {
                    stompClient.send("/app/chat/" + meeting_id, JSON.stringify({
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
                await peers[peer_id].setRemoteDescription(description)
                if (remoteDescription.type == "offer") {
                    console.log('Creating answer');
                    let localDescription = await peers[peer_id].createAnswer();
                    console.log('Answer description is: ', localDescription);
                    await peers[peer_id].setLocalDescription(localDescription);
                    this.stompClient.send("/app/chat/" + this.getMeetingId, JSON.stringify({
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
            peers[config.peerId].addIceCandidate(new RTCIceCandidate(config.iceCandidate))
                .catch((err) => {
                    console.log('[Error] addIceCandidate', err);
                    // console.log(err);
                });
        },

        onRemovePeer(config) {
            const peer_id = config.peerId;
            if (peer_id in peerMediaElements) {
                document.getElementById("videos").removeChild(peerMediaElements[peer_id].parentNode);
                this.resizeVideos();
            }

            if (peer_id in peers) {
                peers[peer_id].close();
            }

            delete dataChannels[peer_id];
            delete peers[peer_id];
            delete peerMediaElements[peer_id];
        },

        handleOnTrack(peer_id) {
            peers[peer_id].ontrack = (event) => {
                // console.log('handleOnTrack', event);
                if (event.track.kind === 'video') {
                    this.loadRemoteMediaStream(event.streams[0], peer_id);
                }
            };
        },

        loadRemoteMediaStream(stream, peer_id) {
            // console.log("add stream remote" + peer_id)
            const remoteMedia = this.getVideoElement(peer_id);
            peerMediaElements[peer_id] = remoteMedia;
            this.attachMediaStream(remoteMedia, stream);
            this.resizeVideos();
            this.showIntro = false;
        },

        init() {
            this.$log.debug(this.getAudioEnabled);
            this.setMeetingId(this.roomId());
            this.setRoomLink(this.getAppUrl() + "/teams/meeting/" + this.getMeetingId);
            this.connect();
            this.setIsLeave(false);
        }
    },
};
</script>

<style lang="css">
#videos {
    display: flex;
    height: 100%;
    flex-wrap: wrap;
    justify-content: flex-start;
    background-image: linear-gradient(to left, #1f1e1e, #000000);
}

.light {
    color: #878787;
}

.video {
    overflow: hidden;
    position: relative;
    box-sizing: border-box;
}

#video .video.one {
    width: 100%;
    height: 100%;
}

#video .video.two {
    width: 50%;
    max-width: 50%;
    height: 50%;
}

#video .video.three {
    width: calc(100/3%);
    height: calc(100/3%);
}

#video .video.four {
    width: 25%;
    height: 25%;
}

#video .video.especially {
    width: 50%;
    height: 100%;
}

.video video {
    width: 100%;
    /* max-height: 100%;
    height: 100%; */
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

.video button {
    position: absolute;
    top: 0.1rem;
    right: 0px;
    z-index: 10;
    font-size: 2rem;
    color: white;
    background: none;
    border: none;
    cursor: pointer;
    text-shadow: 2px 2px 5px #989898;
    padding: 0.1rem 0.4rem;
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
        /* Shadow covers */
        linear-gradient(white 30%, rgba(255, 255, 255, 0)),
        linear-gradient(rgba(255, 255, 255, 0), white 70%) 0 100%,
        /* Shadows */
        radial-gradient(50% 0, farthest-side, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0)),
        radial-gradient(50% 100%, farthest-side, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0)) 0 100%;
    background:
        /* Shadow covers */
        linear-gradient(white 30%, rgba(255, 255, 255, 0)),
        linear-gradient(rgba(255, 255, 255, 0), white 70%) 0 100%,
        /* Shadows */
        radial-gradient(farthest-side at 50% 0, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0)),
        radial-gradient(farthest-side at 50% 100%, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0)) 0 100%;
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

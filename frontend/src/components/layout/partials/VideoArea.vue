<template>
<div id="videos">
    <main>
        <div id="introduce" v-if="showIntro && !showSettings">
            <p>
                <small>
                    <i>Share this link to start meeting</i><br />
                    <span class="roomLink">{{roomLink}}</span>
                    <a class="copyURL" href="javascript:void(0)" @click="copyURL">{{copyText || "Copy"}}</a>
                </small>
            </p>
        </div>

        <div id="settings" v-if="showSettings">
            <div class="label">Camera&nbsp;📹</div>
            <div v-for="(videoDevice) in videoDevices" :key="videoDevice.deviceId">
                <div :class="'link '+(selectedVideoDeviceId === videoDevice.deviceId ? 'active' : '')" @click="changeCamera(videoDevice.deviceId)">
                    {{ videoDevice.label }}
                </div>
            </div>

            <div class="label">Microphone&nbsp;🎙️</div>
            <div v-for="(audioDevice) in audioDevices" :key="audioDevice.deviceId">
                <div :class="'link '+(selectedAudioDeviceId === audioDevice.deviceId ? 'active' : '')" @click="changeMicrophone(audioDevice.deviceId)">
                    {{audioDevice.label}}
                </div>
            </div>

            <div class="label"><i class="ti-user mr-1"></i> {{ users.username }} </div>

            <div class="copy link" @click="toggleSelfVideoMirror">
                Mirror / Unmirror
                <small class="light">(self-view)</small>
            </div>
        </div>

        <div id="actionsWrap" :class="{ hidden: hideToolbar}">
            <div id="actions">
                <button :class="'icon-mic'+(audioEnabled ? '' : '-off')" @click="audioToggle"></button>
                <button :class="'icon-video'+(videoEnabled ? '' : '-off')" @click="videoToggle"></button>
                <button :class="'icon-message-square '+(showChat ? 'active' : '')" @click="showChat = !showChat"></button>
                <button :class="'ti-share-alt '+(screenshareEnabled ? 'active' : '')" @click="screenShareToggle"></button>
                <button :class="'ti-microphone '+(showSettings ? 'active' : '')" @click="showSettings = !showSettings"></button>
            </div>
        </div>
    </main>
</div>
</template>

<script>
let signalingSocket = null; /* our socket.io connection to our webserver */
let localMediaStream = null; /* our own microphone / webcam */
let peers = {}; /* keep track of our peer connections, indexed by peer_id (aka socket.io id) */
let peerMediaElements = {}; /* keep track of our <video>/<audio> tags, indexed by peer_id */
let dataChannels = {};

export default {
    name: 'VideoArea',

    data() {
        return {
            roomLink: "",
            copyText: "",
            videoDevices: [],
            audioDevices: [],
            audioEnabled: true,
            videoEnabled: true,
            screenshareEnabled: false,
            showIntro: true,
            showChat: false,
            showSettings: false,
            hideToolbar: false,
            selectedAudioDeviceId: "",
            selectedVideoDeviceId: "",
            name: window.localStorage.name || "",
            typing: "",
            chats: [],
        };
    },

    mounted() {
        
    },

    methods: {
        copyURL: function () {
            navigator.clipboard.writeText(this.roomLink).then(() => {
                this.copyText = "Copied 👍";
                setTimeout(() => (this.copyText = ""), 2000);
            }, (err) => console.error(err));
        },

        audioToggle: function (e) {
            e.stopPropagation();
            localMediaStream.getAudioTracks()[0].enabled = !localMediaStream.getAudioTracks()[0].enabled;
            this.audioEnabled = !this.audioEnabled;
        },

        videoToggle: function (e) {
            e.stopPropagation();
            localMediaStream.getVideoTracks()[0].enabled = !localMediaStream.getVideoTracks()[0].enabled;
            this.videoEnabled = !this.videoEnabled;
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
                    const newStream = new MediaStream([screenStream.getVideoTracks()[0], localMediaStream.getAudioTracks()[0]]);
                    localMediaStream = newStream;
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
        changeCamera: function(deviceId) {
			navigator.mediaDevices
				.getUserMedia({ video: { deviceId: deviceId } })
				.then((camStream) => {
					console.log(camStream);
					for (let peer_id in peers) {
						const sender = peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "video" : false));
						sender.replaceTrack(camStream.getVideoTracks()[0]);
					}
					camStream.getVideoTracks()[0].enabled = true;

					const newStream = new MediaStream([camStream.getVideoTracks()[0], localMediaStream.getAudioTracks()[0]]);
					localMediaStream = newStream;
					this.attachMediaStream(document.getElementById("selfVideo"), newStream);
					this.selectedVideoDeviceId = deviceId;
				})
				.catch((err) => {
					console.log(err);
					alert("Error while swaping camera");
				});
		},
		changeMicrophone: function(deviceId) {
			navigator.mediaDevices
				.getUserMedia({ audio: { deviceId: deviceId } })
				.then((micStream) => {
					for (let peer_id in peers) {
						const sender = peers[peer_id].getSenders().find((s) => (s.track ? s.track.kind === "audio" : false));
						sender.replaceTrack(micStream.getAudioTracks()[0]);
					}
					micStream.getAudioTracks()[0].enabled = true;

					const newStream = new MediaStream([localMediaStream.getVideoTracks()[0], micStream.getAudioTracks()[0]]);
					localMediaStream = newStream;
					this.attachMediaStream(document.getElementById("selfVideo"), newStream);
					this.selectedAudioDeviceId = deviceId;
				})
				.catch((err) => {
					console.log(err);
					alert("Error while swaping microphone");
				});
		},
        sanitizeString: function(str) {
			const tagsToReplace = { "&": "&amp;", "<": "&lt;", ">": "&gt;" };
			const replaceTag = (tag) => tagsToReplace[tag] || tag;
			const safe_tags_replace = (str) => str.replace(/[&<>]/g, replaceTag);
			return safe_tags_replace(str);
		},
        linkify: function(str) {
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
        edit: function(e) {
			this.typing = e.srcElement.textContent;
		},
		paste: function(e) {
			e.preventDefault();
			const clipboardData = e.clipboardData || window.clipboardData;
			const pastedText = clipboardData.getData("Text");
			document.execCommand("inserttext", false, pastedText.replace(/(\r\n\t|\n|\r\t)/gm, " "));
		},
		sendChat: function(e) {
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
        handleIncomingDataChannelMessage: function(chatMessage) {
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
        formatDate: function(dateString) {
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
    },
};
</script>

<style lang="css" scoped>
input {
    display: block;
    box-sizing: border-box;
    border: 1px solid #efefef;
    border-radius: 0.5rem;
    padding: 0.6rem;
    font-size: 1rem;
}

.light {
    color: #878787;
}

.video {
    float: left;
    width: 25vw;
    height: 50vh;
    overflow: hidden;
    position: relative;
}

.video.one {
    width: 100vw;
    height: 100vh;
}

.video.two {
    width: 50vw;
    height: 100vh;
}

.video.three {
    width: 33.33vw;
    height: 100vh;
}

.video.four {
    width: 50vw;
    height: 50vh;
}

.video.five,
.video.six {
    width: 33.33vw;
    height: 50vh;
}

.video video {
    width: 100%;
    height: 100%;
    display: block;
    margin: auto;
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

#chatWrap .chat .message {}

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

    .video.two {
        width: 100vw;
        height: 50vh;
    }

    .video.three {
        width: 100vw;
        height: 33.33vh;
    }

    .video.five,
    .video.six {
        width: 50vw;
        height: 33.33vh;
    }
}
</style>

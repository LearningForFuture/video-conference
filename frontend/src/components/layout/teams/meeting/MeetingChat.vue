<template>
  <div class="meeting-chat">
    <div class="container">
      <div
        id="header-chat"
        class="row justify-content-between"
      >
        <h3>Meeting Chat</h3>
        <div class="close-chat">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-x-lg"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M13.854 2.146a.5.5 0 0 1 0 .708l-11 11a.5.5 0 0 1-.708-.708l11-11a.5.5 0 0 1 .708 0Z"
            />
            <path
              fill-rule="evenodd"
              d="M2.146 2.146a.5.5 0 0 0 0 .708l11 11a.5.5 0 0 0 .708-.708l-11-11a.5.5 0 0 0-.708 0Z"
            />
          </svg>
        </div>
      </div>
      <div class="row">
        <div class="chat-body">
          <div class="message">
            <perfect-scrollbar>
            <div  v-for="(message, messageId) in getAllMessages" :key="messageId">
              <div class="message-item" v-if="getSender != message.senderId">
                <div class="row m-0 mw-100">
                  <a class="avatar">
                    <img
                      :src="'https://eu.ui-avatars.com/api/?name=' + message.fullName + '&size=200&background=random&rounded=true'"
                      alt="avatar"
                    >
                  </a>
                  <div class="chat-content">
                    <div class="d-flex">
                      <span class="full-name">{{ message.fullName }}</span>
                      <time class="ml-2" v-text="formatTimChat(message.createdAt)"/>
                    </div>
                    <div class="message-source">
                      <span class="message-text">{{ message.body }}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="message-item-out" v-else>
                <div class="row m-0 mw-100">
                  <div class="chat-content">
                    <div class="d-flex justify-content-end">
                      <time class="mr-2" v-text="formatTimChat(message.createdAt)"/>
                      <span class="full-name">{{ message.fullName }}</span>
                    </div>
                    <div class="message-source">
                      <span class="message-text">{{ message.body }}</span>
                    </div>
                  </div>
                  <a class="avatar">
                    <img
                      :src="'https://eu.ui-avatars.com/api/?name=' + message.fullName + '&size=200&background=random&rounded=true'"
                      alt="avatar"
                    >
                  </a>
                </div>
              </div>
            </div>
            </perfect-scrollbar>
          </div>
        </div>
      </div>
      <div
        id="chat-message-field"
        class="row"
      >
        <div
          id="message-text"
          ref="messageField"
          contenteditable
          @input="getCurrentMessage($event)"
          @keydown="senMessageByEnter($event)"
        />
        <div class="form-buttons">
          <button :class="['btn btn-light btn-floating', hiddenBtn ? 'btn-hidden-input': '']">
            <i class="fa fa-paperclip"></i>
          </button>
          <button :class="['btn btn-light btn-floating', hiddenBtn ? 'btn-hidden-input': '']">
            <i class="fa fa-microphone"></i>
          </button>
          <button class="btn btn-light btn-floating" @click="sendMessage">
            <i class="fa fa-send"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { PerfectScrollbar } from "vue2-perfect-scrollbar";
import 'vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { mapGetters, mapMutations } from 'vuex';

export default {
  name: 'Meetingchat',

  components: {
    PerfectScrollbar
  },

  data() {
    return {
      current_message: null,
      hiddenBtn: false
    };
  },

  computed: {
    ...mapGetters('meeting', ['getMeetingId', 'getSender']),
    ...mapGetters('messages', ['getAllMessages'])
  },

  watch: { 
    current_message(value) {
      if (value) {
        this.hiddenBtn = true;
      } else {
        this.hiddenBtn = false;
      }
    }
  },

  mounted() {
    document.getElementById("chat-message-field").style.width = document.querySelector(".meeting-chat .container").offsetWidth + "px";
    let maxHeight = document.querySelector('section').offsetHeight - document.getElementById("chat-message-field").offsetHeight - 20 - document.getElementById("header-chat").offsetHeight - 40;
    document.querySelector(".chat-body .message").style.maxHeight = maxHeight + "px";
    document.querySelector(".ps").style.height = maxHeight + "px";
    this.connect();
  },

  methods: {
    ...mapMutations('messages', ['ADD_NEW_MESSAGE']),

    sendMessage: function () {
      if (!this.current_message) return;
      this.stompClient.send(
        "/app/meeting/" + this.getMeetingId, 
        JSON.stringify({ 
          type: "CHAT",
          meetingId: this.getMeetingId,
          message: {
            senderId: this.getSender,
            meetingId: this.getMeetingId,
            body: this.current_message
          }
        })
      )
      this.$refs.messageField.innerHTML = '';
    },

    getCurrentMessage: function (event) {
      // console.log(event.target.firstChild)
      this.current_message =  event.target.textContent;
    },

    senMessageByEnter: function (event) {
      if (event.keyCode != 13) return;
      this.sendMessage();
      event.preventDefault();
    },

    getServerUrl() {
      return process.env.VUE_APP_SERVICE_ENDPOINT || 'http://localhost:3000';
    },

    connect() {
      this.socket = new SockJS(this.getServerUrl() + "/chat");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({
        sender: this.getSender,
        meetingId: this.getMeetingId
      }, this.onConnected, this.onError);
    },

    onConnected() {
      // Subscribe to the messages Topic
      this.stompClient.subscribe("/topic/meeting/" + this.getMeetingId + "/chat", this.onMessageReceived);
      this.stompClient.subscribe("/user/topic/meeting/" + this.getMeetingId + "/chat", this.onMessageReceived);
    },

    onError(error) {
      this.$log.debug(error);
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
    },

    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
    },

    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);
      this.ADD_NEW_MESSAGE(message);
    },

    zeroPadding(num, digit) {
      var zero = '';
      for (var i = 0; i < digit; i++) {
        zero += '0';
      }
      return (zero + num).slice(-digit);
    },

    formatTimChat(time) {
      const date =  new Date(time);
      const hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
      return this.zeroPadding(date.getMonth() + 1, 2) + '/' + this.zeroPadding(date.getDay(), 2) + ' '
        + this.zeroPadding(hours, 2) + ':' + this.zeroPadding(date.getMinutes(), 2) + ' ' 
        + (date.getHours() > 12 ? 'PM': 'AM');
    },
  },
};
</script>

<style lang="css" scoped>
.meeting-chat {
    flex: 30%;
    height: 100%;
    padding: 10px 10px 10px 0;
    box-sizing: border-box;
    overflow: hidden;
}

.container {
    border-radius: 4px;
    background-color: #efefef;
    box-shadow: -4px -4px 5px #3e4247, 7px 7px 7px #1d1f23 !important;
    height: 100%;
    position: relative;
    overflow: hidden;
}

.row {
    padding: 5px;
}

.avatar img {
    max-width: 2rem;
}

.close-chat svg {
    cursor: pointer;
    width: 1.2rem;
    height: 1.2rem;
}

h3 {
    font-size: 1.5rem;
    color: #3db16b;
    margin: 0;
}

p {
    color: #292d32;
    margin: 0;
}

.message-item-out .row {
    justify-content: flex-end !important;
}

.row {
    flex-wrap: nowrap;
    justify-content: flex-start;
}

.chat-body {
    min-height: 100%;
    box-sizing: border-box;
    overflow-x: hidden;
    overflow-y: auto;
    width: 100%;
}

.message-item .chat-content {
    margin-left: 10px;
    border-left: 2px solid #23A6D5;
}

.message-item-out .chat-content {
    margin-right: 10px;
    border-right: 2px solid #23A6D5;
}

.chat-content {
    padding: 5px;
    /* background-color: #fff; */
    border-radius: 2px;
}

.chat-content p {
  text-wrap: break-word;
  text-overflow: ellipsis;
}

span.full-name {
    color: rgba(17, 17, 17, 0.87) !important;
    font-weight: 800;
}

time {
    color: #36393d8f;
    font-size: 12px;
}

#chat-message-field {
    position: fixed;
    min-height: 30px;
    bottom: 10px;
    box-sizing: border-box;
    border-top: 1px solid #5e6e80;
    font-size: 0;
    white-space: nowrap;
    z-index: 9;
}

#chat-message-field:before {
    content: '';
    display: inline-block;
    height: 100%;
    min-height: 30px;
    vertical-align: middle;
}

#message-text {
    display: inline-block;
    width: 100%;
    max-height: 60px;
    height: auto;
    width: calc(100% - 40px);
    padding-right: 5px;
    box-sizing: border-box;
    border: none;
    font-size: 15px;
    font-weight: 400;
    color: #3e4247;
    white-space: normal;
    vertical-align: middle;
    overflow-y: auto;
    overflow-x: hidden;
}

#message-text:focus {
    outline: none;
    border: none;
}

#message-text:empty:before {
    content: 'Enter message...';
    font-size: 14px;
    line-height: 18px;
    color: #3e4247;
    opacity: .5;
}

.message {
    overflow-x: hidden;
    /* overflow-y: scroll; */
    margin-bottom: 30px;
}

.message-item-out:not(:first-child),
.message-item:not(:first-child) {
    margin-top: .3rem;
}

.chat-content span.message-text {
    word-break: break-word;
}

.message-source {
  border-radius: 10px;
  padding-left: 10px; padding-right: 10px; padding-top: 5px; padding-bottom: 5px;
  display: inline-block;
}

.message-item .message-source {
  text-align: left;
  background-color: #f4f4f4;
}

.message-item-out .message-source {
  text-align: right;
  float:right;
  background-color: #C8EDFA;
}

.btn-hidden-input {
  display: none;
}

#header-chat {
  border-bottom: 1px solid #5e6e8083;
  box-sizing: border-box;
}

@media screen and (max-width: 992px) {
    #chat-message-field {
        bottom: 70px;
    }
}

</style>

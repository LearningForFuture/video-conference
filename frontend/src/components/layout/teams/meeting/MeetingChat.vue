<template>
  <div class="meeting-chat">
    <div class="container">
      <div class="row">
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
            <div class="message-item">
              <div class="row m-0">
                <a class="avatar">
                  <img
                    src="https://eu.ui-avatars.com/api/?name=le%20van%20ket&size=200&background=random&rounded=true"
                    alt="avatar"
                  >
                </a>
                <p>Hi you! I'm sensei, owner application</p>
              </div>
            </div>
            <div class="message-item-out">
              <div class="row m-0">
                <a class="avatar">
                  <img
                    src="https://eu.ui-avatars.com/api/?name=le%20van%20ket&size=200&background=random&rounded=true"
                    alt="avatar"
                  >
                </a>
                <p>Hi you! I'm sensei, owner application</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="form-send-message">
          <form action="">
            <div class="form-group">
              <input
                type="text"
                name="message"
                class="form-control"
                required
                placeholder="Enter message"
              >
              <input
                type="submit"
                class="btn-send"
                value="send"
              >
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'Meetingchat',

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

<style lang="css" scoped>
.meeting-chat {
    flex: 25%;
    max-width: 25%;
    height: 100%;
    background-color: #fff;
    padding: 1rem;
    box-sizing: border-box;
}

.avatar img {
    max-width: 2rem;
}

.close-chat svg {
    width: 1rem;
}

h3 {
    font-size: 1.5rem;
}

p {
    color: #111;
}

.row:first-child {
    justify-content: space-between !important;
    align-items: center;
}

.row {
    flex-wrap: nowrap;
}
</style>

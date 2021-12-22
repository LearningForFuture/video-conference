import MessageService from "../../services/MessageService";
const service = new MessageService();

const state = {
  messages: [],
}
const mutations = {
  SET_MESSAGES(state, messages) {
    state.messages = [...messages];
  },

  ADD_NEW_MESSAGE(state, message) {
    const { messages } = state;
    const msgIndx = messages.findIndex(m => m.messageId === message.messageId);
    if (msgIndx !== -1) return;
    messages.push(message);
    state.messages = [...messages];
  },

  UPDATE_MESSAGE(state, message) {
    const { messages } = state;
    const msgIdx = messages.findIndex(msg => msg.messageId === message.messageId);
    if (msgIdx !== -1) {
      messages[msgIdx] = message;
    }
  },

  REMOVE_MESSAGE(state, message) {
    state.messages.splice(state.messages.indexOf(message), 1);
  }
}
const actions = {
  async getAllMessageByMeeting({ commit }, data) {
    const messages = await service.findMessageByMeetingId(data);
    if (messages) {
      commit('SET_MESSAGES', messages.data);
    }
  }
}
const getters = {
  getAllMessages: (state) => state.messages
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
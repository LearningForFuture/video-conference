import ParticipantRoomService from "../../services/ParticipantRoomService";
const service = new ParticipantRoomService();

const state = {
  paticipants: []
}
const mutations = {
  SET_PARTICIPANTS(state, payload) {
    state.paticipants = [...payload];
  },

  ADD_NEW_PARTICIPANT(state, participant) {
    const { participants } = state;
    participants.push(participant);
    state.participants = [...participants];
  },

  UPDATE_PARTICIPANT(state, participant) {
    const { participants } = state;
    const participantIndx = participants.findIndex(p => p.participantId === participant.participantId);
    if (participantIndx > 0) {
      participants[participantIndx] = { ...participant };
    }
  },

  REMOVE_PARTICIPANT(state, participant) {
    state.participants.splice(state.participants.indexOf(participant), 1);
  }
}
const actions = {
  async addParticipantRoom({ commit }, data) {
    const room = await service.addParticipantRoom(data);
  },

  async getAllParticipantsByRoomId({commit}, roomId) {
    const participants = await service.getParticipantsByRoomId(roomId);
    if (participants) {
      commit('SET_PARTICIPANTS', participants.data);
    }
  }
}
const getters = {
  getPaticipants: (state) => state.paticipants
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
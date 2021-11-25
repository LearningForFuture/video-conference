import ParticipantMeetingService from "../../services/ParticipantMeetingService";
const service = new ParticipantMeetingService();

const state = {
  participants: []
}
const mutations = {

  SET_PATICIPANTS(state, participants) {
    state.participants = [...participants];
  },

  ADD_NEW_PARTICIPANT(state, participant) {
    const { participants } = state;
    participants.push(participant);
    state.participants = [...participants];
  },

  UPDATE_PARTICIPANT(state, participant) {
    const { participants } = state;
    const participantIdx = participants.findIndex(pm => pm.meetingId === participant.meetingId);
    if (participantIdx !== -1) {
      participants[participantIdx] = participant;
    }
  },

  REMOVE_PARTICIPANT(state, participant) {
    const { participants } = state;
    participants.splice(participants.indexOf(participant), 1);
  }
}
const actions = {
  async joinMeeting({commit}, participant) {
    const participantResp = await service.joindMeeting(participant);
    if (participantResp) {
      commit('ADD_NEW_PARTICIPANT', participantResp.data);
    }
  },

  async leftMeeting({ commit }, participant) {
    const participantResp = await service.leftMeeting(participant);
    if (participantResp) {
      commit('UPDATE_PARTICIPANT', participantResp.data);
      commit('REMOVE_PARTICIPANT', participantResp.data);
    }
  }

}
const getters = {
  getParticipants: (state) => state.participants
}
export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
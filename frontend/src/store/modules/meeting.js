import {
    uuid
} from 'vue-uuid';

const state = {
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
    selectedAudioDeviceId: "",
    selectedVideoDeviceId: "",
    username: "le van ket",
    typing: "",
    chats: [],
    meeting_id: null,
    send_message: null,
    sender: uuid.v1(),
    connected: false,
    localMediaStream: null,
    isLeave: true,
}

const mutations = {
    SET_IS_LEAVE(state, payload) {
        state.isLeave = payload;
    },

    SET_LOCAL_MEDIA_STREAM(state, payload) {
        state.localMediaStream = payload;
    },

    SET_AUDIO_TOGGLE(state) {
        state.localMediaStream.getAudioTracks()[0].enabled = !state.localMediaStream.getAudioTracks()[0].enabled;
    },

    SET_VIDEO_TOGGLE(state) {
        state.localMediaStream.getVideoTracks()[0].enabled = !state.localMediaStream.getVideoTracks()[0].enabled;
    },

    SET_ROOM_LINK(state, payload) {
        state.roomLink = payload;
    },

    SET_COPY_TEXT(state, payload) {
        state.copyText = payload;
    },

    SET_VIDEO_DEVICES(state, payload) {
        state.videoDevices = payload;
    },

    SET_AUDIO_DEVICES(state, payload) {
        state.audioDevices = payload;
    },

    SET_VIDEO_ENABLED(state, payload) {
        state.videoEnabled = payload;
    },

    SET_AUDIO_ENABLED(state, payload) {
        state.audioEnabled = payload;
    },

    SET_SCREEN_SHARE_ENABLED(state, payload) {
        state.screenshareEnabled = payload;
    },

    SET_SHOW_INTRO(state, payload) {
        state.showIntro = payload;
    },

    SET_SHOW_CHAT(state, payload) {
        state.showChat = payload;
    },

    SET_SHOW_SETTINGS(state, payload) {
        state.settings = payload;
    },

    SET_SELECTED_AUDIO_DEVICES_ID(state, payload) {
        state.selectedAudioDeviceId = payload;
    },

    SET_SELECTED_VIDEO_DEVICES_ID(state, payload) {
        state.selectedVideoDeviceId = payload;
    },

    SET_USERNAME(state, payload) {
        state.username = payload;
    },

    SET_TYPING(state, payload) {
        state.typing = payload;
    },

    SET_CHATS(state, payload) {
        state.chats = payload;
    },

    SET_MEETING_ID(state, payload) {
        state.meeting_id = payload;
    },

    SET_SENDER(state, payload) {
        state.sender = payload;
    },

    SET_CONNECTED(state, payload) {
        state.connected = payload;
    }

}

const actions = {
    setIsLeave({ commit }, isleave) {
        commit("SET_IS_LEAVE", isleave);  
    },

    setLocalMediaStream({ commit }, localMediaStream) {
        commit('SET_LOCAL_MEDIA_STREAM', localMediaStream);
    },

    setAudioToggle({ commit }) {
        commit('SET_AUDIO_TOGGLE');
    },

    setVideoToggle({ commit }) {
        commit('SET_VIDEO_TOGGLE');
    },

    setRoomLink ({ commit, state }, roomLink) {
        commit('SET_ROOM_LINK', roomLink);
        console.log(state.roomLink);
    },

    setCopyText: ({ commit }, copyText) => {
        commit('SET_COPY_TEXT', copyText);
    },

    setVideoDevices: ({ commit }, videoDevices) => {
        commit('SET_VIDEO_DEVICES', videoDevices);
    },

    setAudioDevices: ({ commit }, audioDevices) => {
        commit('SET_AUDIO_DEVICES', audioDevices);
    },

    setAudioEnabled: ({ commit }, audioEnabled) => {
        commit('SET_AUDIO_ENABLED', audioEnabled);
    },

    setVideoEnabled: ({ commit }, videoEnabled) => {
        commit('SET_VIDEO_ENABLED', videoEnabled);
    },

    setScreenshareEnabled: ({ commit }, screenshareEnabled) => {
        commit('SET_SCREEN_SHARE_ENABLED', screenshareEnabled);
    },

    setShowIntro: ({ commit }, showIntro) => {
        commit('SET_SHOW_INTRO', showIntro);
    },

    setShowChat: ({ commit }, showChat) => {
        commit('SET_SHOW_CHAT', showChat);
    },

    setShowSettings: ({ commit }, showSettings) => {
        commit('SET_SHOW_SETTINGS', showSettings);
    },

    setSelectedAudioDeviceId: ({ commit }, selectedAudioDeviceId) => {
        commit('SET_SELECTED_AUDIO_DEVICE_ID', selectedAudioDeviceId);
    },

    setSelectedVideoDeviceId: ({ commit }, selectedVideoDeviceId) => {
        commit('SET_SELECTED_VIDEO_DEVICES_ID', selectedVideoDeviceId);
    },

    setUsername: ({commit}, username) => commit('SET_USERNAME', username),
    setTyping: ({commit}, typing) => commit('SET_TYPING', typing),
    setShats: ({commit}, chats) => commit('SET_SHAT', chats),
    setMeetingId: ({commit}, meeting_id) => commit('SET_MEETING_ID', meeting_id),
    setSender: ({commit}, sender) => commit('SET_SENDER', sender),
    setConnected: ({commit}, connected) => commit('SET_CONNECTED', connected) 
}

const getters = {
    getIsLeave: (state) => state.isLeave,
    getLocalMediaStream: (state) => state.localMediaStream,
    getRoomLink: (state) => state.roomLink,
    getCopyText: (state) => state.copyText,
    getVideoDevices: (state) => state.videoDevices,
    getAudioDevices: (state) => state.audioDevices,
    getAudioEnabled: (state) => state.audioEnabled,
    getVideoEnabled: (state) => state.videoEnabled,
    getScreenshareEnabled: (state) => state.screenshareEnabled,
    getShowIntro: (state) => state.showIntro,
    getShowChat: (state) => state.showChat,
    getShowSettings: (state) => state.showSettings,
    getSelectedAudioDeviceId: (state) => state.selectedAudioDeviceId,
    getSelectedVideoDeviceId: (state) => state.selectedVideoDeviceId,
    getUsername: (state) => state.username,
    getTyping: (state) => state.typing,
    getShats: (state) => state.shats,
    getMeetingId: (state) => state.meeting_id,
    getSender: (state) => state.sender,
    getConnected: (state) => state.connected,
}

const modules = {};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
    modules,
}
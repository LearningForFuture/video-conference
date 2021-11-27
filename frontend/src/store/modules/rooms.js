
import RoomService from "../../services/RoomService";
const service = new RoomService();

const state = {
  isJoined: false,
  rooms: [],
  roomDetails: {},
  roomPage: {
    rooms: [],
    currentPage: 0,
    totalItems: 0,
    totalPages: 0,
  }
}

const mutations = {
  SET_IS_JOINED(state, joined) {
    state.isJoined = joined;
  },

  SET_ROOMS(state, rooms) {
    state.rooms = [...rooms];
  },

  ADD_NEW_ROOM(state, room) {
    const { rooms } = state;
    rooms.push(room);
    state.rooms = [...rooms];
  },

  REMOVE_ROOM(state, room) {
    state.rooms.splice(state.rooms.indexOf(room), 1);
  },

  UPDATE_ROOM(state, room) {
    const { rooms } = state;
    const roomIndex = rooms.findIndex(r => r.roomId === room.roomId);
    if (roomIndex > 0) {
      rooms[roomIndex] = { ...room };
    }
  },

  SET_ROOM_DETAILS(state, room) {
    state.roomDetails = {};
    state.roomDetails = { ...room };
  },

  SET_ROOM_PAGE(state, roomPage) {
    state.roomPage.currentPage = roomPage.currentPage;
    state.roomPage.totalItems = roomPage.totalItems;
    state.roomPage.totalPages = roomPage.totalPages;
    state.roomPage.rooms = [...roomPage.data];
  }
}

const actions = {
  async changeStateJoined({ commit }, joined) {
    commit('SET_IS_JOINED', joined);
  },

  async getRoomList({ commit }) {
    const rooms = await service.findAll();
    commit('SET_ROOMS', rooms.data);
  },

  async addNewRoom({ commit }, room) {
    const newRoom = await service.createRoom(room);
    if (newRoom) {
      commit('ADD_NEW_ROOM', newRoom.data);
    }
  },

  async getDetailRoom({ commit }, roomId) {
    const room = await service.findById(roomId);
    if (room) {
      commit('SET_ROOM_DETAILS', room.data);
    }
  },

  async getAllRooms({ commit }, pageRoom) {
    let response = await service.findAllHasPagination(pageRoom);
    commit('SET_ROOM_PAGE', response.data);
  },
}

const getters = {
  getIsJoined(state) {
    return state.isJoined;
  },

  getAll(state) {
    return state.rooms;
  },

  getRoomDetails(state) {
    return state.roomDetails;
  },
  getRoomPage: (state) => state.roomPage,
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
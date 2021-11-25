
import RoomService from "../../services/RoomService";
const service = new RoomService();

const state = {
  isJoined: false,
  rooms: [],
  roomDetails: {},
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
    if (roomIndex != -1) {
      rooms[roomIndex] = { ...room };
    }
  },

  SET_ROOM_DETAILS(state, room) {
    state.roomDetails = {};
    state.roomDetails = { ...room };
  }
}

const actions = {
  async changeStateJoined({ commit }, joined) {
    commit('SET_IS_JOINED', joined);
  },

  async getRoomList({commit}) {
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
      localStorage.setItem('room_id_current', room.data.roomId);
    }
  },

  async joinRoomByCode({ commit, state }, data) {
    const room = await service.joindRoomByCode(data);
    const { rooms } = state;
    const roomIdx = rooms.findIndex(r => r.roomId === room.data.roomId);
    if (roomIdx == -1) {
      commit('ADD_NEW_ROOM', room.data);
    }
  }
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
  }
}

const modules = {
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
  modules,
}
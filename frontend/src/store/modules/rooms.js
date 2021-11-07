
const state = {
    isJoined: false,
    teams: [],
}

const mutations = {
    SET_IS_JOINED(state, joined) {
        state.isJoined = joined;
    }
}

const actions = {
    async changeStateJoined({ commit }, joined) {
        commit('SET_IS_JOINED', joined);
    }
}

const getters = {
    getIsJoined(state) {
        return state.isJoined;
    },

    all(state) {
        return state.teams;
    },
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
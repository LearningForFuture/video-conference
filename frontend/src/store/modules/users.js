import UserService from "../../services/UserService";
const service = new UserService();

const state = {
  username: null,
  password: null,
  email: null,
  verifyEmail: false,
  fullName: null,
  userId: null,
  createdAt: null,
  updateAt: null,
  roles: [],
  userPage: {
    users: [],
    currentPage: 0,
    totalItems: 0,
    totalPages: 0,
  }
}

const mutations = {
  SET_USERNAME(state, username) {
    state.username = username
  },
  SET_PASSWORD(state, password) {
    state.password = password
  },
  SET_EMAIL(state, email) {
    state.email = email
  },
  SET_VERIFY_EMAIL(state, verifyEmail) {
    state.verifyEmail = verifyEmail
  },
  SET_ROLES(state, roles) {
    state.roles = [...roles];
  },
  SET_FULLNAME(state, fullName) {
    state.fullName = fullName;
  },
  SET_USER_ID(state, userId) {
    state.userId = userId;
  },
  SET_USER_PAGE(state, userPage) {
    state.userPage.currentPage = userPage.currentPage;
    state.userPage.totalItems = userPage.totalItems;
    state.userPage.totalPages = userPage.totalPages;
    state.userPage.users = [...userPage.data];
  }
}

const actions = {

  async createUser({ commit }, user) {
    const newUser = await service.createUser(user);
    if (newUser) {
      commit('SET_USERNAME', newUser.data.username);
      commit('SET_PASSWORD', user.password);
      commit('SET_EMAIL', newUser.data.email);
      localStorage.setItem('username', newUser.data.username);
      localStorage.setItem('password', user.password);
      localStorage.setItem('user_id', user.userId);
    }
  },

  async confirmEmailRegistration({ commit }, token) {
    const respone = await service.confirmEmailRegistration(token);
    if (respone) {
      commit('SET_VERIFY_EMAIL', true);
    }
  },

  async login({ commit }, data) {
    const account = await service.login(data);
    if (account) {
      console.log(account);
      commit('SET_FULLNAME', account.data.fullName);
      commit('SET_USER_ID', account.data.userId);
      commit('SET_ROLES', account.data.roles);
      localStorage.setItem('full_name', account.data.fullName);
      localStorage.setItem('roles', account.data.roles);
      localStorage.setItem('user_id', account.data.userId);
      // do something with status user
    }
  },
  async getAllUsers({ commit }, pageUser) {
    let response = await service.findAll(pageUser);
    commit('SET_USER_PAGE', response.data);
  },
}
const getters = {
  getUsername: (state) => state.username,
  getPassword: (state) => state.password,
  getEmail: (state) => state.email,
  getFullName: (state) => state.fullName,
  getUserId: (state) => state.userId,
  getRoles: (state) => state.roles,
  getUserPage: (state) => state.userPage,
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
import Vue from 'vue';
import Vuex from 'vuex';
import rooms from './modules/rooms';
import meeting from './modules/meeting';
import users from './modules/users';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    rooms,
    meeting,
    users
  }
});

// store.subscribeAction(function callback(action, state) {
//   console.log(action);
// });

export default store;
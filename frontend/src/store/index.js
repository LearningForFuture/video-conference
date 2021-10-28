import Vue from 'vue';
import Vuex from 'vuex';
import groups from './modules/groups';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    groups
  }
});

// store.subscribeAction(function callback(action, state) {
//   console.log(action);
// });

export default store;
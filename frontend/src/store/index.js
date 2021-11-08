import Vue from 'vue';
import Vuex from 'vuex';
import rooms from './modules/rooms';
import meeting from './modules/meeting';

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        rooms,
        meeting,
    }
});

// store.subscribeAction(function callback(action, state) {
//   console.log(action);
// });

export default store;
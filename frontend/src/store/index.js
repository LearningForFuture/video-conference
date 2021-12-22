import Vue from 'vue';
import Vuex from 'vuex';
import rooms from './modules/rooms';
import meeting from './modules/meeting';
import users from './modules/users';
import ParticipantRoom from './modules/ParticipantRoom';
import ParticipantMeeting from './modules/ParticipantMeeting';
import messages from './modules/message';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    rooms,
    meeting,
    users,
    ParticipantRoom,
    ParticipantMeeting,
    messages
  }
});

// store.subscribeAction(function callback(action, state) {
//   console.log(action);
// });

export default store;
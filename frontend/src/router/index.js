import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Teams from '../views/Teams.vue';
import Meeting from '../views/Meeting.vue';
import ConfirmEmailRegistration from '../views/ConfirmEmail.vue';
import RoomDetail from '../views/RoomDetail.vue';

Vue.use(VueRouter);

import HomeNavigation from '../components/layout/home/HomeNavigation.vue'
import HomeFooter from '../components/layout/home/HomeFooter.vue'
import Conversation from '../views/Conversations.vue'
import RoomBody from '../components/layout/teams/room/RoomBody.vue';
import RoomParticipants from '../components/layout/teams/room/RoomParticipants.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    components: {
      default: Home,
      navigation: HomeNavigation,
      footer: HomeFooter
    },
  },

  {
    path: '/conversations',
    component: Conversation,
    children: [
      {
        path: 'teams',
        component: Teams
      },
      {
        path: 'teams/room/:roomId',
        component: RoomDetail,
        props: true,
        children: [
          {
            path: 'post',
            component: RoomBody,
          },
          {
            path: 'participant-room',
            component: RoomParticipants
          }
        ]
      },
      {
        path: 'teams/room/:roomId/meeting/:meetingId',
        component: Meeting,
        props: true,
      }
    ]
  },

  // {
  //     path: '/teams',
  //     name: 'Teams',
  //     component: Teams,
  // },
  // {
  //     path: '/teams/room/:room_id',
  //     name: 'RoomDetail',
  //     component: RoomDetail,
  // },

  // {
  //   path: '/teams/meeting/:id',
  //   name: 'Meeting',
  //   component: Meeting,
  // },
  {
    path: '/register/registration-confirm/:token',
    name: 'ConfirmEmailRegistration',
    component: ConfirmEmailRegistration,
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
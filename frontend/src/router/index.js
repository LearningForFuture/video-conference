import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import ClassRoom from '../views/ClassRoom.vue';
import CallVideo from '../views/CallVideo.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/classroom',
    name: 'ClassRoom',
    component: ClassRoom,
    // children: [
    //   {
    //     path: 'groups/:id',
    //     component: CallVideo,
    //   }
    // ]
  },
  {
    path: '/groups/:id',
    name: 'groups',
    component: CallVideo,
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
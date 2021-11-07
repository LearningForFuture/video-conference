import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Teams from '../views/Teams.vue';
import Meeting from '../views/Meeting.vue';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/teams',
        name: 'Teams',
        component: Teams,
    // children: [
    //   {
    //     path: 'meeting/:id',
    //     name: 'Meeting',
    //     component: Meeting,
    //   }
    // ]
    },
    {
        path: '/teams/meeting/:id',
        name: 'Meeting',
        component: Meeting,
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});

export default router;
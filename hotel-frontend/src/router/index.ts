import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/myhotel/',
    },
    {
        path: '/myhotel/',
        component: () => import("@/views/HomePage.vue"),
    },
    {
        path: '/myhotel/allrooms',
        component: () => import("@/views/AllRoomsPage.vue"),
    },
    {
        path: '/myhotel/:typetitle/rooms',
        component: () => import("@/views/RoomsByTypePage.vue"),
    },
    {
        path: '/myhotel/:typetitle/rooms/:title',
        component: () => import("@/views/OneRoomPage.vue"),
    },
    {
        path: '/auth/register',
        component: () => import("@/views/Auth/RegPage.vue"),
    },
    {
        path: '/auth/authenticate',
        component: () => import("@/views/Auth/LoginPage.vue"),
    },
    {
        path: '/reviews/',
        component: () => import("@/views/Review/ReviewPage.vue")
    },
    {
        path: '/profile/',
        component: () => import("@/views/Profile/MainProfilePage.vue")
    },
    {
        path: '/profile/booking',
        component: () => import("@/views/Profile/UserBookingPage.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        return { left: 0, top: 0, behavior: "smooth" };
    }
});

export default router;

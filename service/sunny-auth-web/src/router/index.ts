import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/layout/Home.vue'
import Login from '@v/Login.vue'
import util from "@/util";

const routes = [
    {
        path: '/',
        component: Home,
        beforeEnter(to: any, from: any, next: any) {
            let token = util.auth.getToken()
            !token && next({path: 'login'})
            next()
        }
    },
    {
        path: '/login',
        component: Login
    },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router

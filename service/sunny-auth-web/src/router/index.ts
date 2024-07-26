import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/layout/Home.vue'
import Login from '@v/Login.vue'
import util from "@g/util";

const routes = [
    {
        path: '/',
        component: Home,
        children: [
            {
                name: "Dashboard",
                path: "dashboard",
                component: () => import('@v/Dashboard.vue')
            },
        ],
        beforeEnter(to: any, from: any, next: any) {
            let token = util.auth.getToken()
            if (!token) {
                next({path: 'login'})
            } else {
                next()
            }
        }
    },
    {
        path: '/login',
        name: "login",
        component: Login
    },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router

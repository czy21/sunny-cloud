import {createRouter, createWebHistory} from 'vue-router'
import util from "@sunny-framework-js/util";

const routes = [
    {
        path: '/',
        component: () => import('@/layout/Home.vue'),
        children: [
            {
                name: "Dashboard",
                path: "dashboard",
                component: () => import('@v/Dashboard.vue')
            }
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
        name: "Login",
        path: '/login',
        component: () => import('@v/Login.vue')
    },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router

import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/layout/Home.vue'
import util from "@/util";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                name: "用户管理",
                path: "user",
                component: () => import('@v/user/index.vue')
            },
            {
                name: "菜单管理",
                path: "menu",
                component: () => import('@v/menu/index.vue')
            },
        ],
        beforeEnter: (to: any, from: any, next: any) => {
            const token = util.auth.getToken()
            if (token) {
                next()
            }
        }
    },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router

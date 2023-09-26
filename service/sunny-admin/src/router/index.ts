import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/layout/Home.vue'

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
        ]
    },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router

import {createRouter, createWebHistory} from 'vue-router'
import {util} from "@sunny-framework-js/core";
import {helper} from '@sunny-framework-js/vue';

const routes = [
    {
        path: '/',
        component: () => import('@/layout/Home.vue'),
        children: [
            {
                name: "Dashboard",
                path: "dashboard",
                component: () => import('@v/Dashboard.vue')
            },
            {
                path: "/sys/user",
                component: () => import('@v/user/index.vue')
            },
            {
                path: "/sys/menu",
                component: () => import('@v/menu/index.vue')
            },
            {
                path: "/sys/table/dynamic-el-table",
                component: () => import('@v/table/dynamic-el-table.vue')
            },
            {
                path: "/sys/table/dynamic-virtual-table",
                component: () => import('@v/table/dynamic-virtual-table.vue')
            },
            {
                path: "/sys/table/dynamic-vxe-table",
                component: () => import('@v/table/dynamic-vxe-table.vue')
            },
            {
                path: "/sys/table/dynamic-naive-table",
                component: () => import('@v/table/dynamic-naive-table.vue')
            }
        ],
        beforeEnter: (to: any, from: any, next: any) => {
            helper.api.checkVersion()
            
            if (import.meta.env.DEV) {
                util.cookie.setToken(import.meta.env.VITE_AUTHORIZATION)
            }
            const token = util.cookie.getToken()
            if (!token) {
                helper.api.get("auth/login-uri").then((t: any) => {
                    window.location.href = t.data.data + "?redirectUri=" + window.location
                })
            } else {
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

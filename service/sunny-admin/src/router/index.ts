import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/layout/Home.vue'
import util from "@sunny-framework-js/util";
import {api} from '@sunny-framework-js/vue';

const routes = [
    {
        path: '/',
        component: Home,
        children: [
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
                path: "/sys/table/dynamic-vxe-table",
                component: () => import('@v/table/dynamic-vxe-table.vue')
            }
        ],
        beforeEnter: (to: any, from: any, next: any) => {
            const token = util.auth.getToken()
            if (!token) {
                console.log(window.location)
                api.get("auth/login-uri").then((t: any) => {
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

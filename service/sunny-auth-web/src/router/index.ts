import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/layout/Home.vue'
import Login from '@v/Login.vue'
import util from "@sunny-framework-js/util";

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
            {
                name: "Test",
                path: "test-component",
                component: () => import('@v/TestComponent.vue')
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
        beforeEnter(to: any, from: any, next: any) {
            let token = util.auth.getToken()
            if (!token) {
                next({path: 'login'})
            } else {

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

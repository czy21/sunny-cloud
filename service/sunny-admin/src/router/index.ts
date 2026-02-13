import {createRouter, createWebHashHistory} from 'vue-router'
import {useUserStore} from "@/store";
import {util} from "@sunny-framework-js/core";
import menus from '@/menu'

const routes = [
    {
        name: 'Home',
        path: '/',
        component: () => import('@/layout/Home.vue'),
        redirect: '/dashboard',
        children: [
            ...util.tree.flatten({children: menus}).map(t => {
                const extra = t.extra instanceof String ? JSON.parse(t.extra || '{}') : t.extra;
                return {
                    name: t.code,
                    path: t.path.startsWith('/') ? t.path.slice(1) : t.path,
                    meta: {title: t.name, ...extra?.meta},
                    component: t.component,
                }
            }),
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router

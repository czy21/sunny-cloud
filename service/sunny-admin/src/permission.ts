import {util} from "@sunny-framework-js/core";
import helper from '@h';
import {useTabStore, useUserStore} from "@/store";
import router from '@/router'
import {ElMessage} from "element-plus";

const LOGIN_FLAG = "SSO_REDIRECTING"

router.beforeEach(async (to: any, from: any, next: any) => {
    helper.api.checkVersion()
    const tabStore = useTabStore()
    const userStore = useUserStore()

    if (import.meta.env.DEV) {
        util.cookie.setToken(import.meta.env.VITE_AUTHORIZATION)
    }

    const token = util.cookie.getToken()

    if (!token) {

        if (sessionStorage.getItem(LOGIN_FLAG)) {
            return next(false)
        }

        try {
            sessionStorage.setItem(LOGIN_FLAG, "1")

            const res = await helper.api.get("auth/login-uri")

            if (!res?.data?.data) {
                throw new Error("登录地址为空")
            }
            const loginUrl = res.data.data
            window.location.href = loginUrl + "?redirectUri=" + encodeURIComponent(window.location.href)
            return
        } catch (e) {
            sessionStorage.removeItem(LOGIN_FLAG)
            ElMessage.error("登录服务暂时不可用，请刷新页面重试")
            return next()
        }
    }

    sessionStorage.removeItem(LOGIN_FLAG)

    if (!userStore.profile.loaded) {
        const views = import.meta.glob('@/view/**/*.vue')
        await userStore.getProfile()
        const menus = util.tree.flatten({children: userStore.profile.menus})
        menus.filter(t => util.object.isNotEmpty(t.component))
            .forEach(t => {
                const extra = t.extra instanceof String ? JSON.parse(t.extra || '{}') : t.extra;
                router.addRoute('Home', {
                    name: t.code,
                    path: t.path.startsWith('/') ? t.path.slice(1) : t.path,
                    meta: {title: t.name, ...extra?.meta},
                    component: views[`/src/view/${t.component}`],
                })
            })
        router.addRoute({
            path: '/404',
            component: () => import("@v/error/404.vue"),
            name: '404'
        })
        router.addRoute({
            path: '/:path(.*)',
            redirect: '/404'
        })
        return next({...to, replace: true})
    }
    next()
})
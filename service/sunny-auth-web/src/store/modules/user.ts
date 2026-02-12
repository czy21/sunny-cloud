import helper from '@h'
import {defineStore} from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        profile: {},
        changePasswordShow: false
    }),
    actions: {
        async getProfile() {
            const res = await helper.api.get('user/profile')
            this.profile = {...res.data.data, ...{loaded: true}}
            this.profile['menus'] = [
                {
                    code: 'Dashboard',
                    name: '首页',
                    path: '/dashboard',
                    component: 'dashboard.vue',
                    type: 'MENU',
                    sort: 0,
                    extra: {
                        meta: {
                            affix: true
                        }
                    }
                }, ...(this.profile.menus)
            ]
        },
        async logout(redirectFunc?: () => void) {
            helper.api.get("logout").then(() => {
                localStorage.removeItem("isLoggedIn")
                redirectFunc ? redirectFunc() : window.location.reload();
            })
        }
    }
})
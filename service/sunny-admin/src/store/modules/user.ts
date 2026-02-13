import helper from '@h'
import {defineStore} from 'pinia'
import {DynamicMenus} from '@/menu'

export const useUserStore = defineStore('user', {
    state: () => ({
        profile: {},
        changePasswordShow: false
    }),
    actions: {
        async getProfile() {
            this.profile['loaded'] = true
            this.profile['menus'] = DynamicMenus
        },
        async logout(redirectFunc?: () => void) {
            helper.api.get("logout").then(() => {
                localStorage.removeItem("isLoggedIn")
                redirectFunc ? redirectFunc() : window.location.reload();
            })
        }
    }
})
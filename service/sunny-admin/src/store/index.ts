import Vuex from 'vuex'
import app from '@/store/modules/app'
import getters from "@/store/getters";

export default new Vuex.Store({
    modules: {
        app
    },
    getters
})

import Vuex from 'vuex'
import {store} from '@sunny-framework-js/vue'

const {app, getters} = store

export default new Vuex.Store({
    modules: {
        app
    },
    getters
})

import {createApp} from '@sunny-framework-js/vue'
import router from "@/router";
import store from "@/store"

const app = createApp()
app.use(router)
app.use(store)
app.mount('#app')
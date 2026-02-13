import {createApp} from '@sunny-framework-js/vue'
import router from "@/router";
import './permission'

const app = createApp()
app.use(router)
app.mount('#app')
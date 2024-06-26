import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from "@/router";
import store from "@/store"
import util from "@/util"

const app = createApp(App)
app.use(ElementPlus, {size: 'mini'} as any)
app.use(router)
app.use(store)
app.mount('#app')
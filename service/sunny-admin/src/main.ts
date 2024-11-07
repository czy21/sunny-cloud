import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from "@/router";
import store from "@/store"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import VxeUITable from 'vxe-table'
import 'vxe-table/lib/style.css'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus, {size: 'mini'} as any)
app.use(router)
app.use(store)
app.use(VxeUITable)
app.mount('#app')

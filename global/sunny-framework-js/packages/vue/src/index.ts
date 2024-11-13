import * as api from "./api";
import * as eui from "./eui";

export {
    api,
    eui
}

import DynamicElColumn from './table'
import {App} from "vue";

// 所有组件列表
const components = [DynamicElColumn]

// 定义 install 方法， App 作为参数
const install = (app: App): void => {
    // 遍历注册所有组件
    components.map((component) => app.component(component.name, component))
}

export {
    DynamicElColumn
}

export default {
    install
}
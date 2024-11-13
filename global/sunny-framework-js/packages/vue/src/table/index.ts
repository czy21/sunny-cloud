import {App} from 'vue'

import DynamicElColumn from './src/DynamicElColumn.vue'

DynamicElColumn.install = (app: App) => {
    app.component(DynamicElColumn.name, DynamicElColumn)
}

export default DynamicElColumn
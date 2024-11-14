import type {App} from "vue";
import DynamicElColumn from './src/DynamicElColumn.vue'
import DynamicElTable from './src/DynamicElTable.vue'
import DynamicVxeColumn from "./src/DynamicVxeColumn.vue";
import DynamicVxeTable from './src/DynamicVxeTable.vue'

const components = [DynamicElColumn, DynamicElTable, DynamicVxeColumn, DynamicVxeTable]

components.forEach(t => {
    t.install = (app: App) => app.component(t.name, t)
})

export {DynamicElColumn, DynamicElTable, DynamicVxeColumn, DynamicVxeTable}
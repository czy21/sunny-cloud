import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path, {resolve} from 'path'

const alias = {
    "@": "src",
    "@v": "src/view",
}
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    build: {
        outDir: "build"
    },
    resolve: {
        alias: Object.keys(alias).reduce((p, c) => ({...p, [c]: resolve(alias[c])}), {})
    }
})

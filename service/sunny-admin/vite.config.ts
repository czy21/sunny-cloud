import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'

const alias = {
    "@": "src",
    "@v": "src/view",
    "@g": "../../global/sunny-framework-js"
}
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    build: {
        outDir: "build"
    },
    resolve: {
        alias: Object.keys(alias).reduce((p, c) => ({...p, [c]: resolve(alias[c])}), {})
    },
    server: {
        host: '0.0.0.0',
        port: 5174,
        proxy: {
            '/api': {
                target: 'http://sunny-gateway.czy21-internal.com/',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
        }
    }
})

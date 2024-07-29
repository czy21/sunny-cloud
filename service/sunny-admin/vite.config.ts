import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'

const alias = {
    "@": "src",
    "@v": "src/view"
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
            '/api/sys': {
                target: 'http://127.0.0.1:8082/',
                changeOrigin: true,
                rewrite: (path) => {
                    return path.replace(/^\/api\/sys/, '')
                },
            },
            '/api': {
                target: 'http://sunny-gateway.czy21-internal.com/',
                changeOrigin: true,
                rewrite: (path) => {
                    return path.replace(/^\/api/, '')
                },
            },
        }
    }
})

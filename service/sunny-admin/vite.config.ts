import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import {resolveFrameworkImporter} from "../../global/sunny-framework-js/plugin"

const alias = {
    "@": "src",
    "@v": "src/view",
    "@c": "src/component"
}

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        resolveFrameworkImporter(__dirname)
    ],
    build: {
        outDir: "build",
        rollupOptions: {
            output: {
                manualChunks(id) {
                    if (/node_modules|sunny-framework-js/.test(id)) {
                        return "vendor";
                    }
                },
            },
        }
    },
    resolve: {
        alias: Object.keys(alias).reduce((p, c) => ({...p, [c]: fileURLToPath(new URL(alias[c], import.meta.url))}), {})
    },
    css: {
        preprocessorOptions: {
            scss: {api: 'modern-compiler'},
        }
    },
    server: {
        host: '0.0.0.0',
        port: 5174,
        proxy: {
            // '/api/sys': {
            //     target: 'http://127.0.0.1:8082/',
            //     changeOrigin: true,
            //     rewrite: (path) => {
            //         return path.replace(/^\/api\/sys/, '')
            //     },
            // },
            // '/api': {
            //     target: 'http://sunny-gateway.czy21-internal.com/',
            //     changeOrigin: true,
            //     rewrite: (path) => {
            //         return path.replace(/^\/api/, '')
            //     },
            // },
            '/api': {
                target: 'https://sunny-cloud.czy21.com',
                changeOrigin: true,
            },
        }
    }
})

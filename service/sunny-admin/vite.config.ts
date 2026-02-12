import fs from "fs";
import path from "path";
import {fileURLToPath, URL} from 'node:url'
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import {generateVersion, resolveFrameworkImporter} from "../../framework/sunny-framework-js/plugin"

const alias = {
    "@": "src",
    "@h": "src/helper",
    "@v": "src/view",
    "@c": "src/component"
}

// const version_number = fs.readFileSync(path.join(__dirname, "../../version"), {encoding: "utf-8"})

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        resolveFrameworkImporter(__dirname),
        // generateVersion(__dirname, "build", {"version": version_number})
    ],
    build: {
        outDir: "build",
        rollupOptions: {
            output: {
                manualChunks: (id) => /node_modules|sunny-framework-js/.test(id) ? "vendor" : null
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

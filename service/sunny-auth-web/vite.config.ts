import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

const alias = {
    "@": "src",
    "@v": "src/view"
}
const resolveFrameworkImporter = () => {
    return {
        name: 'transform-framework-importer',
        async resolveId(source, importer, options) {
            if (/sunny-framework-js/.test(importer)) {
                const resolution = await this.resolve(fileURLToPath(new URL(`./node_modules/${source}`, import.meta.url)), importer, options);
                return resolution.id;
            }
        }
    }
}

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        resolveFrameworkImporter()
    ],
    build: {
        outDir: "build"
    },
    resolve: {
        alias: Object.keys(alias).reduce((p, c) => ({ ...p, [c]: fileURLToPath(new URL(alias[c], import.meta.url)) }), {})
    },
    css: {
        preprocessorOptions: {
            scss: { api: 'modern-compiler' },
        }
    },
    server: {
        host: '0.0.0.0',
        port: 5173,
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:8081',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api\/auth/, ''),
            },
        }
    }
})

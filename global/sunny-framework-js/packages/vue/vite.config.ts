import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import dts from 'vite-plugin-dts'
import {readFileSync} from "fs";

const externalPackages = ["../util/package.json", "./package.json"]

let external = externalPackages.flatMap(t => {
    let pkg = JSON.parse(readFileSync(new URL(t, import.meta.url), 'utf8'))
    return Object.keys(pkg.dependencies || {}).concat(Object.keys(pkg.peerDependencies || {})).concat([pkg.name])
})

console.log(external)

// https://vite.dev/config/
export default defineConfig({
    build: {
        target: "modules",
        manifest: true,
        minify: false,
        lib: {
            entry: ['src/index.ts']
        },
        rollupOptions: {
            external: external,
            output: [
                {
                    globals: {
                        vue: 'Vue',
                    },
                    preserveModules: true,
                    preserveModulesRoot: "src",
                    exports: "named",
                    format: "cjs",
                    dir: "dist/lib",
                    entryFileNames: '[name].js',
                },
                {
                    globals: {
                        vue: 'Vue',
                    },
                    preserveModules: true,
                    preserveModulesRoot: "src",
                    exports: "named",
                    format: "esm",
                    dir: "dist/es",
                    entryFileNames: '[name].js',
                }
            ],
        },
    },
    plugins: [
        vue(),
        dts({
            outDir: "dist/lib"
        }),
        dts({
            outDir: "dist/es"
        }),
        vueJsx()
    ]
})

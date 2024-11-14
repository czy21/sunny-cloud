import path from 'path'
import {readFileSync} from 'fs';
import {UserConfig} from "vite";
import {OutputOptions} from 'rollup'
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import dts from 'vite-plugin-dts'

/**
 * Create a base vite config
 * @param {Record<string,any>} pkg Imported package.json
 * @param {string[]} external Imported package.json
 * @param {import('vite').PluginOption[]} plugins Imported package.json
 * @returns {UserConfig}
 */
export const createConfig = ({pkg, external = [], plugins = []}): UserConfig => {

    if (pkg.name !== '@sunny-framework-js/util') {
        let util_pkg = JSON.parse(readFileSync(new URL('../packages/util/package.json', import.meta.url), 'utf8'))
        external.push(util_pkg.name)
        external = external.concat(Object.keys(util_pkg.dependencies || {})).concat(Object.keys(util_pkg.peerDependencies || {}))
    }
    external = external.concat(Object.keys(pkg.dependencies || {}).concat(Object.keys(pkg.peerDependencies || {})))
    external.push("vue")
    external.push("element-plus/es/utils")

    external = Array.from(new Set(external))
    console.log(`external: ${external}`)

    let outputOptions: OutputOptions[] = [
        {
            "format": "cjs",
            "dir": path.resolve(pkg.main, '..'),
        },
        {
            "format": "esm",
            "dir": path.resolve(pkg.module, '..')
        }
    ]

    return {
        build: {
            target: "modules",
            sourcemap: true,
            minify: false,
            lib: {
                entry: ['src/index.ts']
            },
            rollupOptions: {
                external: external,
                output: outputOptions.map(t => {
                    return {
                        ...t,
                        globals: {
                            vue: 'Vue',
                        },
                        preserveModules: true,
                        preserveModulesRoot: "src",
                        exports: "named",
                        entryFileNames: '[name].js'
                    }
                })
            },
        },
        plugins: [
            vue({

            }),
            vueJsx({

            }),
            dts({
                outDir: outputOptions.map(t => t.dir)
            })
        ]
    }
}
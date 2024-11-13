import path from 'path'
import {readFileSync} from 'fs';
import {builtinModules} from 'module';
import typescript from '@rollup/plugin-typescript';

/**
 * Create a base rollup config
 * @param {Record<string,any>} pkg Imported package.json
 * @param {string[]} external Imported package.json
 * @returns {import('rollup').RollupOptions || import('rollup').RollupOptions[]}
 */
export function createConfig({pkg, external = []}) {

    if (pkg.name !== '@sunny-framework-js/util') {
        let util_pkg = JSON.parse(readFileSync(new URL('../packages/util/package.json', import.meta.url), 'utf8'))
        external.push(util_pkg.name)
        external = external.concat(Object.keys(util_pkg.dependencies || {})).concat(Object.keys(util_pkg.peerDependencies || {}))
    }

    let builds = [
        {
            "format": "cjs",
            "dir": path.resolve(pkg.main, '..'),
        },
        {
            "format": "es",
            "dir": path.resolve(pkg.module, '..')
        }
    ]

    return builds.map(t => {
        return {
            input: 'src/index.ts',
            external: Object.keys(pkg.dependencies || {}).concat(Object.keys(pkg.peerDependencies || {})).concat(external),
            onwarn: (warning) => {
                throw Object.assign(new Error(), warning);
            },
            output: [
                {
                    ...t,
                    sourcemap: true,
                    preserveModules: true,
                    preserveModulesRoot: "src",
                    exports: "named",
                }
            ],
            plugins: [typescript({sourceMap: true, declaration: true, declarationDir: t.dir})]
        }
    })
}
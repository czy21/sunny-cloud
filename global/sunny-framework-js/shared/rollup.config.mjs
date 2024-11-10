import path from 'path'
import { builtinModules } from 'module';
import typescript from '@rollup/plugin-typescript';

/**
 * Create a base rollup config
 * @param {Record<string,any>} pkg Imported package.json
 * @param {string[]} external Imported package.json
 * @returns {import('rollup').RollupOptions}
 */
export function createConfig({ pkg, external = [] }) {
  external.push(/node_modules/)
  if (pkg.name !== '@sunny-framework-js/util'){
    external.push("@sunny-framework-js/util")
    external.push("axios")
    external.push("lodash")
  }
  return {
    input: 'src/index.ts',
    external: Object.keys(pkg.dependencies || {})
      .concat(Object.keys(pkg.peerDependencies || {}))
      .concat(builtinModules)
      .concat(external),
    onwarn: (warning) => {
      throw Object.assign(new Error(), warning);
    },
    output: [
      {
        format: 'cjs',
        dir: path.resolve(pkg.main,'..'),
        sourcemap: true,
        preserveModules: true,
        preserveModulesRoot: "src",
        exports: "named"
      },
      {
        format: 'es',
        dir: path.resolve(pkg.module,'..'),
        sourcemap: true,
        preserveModules: true,
        preserveModulesRoot: "src",
        exports: "named"
      }
    ],
    plugins: [typescript({ sourceMap: true })]
  };
}
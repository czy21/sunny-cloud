import {fileURLToPath, URL} from 'node:url'

const resolveFrameworkImporter = () => {
    return {
        name: 'resolve-framework-importer',
        async resolveId(source, importer, options) {
            if (/sunny-framework-js/.test(importer)) {
                const resolution = await this.resolve(fileURLToPath(new URL(`./node_modules/${source}`, import.meta.url)), importer, options);
                return resolution.id;
            }
        }
    }
}

export {resolveFrameworkImporter}
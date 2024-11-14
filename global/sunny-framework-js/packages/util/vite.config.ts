import {defineConfig} from 'vite'
import {readFileSync} from 'fs';

import {createConfig} from '../../shared/vite.config';

export default defineConfig(createConfig({
    pkg: JSON.parse(readFileSync(new URL('./package.json', import.meta.url), 'utf8'))
}))
import { defineConfig } from 'vite'
import { readFileSync } from 'fs';
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

import { createConfig } from '../../shared/vite.config';

export default defineConfig(createConfig({
    pkg: JSON.parse(readFileSync(new URL('./package.json', import.meta.url), 'utf8')),
    output: {
        globals: {
            vue: 'Vue',
        }
    },
    plugins: [vue(), vueJsx()]
}))
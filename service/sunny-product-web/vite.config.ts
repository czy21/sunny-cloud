import {fileURLToPath, URL} from 'node:url'
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import {resolveFrameworkImporter} from "../../framework/sunny-framework-js/plugin"

const alias = {
  "@": "src",
  "@v": "src/view",
  "@c": "src/component"
}

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
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
})

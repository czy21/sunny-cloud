<template>
  <el-container>
    <Aside :menus="menus">
      <template #logo>
        <div style="text-align: center;color: var(--el-color-white)"></div>
      </template>
    </Aside>
    <el-container direction="vertical">
      <Header/>
      <Tab/>
      <el-main :style="{backgroundColor: 'var(--el-bg-color-page)'}">
        <router-view :key="route.fullPath"/>
      </el-main>
    </el-container>
  </el-container>
</template>
<script lang="ts" setup>
import {computed} from 'vue'
import {Aside, Tab} from '@sunny-framework-js/vue'
import Header from './Header.vue'
import {useUserStore} from '@/store'
import {useRoute} from "vue-router";
import menu from '@/menu'

const route = useRoute()
const menus = computed(() => [
  ...menu,
  ...(useUserStore().profile.menus ?? [])
])
</script>
<template>
  <div>
    <template v-for="t in menuTree">
      <el-sub-menu v-if="t.children" :data="t" :index="t.name">
        <template #title>
          <i :class="t.icon" :style="{fontSize:iconSize}"></i>
          <span slot="title" v-if="!collapse"> {{ t.name }}</span>
        </template>
        <NavMenu :menuTree="t.children"/>
      </el-sub-menu>
      <el-menu-item v-if="!t.children" :data="t" :index="t.path" :route="t.path">
        <template #title>
          <i :class="t.icon" :style="{fontSize:iconSize}"></i>
          <span slot="title">{{ t.name }}</span>
        </template>
      </el-menu-item>
    </template>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {MenuModel} from '@/menu'

export default defineComponent({
  props: {
    menuTree: {type: Array<MenuModel>},
    collapse: {
      type: Boolean, default() {
        return false
      }
    },
    iconSize: {
      type: String, default() {
        return '16px'
      }
    }
  }
})
</script>
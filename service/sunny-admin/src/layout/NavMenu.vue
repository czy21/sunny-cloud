<template>
  <div>
    <template v-for="t in menuTree">
      <el-sub-menu v-if="t.children" :key="t.id" :data="t" :index="t.name">
        <template #title>
          <span slot="title" v-if="!collapse"> {{ t.name }}</span>
        </template>
        <NavMenu :menuTree="t.children"/>
      </el-sub-menu>
      <el-menu-item v-if="!t.children" :key="t.id" :data="t" :index="t.path" :route="t.path">
        <template #title>
          <span slot="title">{{ t.name }}</span>
        </template>
      </el-menu-item>
    </template>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  props: {
    menuTree: {type: Object},
    collapse: {type: Boolean},
    iconSize: {type: String}
  }
})
</script>
<template>
  <el-table-column :label="columnItem.desc" align="center">
    <template v-for="t in columnItem.children">
      <dynamic-column v-if="t.children" :column-item="t"/>
      <el-table-column v-else :label="t.desc" :prop="t.name"/>
    </template>
  </el-table-column>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  props: {
    columnItem: {type: Object}
  }
})
</script>
<script setup lang="ts">
</script>
<template>
  <el-table :data="props.data" ref="tableRef">
    <dynamic-column :node="t" v-for="t in props.columns">
      <template #default="{columnName,column,scope}">
        <slot :name="columnName" :column="column" :scope="scope" :row="scope.row" :rowIndex="scope.$index"/>
      </template>
    </dynamic-column>
  </el-table>
</template>

<script lang="ts" setup>
import {defineProps, ref, defineExpose} from "vue"
import DynamicColumn from "./DynamicColumn.vue"

const props = defineProps(["columns", "data"])

const tableRef = ref(null)

defineExpose({
  tableRef
})
</script>
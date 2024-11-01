<template>
  <el-table :data="props.data" ref="tableRef">
    <template v-for="t in props.columns">
      <dynamic-column :node="t">
        <template #default="{columnName,column,scope}">
          <slot :name="columnName" :column="column" :scope="scope" :row="scope.row" :rowIndex="scope.$index"/>
        </template>
      </dynamic-column>
    </template>
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
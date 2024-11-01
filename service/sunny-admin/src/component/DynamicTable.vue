<template>
  <el-table fix="true" :data="props.data" ref="tableRef" @cell-click="handleCell">
    <dynamic-column :node="t" v-for="t in props.columns">
      <template #default="{columnName,column,scope}">
        <slot :name="columnName" :column="column" :scope="scope" :row="scope.row" :rowIndex="scope.$index"/>
      </template>
    </dynamic-column>
  </el-table>
</template>

<script lang="ts" setup>
import {ref, defineProps, defineExpose} from "vue"
import DynamicColumn from "./DynamicColumn.vue"

const props = defineProps(["columns", "data"])

const tableRef = ref(null)

const handleCell = (row: any, column: any) => {
  row[`${column.property}_editable`] = true
}

defineExpose({
  tableRef
})
</script>
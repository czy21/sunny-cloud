<template>

  <n-data-table ref="tableRef"
                :columns="columns"
                :data="props.data"
                :max-height="500"
                :scroll-x="1800"
                virtual-scroll
                :single-line="false"
                size="small"
                striped
                :render-cell="(value, rowData, column)=>{console.log(column);return value}"
  >

  </n-data-table>
</template>

<script setup lang="tsx">
import {FunctionalComponent, ref, computed} from "vue"
import DynamicVxeColumn from "./DynamicVxeColumn.vue"
import util from '@sunny-framework-js/util'
import {TableProps, TableEmits} from "./DynamicTable.ts";
import {NDataTable} from "naive-ui";
import {ElButton, ElDatePicker, ElInput, ElInputNumber, ElOption, ElSelect} from "element-plus";
import {it} from "node:test";
import _ from "lodash";

const props = withDefaults(defineProps<TableProps>(), {
  defaultRowValue() {
    return {}
  },
  columns: () => [],
  data: () => [],
  dict() {
    return {}
  },
  rules() {
    return {}
  },
  subTotal() {
    return []
  },
  editable() {
    return false
  },
  showSummary() {
    return true
  },
  showAddRow() {
    return true
  }
})

function recursiveColumn(node: any) {
  const newNode: any = {
    key: node.prop,
    title: node.name,
    width: node.style?.width || node.width || 150,
    titleAlign: "center",
    params: _.omit(node, ["children"])
  };
  for (let child of (node.children || [])) {
    newNode.children = newNode.children || []
    newNode.children.push(recursiveColumn(child));
  }
  return newNode;
}

let columns = computed(() => recursiveColumn({children: props.columns}).children)

</script>

<style scoped lang="scss">

</style>
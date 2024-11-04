<template>
  <el-table-column :label="props.node.desc" :prop="props.node.name" header-align="center" v-if="props.node.children && props.node.children.length > 0">
    <dynamic-column :node="child" v-for="child in props.node.children">
      <template #default="scope">
        <slot :columnName="scope.columnName" :scope="scope.scope"/>
      </template>
    </dynamic-column>
  </el-table-column>
  <el-table-column :label="props.node.desc" :prop="props.node.name" header-align="center" v-else>
    <template #header="scope">
      {{ renderHeader(props.node, scope) }}
    </template>
    <template #default="scope">
      <slot :columnName="props.node.name" :scope="scope"/>
    </template>
  </el-table-column>
</template>

<script setup lang="tsx">
import {defineProps} from "vue"
import {ElTableColumn} from "element-plus";

const props = defineProps(["node"])

const renderHeader = (node, scope) => {
  scope.column.node = node
  return scope.column.label
}
</script>
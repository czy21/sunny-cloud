<template>
  <vxe-colgroup :title="props.node.name" :field="props.node.prop" header-align="center" v-if="props.node.children && props.node.children.length > 0">
    <template #header="scope">
      {{ renderHeader(props.node, scope) }}
    </template>
    <dynamic-vxe-column :node="child" v-for="child in props.node.children">
      <template #default="scope">
        <slot :prop="scope.prop" :scope="scope.scope"/>
      </template>
    </dynamic-vxe-column>
  </vxe-colgroup>
  <vxe-column :title="props.node.name" :field="props.node.prop" header-align="center" :fixed="props.node.fixed" :width="props.node.style?.width||150" v-else>
    <template #header="scope">
      {{ renderHeader(props.node, scope) }}
    </template>
    <template #default="scope">
      <slot :prop="props.node.prop" :scope="scope"/>
    </template>
  </vxe-column>
</template>

<script setup lang="tsx">
import {TableColumn} from "./DynamicTable";

const props = defineProps(["node"])

const renderHeader = (node: TableColumn, scope: any) => {
  scope.column.node = node
  return scope.column.title
}
</script>
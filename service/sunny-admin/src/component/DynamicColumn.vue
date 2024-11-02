<template>
  <el-table-column :label="props.node.desc" :prop="props.node.name" align="center" v-if="props.node.children && props.node.children.length > 0">
    <dynamic-column :node="child" v-for="child in props.node.children">
      <template #default="scope">
        <slot :columnName="scope.columnName" :column="scope.column" :scope="scope.scope"/>
      </template>
    </dynamic-column>
  </el-table-column>
  <el-table-column :label="props.node.desc" :prop="props.node.name" align="center" v-else>
    <template #default="scope">
      <slot :columnName="props.node.name" :column="node" :scope="scope" v-if="props.node.custom"/>
      <el-input ref="editRef" v-model="scope.row[props.node.name]" @blur="onExitEditMode(scope)" v-else-if="isInputString(scope)"/>
      <el-input ref="editRef" v-model="scope.row[props.node.name]" type="number" @blur="onExitEditMode(scope)" v-else-if="isInputNumber(scope)"/>
    </template>
  </el-table-column>
</template>

<script setup lang="tsx">
import {ref, defineProps} from "vue"

const props = defineProps(["node"])

const editRef = ref(null)

const isEditable = (scope) => {
  return props.node.editable && scope.row[`${props.node.name}_editable`]
}

const isInputString = (scope) => {
  let val = (props.node.type === 'string' || !props.node.type) && isEditable(scope)
  if (val) {
    editRef.value?.focus()
  }
  return val
}

const isInputNumber = (scope) => {
  let val = props.node.type === 'number' && isEditable(scope)
  if (val) {
    editRef.value?.focus()
  }
  return val
}

const onExitEditMode = (scope) => (scope.row[`${props.node.name}_editable`] = null)

</script>
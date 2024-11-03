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
      <edit-cell :scope="scope" v-else-if="isEdit(scope)"/>
    </template>
  </el-table-column>
</template>

<script setup lang="tsx">
import {ref, defineProps, FunctionalComponent} from "vue"
import {ElInput} from "element-plus";

const props = defineProps(["node"])

const editRef = ref()

const isEdit = (scope) => props.node.editable && scope.row[`${props.node.name}_editable`]

const onExitEditMode = (scope) => delete scope.row[`${props.node.name}_editable`]

const EditCell: FunctionalComponent = ({scope}) => {
  let instance = <ElInput ref={editRef} modelValue={scope.row[props.node.name]} onBlur={() => onExitEditMode(scope)} onInput={(value) => scope.row[props.node.name] = value}/>
  editRef.value?.focus()
  if (props.node.type === 'number') {
    instance.props.type = "number"
  }
  return instance
}

</script>
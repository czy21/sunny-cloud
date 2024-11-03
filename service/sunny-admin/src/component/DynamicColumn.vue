<template>
  <el-table-column :label="props.node.desc" :prop="props.node.name" align="center" v-if="props.node.children && props.node.children.length > 0">
    <dynamic-column :node="child" v-for="child in props.node.children" :dict="props.dict">
      <template #default="scope">
        <slot :columnName="scope.columnName" :column="scope.column" :scope="scope.scope"/>
      </template>
    </dynamic-column>
  </el-table-column>
  <el-table-column :label="props.node.desc" :prop="props.node.name" align="center" v-else>
    <template #default="scope">
      <slot :columnName="props.node.name" :column="node" :scope="scope" v-if="props.node.custom"/>
      <template v-else-if="isEdit(scope)">
        <el-input ref="editRef" v-model="scope.row[props.node.name]" @blur="onExitEditMode(scope)" v-if="isInputString(scope)"/>
        <el-input ref="editRef" v-model="scope.row[props.node.name]" @blur="onExitEditMode(scope)" :type="props.node.type" v-else-if="isInputNumber(scope)"/>
        <el-select ref="editRef" v-model="scope.row[props.node.name]" @blur="onExitEditMode(scope)" v-else-if="isSelect(scope)">
          <el-option v-for="t in (props.dict[props.node.name])" :label="t.label" :value="t.value"/>
        </el-select>
        <el-date-picker ref="editRef" v-model="scope.row[props.node.name]" @blur="onExitEditMode(scope)"
                        @visible-change="()=>{editRef.value?.blur()}"
                        :type="props.node.type"
                        size="default"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        v-else-if="isDate(scope)"/>
      </template>
      <show-cell :scope="scope" v-else/>
    </template>
  </el-table-column>
</template>

<script setup lang="tsx">
import {defineProps, FunctionalComponent, ref} from "vue"
import {ElDatePicker, ElInput, ElOption, ElSelect} from "element-plus";

const props = defineProps(["node", "dict"])

const editRef = ref()

const handleCellFocus = () => {
  editRef.value?.focus()
}

const isEdit = (scope) => props.node.editable && scope.row[`${props.node.name}_editable`]

const isInputString = (scope) => {
  let val = (props.node.type === 'string' || !props.node.type)
  if (val) {
    handleCellFocus()
  }
  return val
}

const isInputNumber = (scope) => {
  let val = props.node.type === 'number'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isSelect = (scope) => {
  let val = props.node.type === 'select'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isDate = (scope) => {
  return props.node.type === 'date' || props.node.type === 'datetime'
}

const onExitEditMode = (scope) => delete scope.row[`${props.node.name}_editable`]

const ShowCell: FunctionalComponent = ({scope}) => {
  let label = scope.row[props.node.name]
  if (props.node.dictKey && props.dict) {
    let value = props.dict[props.node.dictKey].find(t => t.value === scope.row[props.node.name])?.label
    if (value) {
      label = value
    }
  }
  return label
}

</script>
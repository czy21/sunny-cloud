<template>
  <el-table ref="tableRef" :data="props.data" @cell-click="handleCell" width="100%" height="100%">
    <dynamic-column :node="t" v-for="t in props.columns">
      <template #default="{columnName,scope}">
        <slot :name="columnName" :=scope v-if="scope.column.node.custom"/>
        <template v-else-if="isEdit(scope)">
          <el-input ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" v-if="isInputString(scope)"/>
          <el-input ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" :type="scope.column.node.type" v-else-if="isInputNumber(scope)"/>
          <el-select ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" v-else-if="isSelect(scope)">
            <el-option v-for="t in (props.dict[scope.column.property])" :label="t.label" :value="t.value"/>
          </el-select>
          <el-date-picker ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)"
                          :type="scope.column.node.type"
                          size="default"
                          value-format="YYYY-MM-DD HH:mm:ss"
                          v-else-if="isDate(scope)"/>
        </template>
        <span v-else-if="scope.column.property==='action'">
          <el-button @click="addRow(scope)" link type="primary" v-if="showAddRow(scope)">加行</el-button>
          <el-button @click="delRow(scope)" link type="danger">删除</el-button>
        </span>
        <show-cell :="scope" v-else/>
      </template>
    </dynamic-column>
  </el-table>
</template>

<script lang="tsx" setup>
import {ref, defineProps, defineExpose, FunctionalComponent} from "vue"
import DynamicColumn from "./DynamicColumn.vue"
import {ElTable, ElDatePicker, ElInput, ElOption, ElSelect, ElButton} from "element-plus";

const props = defineProps(["columns", "data", "dict"])

const tableRef = ref()
const editRef = ref()

const handleCellFocus = () => {
  if (editRef.value && editRef.value.length > 0) {
    editRef.value[editRef.value.length - 1].focus?.()
  }
}

const isEdit = (scope) => scope.column.node.editable && scope.row[`${scope.column.property}_editable`]

const isInputString = (scope) => {
  let val = (scope.column.node.type === 'string' || !scope.column.node.type)
  if (val) {
    handleCellFocus()
  }
  return val
}

const isInputNumber = (scope) => {
  let val = scope.column.node.type === 'number'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isSelect = (scope) => {
  let val = scope.column.node.type === 'select'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isDate = (scope) => {
  let val = scope.column.node.type === 'date' || scope.column.node.type === 'datetime'
  if (val) {
    handleCellFocus()
  }
  return val
}

const onExitEditMode = (scope) => {
  delete scope.row[`${scope.column.property}_editable`]
}

const ShowCell: FunctionalComponent = (scope) => {
  let label = scope.row[scope.column.property]
  if (scope.column.node.dictKey && props.dict) {
    let value = props.dict[scope.column.node.dictKey].find(t => t.value === scope.row[scope.column.property])?.label
    if (value) {
      label = value
    }
  }
  if (scope.column.node.type === 'index') {
    label = scope.row[scope.column.property] = scope.$index + 1
  }
  return label
}

const handleCell = (row: any, column: any) => {
  if (column.node.editable) {
    row[`${column.property}_editable`] = true
  }
}

const showAddRow = (scope) => {
  return scope.$index == props.data.length - 1
}

const addRow = (scope) => {
  props.data.splice(scope.$index + 1, 0, {})
}
const delRow = (scope) => {
  props.data.splice(scope.$index, 1)
}

const summaryMethod = (data: { columns: any[], data: any[] }) => {
  const sums = []
  const totalSummary = {}
  if (data.columns && data.columns.length > 0) {
    totalSummary[`${data.columns[0].property}`] = "合计"
    data.columns.forEach((c, ci) => {
      data.data.forEach((t, ti) => {
        if (c.node.colTotal) {
          console.log(t[c.property])
        }
      })
    })
    sums.push(totalSummary)
    sums.push(totalSummary)
  }
  return sums
}

defineExpose({
  tableRef
})
</script>
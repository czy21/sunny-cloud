<template>
  <vxe-table ref="tableRef"
             :data="props.data"
             :header-cell-style="headerCellStyle"
             @cell-click="handleCell"
             :edit-rules="props.rules"
             border
             width="100%"
             height="100%"
             size="mini"
             :column-config="{ resizable: true }"
             :scroll-x="{ enabled: true, gt: 0 }"
             :scroll-y="{ enabled: true, gt: 0 }"
             show-overflow
             show-footer
             :footer-method="summaryMethod"
             @scroll="handleScroll"
  >
    <dynamic-vxe-column :node="t" v-for="t in props.columns">
      <template #default="{ prop, scope }">
        <slot :name="prop" :=scope v-if="scope.column.node.custom"/>
        <template v-else-if="scope.row[`${scope.column.property}_editable`]">
          <el-input ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" @change="(value)=>props.handleInput(value,scope)" v-if="isInputString(scope)"/>
          <el-input ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" v-else-if="isInputNumber(scope)" @change="(value)=>props.handleInput(value,scope)" :type="scope.column.node.type"/>
          <el-select ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" v-else-if="isSelect(scope)" @change="(value) => handleSelect(value, scope)">
            <el-option v-for="t in props.dict[scope.column.node.dictKey]" :label="t.label" :value="t.value"/>
          </el-select>
          <el-date-picker ref="editRef" v-model="scope.row[scope.column.property]"
                          @blur="onExitEditMode(scope)"
                          :type="scope.column.node.type"
                          size="default"
                          :value-format="scope.column.node.format || 'YYYY-MM-DD HH:mm:ss'"
                          v-else-if="isDate(scope)"/>
        </template>
        <span v-else-if="props.editable && scope.column.property === 'action'">
          <el-button @click="addRow(scope)" link type="primary" v-if="showAddRow(scope)">加行</el-button>
          <el-button @click="delRow(scope)" link type="danger">删除</el-button>
        </span>
        <show-cell :="scope" v-else/>
      </template>
    </dynamic-vxe-column>
    <template #empty>
      <el-button @click="addRow" type="primary">加行</el-button>
    </template>
  </vxe-table>
</template>

<script lang="tsx" setup>
import {FunctionalComponent, ref} from "vue"
import DynamicVxeColumn from "./DynamicVxeColumn.vue"
import util from '@sunny-framework-js/util'
import {TableProps} from "./DynamicTable.ts";
import {VxeTable} from "vxe-table";
import {ElButton, ElDatePicker, ElInput, ElOption, ElSelect} from "element-plus";

const props = withDefaults(defineProps<TableProps>(), {
  columns: () => [],
  data: () => [],
  dict() {
    return {}
  },
  rules() {
    return {}
  },
  subTotal() {
    return {}
  },
  editable() {
    return false
  },
})

const tableRef = ref()
const editRef = ref()

const headerCellStyle = ({column}) => {
  return column.node?.style
}

const handleCellFocus = () => {
  editRef.value?.forEach((t, i, a) => i === a.length - 1 && t.focus?.())
}

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

const ShowCell: FunctionalComponent<any> = (scope) => {
  let label = scope.row[scope.column.property]
  if (scope.column.node.dictKey && props.dict) {
    let value = props.dict[scope.column.node.dictKey]?.find(t => t.value === scope.row[scope.column.property])?.label
    if (value) {
      label = value
    }
  }
  if (scope.column.node.type === 'index') {
    label = scope.row[scope.column.property] = scope.rowIndex + 1
  }
  return label
}

const handleCell = ({row, column}) => {
  if (props.editable && (column.node.editable == true || util.object.getValueByExpression(row, column.node.editable))) {
    row[`${column.property}_editable`] = true
  }
}

const handleSelect = (value: any, scope) => {
  let dictPush = scope.column.node.dictPush
  if (dictPush) {
    let dictValue = props.dict[scope.column.node.dictKey].find((t: any) => t.value === value)
    Object.entries(dictPush).forEach(([k, v]) => scope.row[k] = dictValue[v])
  }
}

const showAddRow = (scope) => {
  return scope.rowIndex == props.data.length - 1
}

const addRow = (scope) => {
  props.data.splice(scope?.rowIndex + 1, 0, {})
}

const delRow = (scope) => {
  props.data.splice(scope.rowIndex, 1)
}

const updateFooterEvent = () => {
  const $table = tableRef.value
  if ($table) {
    $table.updateFooter()
  }
}

const handleScroll = () => {
  editRef.value?.forEach(t => t.blur?.())
}

const summaryMethod = (data: { columns: any[], data: any[] }) => {
  const sums: any[] = []
  if (data.columns && data.columns.length > 0) {
    const totalSummary: any = {}
    totalSummary[data.columns[0].property] = "合计"
    data.columns.forEach(c => {
      data.data.forEach(t => {
        if (c.node?.rowTotal) {
          t[c.property] = Number(util.object.getValueByExpression(t, c.node.rowTotal) || null).toFixed(2)
        }
        if (c.node?.colTotal || c.node?.rowTotal) {
          Object.keys(props.subTotal || {}).forEach(b => {
            if (!props.subTotal[b].byValue && props.subTotal[b].groupBy(t, data)) {
              let subItem = sums.find(p => p[data.columns[0].property] == b)
              if (!subItem) {
                subItem = {}
                subItem[data.columns[0].property] = b
                sums.push(subItem)
              }
              subItem[c.property] = Number(Number(subItem[c.property] || null) + Number(t[c.property] || null)).toFixed(2)
            }
            if (props.subTotal[b].byValue) {
              let valItem = props.subTotal[b].groupBy(t, data)
              let subItem = sums.find(p => p[data.columns[0].property] == valItem)
              if (!subItem) {
                subItem = {}
                subItem[data.columns[0].property] = valItem
                sums.push(subItem)
              }
              subItem[c.property] = Number(Number(subItem[c.property] || null) + Number(t[c.property] || null)).toFixed(2)
            }
          })
          totalSummary[c.property] = Number(Number(totalSummary[c.property] || null) + Number(t[c.property] || null)).toFixed(2)
        }
      })
    })
    sums.push(totalSummary)
  }
  return sums
}

defineExpose({
  tableRef
})
</script>
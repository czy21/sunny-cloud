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
        <slot :name="prop" :=scope v-if="scope.column.params.custom"/>
        <template v-else-if="scope.row[`${scope.column.property}_editable`]">
          <el-input ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" @change="(value)=>handleInput(value,scope)" v-if="isInputString(scope)"/>
          <el-input ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" v-else-if="isInputNumber(scope)" @change="(value)=>handleInput(value,scope)" :type="scope.column.params.type"/>
          <el-select ref="editRef" v-model="scope.row[scope.column.property]" @blur="onExitEditMode(scope)" v-else-if="isSelect(scope)" @change="(value) => handleSelect(value, scope)" clearable>
            <el-option v-for="t in props.dict[scope.column.params.dictKey]" :label="t.label" :value="t.value"/>
          </el-select>
          <el-date-picker ref="editRef" v-model="scope.row[scope.column.property]"
                          @blur="onExitEditMode(scope)"
                          @change="(value:any)=>handleDate(value,scope)"
                          clearable
                          :type="scope.column.params.type"
                          size="default"
                          :value-format="scope.column.params.format || 'YYYY-MM-DD HH:mm:ss'"
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
import {TableProps, TableEmits} from "./DynamicTable.ts";
import {VxeTable} from "vxe-table";
import {ElButton, ElDatePicker, ElInput, ElOption, ElSelect} from "element-plus";

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
  }
})

const emit = defineEmits<TableEmits>()

const tableRef = ref()
const editRef = ref()

const headerCellStyle = ({column}) => {
  return column.params?.style
}

const handleCellFocus = () => {
  editRef.value?.forEach((t, i, a) => i === a.length - 1 && t.focus?.())
}

const isInputString = (scope) => {
  let val = (scope.column.params.type === 'string' || !scope.column.params.type)
  if (val) {
    handleCellFocus()
  }
  return val
}

const isInputNumber = (scope) => {
  let val = scope.column.params.type === 'number'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isSelect = (scope) => {
  let val = scope.column.params.type === 'select'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isDate = (scope) => {
  let val = scope.column.params.type === 'date' || scope.column.params.type === 'datetime'
  if (val) {
    handleCellFocus()
  }
  return val
}

const onExitEditMode = (scope) => {
  delete scope.row[`${scope.column.property}_editable`]
  if (scope.column.params.type == 'number') {
    tableRef.value.updateFooter?.()
  }
}

const ShowCell: FunctionalComponent<any> = (scope) => {
  let label = scope.row[scope.column.property]
  if (scope.column.params.dictKey && props.dict) {
    let value = props.dict[scope.column.params.dictKey]?.find(t => t.value === scope.row[scope.column.property])?.label
    if (value) {
      label = value
    }
  }
  if (scope.column.params.type === 'index') {
    label = scope.row[scope.column.property] = scope.rowIndex + 1
  }
  return label
}

const handleCell = (scope) => {
  if (props.editable && (scope.column.params.editable == true || util.object.getValueByExpression(scope.row, scope.column.params.editable))) {
    scope.row[`${scope.column.property}_editable`] = true
    emit("handleEdit", scope.row[scope.column.property], scope, props.dict)
  }
}

const changeColumn = (value, scope) => {
  const changeColumns = scope.$table.getColumns().filter(t => t.params.changeByProps?.includes(scope.column.property))
  changeColumns.forEach(c => {
    if (c.params?.rowTotal) {
      scope.row[c.property] = Number(util.object.getValueByExpression(scope.row, c.params.rowTotal) || null).toFixed(2)
    }
  })
}

const handleInput = (value: any, scope) => {
  changeColumn(value, scope)
  emit('handleEditChange', value, scope, props.dict)
}

const handleSelect = (value: any, scope) => {
  let dictPush = scope.column.params.dictPush
  if (dictPush) {
    let dictValue = props.dict[scope.column.params.dictKey].find((t: any) => t.value === value)
    Object.entries(dictPush).forEach(([k, v]) => scope.row[k] = dictValue[v])
  }
  changeColumn(value, scope)
  emit('handleEditChange', value, scope, props.dict)
}

const handleDate = (value: any, scope) => {
  changeColumn(value, scope)
  emit('handleEditChange', value, scope, props.dict)
}

const showAddRow = (scope) => {
  return scope.rowIndex == props.data.length - 1
}

const addRow = (scope) => {
  props.data.splice(scope?.rowIndex + 1, 0, {...props.defaultRowValue})
}

const delRow = (scope) => {
  props.data.splice(scope.rowIndex, 1)
}

const handleScroll = () => {
  editRef.value?.forEach(t => t.blur?.())
}

const summaryMethod = (data: { columns: any[], data: any[] }) => {
  const sums: any[] = []
  const subTotal = new Map((props.subTotal || []).map(t => [t.key, t]));
  if (data.columns && data.columns.length > 0) {
    const totalSummary: any = {}
    totalSummary[data.columns[0].property] = "合计"
    data.columns.forEach(c => {
      data.data.forEach(t => {
        if (c.params?.rowTotal && !c.params.changeByProps) {
          t[c.property] = Number(util.object.getValueByExpression(t, c.params.rowTotal) || null).toFixed(2)
        }
        if (c.params?.colTotal || c.params?.rowTotal) {
          subTotal.keys().forEach((k, i) => {
            if (!subTotal.get(k).byValue && subTotal.get(k).groupBy(t, data)) {
              let subItem = sums.find(p => p[data.columns[0].property] == k)
              if (!subItem) {
                subItem = {}
                subItem[data.columns[0].property] = k
                sums.push(subItem)
              }
              subItem[c.property] = Number(Number(subItem[c.property] || null) + Number(t[c.property] || null)).toFixed(2)
              subItem["sort"] = i
            }
            if (subTotal.get(k).byValue) {
              let valItem = subTotal.get(k).groupBy(t, data)
              let subItem = sums.find(p => p[data.columns[0].property] == valItem)
              if (!subItem) {
                subItem = {}
                subItem[data.columns[0].property] = valItem
                sums.push(subItem)
              }
              subItem[c.property] = Number(Number(subItem[c.property] || null) + Number(t[c.property] || null)).toFixed(2)
              subItem["sort"] = i
            }
          })
          totalSummary[c.property] = Number(Number(totalSummary[c.property] || null) + Number(t[c.property] || null)).toFixed(2)
        }
      })
    })
    totalSummary["sort"] = sums.length + 1
    sums.push(totalSummary)
    sums.sort((a, b) => a.sort - b.sort)
  }
  return sums
}

defineExpose({
  tableRef
})
</script>
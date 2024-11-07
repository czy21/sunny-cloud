<template>
  <vxe-table :data="props.data"
             @cell-click="handleCell"
             width="100%" height="100%"
             show-header-overflow
             show-footer-overflow
  >
    <dynamic-column :node="t" v-for="t in props.columns">
      <template #default="{prop,scope}">
        <slot :name="prop" :=scope v-if="scope.column.node.custom"/>

        <template v-else-if="isEdit(scope)">
          <el-input ref="editRef"
                    v-model="scope.row[scope.column.property]"
                    @blur="onExitEditMode(scope)"
                    v-if="isInputString(scope)"
          />
          <el-input ref="editRef"
                    v-model="scope.row[scope.column.property]"
                    @blur="onExitEditMode(scope)"
                    :type="scope.column.node.type"
                    v-else-if="isInputNumber(scope)"
          />
          <el-select ref="editRef"
                     v-model="scope.row[scope.column.property]"
                     @blur="onExitEditMode(scope)"
                     v-else-if="isSelect(scope)"
                     @change="(value)=>handleSelect(value,scope)"
          >
            <el-option v-for="t in props.dict[scope.column.node.dictKey]" :label="t.label" :value="t.value"/>
          </el-select>
          <el-date-picker ref="editRef"
                          v-model="scope.row[scope.column.property]"
                          @blur="onExitEditMode(scope)"
                          :type="scope.column.node.type"
                          size="default"
                          value-format="scope.column.node.format|| 'YYYY-MM-DD HH:mm:ss'"
                          v-else-if="isDate(scope)"/>
        </template>
        <span v-else-if="props.editable && scope.column.property==='action'">
          <el-button @click="addRow(scope)" link type="primary" v-if="showAddRow(scope)">加行</el-button>
          <el-button @click="delRow(scope)" link type="danger">删除</el-button>
        </span>
        <show-cell :="scope" v-else/>
      </template>
    </dynamic-column>
    <template #empty>
      <el-button @click="addRow(scope)" type="primary">加行</el-button>
    </template>
  </vxe-table>
</template>

<script lang="tsx" setup>
import {ref, defineProps, defineExpose, FunctionalComponent, h} from "vue"
import DynamicColumn from "./DynamicVxeColumn.vue"
import util from '@sunny-framework-js/util'
import {TableProps} from "@c/DynamicTable";
import _ from "lodash";
import {ElButton, ElDatePicker, ElInput, ElOption, ElSelect, ElTable} from "element-plus";

const props = withDefaults(defineProps<TableProps>(), {
  columns: () => [],
  data: () => [],
  dict() {
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
const spanRef = ref({})

const handleCellFocus = () => {
  console.log(editRef)
  if (editRef.value && editRef.value.length > 0) {
    editRef.value[editRef.value.length - 1].focus?.()
  }
}

const isEdit = (scope: RenderRowData<any>) => scope.row[`${scope.column.property}_editable`]

const isInputString = (scope: RenderRowData<any>) => {
  let val = (scope.column.node.type === 'string' || !scope.column.node.type)
  if (val) {
    handleCellFocus()
  }
  return val
}

const isInputNumber = (scope: RenderRowData<any>) => {
  let val = scope.column.node.type === 'number'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isSelect = (scope: RenderRowData<any>) => {
  let val = scope.column.node.type === 'select'
  if (val) {
    handleCellFocus()
  }
  return val
}

const isDate = (scope: RenderRowData<any>) => {
  let val = scope.column.node.type === 'date' || scope.column.node.type === 'datetime'
  if (val) {
    handleCellFocus()
  }
  return val
}

const onExitEditMode = (scope: RenderRowData<any>) => {
  console.log(scope)
  delete scope.row[`${scope.column.property}_editable`]
}

const ShowCell: FunctionalComponent<any> = (scope: RenderRowData<any>) => {
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

const handleSelect = (value: any, scope: RenderRowData<any>) => {
  let dictPush = scope.column.node.dictPush
  if (dictPush) {
    let dictValue = props.dict[scope.column.node.dictKey].find((t: any) => t.value === value)
    Object.entries(dictPush).forEach(([k, v]) => scope.row[k] = dictValue[v])
  }
}

const showAddRow = (scope: RenderRowData<any>) => {
  return scope.rowIndex == props.data.length - 1
}

const addRow = (scope: RenderRowData<any>) => {
  props.data.splice(scope?.rowIndex + 1, 0, {})
}

const delRow = (scope: RenderRowData<any>) => {
  props.data.splice(scope.rowIndex, 1)
}

// const spanMethod = ({row, column, rowIndex, columnIndex}) => {
//   if (column.node.spanMerge) {
//     let spanMergeColumn = spanRef.value[column.property]
//     if (_.isEmpty(spanMergeColumn)) {
//       spanRef.value[column.property] = column.node.spanMerge
//     }
//   }
//   let ret
//   Object.keys(spanRef.value).forEach(t => {
//     spanRef.value[t].forEach(c => {
//       if (util.object.getValueByExpression(row, c.condition)) {
//         if (column.property == t) {
//           ret = [1, c.props.length + 1]
//         }
//         if (c.props.includes(column.property)) {
//           ret = [0, 0]
//         }
//       }
//     })
//   })
//
//   return ret
// }

const summaryMethod = (data: { columns: any[], data: any[] }) => {
  const sums: any[] = []
  if (data.columns && data.columns.length > 0) {
    const totalSummary: any = {}
    totalSummary[data.columns[0].property] = "合计"
    data.columns.forEach(c => {
      data.data.forEach(t => {
        if (c.node.rowTotal) {
          t[c.property] = Number(util.object.getValueByExpression(t, c.node.rowTotal) || null).toFixed(2)
        }
        if (c.node.colTotal || c.node.rowTotal) {
          Object.keys(props.subTotal || {}).forEach(b => {
            let subItem = sums.find(p => p[data.columns[0].property] == b)
            if (props.subTotal[b].groupBy && props.subTotal[b].groupBy(t, data)) {
              if (!subItem) {
                subItem = {}
                subItem[data.columns[0].property] = b
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
    return data.columns.map(c => h('dl', {style: {"text-align": "center"}}, sums.map(t => h('dt', null, [Object.keys(totalSummary).includes(c.property) ? (t[c.property] || 0) : ""]))))
  }

  return []
}

defineExpose({
  tableRef
})
</script>
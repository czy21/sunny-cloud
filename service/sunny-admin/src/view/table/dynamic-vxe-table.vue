<template>

  <dynamic-vxe-table ref="tableRef"
                     :columns="tableDataRef.columns"
                     :data="tableDataRef.data"
                     :dict="tableDataRef.dict"
                     :sub-total="tableDataRef.subTotal"
                     :editable="tableDataRef.editable"
                     :handle-select="handleSelect"
  >
    <template #age="scope">
      <el-input v-model="scope.row['age']"/>
    </template>
  </dynamic-vxe-table>

  <el-button @click="handleClick">确认</el-button>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue"
import {DynamicVxeTable} from "@sunny-framework-js/vue";
import util from "@sunny-framework-js/util";
import * as meta from './meta'

const tableRef = ref(null)

const tableDataRef = reactive({
  columns: [],
  data: [],
  dict: {},
  subTotal: {},
  editable: true
})

const handleSelect = (value, scope, dict) => {
  if (!value) {
    delete scope.row[scope.column.property]
  }
  if (scope.column.params.dictKey === 'DICT_PROVINCE') {
    !value && scope.$table.getColumns()
        .filter((t: any) => ['DICT_CITY', 'DICT_DISTRICT'].includes(t.params.dictKey))
        .forEach((t: any) => delete scope.row[t.property])
    dict["DICT_CITY"] = dict["DICT_PROVINCE"].find((t: any) => t.value === value)?.children
  }
  if (scope.column.params.dictKey === 'DICT_CITY') {
    !value && scope.$table.getColumns()
        .filter((t: any) => ['DICT_DISTRICT'].includes(t.params.dictKey))
        .forEach((t: any) => delete scope.row[t.property])
    dict["DICT_DISTRICT"] = dict["DICT_CITY"].find((t: any) => t.value === value)?.children
  }
}

const handleClick = () => {
  console.log(util.object.isEmpty(1.2))
  console.log(tableRef.value.tableRef.data)
}

onMounted(() => {
  tableDataRef.columns = meta.getColumns()
  tableDataRef.data = meta.getData(200)
  tableDataRef.dict = meta.getDict()
  tableDataRef.subTotal = meta.getSubTotal()
})

</script>
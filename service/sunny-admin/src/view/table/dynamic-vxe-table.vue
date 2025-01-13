<template>

  <dynamic-vxe-table ref="tableRef"
                     :columns="tableDataRef.columns"
                     :data="tableDataRef.data"
                     :dict="tableDataRef.dict"
                     :sub-total="tableDataRef.subTotal"
                     :show-add-row="true"
                     :editable="true"
                     @handle-edit="handleEdit"
                     @handle-edit-change="handleEditChange"
  >
    <template #age="scope">
      <el-input v-model="scope.row['age']"/>
    </template>
    <template #action="scope">
      <el-button @click="console.log(scope)" link type="primary">查看</el-button>
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
  subTotal: []
})

const handleEdit = (value, scope, dict) => {

  dict["DICT_CITY"] = (dict["DICT_PROVINCE"] || []).find((t: any) => t.value === scope.row["province"])?.children
  dict["DICT_DISTRICT"] = (dict["DICT_CITY"] || []).find((t: any) => t.value === scope.row["city"])?.children

}

const handleEditChange = (value, scope, dict) => {
  console.log(value)
  const cascadeProps = ['province', "city", "district"]
  if (!value && cascadeProps.includes(scope.column.property)) {
    const cascadePropIndex = cascadeProps.findIndex(t => t === scope.column.property)
    cascadeProps.slice(cascadePropIndex).forEach(t => delete scope.row[t])
  }
}

const handleClick = () => {
  console.log(util.object.isEmpty(1.2))
  console.log(tableRef.value.tableRef.data)
}

onMounted(() => {
  tableDataRef.columns = meta.getColumns()
  tableDataRef.data = meta.getData(20)
  tableDataRef.dict = meta.getDict()
  tableDataRef.subTotal = meta.getSubTotal()
})

</script>
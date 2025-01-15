<template>

  <dynamic-el-table ref="tableRef"
                    :columns="tableMeta.columns"
                    :data="tableData"
                    :dict="tableMeta.dict"
                    :sub-total="tableMeta.subTotal"
                    :editable="true"
                    @handle-edit="handleEdit"
                    @handle-edit-change="handleEditChange"
  >
    <template #age="scope">
      <el-input v-model="scope.row['age']"/>
    </template>
  </dynamic-el-table>

  <el-button @click="handleClick">确认</el-button>
</template>

<script lang="ts" setup>
import {reactive, onMounted, ref} from "vue"
import {DynamicElTable} from "@sunny-framework-js/vue";
import * as meta from './meta'

const tableRef = ref(null)

const tableMeta = reactive({
  columns: [],
  dict: {},
  subTotal: []
})

const tableData = ref([])

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
  console.log(tableRef.value.tableRef.data)
}

onMounted(() => {
  tableMeta.columns = meta.getColumns()
  tableMeta.dict = meta.getDict()
  tableMeta.subTotal = meta.getSubTotal()
  tableData.value = meta.getData(20)
})

</script>
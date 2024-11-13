<template>

  <dynamic-el-table ref="tableRef"
                 :columns="tableDataRef.columns"
                 :data="tableDataRef.data"
                 :dict="tableDataRef.dict"
                 :sub-total="tableDataRef.subTotal"
                 :editable="tableDataRef.editable"
  >
    <template #age="scope">
      <el-input v-model="scope.row['age']"/>
    </template>
  </dynamic-el-table>

  <el-button @click="handleClick">确认</el-button>
</template>

<script lang="ts" setup>
import {reactive, onMounted, ref} from "vue"
import DynamicElTable from "@c/DynamicElTable.vue";
import * as meta from './meta'

const tableRef = ref(null)

const tableDataRef = reactive({
  columns: [],
  data: [],
  dict: {},
  subTotal: {},
  editable: true
})

const handleClick = () => {
  console.log(tableRef.value.tableRef.data)
}

onMounted(() => {
  tableDataRef.columns = meta.getColumns()
  tableDataRef.data = meta.getData(20)
  tableDataRef.dict = meta.getDict()
  tableDataRef.subTotal = meta.getSubTotal()
})

</script>
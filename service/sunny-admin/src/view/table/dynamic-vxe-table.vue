<template>

  <dynamic-vxe-table ref="tableRef"
                 :columns="tableDataRef.columns"
                 :data="tableDataRef.data"
                 :dict="tableDataRef.dict"
                 :sub-total="tableDataRef.subTotal"
                 :editable="tableDataRef.editable"
  >
    <template #age="scope">
      <el-input v-model="scope.row['age']"/>
    </template>
  </dynamic-vxe-table>

  <el-button @click="handleClick">确认</el-button>
</template>

<script lang="ts" setup>
import {reactive, onMounted, ref} from "vue"
import {DynamicVxeTable} from "@sunny-framework-js/vue";
import util from "@sunny-framework-js/util";
import * as meta from './meta'
import _ from 'lodash'

const tableRef = ref(null)

const tableDataRef = reactive({
  columns: [],
  data: [],
  dict: {},
  subTotal: {},
  editable: true
})

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
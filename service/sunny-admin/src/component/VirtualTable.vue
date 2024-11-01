<template>
  <el-table-v2 :ref="tableRef" :columns="props.columns" :data="props.data" :width="800" :height="400" fixed>
    <template #header="scope">
      <customized-header v-bind="scope"/>
    </template>
  </el-table-v2>
</template>

<script setup lang="tsx">
import {defineExpose, defineProps, FunctionalComponent, ref} from "vue";
import {TableV2CustomizedHeaderSlotParam, TableV2FixedDir, TableV2Placeholder} from 'element-plus'

const props = defineProps(["columns", "data"])

const tableRef = ref(null)

const handleClick = (value) => {
  console.log(value)
}

const CustomizedHeader: FunctionalComponent<TableV2CustomizedHeaderSlotParam> = ({cells, columns, headerIndex}) => {
  console.log(cells)
  console.log(columns)
  console.log(headerIndex)
  return columns.map(t=>t.desc)
}

defineExpose({
  tableRef
})
</script>

<style scoped lang="scss">

</style>
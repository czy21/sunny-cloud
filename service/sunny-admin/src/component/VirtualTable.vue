<template>
  <el-table-v2 :ref="tableRef"
               :columns="props.columns"
               :data="props.data"
               :width="1500"
               :height="800"
               :header-height="headerHeight"
               fixed>
    <template #header="scope">
      <customized-header v-bind="scope"/>
    </template>
<!--    <template #row="scope">-->
<!--      <span v-for="t in scope.columns">-->
<!--        {{ processRow(scope) }}-->
<!--      </span>-->
<!--    </template>-->
  </el-table-v2>
</template>

<script setup lang="tsx">
import {FunctionalComponent, onBeforeMount, onMounted, ref} from "vue";
import {TableV2CustomizedHeaderSlotParam, TableV2FixedDir, TableV2Placeholder} from 'element-plus'

const props = defineProps(["columns", "data", "headerHeight"])

const tableRef = ref(null)
const handleClick = (value) => {
  console.log(value)
}
const processRow=(scope)=>{

}
const CustomizedHeader: FunctionalComponent<TableV2CustomizedHeaderSlotParam> = ({cells, columns, headerIndex}) => {
  if (headerIndex === props.headerHeight?.length - 1) return cells

  const groupCells = [] as typeof cells

  columns.forEach((column, columnIndex) => {

    groupCells.push(
        <span

            style={{
              width: `${column.width}px`,
            }}
        >
          {column.heads.slice(0,-1)[headerIndex]}
        </span>
    )
  })
  return groupCells
}

defineExpose({
  tableRef
})
</script>

<style scoped lang="scss">
.el-el-table-v2__header-row .custom-header-cell {
  border-right: 1px solid var(--el-border-color);
}

.el-el-table-v2__header-row .custom-header-cell:last-child {
  border-right: none;
}

.el-primary-color {
  background-color: var(--el-color-primary);
  color: var(--el-color-white);
  font-size: 14px;
  font-weight: bold;
}

.el-primary-color .custom-header-cell {
  padding: 0 4px;
}
</style>
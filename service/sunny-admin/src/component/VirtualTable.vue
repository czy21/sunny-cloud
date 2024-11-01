<template>
  <el-table-v2 :ref="tableRef" :columns="props.columns" :data="props.data" :width="800" :height="400" fixed>
    <template #header="scope">
      <customized-header v-bind="scope" />
    </template>
  </el-table-v2>
</template>

<script setup lang="tsx">
import {defineExpose, defineProps, ref} from "vue";
import { TableV2FixedDir, TableV2Placeholder } from 'element-plus'

const props = defineProps(["columns", "data"])

const tableRef = ref(null)

const handleClick = (value) => {
  console.log(value)
}

const CustomizedHeader: FunctionalComponent<
    TableV2CustomizedHeaderSlotParam
> = ({ cells, columns, headerIndex }) => {
  if (headerIndex === 2) return cells

  const groupCells = [] as typeof cells
  let width = 0
  let idx = 0

  columns.forEach((column, columnIndex) => {
    if (column.placeholderSign === TableV2Placeholder)
      groupCells.push(cells[columnIndex])
    else {
      width += cells[columnIndex].props!.column.width
      idx++

      const nextColumn = columns[columnIndex + 1]
      if (
          columnIndex === columns.length - 1 ||
          nextColumn.placeholderSign === TableV2Placeholder ||
          idx === (headerIndex === 0 ? 4 : 2)
      ) {
        groupCells.push(
            <div
                class="flex items-center justify-center custom-header-cell"
                role="columnheader"
                style={{
                  ...cells[columnIndex].props!.style,
                  width: `${width}px`,
                }}
            >
              Group width {width}
            </div>
        )
        width = 0
        idx = 0
      }
    }
  })
  return groupCells
}

defineExpose({
  tableRef
})
</script>

<style scoped lang="scss">

</style>
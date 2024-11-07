<template>
  <el-table-v2
      :columns="columns"
      :data="data"
      :width="700"
      :height="400"
      fixed
  />
</template>

<script lang="ts" setup>
import {ref, onMounted} from "vue";

const generateColumns = (length = 10, prefix = 'column-', props?: any) =>
    Array.from({length}).map((_, columnIndex) => ({
      ...props,
      key: `${prefix}${columnIndex}`,
      dataKey: `${prefix}${columnIndex}`,
      title: `Column ${columnIndex}`,
      width: 150,
    }))

const generateData = (
    columns: ReturnType<typeof generateColumns>,
    length = 200,
    prefix = 'row-'
) =>
    Array.from({length}).map((_, rowIndex) => {
      return columns.reduce(
          (rowData, column, columnIndex) => {
            rowData[column.dataKey] = `Row ${rowIndex} - Col ${columnIndex}`
            return rowData
          },
          {
            id: `${prefix}${rowIndex}`,
            parentId: null,
          }
      )
    })

const columns = ref([])
const data = ref([])

onMounted(() => {
  // columns.value = generateColumns(2)
  // data.value = generateData(columns.value, 2)
  columns.value.push({
    "key":"name",
    "dataKey":"name",
    "title": "姓名",
    "width": 150
  })
  data.value.push({
    "name":"好"
  })
  console.log(columns)
  console.log(data)
})

</script>
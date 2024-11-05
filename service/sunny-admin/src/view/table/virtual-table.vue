<template>

  <virtual-table ref="tableRef" :columns="tableDataRef.columns" :data="tableDataRef.data">
    <template #age="{column,scope}">
      <el-input v-model="scope.row['age']"/>
    </template>
    <template #c="{column,scope}">
      <el-input v-model="scope.row['c']"/>
    </template>
  </virtual-table>

  <el-button @click="handleClick">确认</el-button>
  <el-button @click="getMergeTree">树合并</el-button>
</template>

<script lang="ts" setup>
import VirtualTable from "@c/VirtualTable.vue";
import util from "../../../../../global/sunny-framework-js/util"
import {reactive, onMounted, ref} from "vue"

const handleClick = (scope) => {
  console.log(scope)
  // console.log(tableRef.value.tableRef.data)
  // console.log('click')
}

const tableRef = ref(null)

const tableDataRef = reactive({
  columns: [],
  data: []
})

onMounted(() => {
  tableDataRef.columns = getHeadGroup()
  tableDataRef.data = [
    {
      "name": "张三",
      "address": "上海",
      "age": 25,
      "c": 980
    },
    {
      "name": "李四"
    }
  ]
})
const headData: any[] = [
  {
    "name": "name",
    "desc": "姓名",
    "heads": ["姓名"],
    "editable": true
  },
  {
    "name": "age",
    "desc": "年龄",
    "heads": ["年龄"],
    "type": "select",
    "custom": true
  },
  {
    "name": "address",
    "desc": "a",
    "heads": ["a1", "a2", "address"],
    "type": "number",
    "editable": true
  },
  {
    "name": "b",
    "desc": "b",
    "heads": ["a1", "a2", "b"],
    // "editable": true,
  },
  {
    "name": "c",
    "desc": "c",
    "heads": ["a1", "a2", "a3", "c"],
    "custom": true
  }
]

const getHeadGroup = () => {
  headData.forEach((t: any) => {
    t["heads"] = t["heads"]
    t["desc"] = t.heads[t.heads.length - 1]
  })

  let tree = util.tree.buildByPath(headData, null, "heads", "desc", "parentDesc")
  console.log(tree)
  return tree
}

const getMergeTree = () => {
  let t1 = {
    id: "",
    children: [
      {
        id: "1",
        children: [
          {
            id: "1-1"
          }
        ]
      },
      {
        id: "2",
        children: [
          {
            id: "2-1"
          }
        ]
      },
      {
        id: "3",
        children: [
          {
            id: "3-1"
          }
        ]
      }
    ]
  }
  let t2 = {
    id: "",
    children: [
      {
        id: "1",
        name: "hao"
      },
      {
        id: "2",
        children: [
          {
            id: "2-1",
            name: "2-1"
          }
        ]
      },
      {
        id: "4",
        children: [
          {
            id: "4-1"
          }
        ]
      }
    ]
  }
  console.log(util.tree.override(t1, t2))
}
</script>
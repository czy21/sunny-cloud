<template>

  <dynamic-table ref="tableRef" :columns="tableDataRef.columns" :data="tableDataRef.data" :dict="tableDataRef.dict">
    <template #age="{column,scope}">
      <el-input v-model="scope.row['age']"/>
    </template>
    <!--    <template #c="{column,scope}">-->
    <!--      <el-input v-model="scope.row['c']"/>-->
    <!--    </template>-->
  </dynamic-table>

  <el-button @click="handleClick">确认</el-button>
  <el-button @click="getMergeTree">树合并</el-button>
</template>

<script lang="ts" setup>
import DynamicTable from "@c/DynamicTable.vue";
import util from "@/util"
import {reactive, onMounted, ref} from "vue"

const handleClick = (scope) => {
  console.log(tableRef.value.tableRef.data)
}

const tableRef = ref(null)

const tableDataRef = reactive({
  columns: [],
  data: [],
  dict: []
})

onMounted(() => {
  tableDataRef.columns = getColumns()
  tableDataRef.data = [
    {
      "name": "张三",
      "address": "上海",
      "age": 25,
      "hobby": "dance",
      "c": 980
    },
    {
      "name": "李四",
      "address": "上海",
      "age": 25,
      "hobby": "b"
    },
  ]
  tableDataRef.dict = {
    "hobby": [
      {"label": "唱歌", "value": "music"},
      {"label": "跳舞", "value": "dance"}
    ]
  }
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
    "type": "number",
    "editable": true
  },
  {
    "name": "address",
    "desc": "a",
    "heads": ["a1", "a2", "地址"],
    "editable": true
  },
  {
    "name": "entryTime",
    "desc": "入职时间",
    "heads": ["a1", "a2", "入职时间"],
    "type": "datetime",
    "editable": true,
  },
  {
    "name": "hobby",
    "desc": "爱好",
    "heads": ["爱好"],
    "type": "select",
    "dictKey": "hobby",
    "editable": true,
    "heads": ["a1", "a2", "爱好"]
  },
]

const getColumns = () => {
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
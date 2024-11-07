<template>

  <dynamic-table ref="tableRef"
                 :columns="tableDataRef.columns"
                 :data="tableDataRef.data"
                 :dict="tableDataRef.dict"
                 :sub-total="tableDataRef.subTotal"
                 :editable="tableDataRef.editable"
  >
    <template #age="scope">
      <el-input v-model="scope.row['age']"/>
    </template>
  </dynamic-table>

  <el-button @click="handleClick">确认</el-button>
  <el-button @click="getMergeTree">树合并</el-button>
</template>

<script lang="ts" setup>
import DynamicTable from "@c/DynamicTable.vue";
import util from "@sunny-framework-js/util"
import {reactive, onMounted, ref} from "vue"
import {TableColumn} from "@c/DynamicTable";

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
  tableDataRef.columns = getColumns()
  tableDataRef.data = Array.from({length: 10}).map((t, i) => {
    return {
      "name": "李四" + i,
      "address": "上海",
      "age": 25,
      "hobby": "b",
      "m1": 100 + i,
      "m2": 100 + (i * 2)
    }
  })
  console.log(Array.from(100).map((t, i) => {
    return {
      "name": "李四" + i,
      "address": "上海",
      "age": 25,
      "hobby": "b"
    }
  }))
  tableDataRef.dict = {
    "hobby": [
      {"label": "唱歌", "value": "music"},
      {"label": "跳舞", "value": "dance"}
    ]
  }

  tableDataRef.subTotal = {
    "一月<105": {
      "groupBy": (row) => row.m1 < 105
    }
  }
})

const headData: TableColumn[] = [
  {
    "prop": "seq",
    "name": "序号",
    "heads": ["序号"],
    "type": "index",
    "fixed": "left",
  },
  {
    "prop": "name",
    "name": "姓名",
    "heads": ["姓名"],
    "editable": true,
    "required": true
  },
  {
    "prop": "age",
    "name": "年龄",
    "heads": ["年龄"],
    "type": "number",
    "editable": true,
    "custom": true,
    "required": true
  },
  {
    "prop": "address",
    "name": "a",
    "heads": ["a1", "a2", "地址"],
    "editable": true
  },
  {
    "prop": "entryTime",
    "name": "入职时间",
    "heads": ["a1", "a2", "入职时间"],
    "type": "datetime",
    "editable": true,
    "width": 200
  },
  {
    "prop": "hobby",
    "name": "爱好",
    "type": "select",
    "dictKey": "hobby",
    "editable": true,
    "heads": ["a1", "a2", "爱好"]
  },
  {
    "prop": "m1",
    "name": "1月",
    "heads": [
      "工资",
      "1月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m2",
    "name": "2月",
    "heads": [
      "工资",
      "2月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m3",
    "name": "3月",
    "heads": [
      "工资",
      "3月"
    ],
    "type": "number",
    "editable": "obj.m1 > 100",
    "colTotal": true
  },
  {
    "prop": "m4",
    "name": "4月",
    "heads": [
      "工资",
      "4月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m5",
    "name": "5月",
    "heads": [
      "工资",
      "5月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m6",
    "name": "6月",
    "heads": [
      "工资",
      "6月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m7",
    "name": "7月",
    "heads": [
      "工资",
      "7月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m8",
    "name": "8月",
    "heads": [
      "工资",
      "8月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m9",
    "name": "9月",
    "heads": [
      "工资",
      "9月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m10",
    "name": "10月",
    "heads": [
      "工资",
      "10月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m11",
    "name": "11月",
    "heads": [
      "工资",
      "11月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "m12",
    "name": "12月",
    "heads": [
      "工资",
      "12月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "prop": "yearTotal",
    "name": "合计",
    "heads": [
      "工资",
      "年合计"
    ],
    "type": "number",
    "colTotal": true,
    "rowTotal": "Array.from({length: 12},(v,i)=>Number(obj[`m${i+1}`]||null)).reduce((a,b)=>a+b)"
  },
  {
    "prop": "action",
    "name": "操作",
    "heads": ["操作"],
    "fixed": "right"
  }
]

const getColumns = () => {
  headData.forEach((t: any) => {
    if (t.heads && t.required) {
      t.heads[t.heads.length - 1] = !t.heads[t.heads.length - 1].includes("*") ? ("*" + t.heads[t.heads.length - 1]) : t.heads[t.heads.length - 1]
    }
  })

  let tree = util.tree.buildByPath(headData, null, "heads", "name", "parentName")
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
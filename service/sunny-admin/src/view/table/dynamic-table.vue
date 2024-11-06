<template>

  <dynamic-table ref="tableRef" :columns="tableDataRef.columns" :data="tableDataRef.data" :dict="tableDataRef.dict" :sub-total="tableDataRef.subTotal">
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

const tableRef = ref(null)

const tableDataRef = reactive({
  columns: [],
  data: [],
  dict: {},
  subTotal: {}
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

const headData: any[] = [
  {
    "name": "seq",
    "desc": "序号",
    "heads": ["序号"],
    "type": "index",
    "fixed": "left",
  },
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
    "editable": true,
    "custom": true
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
    "width": 200
  },
  {
    "name": "hobby",
    "desc": "爱好",
    "type": "select",
    "dictKey": "hobby",
    "editable": true,
    "heads": ["a1", "a2", "爱好"]
  },
  {
    "name": "m1",
    "desc": "1月",
    "heads": [
      "工资",
      "1月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m2",
    "desc": "2月",
    "heads": [
      "工资",
      "2月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m3",
    "desc": "3月",
    "heads": [
      "工资",
      "3月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m4",
    "desc": "4月",
    "heads": [
      "工资",
      "4月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m5",
    "desc": "5月",
    "heads": [
      "工资",
      "5月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m6",
    "desc": "6月",
    "heads": [
      "工资",
      "6月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m7",
    "desc": "7月",
    "heads": [
      "工资",
      "7月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m8",
    "desc": "8月",
    "heads": [
      "工资",
      "8月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m9",
    "desc": "9月",
    "heads": [
      "工资",
      "9月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m10",
    "desc": "10月",
    "heads": [
      "工资",
      "10月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m11",
    "desc": "11月",
    "heads": [
      "工资",
      "11月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "m12",
    "desc": "12月",
    "heads": [
      "工资",
      "12月"
    ],
    "type": "number",
    "editable": true,
    "colTotal": true
  },
  {
    "name": "yearTotal",
    "desc": "合计",
    "heads": [
      "工资",
      "年合计"
    ],
    "type": "number",
    "colTotal": true,
    "rowTotal": "Number(obj.m1||null)+Number(obj.m2||null)+Number(obj.m3||null)+Number(obj.m4||null)+Number(obj.m5||null)+Number(obj.m6||null)+Number(obj.m7||null)+Number(obj.m8||null)+Number(obj.m9||null)+Number(obj.m10||null)+Number(obj.m11||null)+Number(obj.m12||null)"
  },
  {
    "name": "action",
    "desc": "操作",
    "heads": ["操作"],
    "fixed": "right"
  }
]

const getColumns = () => {
  headData.forEach((t: any) => {
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
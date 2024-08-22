<template>
  <el-table>
    <template v-for="t in treeHead">
      <dynamic-column v-if="t.children" :column-item="t"/>
      <el-table-column v-else :label="t.desc" :prop="t.name"/>
    </template>
  </el-table>
  <el-button @click="getHeadGroup">确认</el-button>
</template>

<script lang="ts" setup>
import DynamicColumn from "@v/table/dynamic-column.vue";

const handleClick = () => {
  console.log('click')
}

const treeHead = [
  {
    "desc": "一级",
    "children": [
      {
        "name": "name",
        "desc": "姓名",
        "children": [
          {
            "name": "name1",
            "desc": "姓名1"
          },
          {
            "name": "name1",
            "desc": "姓名2"
          }
        ]
      },
      {
        "name": "name",
        "desc": "年龄"
      }
    ]
  },
  {
    "name": "address",
    "desc": "地址"
  }
]
const headData = [
  {
    "name": "name",
    "heads": ["一级", "二级", "姓名"]
  },
  {
    "name": "age",
    "heads": ["一级", "年龄"]
  },
  {
    "name": "age",
    "heads": ["一级", "手机号"]
  },
  {
    "name": "address",
    "heads": ["地址"]
  }
]

const getHeadGroup = () => {
  const flat = headData.map((t: any) => {
    t["desc"] = t.heads[t.heads.length - 1]
    t["parentDesc"] = t.heads.length == 1 ? null : t.heads[t.heads.length - 2]
    return t
  })
  const parents = flat.map(t => t.heads.slice(0, -1)).filter(t => t.length > 0).map(t=>{
    return {
      "desc": t[t.length - 1],
      "parentDesc": t[t.length - 2]
    }
  })
  console.log(flat)
  console.log(parents)
}
// const getComponent=()=>{
//   return
// }
</script>
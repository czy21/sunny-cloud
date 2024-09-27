<template>
  <el-table>
    <template v-for="t in getHeadGroup()">
      <dynamic-column v-if="t.children" :column-item="t"/>
      <el-table-column v-else :label="t.desc" :prop="t.name"/>
    </template>
  </el-table>
  <el-button @click="getHeadGroup">确认</el-button>

  <el-button @click="getMergeTree">树合并</el-button>
</template>

<script lang="ts" setup>
import DynamicColumn from "@v/table/dynamic-column.vue";
import util from "@/util"

const handleClick = () => {
  console.log('click')
}

const headData: any[] = []

const getHeadGroup = () => {
  headData.forEach((t: any) => {
    t["heads"] = t["detailMeta"]["heads"]
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
  console.log(util.tree.override(t1,t2))
}
</script>
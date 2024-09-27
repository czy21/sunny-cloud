<template>
  <el-table>
    <template v-for="t in getHeadGroup()">
      <dynamic-column v-if="t.children" :column-item="t"/>
      <el-table-column v-else :label="t.desc" :prop="t.name"/>
    </template>
  </el-table>
  <el-button @click="getHeadGroup">确认</el-button>
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
</script>
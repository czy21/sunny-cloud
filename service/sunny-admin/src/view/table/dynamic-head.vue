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

const handleClick = () => {
  console.log('click')
}

const headData:any = [
]

const insert_head = (node: any, next: any) => {
  let current = node
  next.heads.forEach((tt: any) => {
    current.children = current.children || []
    let parent = current.children.find((pt: any) => pt.desc == tt)
    if (!parent) {
      parent = {
        "desc": tt
      }
      current.children.push(parent)
    }
    current = parent
  })
}

const getHeadGroup = () => {
  headData.forEach((t: any) => {
    t["heads"] = t["detailMeta"]["heads"]
  })
  let root = {
    "desc": "",
    "children": []
  }
  headData.forEach((t: any, i: any) => {
    t["desc"] = t.heads[t.heads.length - 1]
    insert_head(root, t)
  })
  let tree=Object.values(root.children)
  console.log(tree)
  return tree
}

const buildTree = (all: any) => {
  return all.filter((t: any) => t.parentId == "").map((t: any) => buildChildren(all, t)).sort((a, b) => a.sort - b.sort)
}

const buildChildren = (items: any, node: any) => {
  let children = items.filter((t: any) => node.id == t.parentId).map((t: any) => buildChildren(items, t))
  if (node['children']) {
    node['children'].push(...children)
  } else {
    node['children'] = children
  }
  node.children.sort((a, b) => a.sort - b.sort)
  return node
}
</script>
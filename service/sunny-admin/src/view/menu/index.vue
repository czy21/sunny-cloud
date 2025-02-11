<template>
  <div>菜单管理</div>
  <el-button @click="executeExpression">确认</el-button>
</template>
<script setup lang="ts">
import {util, socket} from "@sunny-framework-js/core"

const expressionByObj = (row, expression) => {
  return Function("row", "return " + expression)(row)
}

const executeExpression = () => {

  let peoples = [
    {
      "d1": "10",
      "d2": 20
    },
    {
      "d1": 10,
      "d2": 10
    }
  ]

  peoples.forEach(t => {
    console.log(util.object.getValueByExpression(t, "obj.d1+obj.d2"))
  })

  let s = new socket.web.Socket({
    server: "ws://127.0.0.1:8080/socket/test/1"
  })

  s.onmessage = ev => {
    console.log(ev)
  }
}

</script>
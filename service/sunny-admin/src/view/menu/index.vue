<template>
  <div></div>
  <el-button @click="page">查询</el-button>
</template>
<script setup lang="ts">
import api from "@/api"

const page = () => {
  api.post("sys/user/page", {}).then((res: any) => {
    console.log(res)
  })
}
</script>
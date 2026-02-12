<template>
  <el-button @click="onOk">确认</el-button>
</template>
<script setup lang="ts">
import helper from '@h';

const onOk = () => {
  helper.api.get("auth/test").then(t=>{
    // console.log(t.data)
  })
}
</script>
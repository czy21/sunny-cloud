<template>
  <el-button @click="onOk">确认</el-button>
</template>
<script setup lang="ts">
import api from '@g/api';
const onOk = () => {
  api.get("auth/test").then(t=>{
    // console.log(t.data)
  })
}
</script>
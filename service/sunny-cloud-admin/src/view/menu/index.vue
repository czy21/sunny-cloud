<template>
  <div></div>
  <el-button @click="page">查询</el-button>
</template>
<script lang="ts">
import {defineComponent} from "vue";
import api from "@/api"

export default defineComponent({
  methods: {
    page() {
      api.post("sys/user/page", {}).then((res: any) => {
        console.log(res)
      })
    }
  }
})
</script>
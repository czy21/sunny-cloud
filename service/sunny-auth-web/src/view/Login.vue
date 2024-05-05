<template>
  <el-form :model="form" label-width="auto" style="max-width: 600px">
    <el-form-item label="账号">
      <el-input v-model="form.username"/>
    </el-form-item>
    <el-form-item label="账号">
      <el-input v-model="form.password"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script setup lang="ts">
import {reactive} from 'vue'
import {useRouter} from "vue-router";
import api from "@/api"
import util from "@/util"

const router = useRouter()
const form = reactive({
  username: null,
  password: null,
})

const onSubmit = () => {
  const queryMap = router.currentRoute.value.query
  const redirectUri: any = queryMap.redirectUri
  api.post("auth/login", {"username": "user", "password": "password"}).then((res: any) => {
    const token = res.data.data?.token
    if (token) {
      document.domain="https://czy21.com"
      util.auth.setToken(token)
      if (redirectUri) {
        window.location.href = redirectUri + "?token=" + token
      } else {
        router.push("/")
      }
    }
  })
}
</script>
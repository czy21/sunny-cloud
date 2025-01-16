<template>
  <el-form ref="formRef" :model="form" :rules="formRules" label-width="auto" style="max-width: 600px">
    <el-form-item label="账号" prop="username">
      <el-input v-model="form.username"/>
    </el-form-item>
    <el-form-item label="账号" prop="password">
      <el-input v-model="form.password"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script setup lang="ts">
import {reactive, ref} from 'vue'
import router from "@/router";
import {helper} from '@sunny-framework-js/vue';
import {util} from "@sunny-framework-js/core"
import type {FormInstance, FormRules} from "element-plus";

const form = reactive<FormRule>({
  username: null,
  password: null,
})

interface FormRule {
  username: string | null
  password: string | null
}

const formRef = ref<FormInstance>()
const formRules = reactive<FormRules<FormRule>>({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'}
  ]
})

const onSubmit = () => {
  const queryMap = router.currentRoute.value.query
  const redirectUri: any = queryMap.redirectUri
  formRef.value?.validate((valid) => {
    valid && helper.api.post("auth/login", form).then((res: any) => {
      const token = res.data.data?.token
      if (token) {
        util.cookie.setToken(token)
        if (redirectUri) {
          window.location.href = redirectUri
        } else {
          router.push("/")
        }
      }
    })
  })
}
</script>
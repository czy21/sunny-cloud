<template>
  <el-form :inline="true" :model="queryFormModel">
    <el-form-item label="姓名">
      <el-input v-model="queryFormModel.name" placeholder="请输入姓名" clearable/>
    </el-form-item>
    <el-form-item label="手机号">
      <el-input v-model="queryFormModel.phone" placeholder="请输入手机号" clearable/>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="queryFormModel.email" placeholder="请输入邮箱" clearable/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSearch">查询</el-button>
    </el-form-item>
  </el-form>
  <el-table :data="list" border style="width: 100%">
    <el-table-column prop="username" label="账号"/>
    <el-table-column prop="name" label="姓名"/>
    <el-table-column prop="phone" label="手机号"/>
    <el-table-column prop="email" label="邮箱"/>
    <el-table-column prop="address" label="地址"/>
  </el-table>
  <el-pagination
      layout="total, sizes, prev, pager, next"
      :current-page="page.page"
      :page-size="page.pageSize"
      :page-sizes="[50, 100, 150, 200]"
      :size="page.totalPage"
      :total="page.total"
      @size-change="handlePageSizeChange"
      @current-change="handlePageIndexChange"
  />
</template>
<script setup lang="ts">
import {helper} from '@sunny-framework-js/vue';
import type {FormInstance} from 'element-plus';
import _ from 'lodash'
import {onMounted, reactive, ref} from "vue";

const queryFormRef = ref<FormInstance>()
const queryFormModel = reactive({
  name: "",
  phone: "",
  email: ""
})
const page: any = ref({})
const list: any = ref([])
const handleSearch = (query = {}) => {
  helper.api.post("sys/user/page", {..._.omit(page.value, ["total", "totalPage"]), ...queryFormModel}).then((t: any) => {
    page.value = _.omit(t.data.data, ["list"])
    list.value = t.data.data?.list
  })
}
const handlePageIndexChange = (val: number) => {
  page.value.page = val
  handleSearch()
}
const handlePageSizeChange = (val: number) => {
  page.value.pageSize = val
  handleSearch()
}
onMounted(() => {
  handleSearch()
})
</script>
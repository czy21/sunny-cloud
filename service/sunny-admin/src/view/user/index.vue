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
  <dynamic-el-table :columns="tableData.columns" :data="tableData.data" border style="width: 100%"/>
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
import {DynamicElTable} from "@sunny-framework-js/vue";
import type {FormInstance} from 'element-plus';
import _ from 'lodash'
import {onMounted, reactive, ref} from "vue";
import {getListColumns} from "./meta";

const queryFormRef = ref<FormInstance>()
const queryFormModel = reactive({
  name: "",
  phone: "",
  email: ""
})
const page: any = ref({})

const tableData = reactive({
  columns:getListColumns(),
  data:[]
})
const handleSearch = (query = {}) => {
  helper.api.post("sys/user/page", {..._.omit(page.value, ["total", "totalPage"]), ...queryFormModel}).then((t: any) => {
    page.value = _.omit(t.data.data, ["list"])
    tableData.data = t.data.data?.list
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
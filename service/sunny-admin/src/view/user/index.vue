<template>
  <el-button @click="handleSearch">查询</el-button>
  <el-table :data="list" border style="width: 100%">
    <el-table-column prop="username" label="账号"/>
    <el-table-column prop="name" label="姓名"/>
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
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />
</template>
<script setup lang="ts">
import api from '@/api';
import _ from 'lodash'
import {onMounted, ref} from "vue";

const page: any = ref({})
const list: any = ref([])
const handleSearch = (param = {}) => {
  api.post("sys/user/page", param).then((t: any) => {
    page.value = _.omit(t.data.data, ["list"])
    list.value = t.data.data?.list
  })
}
const handleCurrentChange = (val: number) => {
  handleSearch({...page.value, page: val})
}
const handleSizeChange = (val: number) => {
  handleSearch({...page.value, pageSize: val})
}
onMounted(() => {
  handleSearch()
})
</script>
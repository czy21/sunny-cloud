<template>
  <el-button @click="onConnect" :type="status=='success'?'danger':''">{{ status == 'success' ? '断开' : '连接' }}</el-button>
  <el-button @click="messages=[]">清空</el-button>
  <p v-for="t in messages">
    {{ t }}
  </p>

  <el-button @click="pageIteratorFunc">分页迭代</el-button>
</template>


<script setup lang="ts">
import {ref} from "vue";
import {socket, util} from '@sunny-framework-js/core'

const status = ref()
const messages = ref([])

const socketObj = ref(new socket.web.Socket({
  server: "ws://127.0.0.1:8080/socket/simple/1"
}))

const onConnect = () => {
  if (status.value == "success") {

    socketObj.value.onclose = ev => {
    }

    socketObj.value.disConnect()

    status.value = ""
  } else {
    socketObj.value.onclose = null
    socketObj.value.connect()
    socketObj.value.onmessage = ev => messages.value.push(ev.data)
    status.value = "success"
  }
}

const pageIteratorFunc = async () => {
  let data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  await util.page.forEachPage((pageIndex, pageSize) => {
    return new Promise(resolve => {
      return resolve(data.slice((pageIndex - 1) * pageSize, ((pageIndex - 1) * pageSize) + pageSize))
    })
  }, async (pageIndex, pageSize, list) => {
    console.log(pageIndex, pageSize, list)
  }, 3)
}

</script>

<style scoped>

</style>
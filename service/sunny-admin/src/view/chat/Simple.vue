<template>
  <el-button @click="simpleConnect" :type="status=='success'?'danger':''">{{ status == 'success' ? '断开' : '连接' }}</el-button>
  <el-button @click="messages=[]">清空</el-button>
  <p v-for="t in messages">
    {{ t }}
  </p>
</template>


<script setup lang="ts">
import {ref} from "vue";
import {socket} from '@sunny-framework-js/core'

const status = ref()
const messages = ref([])

const socketObj = ref(new socket.web.Socket({
  server: "ws://127.0.0.1:8080/socket/simple/1"
}))

const sockjsConnectStatuColor = ref()
const sockjsMessages = ref([])

const simpleConnect = () => {
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
</script>

<style scoped>

</style>
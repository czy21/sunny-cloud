<template>
  <div>菜单管理</div>
  <el-button @click="executeExpression">确认</el-button>
</template>
<script setup lang="ts">
import {util} from "@sunny-framework-js/core"

const expressionByObj = (row, expression) => {
  return Function("row", "return " + expression)(row)
}

const executeExpression = () => {

  let peoples = [
    {
      "d1": "10",
      "d2": 20
    },
    {
      "d1": 10,
      "d2": 10
    }
  ]

  peoples.forEach(t => {
    console.log(util.object.getValueByExpression(t, "obj.d1+obj.d2"))
  })

  let s = new Socket({
    server: "ws://127.0.0.1:8080/socket/test/1",
    heartbeat: {
      interval: 30,
      timeout: 5
    },
    maxReconnectCount: 5
  })

  s.listen = ev => {
    console.log(ev)
  }
}

interface SocketOptions {
  server: string
  heartbeat?: {
    interval: number
    timeout: number
  }
  maxReconnectCount?: number
}

class Socket {
  ws: WebSocket
  options: any = {}
  reConnectCount: number = 0
  lockReConnect: boolean
  heartTimer: number
  serverTimer: number
  listen: (ev: MessageEvent) => void

  constructor(options: SocketOptions) {
    this.options = {...this.options, ...options}
    this.#connect()
  }

  #connect() {
    this.ws = new WebSocket(this.options.server)

    this.ws.onopen = ev => {
      this.reConnectCount = 0
      this.#ping()
    }

    this.ws.onclose = ev => {
      this.#reconnect()
    }

    this.ws.onerror = ev => {
      this.#reconnect()
    }

    this.ws.onmessage = ev => {
      if (ev.data === 'pong') {
        this.#ping()
      } else {
        this.listen(ev)
      }
    }
  }

  #reconnect() {

    if (this.lockReConnect || this.reConnectCount >= this.options.maxReconnectCount) {
      return
    }

    this.reConnectCount++

    setTimeout(() => {
      this.#connect()
    }, 1000 * this.reConnectCount)
  }

  #ping() {
    this.#reset()
    this.heartTimer = setTimeout(() => {
      this.ws.send("ping")
      this.serverTimer = setTimeout(() => {
      }, this.options.heartbeat?.timeout * 1000)
    }, this.options.heartbeat?.interval * 1000)
  }

  #reset() {
    this.heartTimer && clearTimeout(this.heartTimer)
    this.serverTimer && clearTimeout(this.serverTimer)
  }
}

</script>
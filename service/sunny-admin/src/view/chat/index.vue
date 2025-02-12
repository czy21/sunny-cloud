<template>
  <div>
    <el-tabs v-model="sampleType">
      <el-tab-pane label="Simple" name="Simple">
        <simple/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import Simple from './Simple.vue'

const sampleType = ref('Simple')

</script>

<style scoped>

</style>
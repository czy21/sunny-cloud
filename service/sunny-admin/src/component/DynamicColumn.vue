<template>
  <el-table-column :label="node.desc" :prop="node.name" align="center" v-if="node.children && node.children.length > 0">
    <dynamic-column :node="child" v-for="child in node.children">
      <template #default="scope">
        <slot :columnName="scope.columnName" :column="scope.column" :scope="scope.scope"/>
      </template>
    </dynamic-column>
  </el-table-column>
  <el-table-column :label="node.desc" :prop="node.name" align="center" v-else>
    <template #default="scope">
      <slot :columnName="node.name" :column="node" :scope="scope" v-if="node.custom"/>
      <el-input v-model="scope.row[node.name]" v-if="!node.type"/>
    </template>
  </el-table-column>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  props: {
    node: {type: Object}
  },
  methods: {
    handleClick:(scope) => {
      console.log(scope)
    }
  }
})

</script>
<script setup lang="ts">
</script>
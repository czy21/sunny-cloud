<template>
  <div class="error-page">
    <div class="error-box">
      <div class="error-code">403</div>
      <div class="error-desc">无权限访问该页面</div>
      <div class="error-handle">
        <a href="/">返回首页</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  background: #eef0fc;
  box-sizing: border-box;
}

.error-box {
  width: 400px;
  background-color: #fff;
  padding: 80px 50px;
  border-radius: 5px;
}

.error-code {
  line-height: 1;
  font-size: 100px;
  font-weight: bold;
  color: var(--el-color-primary);
  margin-bottom: 20px;
  text-align: center;
}

.error-desc {
  font-size: 20px;
  color: #777;
  text-align: center;
}

.error-handle {
  margin-top: 50px;
  text-align: center;
}

</style>
<script setup lang="ts">
</script>
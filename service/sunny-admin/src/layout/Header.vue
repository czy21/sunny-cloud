<template>
  <el-header class="layout-header">
    <div class="left">
      <Hamburger class="collapse-btn" :is-active="!collapse" @toggleClick="changeCollapse"/>
    </div>
    <div class="right">
      <el-dropdown class="avatar-container right-menu-item hover-effect" @command="handleCommand" trigger="hover">
        <div class="avatar-wrapper">
          <span class="user-name"> {{ userStore.profile.name }} </span>
        </div>
        <template #dropdown>
<!--          <el-dropdown-menu>-->
<!--            <router-link to="/user/profile">-->
<!--              <el-dropdown-item>个人中心</el-dropdown-item>-->
<!--            </router-link>-->
<!--            <el-dropdown-item divided command="logout">-->
<!--              <span>退出登录</span>-->
<!--            </el-dropdown-item>-->
<!--          </el-dropdown-menu>-->
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>
<script setup lang="ts">
import {Hamburger} from '@sunny-framework-js/vue'
import {computed} from "vue";
import {useAppStore, useUserStore} from '@/store'

const appStore = useAppStore()
const userStore = useUserStore()

const collapse = computed(() => appStore.aside.collapse)

const handleCommand = (command) => {
  switch (command) {
    case "logout":
      userStore.logout()
      break
    default:
      break
  }
}

const changeCollapse = () => {
  appStore.changeAsideCollapse()
}
</script>

<template>
  <el-container>
    <el-aside :class="{'main-collapse':isCollapse}">
      <el-menu :collapse="isCollapse" background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF" :default-active="getCurrentRoute" router>
        <NavMenu :collapse="isCollapse" :menu-tree="getMenuTree"/>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="collapse-btn" @click="changeCollapse">
          <i class="el-icon-menu" style="font-size: 20px;align-self: center"/>
        </div>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
<script lang="ts">
import {defineComponent} from 'vue'
import menus from "@/menu";
import NavMenu from "@/layout/NavMenu.vue";

export default defineComponent({
  components: {NavMenu},
  computed: {
    isCollapse() {
      return this.$store.getters.aside.collapse
    },
    getCurrentRoute() {
      return this.$route.path;
    },
    getMenuTree() {
      return menus
    }
  },
  methods: {
    changeCollapse() {
      this.$store.dispatch("TOGGLE_ASIDE_ACTION")
    }
  }
})
</script>
<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const activeMenu = computed(() => route.path)

const handleLogout = () => {
  // 清除token和用户信息
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
  // 跳转到登录页
  router.push('/login')
}
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
.layout-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #545c64;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px; /* 固定头部高度 */
}

.header-title {
  font-family: 楷体;
  font-size: 30px;
}

.header-user a {
  color: white;
  text-decoration: none;
}

.header-user a:hover {
  color: #e6e6e6;
}

/* 让内容区域占满剩余空间 */
.el-container {
  flex: 1;
  overflow: hidden;
}

.el-menu-vertical {
  height: 100%;
  border-right: none;
}

.el-aside {
  background-color: #f0f2f5;
  height: 100%;
}

.el-main {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}
</style>

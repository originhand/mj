<template>
  <el-container class="layout-container">
    <!-- 头部 -->
    <el-header class="header">
      <div class="header-title">智学云帆-教学管理系统</div>
      <div class="header-user">
        <a href="#" @click.prevent="handleLogout">退出登录【{{ userInfo.name }}】</a>
      </div>
    </el-header>
    
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical"
          router>
          
          <el-menu-item index="/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>

          <el-sub-menu index="/class">
            <template #title>
              <el-icon><User /></el-icon>
              <span>班级学员管理</span>
            </template>
            <el-menu-item index="/class-management">班级管理</el-menu-item>
            <el-menu-item index="/student-management">学员管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/system">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统信息管理</span>
            </template>
            <el-menu-item index="/department-management">部门管理</el-menu-item>
            <el-menu-item index="/employee-management">员工管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!-- 主要内容区 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { House, User, Setting } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

// 修改激活菜单的计算逻辑
const activeMenu = computed(() => {
  // 获取当前路由路径
  const path = route.path
  
  // 如果是子菜单项，返回完整路径
  if (path.includes('management')) {
    return path
  }
  
  // 如果是首页，返回 /home
  if (path === '/') {
    return '/home'
  }
  
  return path
})

// 获取用户信息
const userInfo = computed(() => {
  const info = localStorage.getItem('userInfo')
  return info ? JSON.parse(info) : { name: '' }
})

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
  router.push('/login')
}
</script>

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
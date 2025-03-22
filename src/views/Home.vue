<template>
  <div class="home-container">
    <el-row :gutter="20">
      <!-- 数据统计卡片 -->
      <el-col :span="12">
        <el-card class="stat-card">
          <template #header>
            <div class="card-header">
              <span>部门统计</span>
              <el-button class="button" text @click="$router.push('/department-management')">
                查看详情
              </el-button>
            </div>
          </template>
          <div class="stat-number">
            <el-icon class="stat-icon"><Management /></el-icon>
            <span class="number">{{ deptCount }}</span>
            <span class="label">个部门</span>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card class="stat-card">
          <template #header>
            <div class="card-header">
              <span>员工统计</span>
              <el-button class="button" text @click="$router.push('/employee-management')">
                查看详情
              </el-button>
            </div>
          </template>
          <div class="stat-number">
            <el-icon class="stat-icon"><User /></el-icon>
            <span class="number">{{ empCount }}</span>
            <span class="label">名员工</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷入口 -->
    <el-row :gutter="20" class="quick-access">
      <el-col :span="24">
        <h3 class="section-title">快捷入口</h3>
      </el-col>
      <el-col :span="6">
        <el-card class="quick-card" @click="$router.push('/department-management')">
          <el-icon class="quick-icon"><Management /></el-icon>
          <div class="quick-text">部门管理</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="quick-card" @click="$router.push('/employee-management')">
          <el-icon class="quick-icon"><User /></el-icon>
          <div class="quick-text">员工管理</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="quick-card" @click="$router.push('/class-management')">
          <el-icon class="quick-icon"><School /></el-icon>
          <div class="quick-text">班级管理</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="quick-card" @click="$router.push('/student-management')">
          <el-icon class="quick-icon"><UserFilled /></el-icon>
          <div class="quick-text">学员管理</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { 
  User,
  School, 
  UserFilled,
  Management,
  House
} from '@element-plus/icons-vue'
import { getDeptList } from '@/api/dept'
import { getEmpList } from '@/api/emp'

const deptCount = ref(0)
const empCount = ref(0)

// 获取统计数据
const fetchStats = async () => {
  try {
    // 获取部门数量
    const deptData = await getDeptList()
    deptCount.value = deptData.length

    // 获取员工数量
    const empData = await getEmpList({
      page: 1,
      pageSize: 1
    })
    empCount.value = empData.total
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

onMounted(() => {
  fetchStats()
})
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.stat-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-number {
  display: flex;
  align-items: center;
  padding: 20px 0;
}

.stat-icon {
  font-size: 48px;
  color: var(--el-color-primary);
  margin-right: 20px;
}

.number {
  font-size: 36px;
  font-weight: bold;
  margin-right: 10px;
}

.label {
  font-size: 16px;
  color: #666;
}

.quick-access {
  margin-top: 20px;
}

.section-title {
  margin-bottom: 20px;
  color: #333;
  font-weight: normal;
}

.quick-card {
  cursor: pointer;
  text-align: center;
  padding: 20px;
  transition: all 0.3s;
}

.quick-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.quick-icon {
  font-size: 40px;
  color: var(--el-color-primary);
  margin-bottom: 10px;
}

.quick-text {
  font-size: 16px;
  color: #333;
}
</style> 
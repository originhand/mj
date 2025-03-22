<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">智学云帆-教学管理系统</h2>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="rules"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入员工用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item class="btn-group">
          <el-button
            type="primary"
            :loading="loading"
            @click="handleLogin"
          >
            登录
          </el-button>
          <el-button @click="handleReset">
            取消
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { login } from '@/api/login'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)

// 登录表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

// 登录方法
const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        loading.value = true
        const data = await login(loginForm)
        localStorage.setItem('token', data.token)
        localStorage.setItem('userInfo', JSON.stringify({
          id: data.id,
          username: data.username,
          name: data.name
        }))
        ElMessage.success('登录成功')
        router.push('/home')
      } catch (error) {
        console.error('登录失败:', error)
      } finally {
        loading.value = false
      }
    }
  })
}

// 重置表单
const handleReset = () => {
  if (loginFormRef.value) {
    loginFormRef.value.resetFields()
  }
}
</script>

<style scoped>
.login-container {
  width: 100vw;
  height: 100vh;
  background-color: #f0f2f5;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  font-family: 楷体;
  font-size: 24px;
  color: #333;
}

.login-form {
  .login-button {
    width: 100%;
    margin-bottom: 10px;
  }
}

:deep(.el-input__wrapper) {
  background-color: #f5f7fa;
}

.btn-group {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0;
}

.btn-group :deep(.el-form-item__content) {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-left: 0 !important;
}

.btn-group .el-button {
  width: 45%;  /* 调整按钮宽度 */
}
</style> 
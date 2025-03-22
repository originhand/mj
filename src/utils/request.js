import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

const request = axios.create({
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')
    // 如果有token则添加到请求头
    if (token) {
      config.headers.token = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const { code, msg, data } = response.data
    if (code === 1) {
      return data
    }
    ElMessage.error(msg || '操作失败')
    return Promise.reject(new Error(msg || '操作失败'))
  },
  error => {
    if (error.response && error.response.status === 401) {
      // 清除本地存储的token
      localStorage.removeItem('token')
      // 跳转到登录页
      router.push('/login')
      ElMessage.error('登录已过期，请重新登录')
    } else {
      ElMessage.error(error.message || '请求失败')
    }
    return Promise.reject(error)
  }
)

export default request 
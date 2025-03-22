import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../layout/Layout.vue'),
    redirect: '/home',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('../views/Home.vue')
      },
      {
        path: 'class-management',
        name: 'ClassManagement',
        component: () => import('../views/ClassManagement.vue')
      },
      {
        path: 'student-management',
        name: 'StudentManagement',
        component: () => import('../views/StudentManagement.vue')
      },
      {
        path: 'department-management',
        name: 'DepartmentManagement',
        component: () => import('../views/DepartmentManagement.vue')
      },
      {
        path: 'employee-management',
        name: 'EmployeeManagement',
        component: () => import('../views/EmployeeManagement.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (to.meta.requiresAuth) {
    if (token) {
      next()
    } else {
      next('/login')
    }
  } else {
    if (token && to.path === '/login') {
      next('/home')
    } else {
      next()
    }
  }
})

export default router 
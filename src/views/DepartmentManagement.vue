<template>
  <div class="department-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-button type="primary" @click="handleAdd">新增部门</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="deptList" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="序号" width="180" />
      <el-table-column prop="name" label="部门名称" />
      <el-table-column prop="createTime" label="最后操作时间" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="30%"
      @close="handleDialogClose"
    >
      <el-form
        ref="deptFormRef"
        :model="deptForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="deptForm.name" placeholder="请输入部门名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDeptList, deleteDept, addDept, updateDept } from '@/api/dept'

// 数据列表
const deptList = ref([])
const loading = ref(false)

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const deptFormRef = ref(null)
const deptForm = ref({
  id: '',
  name: ''
})

// 表单校验规则
const rules = {
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
  ]
}

// 获取部门列表
const fetchDeptList = async () => {
  try {
    loading.value = true
    const data = await getDeptList()
    deptList.value = data
  } catch (error) {
    console.error('获取部门列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 新增部门
const handleAdd = () => {
  dialogTitle.value = '新增部门'
  deptForm.value = { name: '' }
  dialogVisible.value = true
}

// 编辑部门
const handleEdit = (row) => {
  dialogTitle.value = '编辑部门'
  deptForm.value = { ...row }
  dialogVisible.value = true
}

// 删除部门
const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除该部门吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteDept(row.id)
      ElMessage.success('删除成功')
      fetchDeptList()
    } catch (error) {
      console.error('删除失败:', error)
    }
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!deptFormRef.value) return
  
  await deptFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (deptForm.value.id) {
          await updateDept(deptForm.value)
          ElMessage.success('修改成功')
        } else {
          await addDept(deptForm.value)
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        fetchDeptList()
      } catch (error) {
        console.error('操作失败:', error)
      }
    }
  })
}

// 关闭对话框
const handleDialogClose = () => {
  if (deptFormRef.value) {
    deptFormRef.value.resetFields()
  }
}

// 页面加载时获取数据
onMounted(() => {
  fetchDeptList()
})
</script>

<style scoped>
.department-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 
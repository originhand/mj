<template>
  <div class="employee-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="searchForm.name" placeholder="请输入姓名" clearable style="width: 240px" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="searchForm.gender" placeholder="请选择性别" clearable style="width: 240px">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="primary" @click="handleAdd">新增</el-button>
          <el-button 
            type="danger" 
            @click="handleBatchDelete"
            :disabled="selectedIds.length === 0"
          >
            批量删除
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <el-table
        :data="empList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
        height="100%"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="姓名" width="180" />
        <el-table-column prop="gender" label="性别" width="180">
          <template #default="scope">
            {{ scope.row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="image" label="头像" width="180">
          <template #default="scope">
            <el-image
              style="width: 50px; height: 50px"
              :src="scope.row.image"
              fit="cover"
              :preview-src-list="[scope.row.image]"
            />
          </template>
        </el-table-column>
        <el-table-column prop="deptName" label="所属部门" width="200" />
        <el-table-column prop="position" label="职位" width="200">
          <template #default="scope">
            {{ getPositionLabel(scope.row.position) }}
          </template>
        </el-table-column>
        <el-table-column prop="hireDate" label="入职日期" width="200" />
        <el-table-column prop="updateTime" label="最后操作时间" min-width="50%" />
        <el-table-column label="操作" min-width="50%" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete([scope.row.id])">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="searchForm.page"
          v-model:page-size="searchForm.pageSize"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="800px"
      @close="handleDialogClose"
    >
      <el-form
        ref="empFormRef"
        :model="empForm"
        :rules="rules"
        label-width="100px"
      >
        <!-- 第一行：用户名和姓名 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="empForm.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="empForm.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行：性别和手机号 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="empForm.gender">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="2">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="empForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行：职位和薪资 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-select v-model="empForm.position" placeholder="请选择职位" style="width: 100%">
                <el-option label="班主任" :value="1" />
                <el-option label="讲师" :value="2" />
                <el-option label="学工主管" :value="3" />
                <el-option label="教研主管" :value="4" />
                <el-option label="咨询师" :value="5" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资" prop="salary">
              <el-input-number v-model="empForm.salary" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行：部门和入职日期 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门" prop="deptId">
              <el-select v-model="empForm.deptId" placeholder="请选择部门" style="width: 100%">
                <el-option
                  v-for="dept in deptList"
                  :key="dept.id"
                  :label="dept.name"
                  :value="dept.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期" prop="hireDate">
              <el-date-picker
                v-model="empForm.hireDate"
                type="date"
                placeholder="请选择入职日期"
                style="width: 100%"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行：头像 -->
        <el-form-item label="头像" prop="image">
          <el-upload
            class="avatar-uploader"
            :action="`${baseURL}/upload`"
            :headers="uploadHeaders"
            name="file"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :on-success="handleAvatarSuccess"
            :on-error="handleAvatarError"
          >
            <img v-if="empForm.image" :src="empForm.image" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <!-- 第六行：工作经历 -->
        <el-form-item label="工作经历">
          <div class="experience-container">
            <el-button type="primary" link @click="addExperience" class="add-btn">
              <el-icon><Plus /></el-icon>
              新增工作经历
            </el-button>
            
            <!-- 工作经历列表 -->
            <div class="experience-list">
              <div v-for="(exp, index) in empForm.exprList" :key="index" class="experience-item">
                <el-row :gutter="10">
                  <el-col :span="10">
                    <el-form-item :prop="`exprList.${index}.dateRange`" label-width="50px" label="时间">
                      <el-date-picker
                        v-model="exp.dateRange"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="YYYY-MM-DD"
                        style="width: 100%"
                        :size="small"
                        @change="handleDateRangeChange($event, exp)"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item :prop="`exprList.${index}.company`" label-width="50px" label="公司">
                      <el-input 
                        v-model="exp.company" 
                        placeholder="请输入公司名称"
                        :size="small"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item :prop="`exprList.${index}.position`" label-width="50px" label="职位">
                      <el-input 
                        v-model="exp.position" 
                        placeholder="请输入职位"
                        :size="small"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="2" class="experience-delete">
                    <el-button 
                      type="danger" 
                      link 
                      :size="small"
                      @click="removeExperience(index)"
                    >
                      <el-icon><Delete /></el-icon>
                    </el-button>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getEmpList, deleteEmp, addEmp, updateEmp, getEmpById } from '@/api/emp'
import { getDeptList } from '@/api/dept'
import { Plus, Delete } from '@element-plus/icons-vue'

// 获取环境变量中的基础URL
const baseURL = '/api'

// 上传请求头
const uploadHeaders = {
  // 如果需要认证信息，可以在这里添加
}

// 搜索表单
const searchForm = reactive({
  name: '',
  gender: '',
  begin: '',
  end: '',
  page: 1,
  pageSize: 10
})

// 日期范围
const dateRange = ref([])

// 表格数据
const empList = ref([])
const loading = ref(false)
const total = ref(0)
const selectedIds = ref([])

// 部门列表
const deptList = ref([])

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const empFormRef = ref(null)
const empForm = ref({
  username: '',
  name: '',
  gender: 1,
  image: '',
  position: '',
  salary: 0,
  hireDate: '',
  deptId: '',
  phone: '',
  exprList: []
})

// 表单校验规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  position: [
    { required: true, message: '请选择职位', trigger: 'change' }
  ],
  salary: [
    { required: true, message: '请输入薪资', trigger: 'blur' }
  ],
  hireDate: [
    { required: true, message: '请选择入职日期', trigger: 'change' }
  ],
  deptId: [
    { required: true, message: '请选择部门', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

// 获取职位标签
const getPositionLabel = (position) => {
  const positions = {
    1: '班主任',
    2: '讲师',
    3: '学工主管',
    4: '教研主管',
    5: '咨询师'
  }
  return positions[position] || '未知'
}

// 查询列表
const fetchEmpList = async () => {
  try {
    loading.value = true
    if (dateRange.value && dateRange.value.length === 2) {
      searchForm.begin = dateRange.value[0]
      searchForm.end = dateRange.value[1]
    }
    const { total: totalCount, rows } = await getEmpList(searchForm)
    empList.value = rows
    total.value = totalCount
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取部门列表
const fetchDeptList = async () => {
  try {
    const data = await getDeptList()
    deptList.value = data
  } catch (error) {
    console.error('获取部门列表失败:', error)
  }
}

// 搜索
const handleSearch = () => {
  searchForm.page = 1
  fetchEmpList()
}

// 重置
const handleReset = () => {
  searchForm.name = ''
  searchForm.gender = ''
  dateRange.value = []
  searchForm.begin = ''
  searchForm.end = ''
  searchForm.page = 1
  fetchEmpList()
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增员工'
  empForm.value = {
    username: '',
    name: '',
    gender: 1,
    image: '',
    position: '',
    salary: 0,
    hireDate: '',
    deptId: '',
    phone: '',
    exprList: []
  }
  dialogVisible.value = true
}

// 编辑
const handleEdit = async (row) => {
  try {
    dialogTitle.value = '编辑员工'
    const data = await getEmpById(row.id)
    empForm.value = data
    // 处理工作经历日期
    empForm.value.exprList = empForm.value.exprList.map(exp => ({
      ...exp,
      dateRange: [exp.startDate, exp.endDate]
    }))
    dialogVisible.value = true
  } catch (error) {
    console.error('获取员工详情失败:', error)
  }
}

// 删除
const handleDelete = async (ids) => {
  ElMessageBox.confirm(
    `确认删除选中的${ids.length}名员工吗？`, 
    '提示', 
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteEmp(ids)
      ElMessage.success('删除成功')
      // 如果当前页删除完了，就跳转到上一页
      if (empList.value.length === ids.length && searchForm.page > 1) {
        searchForm.page--
      }
      fetchEmpList()
    } catch (error) {
      console.error('删除失败:', error)
    }
  })
}

// 表格选择
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 批量删除
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的员工')
    return
  }
  handleDelete(selectedIds.value)
}

// 分页
const handleSizeChange = (val) => {
  searchForm.pageSize = val
  fetchEmpList()
}

const handleCurrentChange = (val) => {
  searchForm.page = val
  fetchEmpList()
}

// 添加工作经历
const addExperience = () => {
  empForm.value.exprList.push({
    company: '',
    position: '',
    dateRange: null,
    startDate: '',
    endDate: ''
  })
}

// 删除工作经历
const removeExperience = (index) => {
  empForm.value.exprList.splice(index, 1)
}

// 处理工作经历日期变化
const handleDateRangeChange = (dates, exp) => {
  if (dates) {
    exp.startDate = dates[0]
    exp.endDate = dates[1]
  } else {
    exp.startDate = ''
    exp.endDate = ''
  }
}

// 上传前验证
const beforeAvatarUpload = (file) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJpgOrPng) {
    ElMessage.error('上传头像图片只能是 JPG 或 PNG 格式!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 2MB!')
    return false
  }
  return true
}

// 上传成功回调
const handleAvatarSuccess = (response) => {
  if (response.code === 1) {
    empForm.value.image = response.data
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(response.msg || '上传失败')
  }
}

// 上传失败回调
const handleAvatarError = () => {
  ElMessage.error('上传失败')
}

// 提交表单
const handleSubmit = async () => {
  if (!empFormRef.value) return
  
  await empFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 处理工作经历数据
        const submitData = {
          ...empForm.value,
          exprList: empForm.value.exprList.map(({ company, position, startDate, endDate }) => ({
            company,
            position,
            startDate,
            endDate
          }))
        }

        if (empForm.value.id) {
          await updateEmp(submitData)
          ElMessage.success('修改成功')
        } else {
          await addEmp(submitData)
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        fetchEmpList()
      } catch (error) {
        console.error('操作失败:', error)
      }
    }
  })
}

// 关闭对话框
const handleDialogClose = () => {
  if (empFormRef.value) {
    empFormRef.value.resetFields()
  }
}

// 页面加载时获取数据
onMounted(() => {
  fetchEmpList()
  fetchDeptList()
})
</script>

<style scoped>
.employee-container {
  height: 100%;  /* 占满父容器高度 */
  display: flex;
  flex-direction: column;
  padding: 0;  /* 移除内边距 */
}

.search-bar {
  padding: 20px 20px 0;  /* 只保留上部和两侧内边距 */
}

.table-container {
  flex: 1;  /* 占用剩余空间 */
  display: flex;
  flex-direction: column;
  padding: 20px;
  overflow: hidden;  /* 防止内容溢出 */
}

.el-table {
  flex: 1;  /* 表格占用剩余空间 */
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.avatar-uploader {
  width: 178px;
  height: 178px;
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  line-height: 178px;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
  object-fit: cover;
}

.experience-container {
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 4px;
}

.add-btn {
  margin-bottom: 10px;
}

.experience-list {
  display: flex;
  flex-direction: column;
  gap: 10px;  /* 设置工作经历之间的间距 */
}

.experience-item {
  position: relative;
}

.experience-item :deep(.el-form-item) {
  margin-bottom: 10px;  /* 减小表单项之间的间距 */
}

.experience-item :deep(.el-form-item__label) {
  font-size: 13px;  /* 减小标签文字大小 */
}

.experience-delete {
  display: flex;
  align-items: center;
  justify-content: center;
}

.search-bar :deep(.el-form-item) {
  margin-bottom: 18px;
  margin-right: 18px;
}

.search-bar :deep(.el-form--inline .el-form-item__content) {
  margin-right: 0;
}

/* 调整表格内容的样式 */
:deep(.el-table__inner-wrapper) {
  height: 100%;  /* 表格内容区域占满高度 */
}

:deep(.el-table__body-wrapper) {
  overflow-y: auto;  /* 内容过多时显示滚动条 */
}

/* 添加按钮组的样式 */
.search-bar :deep(.el-form-item:last-child) {
  margin-right: 0;
  margin-left: auto;  /* 将按钮组靠右对齐 */
}

.search-bar :deep(.el-form-item:last-child .el-form-item__content) {
  display: flex;
  gap: 10px;  /* 按钮之间的间距 */
}
</style> 
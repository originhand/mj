import request from '@/utils/request'

// 获取部门列表
export function getDeptList() {
  return request({
    url: '/depts',
    method: 'get'
  })
}

// 删除部门
export function deleteDept(id) {
  return request({
    url: `/depts/${id}`,
    method: 'delete'
  })
}

// 添加部门
export function addDept(data) {
  return request({
    url: '/depts',
    method: 'post',
    data
  })
}

// 根据ID查询部门
export function getDeptById(id) {
  return request({
    url: `/depts/${id}`,
    method: 'get'
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/depts',
    method: 'put',
    data
  })
} 
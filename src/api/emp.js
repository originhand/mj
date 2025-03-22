import request from '@/utils/request'

// 获取员工列表
export function getEmpList(params) {
  return request({
    url: '/emps',
    method: 'get',
    params
  })
}

// 删除员工
export function deleteEmp(ids) {
  return request({
    url: '/emps',
    method: 'delete',
    params: { ids }
  })
}

// 添加员工
export function addEmp(data) {
  return request({
    url: '/emps',
    method: 'post',
    data
  })
}

// 根据ID查询员工
export function getEmpById(id) {
  return request({
    url: `/emps/${id}`,
    method: 'get'
  })
}

// 修改员工
export function updateEmp(data) {
  return request({
    url: '/emps',
    method: 'put',
    data
  })
} 
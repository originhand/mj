import request from '@/utils/request'

// 文件上传
export function uploadFile(data) {
  return request({
    url: '/upload',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
} 
import request from '@/utils/request'

export function createAPI(data) {
  return request({
    url: '/application/application',
    method: 'post',
    data
  })
}


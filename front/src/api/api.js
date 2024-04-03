import request from '@/utils/request'

export function createAPI(data) {
  return request({
    url: '/application/application',
    method: 'post',
    data
  })
}
export function getAllAPI() {
  return request({
    url: '/application/applications',
    method: 'get'
  })
}

export function getApiById(apiId) {
  return request({
    url: '/application/{apiId}/applications',
    method: 'get',
    params: { apiId }
  })
}


import request from '@/utils/request'

export function getTypeUnions() {
  return request({
    url: 'common/typeUnions',
    method: 'get'
  })
}

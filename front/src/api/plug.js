import request from '@/utils/request'
// api待定，等待后端写好接口文档
export function getFuncList() {
  return request(
    {
      url: '/appinfo/plug/functions'
    }
  )
}

export function getTypeList() {
  return request(
    {
      url: '/appinfo/plug/types'
    }
  )
}

export function getApiList() {
  return request(
    {
      url: '/appinfo/plug/apis'
    }
  )
}

export function addFunc(data) {
  return request({
    method: 'post',
    url: '/appinfo/plug/function',
    data
  })
}

export function updateFunc(data) {
  return request({
    method: 'put',
    url: `appinfo/plug/function/${data.id}`,
    data
  })
}

export function getFuncDetail(id) {
  return request({
    url: `/appinfo/plug/function/${id}`
  })
}

export function delFunc(id) {
  return request({
    method: 'delete',
    url: `appinfo/plug/function/${id}`
  })
}

export function delPlug(name) {
  return request({
    method: 'delete',
    url: 'appinfo/plug/name'
  })
}

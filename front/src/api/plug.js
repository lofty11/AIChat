import request from '@/utils/request'
// api待定，等待后端写好接口文档

export function createPlug(data) {
  return request({
    url: '/plug/plug',
    method: 'post',
    data
  })
}
export function getAllPlug() {
  return request(
    {
      url: '/plug/plugs',
      method: 'get'
    }
  )
}

export function getPlugById(plugId) {
  return request(
    {
      url: `/plug/${plugId}/plug`,
      method: 'get'
    }
  )
}

export function delPlugById(plugId) {
  return request({
    url: `/plug/${plugId}/plug`,
    method: 'delete'
  })
}

export function modifyPlugById(plugId, data) {
  return request({
    url: `/plug/${plugId}/plug`,
    method: 'put',
    data
  })
}

export function getAllFunc() {
  return request(
    {
      url: '/plug/functions',
      method: 'get'
    }
  )
}
export function getFuncById(functionId) {
  return request(
    {
      url: `/plug/id/${functionId}/function`,
      method: 'get'
    }
  )
}
export function getFuncByName(functionName) {
  return request(
    {
      url: `/plug/name/${functionName}/function`,
      method: 'get'
    }
  )
}
export function getFuncTypes() {
  return request(
    {
      url: '/plug/functionTypes',
      method: 'get'
    }
  )
}
export function createFunc(data) {
  return request({
    method: 'post',
    url: '/plug/function',
    data
  })
}

export function modifyFuncById(functionId, data) {
  return request({
    method: 'put',
    url: `/plug/${functionId}/function`,
    data
  })
}

export function delFuncById(id) {
  return request({
    method: 'delete',
    url: `/plug/${id}/function`
  })
}

export function getPlugParaById(id) {
  return request({
    method: 'get',
    url: `/plug/${id}/plugParameter`
  })
}

export function delPlugParaById(id) {
  return request({
    method: 'delete',
    url: `/plug/${id}/plugParameter`
  })
}

export function createPlugPara(plugId, data) {
  return request({
    method: 'post',
    url: `/plug/${plugId}/plugParameter`,
    data
  })
}

export function modifyPlugParaById(id, data) {
  return request({
    method: 'put',
    url: `/plug/${id}/plugParameter`,
    data
  })
}

export function createUserPara(plugId, data) {
  return request({
    method: 'post',
    url: `/plug/${plugId}/userParameter`,
    data
  })
}
export function delUserParaById(id) {
  return request({
    method: 'delete',
    url: `/plug/${id}/userParameter`
  })
}
export function modifyUserParaById(id, data) {
  return request({
    method: 'put',
    url: `/plug/${id}/userParameter`,
    data
  })
}
export function getUserParaById(id) {
  return request({
    method: 'get',
    url: `/plug/${id}/userParameter`
  })
}

export function getServiceApis() {
  return request({
    method: 'get',
    url: '/plug/serviceApis'
  })
}

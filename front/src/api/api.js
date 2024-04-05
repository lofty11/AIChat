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
    url: '/application/' + apiId + '/applicationService',
    method: 'get'
  })
}
export function getApiByName(apiName) {
  return request({
    url: '/application/' + apiName + '/application',
    method: 'get'
  })
}
export function modifyApiById(apiId, data) {
  return request({
    url: '/application/' + apiId + '/application',
    method: 'put',
    data
  })
}
export function deleteApiById(apiId) {
  return request({
    url: '/application/' + apiId + '/application',
    method: 'delete'
  })
}
export function createInputPara(apiId, data) {
  return request({
    url: '/application/' + apiId + '/input',
    method: 'post',
    data
  })
}

export function createOutputPara(apiId, data) {
  return request({
    url: '/application/' + apiId + '/output',
    method: 'post',
    data
  })
}
export function getInputParaById(paraId) {
  return request({
    url: '/application/' + paraId + '/input',
    method: 'get'
  })
}
export function getOutputParaById(paraId) {
  return request({
    url: '/application/' + paraId + '/output',
    method: 'get'
  })
}
export function deleteInputParaById(paraId) {
  return request({
    url: '/application/' + paraId + '/input',
    method: 'delete'
  })
}
export function deleteOutputParaById(paraId) {
  return request({
    url: '/application/' + paraId + '/output',
    method: 'delete'
  })
}

export function modifyOutputParaById(paraId, data) {
  return request({
    url: '/application/' + paraId + '/output',
    method: 'put',
    data
  })
}
export function modifyInputParaById(paraId, data) {
  return request({
    url: '/application/' + paraId + '/input',
    method: 'put',
    data
  })
}

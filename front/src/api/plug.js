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
      url: `/plug/${plugId}/pluginfo`,
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
export function getFuncById(funcId) {
  return request(
    {
      url: `/plug/${funcId}/funcinfo`,
      method: 'get'
    }
  )
}

export function getTypeList() {
  return request(
    {
      url: '/appinfo/plug/types',
      method: 'get'
    }
  )
}

export function createFunc(data) {
  return request({
    method: 'post',
    url: '/plug/func',
    data
  })
}

export function modifyFuncById(funcId, data) {
  return request({
    method: 'put',
    url: `/plug/${funcId}/func`,
    data
  })
}

export function delFuncById(id) {
  return request({
    method: 'delete',
    url: `/plug/${id}/func`
  })
}

export function getPlugParaById(id) {
  return request({
    method: 'get',
    url: `/plug/${id}/plugpara`
  })
}

export function delPlugParaById(id) {
  return request({
    method: 'delete',
    url: `/plug/${id}/plugpara`
  })
}

export function createPlugPara(plugId, data) {
  return request({
    method: 'post',
    url: `/plug/${plugId}/plugpara`,
    data
  })
}

export function modifyPlugParaById(plugId, data) {
  return request({
    method: 'put',
    url: `/plug/${plugId}/plugpara`,
    data
  })
}

export function createUserPara(plugId, data) {
  return request({
    method: 'post',
    url: `/plug/${plugId}/userpara`
  })
}
export function delUserParaById(id) {
  return request({
    method: 'delete',
    url: `/plug/${id}/userpara`
  })
}

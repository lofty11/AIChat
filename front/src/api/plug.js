import request from '@/utils/request'
// api待定，等待后端写好接口文档

export function createPlug(data) {
  return request({
    url: '/plug/plug',
    method: 'post',
    data
  })
}

export function getFuncList() {
  return request(
    {
      url: '/plug/plug/functions'
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

export function createFunc(data) {
  return request({
    method: 'post',
    url: '/plug/func',
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

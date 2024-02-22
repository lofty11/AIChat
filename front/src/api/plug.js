import request from '@/utils/request'

export function getFunc() {
  return request(
    {
      url: '/appinfo/plug'
    }
  )
}

// 不知道写得对不对啊，反正就是这么个意思，要取这个typelist
export function getTypeList() {
  return request(
    {
      url: '/appinfo/plug/types'
    }
  )
}

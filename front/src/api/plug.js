import request from '@/utils/request'

export function getFunc() {
  return request(
    {
      url: '/appinfo/plug'
    }
  )
}

// 不知道写得对不对啊，反正就是这么个意思，要取这个typelist，现在因为后端还没有数据，所以显示的是404
export function getTypeList() {
  return request(
    {
      url: '/appinfo/plug'
    }
  )
}

export function getApiList() {
  return request(
    {
      url: '/appinfo/plug'
    }
  )
}

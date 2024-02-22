import request from '@/utils/request'

export function getFunc() {
  return request(
    {
      url: '/appinfo/plug'
    }
  )
}

import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers.Authorization = store.getters.token
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    // const { errmsg, data, errno } = response.data
    const res = response.data
    // 不能删这个判断啊，删了都进不去系统了哈哈
    if (res.errno !== 0 && res.errno !== 1) {
      // 用户模块
      // 用户名不存在或者密码错误
      if (res.errno === 12 || res.errno === 13) {
        // to re-login
        MessageBox.confirm('用户名不存在或者密码错误', '错误', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      // 用户身份不匹配
      if (res.errno === 21) {
        MessageBox.confirm('用户身份不匹配', '错误', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      Message({
        message: res.errmsg,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.errmsg || 'Error'))
    }
    return res
  },
  error => {
    Message({
      message: error.response.data.errmsg,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service

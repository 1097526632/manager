import axios from 'axios'
import { Message } from 'element-ui'
import {getToken} from "./store";
import constants from './constants'
import router from "vue-router";
import {MessageBox} from "element-ui";
import {closeLoading} from "./utils";

// 创建axios实例
axios.defaults.withCredentials = true
const service = axios.create({
  baseURL: window._BASE_URL, // api的base_url
  timeout: 0 // 10 * 1000
})

// request拦截器
service.interceptors.request.use(config => {
  let token = getToken()
  if (token && token.id) {
    config.headers['TOKEN_INFO'] = encodeURIComponent(JSON.stringify(token)) // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// response 拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === constants.no_login) {
      MessageBox.alert('请先登录系统后再操作', '用户未登录')
      return
    } else if (res.code === constants.no_per) {
      MessageBox.alert('无权限使用此功能，请联系管理员授权', '访问受限')
      return
    }
    return res
  },
  error => {
    Message({
      message: '后台接口服务异常，请稍后重试',
      type: 'error',
      duration: 5 * 1000
    })
    closeLoading()
    console.log(error)
    return Promise.reject(error)
  }
)

export default service

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'font-awesome/scss/font-awesome.scss'
import 'element-ui/lib/theme-chalk/index.css'
import "./assets/css/main.css";
import "./assets/css/img.css";
import './components/common/directives'
import 'babel-polyfill'
import {getToken, saveToken} from "./assets/js/store";
import nofilter from "./assets/js/nofilter";
import {app_start, hasRouterPermission, loadToken} from "./assets/js/business/app_business";
import {closeLoading, showLoading, showMessage} from "./assets/js/utils";
import constatns from "./assets/js/constants";
import app_config from "./assets/js/config"

import App from './App'

Vue.config.productionTip = false
Vue.use(ElementUI, {
  size: 'small'
})

Vue.prototype.$axios = axios

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  if (to.matched.length === 0) { //匹配前往的路由不存在
    ElementUI.Message.error('页面未找到，进入主界面')
    from.name ? next({
      name: from.name
    }) : next('/')
    return
  }

  if (!app_config.app_start) {
    showLoading() // 显示loading
    app_start(function () {
      loadToken(function (res) {
        closeLoading()
        console.log(res)
        app_config.sessionId = res.sessionId
        if (res.code === constatns.success) {
          saveToken(res.data,function () {
            next()
          })
        } else {
          next({path: '/login'})
        }
      })
    }) // app 启动
    app_config.app_start = true
  }else{
    if(nofilter.indexOf(to.path) >= 0) {
      next()
      return
    }

    let token = getToken() // 获取token
    if (token) { // token存在
      if (hasRouterPermission(to.path)) { // 判断是否有路由权限
        next()
      } else {
        showMessage('无访问该资源的权限')
      }
    } else {
      next('/login') // token 不存在，跳转登录界面
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

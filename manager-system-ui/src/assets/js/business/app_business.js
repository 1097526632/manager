import {api_getToken, post} from "../api/app_api";
import constants from "../constants";
import {getToken} from "../store";
import {loadDict, loadSysParams} from "../dict";
import app_config from "../config";
import bus from '@/components/common/bus'
import {formatUrl, toTree} from "../utils";

export function loadToken(callback) {
  api_getToken().then(function (res) {
    if(callback){
      callback(res)
    }
  })
}

/**
 *app启动
 */
export function app_start(callback) {
  // 加载系统参数
  loadSysParams(function () {
    if (callback){
      callback()
    }
  })
}

/**
 * 是否含有按钮权限
 * @param permission_key
 * @returns {boolean}
 */
export function hasBtnPermission (permission_key) {
  let token = getToken()
  if ( token.id == 'u_01') {
    return true
  }
  return constants.btn_per.indexOf(permission_key)>=0
}

/**
 * 判断是否有路由权限
 * @param permission_key
 * @returns {boolean}
 */
export function hasRouterPermission (permission_key) {
  let token = getToken()
  if ( token.id == 'u_01') {
    return true
  }
  return constants.router_per.indexOf(formatUrl(permission_key))>=0
}

/**
 * 加载菜单信息
 */
export function loadMenus(callback) {
  let token = getToken()
  if (token && token.id) {
    post('sys/user/userMenu',{}).then(function (res) {
      if (res.code === constants.success) {
        constants.router_per = []
        constants.btn_per = []
        let menuData = []
        for(let item of res.data) {
          if(item.href){
            constants.router_per.push(formatUrl('/'+item.href))
          }
          if (item.permission){
            constants.btn_per = constants.btn_per.concat(item.permission.split(","))
          }
          if (item.isShow === '1') {
            menuData.push(item)
          }
        }
        app_config.menuList=toTree(menuData,{pid: 'parentId'})
        setTimeout(function () {
          bus.$emit('systemMenu', app_config.menuList)
        },300)
        if (callback) {
          callback()
        }
      }
    })
  }

}

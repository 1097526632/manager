/**
 * 存储信息
 * @param key
 * @param data
 * @param type true保存在sessionStorage
 */
import {loadDict} from "./dict";
import {loadMenus} from "./business/app_business";

export function storeInfo(key, data, type) {
  let dataInfo = JSON.stringify(data)
  if (type) {
    sessionStorage.setItem(key, dataInfo)
  } else {
    localStorage.setItem(key, dataInfo)
  }
}

export function storeLoginFrom(path) {
  storeInfo('login_from', {path:path}, true)
}
export function removeLoginFrom() {
  removeInfo('login_from')
}

/**
 * 清除数据
 * @param key
 */
export function removeInfo(key) {
  localStorage.removeItem(key)
  sessionStorage.removeItem(key)
}

/**
 * 获取信息
 * @param key
 * @returns {string}
 */
export function getInfo(key) {
  let data = localStorage.getItem(key)
  if (!data) {
    data = sessionStorage.getItem(key)
  }
  return data
}

/**
 * 获取登录信息
 * @returns {any}
 */
export function getToken() {
  let tokenInfo = getInfo('_TOKEN_INFO')
  if (tokenInfo) {
    return JSON.parse(tokenInfo)
  }
  return null
}

/**
 * 保存token
 * @param token
 */
export function saveToken(token,callback) {
  storeInfo('_TOKEN_INFO', token, true)
  loadMenus(function () {
    loadDict(function () {
      if (callback){
        callback()
      }
    })
  })
}

export function updateToken(token) {
  storeInfo('_TOKEN_INFO', token, true)
}

/**
 * 清除token
 */
export function removeToken() {
  removeInfo('_TOKEN_INFO')
}

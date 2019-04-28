/**
 * 加载字典信息
 */
import constants from "./constants";
import app_config from "./config";
import {post} from "./api/app_api";
import {setTitle} from "./utils";
import bus from '../../components/common/bus'

/**
 * 加载字典信息
 */
export function loadDict(callback) {
  post('/sys/sysDict/loadList', {page:{pageSize:-1}}).then(function (res) {
    if (res.code === constants.success) {
       let data = res.data.records
       let dictList = app_config.dictList = {}
      for(let i=0; i<data.length;i++) {
        let dict = data[i]
        dictList[dict.type] = dictList[dict.type]?dictList[dict.type]: []
        dictList[dict.type].push(dict)
      }
      if (callback) {
        callback()
      }
    }
  })
}

/**
 * 根据type获取字典信息
 * @param type
 */
export function getDict(type) {
  return app_config.dictList[type]
}

/**
 * 获取字典标签
 * @param type
 * @param key
 * @returns {*}
 */
export function getDictLabel(type, key, defaultVal) {
  let dictList = getDict(type)
  let result = dictList.find(function (res) {
    return res.value == key
  })
  defaultVal = defaultVal ? defaultVal : ''
  return result?result.label:defaultVal
}

/**
 * 加载系统参数
 */
export function loadSysParams(callback) {
  post('/sys/sysParam/loadList', {page:{pageSize:-1}}).then(function (res) {
    if (res.code === constants.success) {
      let data = res.data.records
      let paramsList = app_config.paramsList = {}
      for(let i=0; i<data.length;i++) {
        let item = data[i]
        paramsList[item.code] = item.value
      }
      bus.$set(app_config, 'appName', paramsList['productName'])
      app_config.pageSize = parseInt(paramsList['page.pageSize']?paramsList['page.pageSize']:20)
      setTitle(app_config.appName)
      if (callback){
        callback()
      }
    }
  })
}

/**
 * 获取参数信息
 * @param code
 * @returns {*}
 */
export function getParams(code) {
  return app_config.paramsList[code]
}

/**
 * 获取参数值
 * @param code
 * @returns {*}
 */
export function getParamValue(code) {
  return getParams(code) ? getParams.value : ''
}

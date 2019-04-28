import request from "../request";
import constants from "../constants";

export function post (url, params) {
  return  request({
    url: constants.admin_url + "/" + url,
    method: 'post',
    data: params
  })
}
/**
 * 首次获取token信息
 */
export function api_getToken() {
  return post('/token/getToken', {});
}

/**
 * 用户与密码校验
 * @param data
 */
export function api_userAuth(data) {
  return post('/token/authUser', data);
}

/**
 * 用户退出
 */
export function api_userLogout() {
  return post('/token/logout', {})
}

/**
 * 加载列表信息
 * @param that
 * @param url
 * @param params
 */
export  function loadList (that, url, params, callback, target) { // 加载列表信息
  let loadIns = that.$loading({
    target: target,
    text: '加载数据中...'})
  post(url, params).then(function (res) {
    loadIns.close()
    if (callback) {
      callback(res)
    } else {
      if (res.code === constants.success) {
        that.params.page.totalRecord = res.data.totalRecord
        that.tableData = res.data.records
      } else {
        that.$message.error('加载信息失败')
      }
    }

  }).catch(function (res) {
    console.log(res)
    loadIns.close()
    that.$message.error('加载信息错误')
  })
}



/**
 * 删除信息
 * @param that
 * @param url
 * @param data
 */
export function deleteInfo (that, url, data, title, callback) {
  if(!title||title === ''){
    title='确认删除信息吗?'
  }
  that.$confirm(title, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    let loadIns = that.$loading({
      target: '.table-wrapper',
      text: '删除数据中...'})
    post(url, data).then(function (res) {
      loadIns.close()
      if (res.code === constants.success) {
        if (callback) {
          callback()
        } else {
          try{
            that.loadList()
          }catch (e) {
            console.log(e)
          }
        }
      } else {
        that.$message.error('删除信息失败')
      }

    }).catch(function (res) {
      console.log(res)
      loadIns.close()
      that.$message.error('删除信息错误')
    })

  })
}

/**
 * 保存信息
 * @param that
 * @param formName
 * @param url
 * @param data
 */
export function saveInfo (that, formName, url, data, callback) {
  that.$refs[formName].validate((valid) => {
    if (valid) {
      let loadIns = that.$loading({text: '保存数据中...'})
      post(url,data).then(function (res) {
        that.dialogVisible = false
        loadIns.close()
        if (res.code === constants.success) {
          that.$message.success('保存信息成功')
          that.$emit('updateData',that.data)
        } else {
          that.$message.error('保存信息失败')
        }
        if (callback) {
          callback(res)
        }
      }).catch(function (res) {
        console.log(res)
        loadIns.close()
        that.$message.error('保存信息错误')
      })
    }
  })
}

/**
 * 保存数据
 * @param url
 * @param data
 * @param callback
 */
export function saveData (url, data, callback) {
  post(url,data).then(function (res) {
    if (res.code === constants.success) {
      if (callback) {
        callback(res,true)
      }
    } else {
      if (callback) {
        callback(res,false)
      }
    }

  }).catch(function (res) {
    console.log(res)
  })
}


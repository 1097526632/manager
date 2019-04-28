import { Message, Loading } from "element-ui";
import axios from 'axios'
/**
 * 加载loading
 * @param message
 * @param option
 * @returns {ElLoadingComponent}
 */
export function showLoading (message, option) {
  let _option = option ? option: {}
  if (message && message !== '') {
    _option.text = message
  }
   let loadInst = Loading.service(_option)
  window.loadInst = loadInst
  return loadInst
}

/**
 * 隐藏加载
 * @param loadIns
 */
export function closeLoading(loadIns) {
  let _loadIns = loadIns ? loadIns: window.loadInst

  if (_loadIns) {
    _loadIns.close()
  }
}

/**
 * 显示提示信息
 * @param message
 * @param type
 * @param option
 */
export function showMessage(message, type, option) {
  let _option = option ? option: {}
  if (message && message !== '') {
    _option.message = message
  }
  if(type && type !== '') {
    _option.type = type
  }
  let messageInst = Message(_option)
  window.messageInst = messageInst
  return messageInst
}

/**
 * 关闭信息提示
 * @param messageInst
 */
export function closeMessage(messageInst) {
  let _messageInst = messageInst ? messageInst: window.messageInst
  if (_messageInst) {
    _messageInst.close ()
  }
}

/**
 * 设置标题
 * @param title
 */
export function setTitle(title) {
  window.document.title = title
}

/**
 * 格式化URL
 * @param url
 * @returns {*}
 */
export function formatUrl(url) {
  if(url){
    let subUrl = url
    let result='';
    if (url.indexOf('://')>0) {
      subUrl = url.substring(url.indexOf("://")+3)
      result=url.substring(0,url.indexOf("://")+3)
    }
    while(subUrl.indexOf("//")>=0){
      subUrl=subUrl.replace(/\/\//g,"\/")
    }
    result=result+subUrl
    return result
  }
  return ''
}

export function getUserfiles(filePath) {
 return formatUrl(window._BASE_URL+'/userfiles/'+filePath)
}

export function toTree (list, opt){
  let resultList = []
  let _opt = Object.assign({id:'id',pid: 'pid'}, opt)
  for(let i=0;i<list.length;i++){
    let treeNode=list[i];
    for(let j=0;j<list.length;j++){
      let subTreeObj=list[j];
      if(treeNode[_opt.id]==subTreeObj[_opt.pid]){
        if(treeNode.children==null){
          treeNode.children=[];
        }
        subTreeObj.__parent = treeNode
        treeNode.children.push(subTreeObj);
      }
    }
  }
  for(let i=0;i<list.length;i++){
    if(!list[i].__parent){
      resultList.push(list[i])
    }
  }
  return resultList
}

export function replaceObj(target, source) {
  for(let item in source) {
    target[item] = source[item]
  }
}

export function downloadFile(url, data, fileName) {
  axios({
    method: 'post',
    url: url,
    data: data,
    responseType: 'blob'
  }).then(response => {
    if (!response.data) {
      return
    }
    let blob = new Blob([response.data]);
    let downloadElement = document.createElement('a');
    let href = window.URL.createObjectURL(blob); //创建下载的链接
    downloadElement.href = href;
    if(!fileName&&fileName!=''){
      fileName= decodeURIComponent(response.headers['filename'])
    }
    downloadElement.download = fileName; //下载后文件名
    document.body.appendChild(downloadElement);
    downloadElement.click(); //点击下载
    document.body.removeChild(downloadElement); //下载完成移除元素
    window.URL.revokeObjectURL(href); //释放掉blob对象
  }).catch((error) => {

  })
}

export  function getIcon (item) {
  if (item.type=='category') {
    return 'fa-folder'
  } else{
    if (item.fileType=='txt') {
      return 'fa-file-text'
    } else if (item.fileType=='pdf') {
      return 'fa-file-pdf-o'
    } else if (item.fileType=='zip') {
      return 'fa-file-zip-o'
    } else if (item.fileType=='doc') {
      return 'fa-file-word-o'
    } else if (item.fileType=='xls') {
      return 'fa-file-excel-o'
    } else if (item.fileType=='ppt') {
      return 'fa-file-powerpoint-o'
    } else if (item.fileType=='vsd') {
      return 'fa-file'
    } else if (item.fileType=='apk') {
      return 'fa-android'
    } else if (item.fileType=='exe') {
      return 'fa-windows'
    } else if (item.fileType=='ipa') {
      return 'fa-apple'
    } else if (item.fileType=='video') {
      return 'fa-file-video-o'
    } else if (item.fileType=='music') {
      return 'fa-file-audio-o'
    }
  }
  return 'fa-file'
}

export function S4() {
  return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
export function uuid() {
  return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());
}

export function isImage(fileName) {
  let suffixIndex=fileName.lastIndexOf(".");
  let suffix=fileName.substring(suffixIndex+1).toUpperCase();
  if(suffix=="BMP"||suffix=="JPG"||suffix=="JPEG"||suffix=="PNG"||suffix!="GIF"){
      return true;
  }
  return false
}

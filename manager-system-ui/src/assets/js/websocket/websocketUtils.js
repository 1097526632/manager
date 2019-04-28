import appSocket from "./appSocket";
import {formatUrl, uuid} from "../utils";
import {getToken} from "../store";
import app_config from "../config";

export function initWebSocket(){ //初始化weosocket
  connection()
  sendData('user/register',{}) // 注册用户
}

export function connection() {
  let wsuri =(window.location.protocol=='https:'?'wss:':'ws:')+"//" + window.location.hostname+(window.location.port!=''?':'+window.location.port:'')+formatUrl("/"+window._BASE_URL+"/ws")
  appSocket.websocket = new WebSocket(wsuri);
  appSocket.connection=true// 连接中
  appSocket.websocket.onopen = function() {
    appSocket.opened = true
    socketKeeAlive()
    appSocket.connection = false
    console.log('socket open' );
  }
  appSocket.websocket.onerror = function(e) {
    console.log(e)
    closeWebSocket()
    appSocket.connection = false
  };
  appSocket.websocket.onmessage = messageHandler;
  appSocket.websocket.onclose =function() {
    closeWebSocket()
  }
  window.onunload = function (){
    closeWebSocket()
  }
  window.onbeforeunload = function (){
    closeWebSocket()
  }
}

export function socketKeeAlive() {
  appSocket.keepAlive = setInterval(function () {
    if(appSocket.opened){
      sendData('/app/heartJump',{},function (res) {

      })
    } else{
      if (!appSocket.connection){ // 判断是否在连接中
        connection()
      }
    }
  },10*1000)
}

export function closeWebSocket() {
  appSocket.opened = false
  appSocket.websocket.close()
}

export function destorySocket() {
  closeKeepAlive()
  closeWebSocket()
}

export function closeKeepAlive() {
  clearInterval(appSocket.keepAlive)
}

export function messageHandler(e) {
  if (e.data){
    let data = JSON.parse(e.data)
    if(data.requestId&&appSocket.messageQueue[data.requestId]){
      appSocket.messageQueue[data.requestId](data)
      delete appSocket.messageQueue[data.requestId]
    } else {
      let item =JSON.parse(data.data)
      if(appSocket.registerUrl[formatUrl(item.url)]){
        appSocket.registerUrl[formatUrl(item.url)](item)
      }
    }
  }
}

/**
 * 注册处理的URL
 * @param url
 * @param callback
 */
export function registerSocketUrl(url,callback) {
  appSocket.registerUrl[formatUrl('/'+url)] = callback
}
export function sendData(url,data,callback) {
  let _data = {requestId: uuid(),url:url,sessionId: app_config.sessionId, userId:getToken().id,data: JSON.stringify(data)}
  if(callback){
    appSocket.messageQueue[_data.requestId]=callback
  }
  sendMessage(JSON.stringify(_data))
}

export function sendMessage(message, callback){
  waitForConnection(function () {
    appSocket.websocket.send(message);
    if (typeof callback != 'undefined') {
      callback();
    }
  }, 1000);
}

// 等待连接
export function waitForConnection (callback, interval) {
  if (appSocket.websocket.readyState === 1) {
    callback();
  } else {
    setTimeout(function () {
      waitForConnection(callback, interval);
    }, interval);
  }
};

import {getToken} from "../store";
import app_config from "../config";
const appSocket = {
  websocket: {},
  opened: false,
  connection:false, // 连接中
  sessionId: app_config.sessionId,
  userId: getToken().id,
  messageQueue:{},
  registerUrl:{}
}
export default appSocket

<template>
  <div class='main-wrapper'>
    <nav-head></nav-head>
    <nav-sidebar></nav-sidebar>
    <div class="content-box" :class="{'content-collapse':collapse,'tags': showTags}">
      <tags v-if="showTags"></tags>
      <div class="content">
        <transition name="move" mode="out-in">
          <keep-alive :include="tagsList">
            <router-view></router-view>
          </keep-alive>
        </transition>
      </div>
    </div>
  </div>
</template>

<script>
  import navHead from './nav-head'
  import navSidebar from './nav-sidebar'
  import tags from './tags'
  import bus from '../../common/bus'
  import {destorySocket, initWebSocket} from "../../../assets/js/websocket/websocketUtils";
  import {getToken} from "../../../assets/js/store";
export default {
  name: 'main',
  components: {
    navHead, navSidebar, tags
  },
  computed:{
    showTags () {
      let token = getToken()
      if(token.personality){
       let personality = JSON.parse(token.personality)
        return personality.menuType === 1
      }
      return true
    }
  },
  data(){
    return {
      tagsList: [],
      collapse: false
    }
  },
  created(){
    bus.$on('collapse', msg => {
      this.collapse = msg;
    })

    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
    bus.$on('tags', msg => {
      let arr = [];
      for(let i = 0, len = msg.length; i < len; i ++){
        msg[i].name && arr.push(msg[i].name);
      }
      this.tagsList = arr;
    })
    try{
      initWebSocket()
    }catch (e) {
      console.log(e)
    }
  },
  beforeDestroy(){
    destorySocket() // 停掉websocket
  }
}
</script>

<style scoped lang='scss'>
.main-wrapper{
    height:100%;
  background: #324157;
}
</style>

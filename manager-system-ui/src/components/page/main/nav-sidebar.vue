<template>
<div class='nav-sidebar-wrapper'>
  <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
           text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
    <nav-bar :menuItems="menuItems"></nav-bar>
  </el-menu>
  <div class="user-num">在线{{userNum}}人</div>
</div>
</template>

<script>
  import bus from '../../common/bus'
  import app_config from "../../../assets/js/config";
  import navBar from './nav-bar'
  import {registerSocketUrl} from "../../../assets/js/websocket/websocketUtils";
export default {
  name: 'nav-sidebar',
  components: {
    navBar
  },
  data() {
    return {
      collapse: false,
      menuItems: app_config.menuList,
      userNum:0
    }
  },
  computed:{
    onRoutes(){
      return this.$route.path.replace('/','');
    }
  },
  created(){
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg;
    })
    bus.$on('systemMenu', content => {
      this.menuItems = content
    })
    let that = this
    registerSocketUrl('/app/noticeUserNum',function (res) {
      that.userNum = res.data
    })
  }
}
</script>

<style scoped lang='scss'>
.nav-sidebar-wrapper{
  display: block;
  position: absolute;
  left: 0;
  top: 50px;
  bottom:0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar{
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse){
  width: 150px;
}
.sidebar > ul {
  height:100%;
}
  .el-menu{
    height: calc(100% - 25px);
    overflow:auto;
  }
  .user-num{
    color: #fff;
    text-align: center;
  }
</style>

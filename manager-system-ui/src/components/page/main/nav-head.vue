<template>
<div class='nav-head-wrapper'>
  <!-- 折叠按钮 -->
  <div class="collapse-btn" @click="collapseChage">
    <i class="el-icon-menu"></i>
  </div>
  <div class="head-content">
    <div class="logo">{{appName}}</div>
  </div>
  <div class="header-right">
    <div class="header-user-con">
      <!-- 全屏显示 -->
      <div class="btn-fullscreen" @click="handleFullScreen">
        <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
          <i class="el-icon-rank"></i>
        </el-tooltip>
      </div>
      <!-- 用户名下拉菜单 -->
      <el-dropdown class="user-name" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}} <i class="el-icon-caret-bottom"></i>
                    </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item  command="userhead">
            <img :src="getUserfiles(photo)" class="head-img" v-if="photo&&photo!=''"/>
            <img src="@/assets/img/header.png"  class="head-img" v-else />
          </el-dropdown-item>
          <el-dropdown-item divided  command="personInfo">个人信息</el-dropdown-item>
          <el-dropdown-item divided  command="loginout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</div>
</template>

<script>
  import bus from '../../common/bus'
  import {getToken, removeToken} from "../../../assets/js/store";
  import {api_userLogout} from "../../../assets/js/api/app_api";
  import constants from "../../../assets/js/constants";
  import app_config from "../../../assets/js/config";
  import {getUserfiles} from "../../../assets/js/utils";
export default {
  name: 'nav-head',
  data() {
    return {
      appName: app_config.appName,
      collapse: false,
      fullscreen: false,
      name: '',
      message: 2,
      token: getToken()
    }
  },
  computed:{
    username(){
      return this.token.name ? this.token.name : this.token.username
    },
    photo () {
      return this.token.photo
    }
  },
  methods:{
    getUserfiles(filePath){
      return getUserfiles(filePath)
    },
    // 用户名下拉菜单选择事件
    handleCommand(command) {
      let that = this
      if(command == 'loginout'){
        api_userLogout().then(function (res) {
          if (res.code === constants.success) {
            removeToken()
            that.$router.push('/login');
          } else {
            that.$message.error('登出系统失败')
          }
        })
      } else if(command=='personInfo'){
        that.$router.push('/personInfo');
      }
    },
    // 侧边栏折叠
    collapseChage(){
      this.collapse = !this.collapse;
      bus.$emit('collapse', this.collapse);
    },
    // 全屏事件
    handleFullScreen(){
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
          // IE11
          element.msRequestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    }
  },
  mounted(){
    if(document.body.clientWidth < 1500){
      this.collapseChage();
    }
  },
  created(){
    bus.$on('updateToken',data => {
      this.token = data
    })
  }
}
</script>

<style scoped lang='scss'>
  .nav-head-wrapper {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 50px;
    font-size: 22px;
    color: #fff;
    display: flex;
  }
  .collapse-btn{
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 50px;
  }
  .head-content{
    flex:1;
    height:100%;
    display: flex;
    align-items: center;
  }
  .header .logo{
    float: left;
    width:250px;
    line-height: 50px;
  }
  .header-right{
    padding-right: 50px;
  }
  .header-user-con{
    display: flex;
    height: 50px;
    align-items: center;
  }
  .btn-fullscreen{
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
  }
  .btn-bell, .btn-fullscreen{
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
  }
  .btn-bell-badge{
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
  }
  .btn-bell .el-icon-bell{
    color: #fff;
  }
  .user-name{
    margin-left: 10px;
  }
  .user-avator{
    margin-left: 20px;
  }
  .user-avator img{
    display: block;
    width:40px;
    height:40px;
    border-radius: 50%;
  }
  .el-dropdown-link{
    color: #fff;
    cursor: pointer;
  }
  .el-dropdown-menu__item{
    text-align: center;
    width:100px;
  }
  .head-img{
    height:60px;
    border-radius: 50%;
  }
</style>

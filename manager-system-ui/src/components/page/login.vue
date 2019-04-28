<template>
<div class='login-wrapper'>
  <div class="login-container">
    <div class="logo-info">
      {{appName}}登录
    </div>
    <el-form class="login-form" ref="loginForm" :rules="rules" :model="loginForm" label-width="0"  @submit.native.prevent >
      <el-form-item prop="username">
        <el-input size="medium" class="text-info" v-model="loginForm.username" placeholder="请输入用户名">
          <i slot="prefix" class="fa fa-user"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input  type="password"  class="text-info"  size="medium"  v-model="loginForm.password"  placeholder="请输入密码">
          <i slot="prefix" class="fa fa-lock"></i>
        </el-input>
      </el-form-item>
      <el-button type="danger" native-type="submit" class="lg-btn" @click="handleLogin()">登录</el-button>
    </el-form>
  </div>
</div>
</template>

<script>
  import {getInfo, saveToken} from "../../assets/js/store";
import {api_userAuth} from "../../assets/js/api/app_api";
import constants from "../../assets/js/constants";
import {getParamValue} from "../../assets/js/dict";
import app_config from "../../assets/js/config";
import bus from '../common/bus'

export default {
  name: 'login',
  data () {
    return {
      appName: app_config.appName,
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    handleLogin () {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          let that = this
          let loadIns = this.$loading({text:'正在登录中...'})
          api_userAuth(this.loginForm).then(function (res) {
            loadIns.close()
            if (res.code === constants.success) {
              saveToken(res.data)
              let loginFrom = getInfo('login_from')
              console.log('loginFrom',loginFrom)
              if (loginFrom && loginFrom.path!='') {
                that.$router.push({path: loginFrom.path})
              } else {
                that.$router.push({path: '/main'})
              }

            } else {
              that.$message.error('用户名或密码错误')
            }
          }).catch(function () {
            loadIns.close()
          })
        }
      })

    }
  },
  created() {
    bus.$on('appName', value => {
      this.appName = value;
    })
    let loginFrom = getInfo('login_from')
    console.log('loginFrom',loginFrom)
  }
}
</script>

<style scoped lang='scss'>
.login-wrapper{
    height:100%;
  background-image: url("../../assets/img/bg.jpg");
  background-size: 100% 100%;
  overflow: hidden;
  .bg{
    position: absolute;
    left:0;
    top:0;
    bottom: 0;
    right:0;
    z-index: -1;
  }
  .login-container{
    width:350px;
    margin:130px auto 0;
    padding:30px;
    background: rgba(0, 0, 0, 0.66);

    .logo-info{
      font-size: 20px;
      color:#fff;
      font-weight: bold;
      height:40px;
      line-height: 40px;
      margin-bottom:20px;
      text-align: center;
    }
    .lg-btn{
      width:100%;
      height:46px;
      margin-top:20px;
    }
    .fa{
      font-size: 24px;
      margin-top: 5px;
    }
  }
}
</style>

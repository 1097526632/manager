<template>
<div class='personInfo-wrapper'>
  <el-row>
    <el-col :span="12">
        <div class="panel">
          <div class="panel-title">
            <span class="title">个人信息</span>
            <span class="tools">
              <el-button type="primary" size="mini" circle icon="fa fa-save" title="保存" @click="saveUserInfo()"></el-button>
            </span>
          </div>
          <div class="panel-content">
            <el-form ref="form" :model="form" :rules="rules"  label-width="120px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="头像:" prop="photo" style="height:90px;">
                    <fileupload ref="fileupload" :value.sync="form.photo" :default-img="require('@/assets/img/header.png')" :css-style="'width:120px;height:120px;'"></fileupload>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="工号:" prop="no">
                    {{form.no}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="登录名:" prop="username">
                    {{form.username}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="姓名:" prop="name">
                    <el-input v-model="form.name"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="密码:" prop="password">
                    <el-input type="password" v-model="form.password" placeholder="不修改密码请留空"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item type="password" label="确认密码:" prop="confirmPassword">
                    <el-input v-model="form.confirmPassword"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="邮箱:" prop="email">
                    <el-input v-model="form.email"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="手机:" prop="mobile">
                    <el-input v-model="form.mobile"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-form-item label="个性设置:">
                  <el-radio-group v-model="personality.menuType">
                    <el-radio :label="0">单页模式</el-radio>
                    <el-radio :label="1">标签模式</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="最后登陆IP:">
                    {{form.loginIp}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="最后登陆时间:">
                    {{form.loginDate}}
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </div>
    </el-col>
    <el-col :span="12">
      <div class="panel">
        <div class="panel-title">操作日志</div>
        <div class="panel-content">
          <div class="params-wrapper">
            <el-form :inline="true" :model="params" class="demo-form-inline" @submit.native.prevent>
              <el-form-item label="时间段:">
                <el-date-picker
                  v-model="params.queryBeginDate"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择日期时间">
                </el-date-picker>
                至
                <el-date-picker
                  v-model="params.queryEndDate"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择日期时间">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-button type="primary"  native-type="submit" @click="loadList()"><i class="el-icon-search"></i> 查询</el-button>
              </el-form-item>
            </el-form>
          </div>
          <div class="table-wrapper">
            <el-table
              ref="logTable"
              :data="tableData"
              stripe
              :border="true"
              height="calc(100% - 40px)"
              style="width: 100%">
              <el-table-column
                label="序号"
                width="50"
              >
                <template slot-scope="scope">
                  {{(params.page.page-1)*params.page.pageSize + scope.$index + 1}}
                </template>
              </el-table-column>
              <el-table-column
                prop="title"
                label="标题"
                :show-overflow-tooltip="true"
              >
              </el-table-column>
              <el-table-column
                prop="remoteAddr"
                label="IP地址"
                :show-overflow-tooltip="true"
              >
              </el-table-column>
              <el-table-column
                prop="createDate"
                label="操作时间"
              >
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handlePageChange"
              @current-change="handlePageChange"
              :pager-count="5"
              :current-page.sync="params.page.page"
              :page-sizes="[10, 20, 30, 50, 100]"
              :page-size.sync="params.page.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="params.page.totalRecord">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</div>
</template>

<script>
import {getToken, updateToken} from "../../../../assets/js/store";
import Fileupload from "../../../common/plugin/fileupload/fileupload";
import {loadList, saveInfo} from "../../../../assets/js/api/app_api";
import bus from '@/components/common/bus'
import {nextDate, prevDate} from "../../../../assets/js/dateutils";
import app_config from "../../../../assets/js/config";
export default {
  name: 'personInfo',
  components: {
    Fileupload
  },
  data () {
    return {
      form: getToken(),
      rules: {
        confirmPassword: [
          {validator: this.validatePassword, trigger: 'blur'}
        ]
      },
      tableData: [],
      personality: {
        menuType: 0
      },
      params: {
        queryBeginDate: prevDate(new Date(), 7),
        queryEndDate: nextDate(new Date(),1),
        page: {
          totalRecord: 0,
          pageSize: app_config.pageSize?app_config.pageSize:20,
          page: 1
        }
      }
    }
  },
  methods: {
    validatePassword (rule, value, callback){
      if (this.form.password!='' && value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    },
    saveUserInfo(){
      let that = this
      this.form.personality = JSON.stringify(this.personality)
      saveInfo(this, 'form', 'sys/user/save', this.form, function (res) {
        let copyToken = Object.assign({},that.form)
        updateToken(that.form)
        bus.$emit('updateToken',copyToken)
      })
    },
    handlePageChange (val) {
      this.loadList()
    },
    loadList () { // 加载列表信息
      this.params.createBy = getToken().id
      loadList(this, 'sys/sysLog/loadList', this.params)
    }
  },
  mounted () {
    this.$refs['fileupload'].initUpload(this.form.photo,{'categoryId':'userheader','categoryName':'用户头像'})
    this.loadList()
    console.log(getToken())
    this.personality = Object.assign(this.personality, getToken().personality)
  }
}
</script>

<style scoped lang='scss'>
.personInfo-wrapper{
    height:100%;
  background: #fff;
  .el-row, .el-col,.panel{
    height:100%;
  }
  .panel-content{
    height: calc(100% - 60px);
    .table-wrapper{
      height: calc(100% - 60px);
    }
  }
}
</style>

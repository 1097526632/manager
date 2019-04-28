<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogVisible"
            width="70%"
            v-dialogDrag
            @closed="closeFormDialog"
            :close-on-click-modal="false"
    >
        <div class="form-wrapper">
            <el-form ref="form" :model="form" :class="showFooter === false ?'view':''" :rules="rules"  label-width="120px">
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="头像:" prop="photo" style="height:60px;">
                        <fileupload ref="fileupload" :value.sync="form.photo" :default-img="require('@/assets/img/header.png')"></fileupload>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="归属部门:" prop="officeName">
                        <el-input v-model="form.officeName" @click.native="showTree()" readonly></el-input>
                        <tree-dialog ref="treeDialog" :loadUrl="'sys/sysOffice/loadList'" :rootParams="{}" :lazy="false" @treeConfirm="treeConfirm"></tree-dialog>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="工号:" prop="no">
                        <el-input v-model="form.no"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
              <el-row>
                    <el-col :span="12">
                        <el-form-item label="登录名:" prop="username">
                            <el-input v-model="form.username"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="姓名:" prop="name">
                        <el-input v-model="form.name"></el-input>
                      </el-form-item>
                    </el-col>
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
                  <el-form-item label="是否可登录:" prop="loginFlag">
                    <el-radio-group v-model="form.loginFlag">
                      <el-radio :label="'1'">启用</el-radio>
                      <el-radio :label="'0'">禁用</el-radio>
                    </el-radio-group>
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
        <span slot="footer" class="dialog-footer" v-if="showFooter">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmInfo()">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
import {saveInfo} from "@/assets/js/api/app_api";
import {hasBtnPermission} from "@/assets/js/business/app_business";
import Fileupload from "../../../common/plugin/fileupload/fileupload";
import TreeDialog from "../../../common/plugin/treedialog/treeDialog";
import {post} from "../../../../assets/js/api/app_api";
import constants from "../../../../assets/js/constants";

export default {
    name: 'form',
  components: {TreeDialog, Fileupload},
  data () {
        return {
            title: '',
            dialogVisible: false,
            showFooter: true,
            form: {},
            oldUsername: '',
            rules: {
              officeName: [
                    {required: true, message: '请输入所属机构', trigger: 'blur'}
                ],
                username: [
                    {required: true, message: '请输入登录名', trigger: 'blur'},
                  {validator: this.validateUsername , trigger: 'blur'}
                ],
              confirmPassword: [
                {validator: this.validatePassword , trigger: 'blur'}
              ],
                no: [
                    {required: true, message: '请输入工号', trigger: 'blur'}
                ],
                name: [
                    {required: true, message: '请输入姓名', trigger: 'blur'}
                ],
                loginFlag: [
                    {required: true, message: '请输入是否可登录', trigger: 'blur'}
                ]
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
      validateUsername (rule, value, callback){
        if (this.oldUsername !== value) {
          post('sys/user/findByUsername',{username: value}).then(function (res) {
            if (res.code === constants.success){
              if(res.data && res.data.id){
                callback(new Error('用户名已经存在，请更换用户名'));
              } else {
                callback()
              }
            }
          }).catch(function () {
            callback(new Error('服务错误，校验失败'));
          })
        } else {
          callback();
        }
      },
      showTree () {
        this.$refs['treeDialog'].showDialog('选择组织机构')
      },
      treeConfirm (node) {
        this.$set(this.form,'officeId', node.data.id)
        this.$set(this.form,'officeName', node.data.name)
      },
        confirmInfo () {
            saveInfo(this, 'form', 'sys/user/save', this.form)
        },
        hasBtnPermission (key) {
            return hasBtnPermission(key)
        },
        showDialog(title, visiable, data, footFlag) {
            this.title = title
            this.dialogVisible = visiable
            this.form = Object.assign({}, data)
          this.oldUsername = this.form.username?this.form.username: ''
            this.showFooter = footFlag? footFlag === 0 : true
          let that = this
          setTimeout(function () {
            that.$refs['fileupload'].initUpload(that.form.photo,{'categoryId':'userheader','categoryName':'用户头像'})
          },10)

        },
        closeFormDialog (){
            this.$refs['form'].resetFields()
          this.form = {}
        }
    }
}
</script>

<style scoped lang='scss'>
    .form-wrapper{
        min-height:300px;
        height:100%;
        padding:5px 10px;
    }
</style>

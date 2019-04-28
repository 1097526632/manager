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
                        <el-form-item label="名称:" prop="name">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                    </el-col>
                  <el-col :span="12">
                    <el-form-item label="状态:" prop="useable">
                      <el-radio-group v-model="form.useable">
                        <el-radio :label="'1'">启用</el-radio>
                        <el-radio :label="'0'">禁用</el-radio>
                      </el-radio-group>
                    </el-form-item>
                  </el-col>
                </el-row>
              <el-row>
                  <el-col :span="12">
                    <el-form-item label="功能范围:" prop="dataScope">
                      <div class="fun-wrapper">
                        <el-tree
                          ref="menuTree"
                          :data="menuData"
                          show-checkbox
                          node-key="id"
                          :default-checked-keys="roleMenu"
                          :props="defaultProps">
                        </el-tree>
                      </div>
                    </el-form-item>
                  </el-col>
                    <el-col :span="12">
                        <el-form-item label="数据范围:" prop="dataScope">
                          <el-select
                            v-model="form.dataScope"
                           @change="dataScopeChange"
                           placeholder="请选择">
                            <el-option
                              v-for="item in getDataScope()"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                          <div class="data-wrapper" v-if="showDataScope">
                            <el-tree
                              ref="officeTree"
                              :data="roleData"
                              show-checkbox
                              node-key="id"
                              :default-checked-keys="roleOffice"
                              :props="defaultProps">
                            </el-tree>
                          </div>
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
import {loadList} from "../../../../assets/js/api/app_api";
import {toTree} from "../../../../assets/js/utils";
import {getDict} from "../../../../assets/js/dict";

export default {
    name: 'form',
    data () {
        return {
            title: '',
            dialogVisible: false,
            showFooter: true,
            form: {},
          showDataScope: false,
          defaultProps: {
            children: 'children',
            label: 'name'
          },
          menuData: [],
          roleMenu: [],
          roleData: [],
          roleOffice:[],
            rules: {
                name: [
                    {required: true, message: '请输入角色名称', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
      getDataScope () {
        return getDict('sys_data_scope')
      },
      dataScopeChange (value) {
        if (value === '9') {
          this.showDataScope = true
          this.roleOffice = []
          this.$refs['officeTree'].setCheckedNodes([])
        } else {
          this.showDataScope = false
        }
      },
        confirmInfo () {
        this.getTreeData()
            saveInfo(this, 'form', 'sys/sysRole/save', this.form)
        },
        hasBtnPermission (key) {
            return hasBtnPermission(key)
        },
        showDialog(title, visiable, data, footFlag) {
            this.title = title
            this.dialogVisible = visiable
            this.form = Object.assign({}, data)
            this.showFooter = footFlag? footFlag === 0 : true
          if (data.dataScope === '9') {
            this.showDataScope = true
          } else {
            this.showDataScope = false
          }
          this.loadRoleMenu()
          this.loadRoleOffice()
        },
        closeFormDialog (){
            this.$refs['form'].resetFields()
          this.roleMenu =this.roleMenu.splice(0,this.roleMenu.length)
          this.roleOffice=this.roleOffice.splice(0,this.roleOffice.length)
        },
      loadMenus () {
          let that = this
          loadList(this,'menu/loadList',{}, function (res) {
            let datas = res.data.records ? res.data.records : res.data
            that.menuData = toTree(datas,{pid:'parentId'})
          }, 't')
      },
      loadRoleMenu () {
        let that = this
        loadList(this,'sys/sysRole/loadRoleMenu',this.form, function (res) {
          let datas = res.data.records ? res.data.records : res.data
          that.roleMenu= []
          for(let item of datas) {
            let node = that.$refs['menuTree'].getNode(item.id)
            if (node.childNodes&&node.childNodes.length){
              continue;
            }
            that.roleMenu.push(item.id)
          }
          that.$refs['menuTree'].setCheckedNodes(that.roleMenu)
        },'t')
      },
      loadOffice () {
        let that = this
        loadList(this,'sys/sysOffice/loadList',{}, function (res) {
          let datas = res.data.records ? res.data.records : res.data
          that.roleData = toTree(datas,{pid:'parentId'})
        },'t')
      },
      loadRoleOffice () {
        let that = this
        if (this.showDataScope) {
          loadList(this,'sys/sysRole/loadRoleOffice',this.form, function (res) {
            let datas = res.data.records ? res.data.records : res.data
            that.roleOffice= []
            for(let item of datas) {
              that.roleOffice.push(item.id)
            }
            that.$refs['officeTree'].setCheckedNodes(that.roleOffice)
          },'t')
        }

      },
      getTreeData () {
        let menuDatas = this.$refs['menuTree'].getCheckedNodes(false, true)

        this.form.menuDatas = []

        for(let item of menuDatas) {
          let d= Object.assign({},item)
          d.__parent = null
          d.children = null
          this.form.menuDatas.push(d)
        }
        if (this.showDataScope) {
          let officeDatas = this.$refs['officeTree'].getCheckedNodes()
          this.form.officeDatas = []
          for(let item of officeDatas) {
            let d= Object.assign({},item)
            d.__parent = null
            d.children = null
            this.form.officeDatas.push(d)
          }
        }

      }
    },
  mounted () {
      this.loadMenus ()
    this.loadOffice()
  }
}
</script>

<style scoped lang='scss'>
    .form-wrapper{
        min-height:300px;
        height:100%;
        padding:5px 10px;
    }
  .fun-wrapper{
    height:240px;
    overflow:auto;
  }
  .data-wrapper{
    height:210px;
    overflow:auto;
  }
</style>

<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    v-dialogDrag
    width="70%"
    >
    <el-form ref="form" :rules="rules" :model="data" label-width="130px">
      <el-input type="hidden" v-model="data.parentId" style="width: 0"/>
      <el-input type="hidden" v-model="data.icon" style="width: 0"/>
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="名称:" prop="name">
            <el-input v-model="data.name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上级菜单:">
            <el-input v-model="data.parentName" readonly @click.native="showTreeDialog()">
              <el-button type="danger" slot="append" icon="el-icon-delete" title="清空" @click.native="clearParent()" @click.stop></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="链接:">
            <el-input v-model="data.href"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="权限:">
            <el-input v-model="data.permission"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="图标:">
            <span v-if="data.icon"><i class="fa" :class="data.icon" style="font-size:25px;margin-right:20px;"></i> </span>
            <el-button type="primary" icon="el-icon-edit" @click.native="showAppIcon()" circle></el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="可见:">
            <el-radio-group v-model="data.isShow">
              <el-radio-button label="1">显示</el-radio-button>
              <el-radio-button label="0">隐藏</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="排序:" prop="sort">
            <el-input v-model.number="data.sort"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注:">
            <el-input type="textarea" v-model="data.remarks"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm()">确 定</el-button>
    </span>

    <tree-dialog ref="menuTree" :load-url="'menu/loadList'" :rootParams="{parentId: '0'}" @treeConfirm="confirmParent"></tree-dialog>
    <app-icon ref="appicon" @selectedIcon="selectedIcon"></app-icon>
  </el-dialog>
</template>

<script>

import constants from "../../../../assets/js/constants";
import appIcon from "@/components/common/plugin/icon"
import {post} from "../../../../assets/js/api/app_api";
import treeDialog from "@/components/common/plugin/treedialog/treeDialog"
export default {
  name: 'form',
  components:{
    appIcon,
    treeDialog
  },
  data() {
    return {
      title: '新增菜单',
      dialogVisible: false,
      treeVisiable: false,
      treeDialogOpen: false,
      view: false,
      data: {},
      oldData:{},
      rootNode: {},
      treeProps: {
        label: 'name',
        children: 'children',
        isLeaf: 'leaf'
      },
      rules:{
        name: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入菜单排序', trigger: 'blur' },
          { validator: this.validateNumber, trigger: 'blur' }
        ],
      }
    };
  },
  methods:{
    showDialog (visiable, data, title, view) {
      this.dialogVisible = visiable
      this.oldData = data
      this.data = Object.assign({}, data)
      if (title) {
        this.title = title
      } else {
        this.title =view ? '查看[' + data.name + ']' :  this.data.id ? '编辑[' + this.data.name + ']' : '新增菜单'
      }
    },
    showTreeDialog () {
      // this.treeVisiable = true
      this.$refs['menuTree'].showDialog('选择上级菜单')
    },
    showAppIcon () {
      this.$refs['appicon'].showDialog(true, this.data.icon)
    },
    selectedIcon (icon) {
      this.$set(this.data,'icon',icon)
    },
    validateNumber (rule, value, callback) {
      try{
        parseFloat(value)
        callback();
      }catch (e) {
        callback(new Error('请输入数字'));
      }

    },
    treeClosed () {
      this.treeDialogOpen = false
      this.rootNode.node.childNodes= []
    },
    treeOpen () {
      if(this.rootNode.node) {
        this.loadMenu(this.rootNode.node,this.rootNode.resolve)
      }
    },
    loadMenu (node, resolve) {
      if(!node.data) {
        this.rootNode = {node:node,resolve:resolve}
      }
      let params = {}
      if (node.level === 0) {
        params = {parentId: '0', isShow: 1}
      } else {
        params = {parentId: node.data.id, isShow: 1}
      }
      post('menu/loadList', params).then(function (res) {
        if (res.code === constants.success) {
          resolve(res.data);
        }
      })
    },
    clearParent () {
      this.data.perntId = ''
      this.data.parentName = ''
    },
    confirmParent (node) {
      if (node) {
        if (node.data.id === this.data.id) {
          this.$message.warning('上级菜单不能选择自身')
        } else {
          this.$set(this.data,'parentId',node.data.id)
          this.$set(this.data,'parentName',node.data.name)
        }
      }
    },
    submitForm () {
      let that = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let loadIns = this.$loading({text: "提交数据中..."})
          that.data.__parent = null
          that.data.children = null
          post('/menu/save', that.data).then(function (res) {
            loadIns.close()
            that.dialogVisible = false
            if (res.code === constants.success) {
              that.$emit("updateData", that.data)
            } else {
              that.$message.error('保存信息失败')
            }
          }).catch(function (res) {
            loadIns.close()
          })
        }
      })
    }
  }
}
</script>

<style scoped lang='scss'>
.form-wrapper{
    height:100%;
}
  .tree-container{
    height:400px;
    overflow:auto;
  }
</style>

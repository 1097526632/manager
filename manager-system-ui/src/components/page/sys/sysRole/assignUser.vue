<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="70%"
    v-dialogDrag
    @closed="closeDialog"
    :close-on-click-modal="false"
  >
<el-row class="assign-content">
  <el-col :span="12">
    <div class="title-wrapper">
      <span class="title">用户列表：</span>
      <el-button type="primary" @click="loadOfficeUser()" size="mini" circle icon="el-icon-refresh"></el-button>
    </div>

    <el-tree
      ref="officeTree"
      :data="officeUserData"
      node-key="id"
      :props="defaultProps">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }} {{data.isParent =='0'?'[用户]':'[机构]'}}</span>
        <span v-if="data.isParent =='0'">
          <el-button
            type="text"
            size="mini"
            @click="addUser(node,data)">
            添加
          </el-button>
        </span>
      </span>
    </el-tree>
  </el-col>
  <el-col :span="1"></el-col>
  <el-col :span="11">
    <div class="existUser">
      <span class="title">已选用户：</span>
      <ul>
        <li class="user-info" v-for="(item,index) in existUser" :key="index">
          <span class="seq">{{index+1}}</span>
          <span class="name">{{item.name}}</span>
          <el-button
            type="text"
            size="mini"
            class="remove"
            @click="removeUser(item)">
            移除
          </el-button>
        </li>
      </ul>
    </div>
  </el-col>
</el-row>
    <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveRoleUser()">确 定</el-button>
        </span>
  </el-dialog>
</template>

<script>
  import {closeLoading, showLoading, toTree} from "../../../../assets/js/utils";
import {loadList, post} from "../../../../assets/js/api/app_api";
  import constants from "../../../../assets/js/constants";

export default {
  name: 'assignUser',
  data () {
    return {
      dialogVisible: false,
      title: '角色用户分配',
      data: {},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      officeUserData: [],
      existUser: []
    }
  },
  methods: {
    closeDialog () {

    },
    showDialog (data) {
      this.title = '【' + data.name + '】角色用户分配'
      this.data = data
      this.dialogVisible = true
      this.existUser = []
      this.loadExistUser()
    },
    loadOfficeUser () {
      let that = this
      loadList(this, 'sys/sysOffice/officeUser', {} ,function (res) {
        let datas = res.data.records ? res.data.records : res.data
        that.officeUserData = toTree(datas,{pid: 'parentId'})
      },false)
    },
    loadExistUser() {
      let that = this
      loadList(this, 'sys/sysRole/loadExistUser', this.data ,function (res) {
        let datas = res.data.records ? res.data.records : res.data
        that.existUser = datas
      })
    },
    addUser(node,data) {
     let item = this.existUser.filter(function (item, index, self) {
        return item.id == data.id;
      });
     if (item.length === 0) {
       let copyObj = Object.assign({},data)
       copyObj.__parent=null
       copyObj.children=null
       this.existUser.push(copyObj)
     }
    },
    removeUser(user){
      for(let item =0 ;item<this.existUser.length;item++) {
        if(this.existUser[item].id ==user.id){
          this.existUser.splice(item,1)
        }
      }
    },
    saveRoleUser () {
      this.data.roleUsers = this.existUser
      console.log(this.data)
      let loadIns = showLoading('保存数据中...')
      let that = this
      post('sys/sysRole/saveRoleUser',this.data).then(function (res) {
        closeLoading(loadIns)
        if(res.code === constants.success){
          that.$message.success('保存数据成功')
          that.dialogVisible = false
        } else {
          that.$message.error('保存数据失败')
        }
      }).catch(function (res) {
        closeLoading(loadIns)
      })
    }
  },
  mounted () {
    this.loadOfficeUser()
  }
}
</script>

<style scoped lang='scss'>
.assignUser-wrapper{
    height:100%;
}
  .assign-content{
    height:400px;
  }
  .el-col{
    height:100%;
    overfloe:auto;
  }
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
  li{
    list-style:none;
    display:flex;
    border-bottom:1px solid #ccc;
    padding-bottom:5px;
    padding-top:5px;
    .seq{
      flex:0 0 20px;
      height:20px;
      background: #cccc;
      border-radius: 50%;
      text-align: center;
      line-height:20px;
      margin-right:5px;
    }
    .name{
      flex:1;
    }
    .remove{
      flex:0 0 40px;
      height:20px;
      cursor: pointer;
    }
  }
.title-wrapper{
  display:flex;
  border-bottom:1px solid #ccc;
  padding-bottom:3px;
  .title{
    flex:1;
  }
  .el-button{
    margin-right:10px;
  }
}
</style>

<template>
    <div class='list-wrapper'>
      <el-row>
        <el-col :span="4">
          <div class="panel-header">
            <div class="title">组织机构</div>
            <div class="oper">
              <el-button type="primary" size="mini" icon="el-icon-refresh" circle @click.native="loadOfficeList()"></el-button>
            </div>
          </div>
          <div class="panel-content">
            <el-tree
              ref="officeTree"
              :data="office_data"
              :props="office_props"
              @node-click="officeClick">
            </el-tree>
          </div>
        </el-col>
        <el-col :span="20">
          <div class="params-wrapper">
            <el-form :inline="true" :model="params" class="demo-form-inline" @submit.native.prevent>
              <el-form-item label="关键值:">
                <el-input v-model="params.keyName" placeholder="关键值查询"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary"  native-type="submit" @click="loadList()"><i class="el-icon-search"></i> 查询</el-button>
              </el-form-item>
              <div class="oper-wrapper">
                <el-button v-if="hasBtnPermission('edit:sys:sysUser')" type="success" @click="edit()" title="新增"><i class="el-icon-plus"></i> 新增</el-button>
              </div>
            </el-form>
          </div>
          <div class="table-wrapper">
            <el-table
              :data="tableData"
              stripe
              @row-dblclick="dbclick"
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
                prop="photo"
                label="用户头像"
                width="80"
              >
                <template slot-scope="scope">
                  <img :src="getUserfiles(scope.row.photo)" class="head-img" v-if="scope.row.photo&&scope.row.photo!=''"/>
                  <img src="@/assets/img/header.png"  class="head-img" v-else />
                </template>
              </el-table-column>
              <el-table-column
                prop="officeName"
                label="所属机构"
              >
              </el-table-column>
              <el-table-column
                prop="no"
                label="工号"
              >
              </el-table-column>
              <el-table-column
                prop="username"
                label="登录名"
              >
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
              >
              </el-table-column>
              <el-table-column
                prop="email"
                label="邮箱"
              >
              </el-table-column>
              <el-table-column
                prop="mobile"
                label="手机"
              >
              </el-table-column>
              <el-table-column
                prop="loginFlag"
                label="状态"
              >
                <template slot-scope="scope">
                  {{scope.row.loginFlag=='1'?'激活':'禁用'}}
                </template>
              </el-table-column>
              <el-table-column
                fixed="right"
                width="150"
                label="操作">
                <template slot-scope="scope">
                  <el-button v-if="hasBtnPermission('edit:sys:sysUser')" type="primary"  @click="edit(scope.row)" title="编辑" icon="el-icon-edit" circle></el-button>
                  <el-button v-else-if="hasBtnPermission('view:sys:sysUser')" type="success"  @click="edit(scope.row, true)" title="查看" icon="el-icon-search" circle></el-button>
                  <el-button v-if="scope.row.id !='u_01' && hasBtnPermission('del:sys:sysUser')" type="danger"  @click="deleteInfo(scope.row)" title="删除" icon="el-icon-delete" circle></el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handlePageChange"
              @current-change="handlePageChange"
              :current-page.sync="params.page.page"
              :page-sizes="[10, 20, 30, 50, 100]"
              :page-size.sync="params.page.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="params.page.totalRecord">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
        <form-info ref="formInfo" @updateData="updateData"></form-info>
    </div>
</template>

<script>
import formInfo from './form'
import {hasBtnPermission} from "@/assets/js/business/app_business";
import {deleteInfo, loadList} from "@/assets/js/api/app_api";
import app_config from "@/assets/js/config";
import constants from "../../../../assets/js/constants";
import {getUserfiles, toTree} from "../../../../assets/js/utils";

export default {
    name: 'list',
    components: {
      formInfo
    },
    data () {
      return {
        tableData: [],
        office_data: [],
        office_props: {
          children: 'children',
          label: 'name'
        },
        params: {
          page: {
            totalRecord: 0,
            pageSize: app_config.pageSize?app_config.pageSize:20,
            page: 1
          }
        },
        currentOffice: {}
      }
    },
    methods: {
      officeClick (data) {
        this.params.officeId = data.id
        this.currentOffice = data
        this.loadList()
        this.$nextTick(() => {
          this.$refs.officeTree.setCurrentKey(value); // treeBox 元素的ref   value 绑定的node-key
        });
      },
      getUserfiles(filePath){
        return getUserfiles(filePath)
      },
      handlePageChange (val) {
        this.loadList()
      },
      hasBtnPermission (key) {
        return hasBtnPermission(key)
      },
      loadList () { // 加载列表信息
        loadList(this, 'sys/user/loadList', this.params)
      },
      loadOfficeList () {
        let that = this
        this.params.officeId =''
        this.currentOffice = {}
        loadList(this, 'sys/sysOffice/loadList', {}, function (res) {
          if (res.code === constants.success) {
            that.office_data = toTree(res.data, {id:'id', pid:'parentId'})
          }
        },'.panel-content')
        this.loadList()
      },
      edit (data, view) { //新增或编辑数据
        let title = (view ? '查看' : ( !data || !data.id) ? '新增' : '编辑' ) + '用户表'
        this.$refs['formInfo'].showDialog(title , true, data ? data: {loginFlag:'1',parentId: this.currentOffice.id,parentName:this.currentOffice.name}, view?1:0)
      },
      deleteInfo (data) {
        deleteInfo(this, 'sys/user/delete', data)
      },
      updateData (data) {
        this.loadList()
      },
      dbclick (row, column, event) {
        let view = true
        if (this.hasBtnPermission('edit:sys:sysUser')){
            view = false
        }
        this.edit(row, view)
      }
    },
    mounted () {
      this.loadList()
      this.loadOfficeList()
    }
}
</script>

<style scoped lang='scss'>
  .list-wrapper{
    height:100%;
  }
  .oper-wrapper{
    display:inline-block;
  }
  .table-wrapper{
    height:calc(100% - 55px)
  }
  .head-img{
    width:60px;
    height:60px;
    border-radius: 50%;
  }
  .el-row,.el-col{
    height:100%;
  }
  .panel-header{
    display:flex;
    height:40px;
    line-height:40px;
    background: #fff;
    border-bottom:1px solid #ccc;
    .title{
      flex:1;
    }
    .oper{
      margin-right:5px;
    }
  }
  .panel-content{
    height: calc(100% - 50px);
    background: #fff;
    overflow: auto;
  }

</style>

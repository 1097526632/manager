<template>
<div class='index-wrapper'>
  <div class="params-wrapper">
    <el-form :inline="true" :model="params" class="demo-form-inline" @submit.native.prevent>
      <el-form-item label="名称:">
        <el-input v-model="params.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  native-type="submit" @click="query()"><i class="el-icon-search"></i> 查询</el-button>
      </el-form-item>
      <div class="oper-wrapper">
        <el-button type="success" @click="edit()" title="新增"><i class="el-icon-plus"></i> 新增</el-button>
      </div>
    </el-form>
  </div>
<tree-table ref="treetable"
            :pid="'parentId'"
            :columns="columns"
            :operator="operator"
            :url="url"
            :params="params"
            @handleEdit="edit"
            @handleView="edit"
            @handleDelete="deleteInfo"
            @addSub="addSub"
            @dbclick="dbclick"
            @updateData="updateData"
></tree-table>

  <menu-form ref="menuForm" @updateData="updateData"></menu-form>
</div>
</template>

<script>
  import treeTable from '../../../common/plugin/treetable/index'
  import {post} from "@/assets/js/api/app_api";
  import constants from "../../../../assets/js/constants";
  import menuForm from './form'
  import {hasBtnPermission} from "../../../../assets/js/business/app_business";
export default {
  name: 'index',
  components: {
    treeTable,
    menuForm
  },
  data () {
    return {
      url: '/menu/loadList',
      params: {},
      columns: [
        {name: 'name', label: '名称', render:function(scope) {
          let val = (scope.row['icon'] ? '<i class=\'fa ' +scope.row['icon'] + '\'></i> &nbsp;' : '' )+ scope.row['name']
            return val}},
        {name: 'href', label: '链接'},
        {name: 'isShow', label: '显示', render:function(scope) {return scope.row['isShow'] === '1' ? '显示': '隐藏'}},
        {name: 'sort', label: '排序'},
        {name: 'permission', label: '权限'},
      ],
      operator: {
        view: hasBtnPermission('view:sys:menu'),
        edit: hasBtnPermission('edit:sys:menu'),
        del: hasBtnPermission('del:sys:menu')
      },
      updateIndex: -1
    }
  },
  methods: {
    query () {
      this.$refs['treetable'].setParams(this.params)
    },
    edit(row, index) {
      if(hasBtnPermission('edit:sys:menu')) {
        if (row) {
          row.parentName = row.__parent ? row.__parent.name : ''
        } else {
          row = {sort: 30, isShow: 1}
        }
        this.$refs['menuForm'].showDialog(true, row)
      } else {
        this.$refs['menuForm'].showDialog(true, row, null, true)
      }
      this.updateIndex = index
    },
    updateData (data) {
      this.query()
    },
    deleteInfo (row, index) {
      let that = this
      let params = {id: row.id}
      post('/menu/delete', params).then(function (res) {
        if (res.code === constants.success) {
          that.query()
        } else {
          that.$message.error('删除信息失败')
        }
      })
    },
    addSub (row, index) {
      this.$refs['menuForm'].showDialog(true,
        {parentId: row.id, parentName: row.name,
          sort: (row.child && row.child.length>0)? (parseInt(row.child[row.child.length-1].sort) + 30 ) : 30, isShow: 1}
      )
    },
    dbclick (row, column, event) {
      this.edit(row)
    }
  },
  mounted () {
  }
}
</script>

<style scoped lang='scss'>
.index-wrapper{
    height:100%;
}
  .oper-wrapper{
    display: inline-block;
  }
</style>

<template>
    <div class='list-wrapper'>
        <div class="params-wrapper">
            <el-form :inline="true" :model="params" class="demo-form-inline" @submit.native.prevent>
                <el-form-item label="关键值:">
                    <el-input v-model="params.keyName" placeholder="关键值查询"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  native-type="submit" @click="loadList()"><i class="el-icon-search"></i> 查询</el-button>
                </el-form-item>
                <div class="oper-wrapper">
                    <el-button v-if="hasBtnPermission('edit:sys:sysOffice')" type="success" @click="edit()" title="新增"><i class="el-icon-plus"></i> 新增</el-button>
                </div>
            </el-form>
        </div>

      <tree-table ref="treetable"
                  :pid="'parentId'"
                  :columns="columns"
                  :operator="operator"
                  :url="url"
                  @handleEdit="edit"
                  @handleView="edit"
                  @handleDelete="deleteInfo"
                  @addSub="addSub"
                  @dbclick="dbclick"
                  @updateData="updateData"
      ></tree-table>
        <form-info ref="formInfo" @updateData="updateData"></form-info>
    </div>
</template>

<script>
import formInfo from './form'
import {hasBtnPermission} from "@/assets/js/business/app_business";
import {deleteInfo, loadList} from "@/assets/js/api/app_api";
import constants from "../../../../assets/js/constants";
import treeTable from '../../../common/plugin/treetable/index'
import {getDictLabel} from "../../../../assets/js/dict";

export default {
    name: 'list',
    components: {
        formInfo,
      treeTable
    },
    data () {
        return {
            tableData: [],
          params: {},
          columns: [
            {name: 'name', label: '名称'},
            {name: 'type', label: '类型',render:function(scope) {return getDictLabel('sys_office_type',scope.row['type'])}},
            {name: 'address', label: '地址'},
            {name: 'sort', label: '排序'},
            {name: 'zipCode', label: '邮编'},
            {name: 'usable', label: '状态',render:function(scope) {return scope.row['useable'] === '1' ? '启用': '禁用'}},
          ],
          operator: {
            view: hasBtnPermission('view:sys:sysOffice'),
            edit: hasBtnPermission('edit:sys:sysOffice'),
            del: hasBtnPermission('del:sys:sysOffice')
          },
          url: 'sys/sysOffice/loadList'
        }
    },
    methods: {
        handlePageChange (val) {
            this.loadList()
        },
        hasBtnPermission (key) {
            return hasBtnPermission(key)
        },
        loadList () { // 加载列表信息
          this.$refs['treetable'].setParams(this.params)
        },
        edit (data, index, view) { //新增或编辑数据
            if(this.hasBtnPermission('edit:sys:sysOffice')) {
              let title = (view ? '查看' : ( !data || !data.id) ? '新增' : '编辑' ) + '机构表'
              if (data && data.__parent) {
                data.parentName = data.__parent ? data.__parent.name: ''
              }
              this.$refs['formInfo'].showDialog(title , true, data ? data: {useable: '1', type: '1',sort: 30}, view?1:0)
            } else {
              this.$message.warning('无操作权限')
            }
        },
        deleteInfo (data) {
          if(this.hasBtnPermission('del:sys:sysOffice')) {
            let copyData = Object.assign({}, data)
            copyData.__parent=null
            copyData.children = null
            deleteInfo(this, 'sys/sysOffice/delete', copyData)
          } else {
            this.$message.warning('无操作权限')
          }

        },
      addSub (row, index) {
          this.edit({parentId: row.id, parentName: row.name,
            sort: (row.child && row.child.length>0)? (parseInt(row.child[row.child.length-1].sort) + 30 ) : 30, useable: '1', type: row.type})
      },
        updateData (data) {
            this.loadList()
        },
        dbclick (row, column, event) {
            let view = true
            if (this.hasBtnPermission('edit:sys:sysOffice')){
                view = false
            }
            this.edit(row, view)
        }
    },
    mounted () {}
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
</style>

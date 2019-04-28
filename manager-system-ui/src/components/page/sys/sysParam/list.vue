<template>
    <div class='list-wrapper'>
        <div class="params-wrapper">
            <el-form :inline="true" :model="params" class="demo-form-inline" @submit.native.prevent>
                <el-form-item label="编码/描述:">
                    <el-input v-model="params.keyName" placeholder="编码/描述"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" native-type="submit" @click="loadList()"><i class="el-icon-search"></i> 查询</el-button>
                </el-form-item>
                <div class="oper-wrapper">
                    <el-button v-if="hasBtnPermission('edit:sys:sysParam')" type="success" @click="edit()" title="新增"><i class="el-icon-plus"></i> 新增</el-button>
                </div>
            </el-form>
        </div>
        <div class="table-wrapper">
            <el-table
                    :data="tableData"
                    stripe
                    @row-dblclick="dbclick"
                    style="width: 100%"
                    height="calc(100% - 40px)"
            >
                <el-table-column
                        label="序号"
                        width="50"
                >
                    <template slot-scope="scope">
                        {{(params.page.page-1)*params.page.pageSize + scope.$index + 1}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="code"
                        label="编码"
                >
                </el-table-column>
                <el-table-column
                        prop="value"
                        label="值"
                >
                </el-table-column>
                <el-table-column
                        prop="description"
                        label="描述"
                >
                </el-table-column>
                <el-table-column
                        prop="type"
                        label="参数类型"
                >
                  <template slot-scope="scope">
                    {{scope.row.type==0?'系统内部参数':'自定义参数'}}
                  </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="150"
                        label="操作">
                    <template slot-scope="scope">
                      <el-button v-if="hasBtnPermission('edit:sys:sysParam')" type="primary"  @click="edit(scope.row)" title="编辑" icon="el-icon-edit" circle></el-button>
                        <el-button v-else-if="hasBtnPermission('view:sys:sysParam')" type="success"  @click="edit(scope.row, true)" title="查看" icon="el-icon-search" circle></el-button>
                        <el-button v-if="scope.row.type==1&&hasBtnPermission('del:sys:sysParam')" type="danger"  @click="deleteInfo(scope.row)" title="删除" icon="el-icon-delete" circle></el-button>
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
        <form-info ref="formInfo" @updateData="updateData"></form-info>
    </div>
</template>

<script>
import formInfo from './form'
import {hasBtnPermission} from "@/assets/js/business/app_business";
import {deleteInfo, loadList} from "@/assets/js/api/app_api";
import {getDictLabel} from "../../../../assets/js/dict";
import app_config from "@/assets/js/config";

export default {
    name: 'list',
    components: {
        formInfo
    },
    data () {
        return {
            tableData: [],
            params: {
                page: {
                    totalRecord: 0,
                  pageSize: app_config.pageSize?app_config.pageSize:20,
                    page: 1
                }
            }
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
            loadList(this, 'sys/sysParam/loadList', this.params)
        },
        edit (data, view) { //新增或编辑数据
          let title = (view ? '查看' : ( !data || !data.id) ? '新增' : '编辑' ) + '系统参数表'
          this.$refs['formInfo'].showDialog(title, true, data ? data: {}, view?1:0)
        },
        deleteInfo (data) {
            deleteInfo(this, 'sys/sysParam/delete', data)
        },
        dbclick (row, column, event) {
          let view = true
          if (this.hasBtnPermission('edit:sys:sysParam')){
            view = false
          }
          this.edit(row, view)
        },
        updateData (data) {
            this.loadList()
        }
    },
    mounted () {
        this.loadList()
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
</style>

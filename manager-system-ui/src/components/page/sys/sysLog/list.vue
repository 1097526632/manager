<template>
    <div class='list-wrapper log-wrapper'>
        <div class="params-wrapper">
            <el-form :inline="true" :model="params" class="demo-form-inline" @submit.native.prevent>
                <el-form-item label="日志类型:">
                  <el-select v-model="params.type" placeholder="请选择">
                    <el-option
                      v-for="item in typeDict"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
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
                <el-checkbox v-model="params.exception" true-label="1">异常日志</el-checkbox>
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
              :header-cell-class-name="cellClass"
              :cell-class-name="cellClass"
              @sort-change="sortChange"
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
                        prop="type"
                        label="类型"
                >
                  <template slot-scope="scope">
                    {{getDictLabel(scope.row.type)}}
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
                        prop="userAgent"
                        label="Header"
                        :show-overflow-tooltip="true"
                >
                </el-table-column>
                <el-table-column
                        prop="requestUri"
                        label="URI"
                        :show-overflow-tooltip="true"
                >
                </el-table-column>
                <el-table-column
                        prop="method"
                        label="方法"
                        :show-overflow-tooltip="true"
                >
                </el-table-column>
                <el-table-column
                        prop="params"
                        label="参数"
                        :show-overflow-tooltip="true"
                >
                </el-table-column>
              <el-table-column
                prop="createDate"
                sortable="customer"
                label="操作时间"
                :sort-orders="['ascending', 'descending']"
                :show-overflow-tooltip="true"
              >
              </el-table-column>
              <el-table-column
                prop="createUserName"
                label="操作人"
                :show-overflow-tooltip="true"
              >
              </el-table-column>
                <el-table-column
                        prop="exception"
                        label="异常信息"
                        :show-overflow-tooltip="true"
                >
                  <template slot-scope="scope">
                    <el-button type="danger" icon="search" @click="showException(scope.row)" v-if="scope.row.exception!='' ">{{!scope.row.expand?'查看':'隐藏'}}</el-button>
                  </template>
                </el-table-column>
              <el-table-column
                type="expand"
              >
                <template slot-scope="scope">
                  {{scope.row.exception}}
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
    </div>
</template>

<script>
import {hasBtnPermission} from "@/assets/js/business/app_business";
import {deleteInfo, loadList} from "@/assets/js/api/app_api";
import {getDict,getDictLabel} from "../../../../assets/js/dict";
import {nextDate, prevDate} from "../../../../assets/js/dateutils";
import app_config from "../../../../assets/js/config";

export default {
    name: 'list',
    data () {
        return {
            tableData: [],
            params: {
              queryBeginDate: prevDate(new Date(), 7),
              queryEndDate: nextDate(new Date(),1),
                page: {
                    totalRecord: 0,
                    pageSize: app_config.pageSize?app_config.pageSize:20,
                    page: 1
                }
            },
          typeDict: getDict('sys_log_type')
        }
    },
    methods: {
      cellClass ({row, column, rowIndex, columnIndex}) {
        if(columnIndex===11){
          return 'hide'
        }
        return ''
      },
        handlePageChange (val) {
            this.loadList()
        },
        hasBtnPermission (key) {
            return hasBtnPermission(key)
        },
        loadList () { // 加载列表信息
            loadList(this, 'sys/sysLog/loadList', this.params)
        },
      getDictLabel(value) {
          return getDictLabel('sys_log_type',value)
      },
      showException (row) {
      this.$refs['logTable'].toggleRowExpansion(row,!row.expand)
        row.expand=!row.expand
      },
      sortChange({column, prop, order }) {
        this.params.page.page=1
        this.params.page.orderField='CREATE_DATE'
        this.params.page.orderBy=order==='descending'?'desc':'asc'
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

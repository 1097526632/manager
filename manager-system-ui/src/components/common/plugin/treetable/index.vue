<template>
  <div class="tree-table-wrapper">
    <el-table
      ref="treetable"
      :data="data"
      @row-dblclick="dbclick"
      border
      row-key="id"
      lazy
      :load="loadData"
      v-if="lazy"
    >
      <el-table-column v-for="(column, index) in columns" :key="index" :label="column.label" :width="column.width">
        <template slot-scope="scope">
        <span v-if="column.render" v-html="column.render(scope)">
        </span>
          <span v-else>
          {{scope.row[column.name]}}
        </span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
         <span v-if="operator.render" v-html="operator.render(scope)">
        </span>
          <span v-else>
          <el-button
            type="success"
            title="编辑"
            @click="handleEdit(scope.$index, scope.row)"
            circle
            v-if="operator.edit"
          ><i class="el-icon-edit"></i></el-button>
          <el-button
            type="success"
            title="查看"
            @click="handleView(scope.$index, scope.row)"
            circle
            v-else-if="operator.view"
          ><i class="el-icon-search"></i></el-button>
        <el-button
          type="danger"
          title="删除"
          @click="handleDelete(scope.$index, scope.row)"
          circle
          v-if="operator.del"
        ><i class="el-icon-delete"></i></el-button>
        <el-button
          size="mini"
          type="primary"
          title="添加下级"
          @click="addSub(scope.$index, scope.row)"
          circle
          v-if="operator.edit"
        ><i class="el-icon-plus"></i></el-button>
        </span>
        </template>
      </el-table-column>
    </el-table>
    <el-table
      :data="data"
      @row-dblclick="dbclick"
      border
      row-key="id"
      v-else
    >
      <el-table-column v-for="(column, index) in columns" :key="index" :label="column.label" :width="column.width">
        <template slot-scope="scope">
        <span v-if="column.render" v-html="column.render(scope)">
        </span>
          <span v-else>
          {{scope.row[column.name]}}
        </span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
         <span v-if="operator.render" v-html="operator.render(scope)">
        </span>
          <span v-else>
          <el-button
            type="success"
            title="编辑"
            @click="handleEdit(scope.$index, scope.row)"
            circle
            v-if="operator.edit"
          ><i class="el-icon-edit"></i></el-button>
          <el-button
            type="success"
            title="查看"
            @click="handleView(scope.$index, scope.row)"
            circle
            v-else-if="operator.view"
          ><i class="el-icon-search"></i></el-button>
        <el-button
          type="danger"
          title="删除"
          @click="handleDelete(scope.$index, scope.row)"
          circle
          v-if="operator.del"
        ><i class="el-icon-delete"></i></el-button>
        <el-button
          size="mini"
          type="primary"
          title="添加下级"
          @click="addSub(scope.$index, scope.row)"
          circle
          v-if="operator.edit"
        ><i class="el-icon-plus"></i></el-button>
        </span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {replaceObj, toTree} from "../../../../assets/js/utils";
  import {loadList} from "../../../../assets/js/api/app_api";

  export default {
    name: 'treeTable',
    props: {
      url: String,
      columns: {
        type: Array,
        default: () => []
      },
      operator: {
        type: Object,
        default: () => {
          return {
            edit: true,
            view: true,
            del: true
          }
        }
      },
      pid: {
        type: String,
        default: () => 'pid'
      },
      id: {
        type: String,
        default: () => 'id'
      },
      lazy: {
        type: Boolean,
        default: () => false
      }
    },
    data () {
      return {
        data: [],
        initData: {},
        updateTable:Math.random()
      }
    },
    methods: {
      setParams (params) {
        this.loadDataInfo(params)
      },
      handleView(index, row) {
        this.$emit('handleView', row, index)
      },
      handleEdit(index, row) {
        this.$emit('handleEdit', row, index)
      },
      handleDelete(index, row) {
        this.$emit('handleDelete', row, index)
      },
      addSub(index, row) {
        this.$emit('addSub', row, index)
      },
      dbclick(row, column, event) {
        this.$emit('dbclick', row, column, event)
      },
      loadDataInfo (params) {
        let that = this
        params = params ? params : {}
        loadList(this, this.url, params,function (res) {
          let datas = res.data.records ? res.data.records : res.data
          if (that.lazy) {
            that.data = []
            for(let item of datas) {
              item.hasChildren = true
              that.data.push(item)
              that.initData[item[that.id]] = item
            }
          } else {
            that.initData = {}
            for(let item of datas) {
              that.initData[item[that.id]] = item
            }
            that.data = toTree(datas, {id:that.id,pid: that.pid})
          }
        })
      },
      loadData (tree, treeNode, resolve) {
        let that = this
        let _params = {}
        _params[this.pid] = tree[this.id]
        loadList(this, this.url, _params,function (res) {
          let datas = res.data.records ? res.data.records : res.data
          if (!datas || datas.length === 0) {
            that.$set(tree, 'hasChildren', false)
            that.$set(tree, 'children', null)
            that.updateTable = Math.random()
          } else {
            for(let item of datas) {
              item.hasChildren = true
              item.__parent = tree
            }
            console.log(resolve)
            resolve(datas)
          }

        })
      }
    },
    mounted () {
      this.loadDataInfo()
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>

</style>

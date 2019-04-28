<template>
  <el-dialog
    width="300"
    :title="title"
    :visible.sync="treeVisiable"
    @closed="treeClosed"
    :close-on-click-modal="false"
    v-dialogDrag
    append-to-body>
    <div class="tree-container">
      <el-tree
        :props="treeProps"
        :load="loadData"
        node-key="id"
        ref="tree"
        lazy
        v-if="lazy"
      >
      </el-tree>
      <el-tree
        :props="treeProps"
        :data="treeData"
        node-key="id"
        ref="tree"
        v-else
      >
      </el-tree>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button @click="treeVisiable = false">取 消</el-button>
        <el-button type="primary" @click="confirmParent()">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {loadList, post} from "../../../../assets/js/api/app_api";
import constants from "../../../../assets/js/constants";
  import {toTree} from "../../../../assets/js/utils";

export default {
  name: 'treeDialog',
  props:{
    loadUrl: String,
    rootParams: Object,
    lazy: {
      type:Boolean,
      default: false
    }
  },
  data () {
    return {
      treeVisiable: false,
      title: '',
      rootNode: {},
      treeData: [],
      treeProps: {
        label: 'name',
        children: 'children',
        isLeaf: 'leaf'
      }
    }
  },
  methods: {
    loadData(node, resolve) {
      if(!node.data) {
        this.rootNode = {node:node,resolve:resolve}
      }
      let params = {}
      if (node.level === 0) {
        params = this.rootParams
      } else {
        params = {parentId: node.data.id}
      }
      post(this.loadUrl, params).then(function (res) {
        if (res.code === constants.success) {
          let data = res.data.records
          if (!data){
            data = res.data
          }
          resolve(data);
        }
      })
    },
    loadAllData() {
      let that = this
      loadList(this, this.loadUrl, {}, function (res) {
        console.log(res)
        if (res.code === constants.success) {
          that.treeData = toTree(res.data, {id:'id', pid:'parentId'})
        }
      })
    },
    treeClosed(){
      if(this.rootNode.node) {
        this.rootNode.node.childNodes= []
        this.loadData(this.rootNode.node,this.rootNode.resolve)
      }
    },
    showDialog(title){
      this.title = title
      this.treeVisiable = true
      if (!this.lazy) {
        this.loadAllData()
      }else {
        console.log(this.rootNode)
        if(this.rootNode.node){
          this.rootNode.node.childNodes =[]
          this.loadData(this.rootNode.node,this.rootNode.resolve)
        }
      }

    },
    confirmParent () {
      let nodeKey = this.$refs['tree'].getCurrentKey()
      let node =this.$refs['tree'].getNode(nodeKey)
      if (node) {
        this.$emit('treeConfirm', node)
        this.treeVisiable = false
      } else {
        this.treeVisiable = false
      }
    }
  }
}
</script>

<style scoped lang='scss'>
.tree-container{
  height:400px;
  overflow:auto;
}
</style>

<template>
<div class='index-wrapper filemanager'>
  <el-container @click="catemenuVisable=false">
    <el-header height="30px">
      <div class="toolbar ufui-btn-toolbar">
        <div @click="open()" title="打开" class="ufui-btn"><span class="fa fa-folder-open"></span></div>
        <span class="separater"></span>
        <div @click="parentPath();" title=" 向上一级" class=" ufui-btn">
          <span class="fold-up">
            <span class="fa fa-folder"></span>
            <span class="fa fa-level-up" style="    position: relative;top: 0;color: #fff;right:17px;"></span>
          </span>
        </div>
        <span class="separater"></span>
        <div @click="mkFolder();" title=" 新建文件夹" class=" ufui-btn"><span class="fa fa-folder"></span></div>
        <span class="separater"></span>
        <div @click="editFile();" id="edit" title="文件替换" class=" ufui-btn"><span class="fa fa-retweet"></span></div>
        <span class="separater"></span>
        <div @click="remove();" title=" 删除" class=" ufui-btn" style="color:#ff4941;"><span class="fa fa-trash"></span></div>
        <span class="separater"></span>
        <div @click="downloadFile();" title=" 下载" class=" ufui-btn"><span class="fa fa-download"></span></div>
        <span class="separater"></span>
        <div @click="moveFile()" id="move" title=" 移动" class=" ufui-btn"><span class="fa fa-files-o"></span></div>
        <span class="separater"></span>
        <div @click="refreshCategory()" id="refresh" title=" 刷新" class=" ufui-btn"><span class="fa fa-refresh"></span></div>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-tree
          ref="categoryTree"
          :props="categoryProps"
          :load="loadFileCategory"
          @node-click="changCategory"
          lazy
          highlight-current
          node-key="id"
          >
        </el-tree>
      </el-aside>
      <el-main>
        <el-upload
          class="uploader"
          multiple
          drag
          :data="fileParams"
          :action="action"
          :show-file-list="false"
          :accept="accept"
          :on-success="fileUploadSuccess"
          :before-upload="fileUploadBefore">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </el-upload>
        <div class="file-list">
          <ul>
            <li v-for="(item,index) in fileList" :key="index">
              <div class="file-info" :class="item.id==activeFile.id?'active':''" @click="activeFile=item" @dblclick="open(item)">
                <div class="file-icon">
                  <img :src="getImgUrl(item.realName)" v-if="item.fileType=='image'"/>
                  <span v-else class="fa" :class="getIcon(item)"></span>
                </div>
                <div class="name">{{item.name}}</div>
              </div>
            </li>
          </ul>
        </div>
      </el-main>
    </el-container>
  </el-container>
  <el-dialog
    :title="categoryTitle"
    :visible.sync="categoryVisible"
    v-dialog-drag
    width="60%">
    <el-form :model="newCategory" ref="newCategory" label-width="100px" class="demo-ruleForm">
      <el-form-item label="目录名称" prop="name">
        <el-input v-model="newCategory.name"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
    <el-button @click="categoryVisible = false">取 消</el-button>
    <el-button type="primary" @click="saveCategory">确 定</el-button>
  </span>
  </el-dialog>
  <tree-dialog :loadUrl="'sys/sysFileCategory/loadList'" :rootParams="{parentId:'0'}" :lazy="true" ref="categoryTreeDialog" @treeConfirm="moveFileToCategory"></tree-dialog>
  <fileview ref="fileViewDialog"></fileview>
</div>
</template>

<script>
  import {downloadFile, formatUrl, getIcon} from "../../../../assets/js/utils";
  import {deleteInfo, loadList, saveData, saveInfo} from "../../../../assets/js/api/app_api";
  import TreeDialog from "../../../common/plugin/treedialog/treeDialog";
  import constants from "../../../../assets/js/constants";
  import Fileview from "./fileview";


export default {
  name: 'index',
  components: {Fileview, TreeDialog},
  props:{
  accept: {
      type: String,
      default: () =>''
  }
  },
  data () {
    return {
      action: formatUrl(window._BASE_URL+window._ADMIN_URL+'/sys/sysFileInfo/save'),
      categoryProps:{
        children:'children',
        label:'name',
        isLeaf: 'leaf'
      },
      rootNode: {},
      currentCategory:{},
      fileList: [],
      activeFile: {},
      categoryTitle:'新增目录',
      categoryVisible: false,
      newCategory: {},
      fileParams: {},
      catemenuVisable: true,
      queryFileParams: {}
    }
  },
  methods: {
    fileUploadSuccess () {
      this.loadFileList()
    },
    fileUploadBefore () {
      this.fileParams.categoryId = this.currentCategory.id
    },
    getImgUrl(url){
      return formatUrl(window._BASE_URL+'/userfiles/'+url)
    },
    getIcon (item) {
     return getIcon(item)
    },
    open (item) {
      if (!item) {
        item = this.activeFile
      }
       if(item.id){
         if(item.type=='category'){
           this.loadCategoryFile(item)
         }else{
           this.$refs['fileViewDialog'].showDialog(item)
         }
       }else {
         this.$message.warning('请选择要打开的文件')
       }
    },
    parentPath () {
      this.loadCategoryFile({id:this.currentCategory.parentId})
    },
    loadCategoryFile(category){
      if(category.id){
        let node = this.$refs.categoryTree.getNode(category.id)
        if (node){ // 存在
          this.refreshNode(node.data)
        } else {
          let that = this
          let parentId = category.parentId?category.parentId:category.categoryId
          loadList(this,'sys/sysFileCategory/loadList',{parentId: parentId},function (res) {
            let datas = res.data.records ? res.data.records : res.data
            that.$refs.categoryTree.updateKeyChildren(parentId,datas)
            setTimeout(function () {
              let node = that.$refs.categoryTree.getNode(category.id)
              that.refreshNode(node.data)
            },100)
          })
        }
      }
    },
    updateChilren(category){
      let that = this
      loadList(this,'sys/sysFileCategory/loadList',{parentId: category.id},function (res) {
        let datas = res.data.records ? res.data.records : res.data
        that.$refs.categoryTree.updateKeyChildren(category.id,datas)
        setTimeout(function () {
          let node = that.$refs.categoryTree.getNode(category.id)
          that.refreshNode(node.data)
        },100)
      })
    },
    refreshNode (node) {
      this.$refs.categoryTree.setCurrentKey(node.id)
      this.currentCategory =node
      this.loadFileList()
    },
    mkFolder () {
      this.categoryVisible=true
      this.newCategory={}
    },
    saveCategory () {
      if (this.newCategory.name!=''){
        this.newCategory.parentId=this.currentCategory.id
        let that = this
        saveInfo(this,'newCategory','sys/sysFileCategory/save',this.newCategory,function (res) {
          that.updateChilren(that.currentCategory)
          that.categoryVisible = false
        })
      }else {
        this.$message.warning('目录不能为空')
      }

    },
    editFile () {
      if(this.activeFile&&this.activeFile.type=='file'){
        this.$refs['fileViewDialog'].showDialog(this.activeFile,true)
      }else{
        this.$message.warning('目录不允许替换')
      }

    },
    remove () {
      if(this.activeFile){
        let url= 'sys/sysFileInfo/delete'
        if (this.activeFile.type=='category'){
          url= 'sys/sysFileCategory/delete'
        }
        let that = this
        deleteInfo(this,url,this.activeFile,'确认删除文件吗？',function () {
          that.activeFile={}
          that.updateChilren(that.currentCategory)
        })
      } else {
        this.$message.warning('请选选择文件')
      }
    },
    downloadFile () {
      if (this.activeFile&&this.activeFile.type=='file'){
        let url=formatUrl(window._BASE_URL+'/userfiles/'+this.activeFile.realName)
        downloadFile(url,{}, this.activeFile.name)
      } else {
        this.$message.warning('请选选择文件')
      }
    },
    moveFile () {
      if(this.activeFile&&this.activeFile.id){
        this.$refs['categoryTreeDialog'].showDialog('移动文件')
      } else {
        this.$message.warning('请选选择文件')
      }

    },
    moveFileToCategory (node) {
      if(node){
        let url='sys/sysFileInfo/changeCategory'
        let data={id:this.activeFile.id,categoryId:node.data.id,name:this.activeFile.name}
        if(this.activeFile.type=='category'){
          url ='sys/sysFileCategory/save'
          data.parentId=node.data.id
        }
        let that = this
        saveData(url,data,function (res,flag) {
          if(res.code == constants.success){
            that.updateChilren(that.currentCategory)
          }
        })
      }
    },
    changCategory (data, node) {
      if (this.currentCategory.id != data.id){
        this.currentCategory = data
        this.loadFileList()
      }

    },
    loadFileList(){
      let that = this
      let params = {categoryId:this.currentCategory.id}
      if(this.queryFileParams) {
        for(let item in this.queryFileParams) {
          params[item] = this.queryFileParams[item]
        }
      }
      loadList(this,'sys/sysFileInfo/loadList',params,function (res) {
        let datas = res.data.records?res.data.records: res.data
        that.fileList = datas
      })
      this.activeFile = {}
    },
    refreshCategory (){
      this.rootNode.node.childNodes =[]
      this.loadFileCategory(this.rootNode.node,this.rootNode.resolve)
    },
    getActiveFile () {
      return this.activeFile
    },
    loadFileCategory (node, resolve){
      if (!node.data) {
        this.rootNode ={node:node,resolve:resolve}
      }
      let params = {parentId:'0'}
      let that = this
      params.parentId =node.data? node.data.id:'0'
      loadList(this,'sys/sysFileCategory/loadList',params,function (res) {
        let datas = res.data.records?res.data.records: res.data
        if (!node.data) {
          let rootData={
            id: '0',
            name: '目录',
            children: datas
          }
          let dataArr = [rootData]
          resolve(dataArr)
          setTimeout(function () {
            that.$refs.categoryTree.updateKeyChildren('0',datas)
            that.$refs.categoryTree.getNode('0').expanded = true
            if (datas.length>0) {
              that.$refs.categoryTree.setCurrentKey(datas[0])
              that.currentCategory = datas[0]
              that.loadFileList()
            }
          },100)
        } else {
          resolve(datas)
        }

      })
    }
  }
}
</script>

<style scoped lang='scss'>
@import '../../../../assets/css/fileManager.css';
</style>

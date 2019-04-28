<template>
  <div class="resource-wrapper">
    <div class="file-list">
      <div v-if="isImage" >
        <div class="avatar-round" @click="showDialog()">
          <img v-if="file.realName&&file.realName != '' " :src="fileUrl" class="avatar" :style="cssStyle" :class="cssClass">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </div>

      </div>
      <div v-else>
        <span>{{file.name}}</span> &nbsp;&nbsp;
        <el-button type="primary" @click.native="showDialog()">选择文件</el-button>
      </div>
    </div>
    <el-dialog
      width="80%"
      :title="title"
      :visible.sync="treeVisiable"
      @closed="treeClosed"
      :top="'5px'"
      :close-on-click-modal="false"
      v-dialogDrag
      append-to-body>
      <div class="file-wrapper" :style="'height:'+calcHeight()">
        <file-manager ref="fileManager" :accept="accept" ></file-manager>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="treeVisiable = false">取 消</el-button>
         <el-button type="warning" @click="clearInfo()">清 空</el-button>
        <el-button type="primary" @click="confirmResult()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import fileManager from './index'
  import {formatUrl, isImage} from "../../../../assets/js/utils";
export default {
  name: 'selectFileResource',
  components: {
    fileManager
  },
  props: {
    cssStyle: {
      type: String,
      default: ()=> ''
    },
    cssClass: {
      type: String,
      default: ()=> ''
    },
    params: {
      type: Object,
      default: ()=> {}
    },
    accept: {
      type: String,
      default: () =>''
    }
  },
  data () {
    return {
      treeVisiable: false,
      title: '选择文件',
      file:{realName:'',fileName: ''},
    }
  },
  computed: {
    fileUrl () {
      return formatUrl(window._BASE_URL+'/userfiles/'+this.file.realName);
    }
  },
  methods: {
    treeClosed () {

    },
    calcHeight () {
      return (document.body.offsetHeight - 150)+'px'
    },
    showDialog() {
      this.treeVisiable = true
      let that = this
      setTimeout(function () {
        that.$refs.fileManager.queryFileParams = that.params
        that.$refs.fileManager.loadFileList() // 加载文件信息
      },100)

    },
    clearInfo () {
      this.treeVisiable = false
      this.file = {realName:'',fileName:''}
      this.$emit('update:result',this.file.realName)
      this.$emit('update:resultObj',result)
    },
    confirmResult () {
      let fileInfo = this.$refs.fileManager.getActiveFile()
      if (fileInfo.type == 'folder') {
        this.$message.warning('请选择文件')
      } else{
        let result = {}
        if(fileInfo.id){
          result = {realName:fileInfo.realName,fileName:fileInfo.name}
        }else{
          result = {realName:'',fileName:''}
        }
        this.file = result
        this.$emit('update:result',result.realName)
        this.$emit('update:resultObj',result)
        this.treeVisiable = false
      }
    },
    isImage(){
      return isImage(this.file.realName)
    }
  }
}
</script>

<style scoped lang='scss'>
.selectFileResource-wrapper{
    height:100%;
}
  .file-wrapper{
    min-height:300px;
  }
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
}
.avatar {
  width: 80px;
  height: 80px;
  display: inline-block;
}
  .avatar-round{
    display:inline-block;
    border:1px dashed #d9d9d9;
  }
</style>

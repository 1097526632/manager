<template>
  <el-upload
    class="avatar-uploader"
    :action="uploadPath"
    :data="params"
    :accept="uploadType"
    :show-file-list="false"
    :on-success="uploadSuccess"
    :on-error="uploadError"
    :before-upload="beforeUpload">
    <img v-if="value" :src="fileUrl" class="avatar" :style="cssStyle" :class="cssClass">
    <img v-else-if="defaultImg"  :src="defaultImg" class="avatar" :style="cssStyle" :class="cssClass">
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
</template>

<script>
import {formatUrl} from "../../../../assets/js/utils";
import constants from "../../../../assets/js/constants";

export default {
  name: 'fileupload',
  props:{
    defaultImg: {
      type: String,
      default: () => ''
    },
    cssClass:{
      type: String,
      default: () => ''
    },
    cssStyle:{
      type: String,
      default: () => ''
    },
    uploadType:{
      type: String,
      default: () => ''
    }
  },
  data () {
    return {
      params: {},
      value: '',
      uploadPath: formatUrl(window._BASE_URL+window._ADMIN_URL+'/uploadFile/upload'),//
      fileUrl:formatUrl(window._BASE_URL+'/userfiles/'+this.value),
    }
  },
  methods: {
    initUpload(value,params) {
      this.value=value
      this.params=params
      this.fileUrl = formatUrl(window._BASE_URL+'/userfiles/'+this.value)
    },
    uploadSuccess(res, file, fileList) {
      console.log('upload',res)
      if(res.code === constants.success){
        this.fileUrl=formatUrl(window._BASE_URL+'/userfiles/'+res.data.realName)
        this.value=res.data.realName
        this.$emit('update:value',res.data.realName)
      }
    },
    uploadError(response, file, fileList) {
      this.$message.error(file.name+'上传失败')
    },
    beforeUpload(file) {
      return true
    }
  }
}
</script>

<style scoped lang='scss'>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
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
    display: block;
  }
</style>

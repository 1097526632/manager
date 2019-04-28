<template>
  <el-dialog
    width="calc(100% - 100px)"
    top="20px"
    :title="title"
    :visible.sync="dialogVisiable"
    v-dialogDrag
    :close-on-click-modal="false"
    @close="closeDialog"
    append-to-body>
    <div class="file-view-info" :style="viewStyle">
      <div class="file-icon">
        <img :src="imgUrl" v-if="data.fileType=='image'" style="width:100%;"/>
        <a-player v-else-if="data.fileType=='music'" :music="soneObj" :showlrc="false" :narrow="false" theme="#b7daff" ref="player"></a-player>
        <video-player ref="videoPlayer" v-else-if="data.fileType=='video'" class="video-player-box"
                      :options="playerOptions"
                      :playsinline="true"
        ></video-player>
        <span v-else class="fa" :class="getIcon(data)"></span>
      </div>
      <div v-show="replaceFlag" style="text-align: center">
        <el-upload
          class="uploader"
          :data="data"
          :action="action"
          :show-file-list="false"
          :on-success="fileUploadSuccess">
          <el-button type="primary" style="margin-top:10px;width:150px;">文件替换</el-button>
        </el-upload>

      </div>
      <el-row>
        <el-col :span="12">
          <span class="label">文件名称:</span>
          <span class="value">{{data.name}}</span>
        </el-col>
        <el-col :span="12">
          <span class="label">文件后缀:</span>
          <span class="value">.{{data.extType}}</span>
        </el-col>
        <el-col :span="12">
          <span class="label">文件类型:</span>
          <span class="value">{{data.fileType}}</span>
        </el-col>
        <el-col :span="12">
          <span class="label">文件大小:</span>
          <span class="value">{{data.fileSize}}KB</span>
        </el-col>
        <el-col :span="12">
          <span class="label">创建时间:</span>
          <span class="value">{{data.createDate}}</span>
        </el-col>
        <el-col :span="12">
          <span class="label">修改时间:</span>
          <span class="value">{{data.updateDate}}</span>
        </el-col>
      </el-row>

    </div>
  </el-dialog>
</template>

<script>
  import {formatUrl, getIcon} from "../../../../assets/js/utils";
  import VueAplayer from 'vue-aplayer'
  import { videoPlayer } from 'vue-video-player'
  import 'video.js/dist/video-js.css'

export default {
  name: 'fileview',
  components: {
    //别忘了引入组件
    'a-player': VueAplayer,
    videoPlayer
  },
  data () {
    return {
      title:'文件查看',
      action: formatUrl(window._BASE_URL+window._ADMIN_URL+'/sys/sysFileInfo/save'),
      dialogVisiable: false,
      data: {},
      replaceFlag:true,
      viewStyle:"max-height:"+(document.body.clientHeight - 150)+'px',
      soneObj:{},
      playerOptions:{}
    }
  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player
    },
    imgUrl () {
      return this.getImgUrl(this.data.realName)
    }
  },
  methods: {
    initMusic () {
      let obj={}
      obj.title = this.data.name
      obj.author = '未知'
      obj.url = this.getImgUrl(this.data.realName)
      this.soneObj=obj
      let that = this

      setTimeout(function () {
        that.$refs.player.play()
      },100)
    },
    initAudio(){
      this.playerOptions={
        height: '360',
        autoplay: false,
        controls: true,
        sources: [
          {
            src:this.getImgUrl(this.data.realName),
            type: "video/"+this.data.extType
          }
        ]
      }
    },
    closeDialog () {
      if(this.data.fileType=='music'){
        let that = this
        that.$refs.player.pause()
      } else if(this.data.fileType=='video'){
        if (this.player) {
          this.player.pause()
        }
      }
    },
    fileUploadSuccess (res,file,fileList) {
       this.data.realName = res.data.realName
    },
    getImgUrl(url){
      return formatUrl(window._BASE_URL+'/userfiles/'+url)
    },
    getIcon (item) {
      return getIcon(item)
    },
    showDialog (data, flag){
      this.dialogVisiable=true
      this.title=(flag?'替换':'查看')+ data.name
      this.data = data
      if(flag){
        this.replaceFlag=true
      }else{
        this.replaceFlag=false
      }
      if(this.data.fileType=='music'){
        let that = this
        that.initMusic()
      } else  if(this.data.fileType=='video'){
        let that = this
        that.initAudio()
      }
    }
  }
}
</script>

<style scoped lang='scss'>
  @import '../../../../../node_modules/video.js/dist/video-js.css';
  .file-view-info{
    overflow:auto;
  }
  .el-row{
    padding:10px;
    .el-col{
      height:30px;
      line-height:30px;
      font-size:16px;
    }
  }
  .file-icon{
    text-align:center;
    margin:0 auto;
  }
  .file-icon .fa{
    width:120px;
    height:120px;
    font-size: 100px;
    text-align: center;
    line-height: 120px;
    color:#409eff;
  }
</style>

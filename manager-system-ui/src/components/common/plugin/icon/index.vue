<template>
  <el-dialog
    title="选择图标"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    append-to-body
    width="600"
  >
<div class='index-wrapper'>
<span class="icon-container" v-for="(item,index) in icondata" :key="index" :class="item.indexOf('fa')!=0?'title':''" @click="selectedIcon(item)">
  <span v-if="item.indexOf('fa')==0">
    <i  class="fa" :class="[item, item==selectIcon?'active':'']" ></i>
    <span class="name">{{item}}</span>
  </span>
<span v-else>{{item}}</span>
</span>
</div>
    <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmIcon()">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import icondata from './data'
export default {
  name: 'index',
  data () {
    return {
      dialogVisible: false,
      icondata:icondata,
      selectIcon: ''
    }
  },
  methods: {
    showDialog (visible,icon) {
      this.dialogVisible = visible
      if (icon) {
        this.selectIcon = icon
      }
    },
    confirmIcon () {
      this.$emit('selectedIcon',this.selectIcon)
      this.dialogVisible = false
    },
    selectedIcon (icon) {
      this.selectIcon = icon
    }
  }
}
</script>

<style scoped lang='scss'>
.index-wrapper{
    height:400px;
  overflow:auto;
}
  .fa{
    font-size: 24px;
    padding: 5px;
    width:30px;
    height:30px;
    text-align: center;
    line-height:30px;
  }
  .fa:hover,.active{
    background: #97a8be;
    color:red;
  }
.icon-container.title{
    display: block;
    color: #d82626;
    margin: 10px 5px;
  }
  .icon-container{
    display: inline-block;
    width: 80px;
    text-align: center;
    .name{
      height:30px;
      overflow: hidden;
      width:80px;
      display: block;
    }
  }

</style>

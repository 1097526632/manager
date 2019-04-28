<template>
<div class='tags-wrapper' v-if="showTags">
  <ul>
    <li class="tags-li" v-for="(item,index) in tagsList" :class="{'active': isActive(item.path)}" :key="index">
      <router-link :to="item.path" class="tags-li-title">
        {{item.title}}
      </router-link>
      <span class="tags-li-icon" @click="closeTags(index)" v-show="item.close"><i class="el-icon-close"></i></span>
    </li>
  </ul>
  <div class="tags-close-box">
    <el-dropdown @command="handleTags" style="height:100%">
      <el-button size="mini" type="primary" style="border-radius: 0;height: 100%;">
        标签选项<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu size="small" slot="dropdown" >
        <el-dropdown-item command="other">关闭其他</el-dropdown-item>
        <el-dropdown-item command="all">关闭所有</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</div>
</template>

<script>
  import bus from '../../common/bus'
export default {
  name: 'tags',
  data() {
    return {
      tagsList: []
    }
  },
  methods: {
    isActive(path) {
      return path === this.$route.fullPath;
    },
    // 关闭单个标签
    closeTags(index) {
      const delItem = this.tagsList.splice(index, 1)[0];
      const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
      if (item) {
        delItem.path === this.$route.fullPath && this.$router.push(item.path);
      }else{
        this.$router.push('/');
      }
    },
    // 关闭全部标签
    closeAll(){
      const curItem = this.tagsList.filter(item => {
        return !item.close;
      })
      this.tagsList = curItem;
      if (this.tagsList.length === 0) {
        this.$router.push('/');
      }

    },
    // 关闭其他标签
    closeOther(){
      const curItem = this.tagsList.filter(item => {
        return item.path === this.$route.fullPath || !item.close;
      })
      this.tagsList = curItem;
    },
    // 设置标签
    setTags(route){
      const isExist = this.tagsList.some(item => {
        return item.path === route.fullPath;
      })
      if(!isExist){
        this.tagsList.push({
          title: route.meta.title,
          path: route.fullPath,
          name: route.matched[1].components.default.name,
          close: route.meta.noclose ? !route.meta.noclose: true
        })
      }
      bus.$emit('tags', this.tagsList);
    },
    handleTags(command){
      command === 'other' ? this.closeOther() : this.closeAll();
    }
  },
  computed: {
    showTags() {
      return this.tagsList.length > 0;
    }
  },
  watch:{
    $route(newValue, oldValue){
      this.setTags(newValue);
    }
  },
  created(){
    this.setTags(this.$route);
    // 监听关闭当前页面的标签页
    bus.$on('close_current_tags', () => {
      for (let i = 0, len = this.tagsList.length; i < len; i++) {
        const item = this.tagsList[i];
        if(item.path === this.$route.fullPath){
          if(i < len - 1){
            this.$router.push(this.tagsList[i+1].path);
          }else if(i > 0){
            this.$router.push(this.tagsList[i-1].path);
          }else{
            this.$router.push('/');
          }
          this.tagsList.splice(i, 1);
        }
      }
    })
  }
}
</script>

<style scoped lang='scss'>
.tags-wrapper {
  position: relative;
  height: 34px;
  overflow: hidden;
  background: #fff;
  padding-right: 120px;
  box-shadow: 0 5px 10px #ddd;
}

.tags ul {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  display: flex;
  overflow: auto;
}

.tags-li {
  float: left;
  font-size: 12px;
  overflow: hidden;
  cursor: pointer;
  height: 28px;
  line-height: 28px;
  background: #e9eaec;
  padding: 3px 5px;
  vertical-align: middle;
  color: #666;
  -webkit-transition: all .3s ease-in;
  transition: all .3s ease-in;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-flex: 0;
  -ms-flex: 0 0 90px;
  flex: 0 0 90px;
}

.tags-li:not(.active):hover {
  background: #f8f8f8;
}

.tags-li.active {
  background: lightseagreen;
  color: #fff;
}

.tags-li-title {
  float: left;
  width:80px;
  max-width: 80px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 5px;
  color: #000;
  text-align: center;
}

.tags-li.active .tags-li-title {
  color: #fff;
}

.tags-close-box {
  position: absolute;
  right: 0;
  top: 0;
  box-sizing: border-box;
  text-align: center;
  width: 110px;
  height: 100%;
  background: #fff;
  z-index: 10;
}

</style>

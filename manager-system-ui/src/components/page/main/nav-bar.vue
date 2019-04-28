<template>
  <div class="nav-bar-wrapper">
    <label v-for="(menuItem, index) in menuItems" :key="index">
      <el-submenu v-if="menuItem.children && menuItem.children.length>0"  :index="menuItem.id">
        <template slot="title">
          <i class="fa" :class="menuItem.icon ?menuItem.icon:'fa-list'  "></i>
          <span slot="title">{{menuItem.name}}</span>
        </template>
        <nav-bar :menu-items="menuItem.children"></nav-bar>
      </el-submenu>
      <el-menu-item v-else :data="menuItem" :index="menuItem.href?menuItem.href:'' ">
        <i class="fa" :class="menuItem.icon ?menuItem.icon:'fa-list'  "></i>
        <span slot="title">{{menuItem.name}}</span>
      </el-menu-item>
    </label>
  </div>

</template>

<script>
export default {
  name: 'nav-bar',
  props:{
    menuItems: Array
  }
}
</script>

<style scoped lang='scss'>
.nav-bar-wrapper{

}
</style>

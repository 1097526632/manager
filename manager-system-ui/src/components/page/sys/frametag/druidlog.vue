<template>
<div class='druidlog-wrapper'>
  <sys-frame :src="src"></sys-frame>
</div>
</template>

<script>
import sysFrame from '../../../common/plugin/sysframe'
export default {
    name: 'frame-wrapper',
    components: {
      sysFrame
    },
  data () {
    return {
      src: ''
    }
  },
  mounted () {
      let that =this
    setTimeout(function () {
      that.src=that.$route.meta.src
    },100)

  }
}
</script>

<style scoped lang='scss'>
.druidlog-wrapper{
    height:100%;
}
</style>

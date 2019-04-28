<template>
<div class='index-wrapper'>
<iframe :src="src"></iframe>
</div>
</template>

<script>
export default {
  name: 'index',
  props: {
    src: String
  }
}
</script>

<style scoped lang='scss'>
.index-wrapper{
    height:100%;
  iframe{
    height:100%;
    border:none;
    overflow:auto;
    width:100%;
  }
}
</style>

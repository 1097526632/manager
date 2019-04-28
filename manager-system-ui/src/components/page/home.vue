<template>
<div class='home-wrapper'>
  <div class="canvas-wrapper">
    <div class="canvas-box">
      <div class="box box1">
        第一个
      </div>
      <div class="box box2">
        第二个
      </div>
      <div class="box box3">
        第三个
      </div>
      <div class="box box4">
        第四个
      </div>
      <div class="box box5">
        第五个
      </div>
      <div class="box box6">
        第六个
      </div>
      <div class="box box7">
        第七个
      </div>
      <div class="h-line line1"></div>
      <div class="h-line line2"></div>
      <div class="h-line line3"></div>
      <div class="h-line line4"></div>

      <div class="v-line vline1"></div>
      <div class="v-line vline2"></div>

      <div class="line-arrow arrow1"></div>
      <div class="line-arrow arrow2"></div>
      <div class="line-arrow arrow3"></div>
      <div class="h-line-arrow harrow1"></div>
    </div>
  </div>


</div>
</template>

<script>
export default {
  name: 'home'
}
</script>

<style scoped lang='scss'>
.home-wrapper{
    height:100%;
  text-align: center;
  margin:0 auto;
  overflow:hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  .canvas-wrapper{
    height:90%;
    flex:0 0 80%;
  }
  .canvas-box{
    position: absolute;
    width:100%;
    height:100%;
    min-width:800px;
    min-height:500px;
    background: #97a8be;
  }
  .box{
    width:110px;
    height:110px;
    font-size:18px;
    border:1px solid red;
    position: absolute;
  }
  .box1{
    top: 3%;
    left: 2%;
  }
  .box2{
    top: 40%;
    left: 2%;
  }
  .box3{
    top: 74%;
    left: 2%;
  }
  .box4{
    top: 40%;
    left: 40%;
  }
  .box5{
    top: 40%;
    left: 80%;
  }
  .box6{
    top: 3%;
    left: 40%;
  }
  .box7{
    top: 74%;
    left: 40%;
  }
  .v-line{
    width:1px;
    position: absolute;
    background: red;
  }
  .h-line{
    height:1px;
    position: absolute;
    background: red;
  }
  .line1{
    top: calc(3% + 55px);
    left: calc(2% + 110px);
    width: calc((40% - 2% - 110px)/2);
  }
  .line2{
    top: calc(40% + 55px);
    left: calc(2% + 110px);
    width: calc(40% - 2% - 110px - 20px);
  }
  .line3{
    top: calc(74% + 55px);
    left: calc(2% + 110px);
    width: calc(40% - 2% - 110px - 20px);
  }
  .line4{
    top: calc(40% + 55px);
    left: calc(40% + 110px);
    width: calc(80% - 40% - 110px - 20px);
  }
  .vline1{
    top: calc(3% + 55px);
    left: calc(2% + 110px + (40% - 2% - 110px)/2);
    height: calc(40% - 3%);
  }

  .vline2{
    top: calc(3% + 110px + 20px);
    left: calc(40% + 110px/2);
    height: calc(40% - 3% - 110px - 20px);
  }
  .line-arrow,.h-line-arrow{
    position: absolute;
    width: 20px;
    height:20px;
  }
  .line-arrow:after,.h-line-arrow:after {
    position: absolute;
    right: 10px;
    top: 50%;
    display: inline-block;
    content: "";
    width: 7px;
    height: 7px;
    border: solid red;
    border-width: 1px 1px 0 0;
    -webkit-transform: translate(0,-50%) rotate(45deg);
    transform: translate(0,-50%) rotate(45deg);
  }
  .h-line-arrow:after{
    -webkit-transform: translate(-70%, -100%) rotate(-45deg);
    transform: translate(-70%, -100%) rotate(-45deg);
  }
  .arrow1{
    top: calc(40% + 55px - 10px);
    left: calc(40% - 30px);
  }
  .arrow2{
    top: calc(74% + 55px - 10px);
    left: calc(40% - 30px);
  }
  .arrow3{
    top: calc(40% + 55px - 10px);
    left: calc(80% - 30px);
  }
  .harrow1{
    top: calc(3% + 110px + 20px);
    left: calc(40% + 110px/2);
  }
}
</style>

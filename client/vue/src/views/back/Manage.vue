<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="走马灯管理" name="first">
        <Carousel></Carousel>
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="second">
        <Position></Position>
      </el-tab-pane>
      <el-tab-pane label="热门公司" name="third">
        <Company></Company>
      </el-tab-pane>
    </el-tabs>
    
  </div>
</template>

<script>
  import Carousel from "@/components/back/Carousel.vue"
  import Position from "@/components/back/Position.vue"
  import Company from "@/components/back/Company.vue"
  export default {
    data() {
      return {
        activeName: 'first'
      }
    },
    components: {
      Carousel,
      Position,
      Company
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
  }
</script>

<style lang="less">
  .el-tabs__nav-scroll {
    height: 60px;
    line-height: 60px;
  }
  .el-tabs__item {
    font-size: 18px;
    font-weight: bold;
  }
  .el-tabs__item:hover {
    color: #f4612d!important;
  }
  .el-tabs__item.is-active {
    color: #f4612d!important;
    z-index: 100;
  }
  .el-tabs__active-bar {
    background-color: #f4612d!important;
  }
  
</style>
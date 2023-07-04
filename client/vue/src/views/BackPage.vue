<template>
    <el-container>
        
        <!-- 侧边栏 -->
        <el-aside :width="sideWidth + 'px'">
            <Aside :isCollapse="isCollapse" :width="navside + 'px'" :logoTextShow="logoTextShow" :userInfo="userInfo"></Aside>
        </el-aside>
      
        <el-container>
            <!-- 页面头部 -->
            <!-- <el-header height="100px"> -->
            <el-header>
                <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :userInfo="userInfo"></Header>
            </el-header>
            <!-- 主体内容 -->
            <el-main>
              <router-view @refresh="getUserInfo"/>                
            </el-main>
        </el-container>  
    </el-container>
</template>

<script>
import Aside from '@/components/back/Aside.vue'
import Header from '@/components/back/Header.vue'
export default {
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      navside:270,
      sideWidth: 320,
      logoTextShow:true,
      userInfo:{},
    }
  },
  components: {
    Aside,
    Header
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.navside = 100,
        this.sideWidth = 150
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.navside = 270
        this.sideWidth = 320
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUserInfo() {
      // let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      // if (username) {
      //   // 从后台获取User数据
      //   this.request.get("/user/username/" + username).then(res => {
      //     // 重新赋值后台的最新User数据
      //     this.user = res.data
      //   })
      // }
      this.userInfo = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : ""
      this.request.get("/user/" + this.userInfo.id).then(res => {
          // 重新赋值后台的最新User数据
          this.userInfo = res.data
        })
    }
  }
}
</script>

<style scoped>
  .el-container {
    min-height: 100vh;
  }
  .el-header{
    background-color: #f6f6f6;
    color: #333;
    text-align: center;
    line-height: 60px;
    height: 100px;
  }
  .el-aside {
    background-color: #f6f6f6;
    color: #000;
    text-align: center;
    /* line-height: 200px; */
    /* box-shadow: 2px 0 6px rgb(0 21 41 / 35%); */
  }
  .el-main {
    border-radius: 20px;
    margin: 0 30px 20px 0;
    background-color:#fff;
    position: relative;
  }

</style>
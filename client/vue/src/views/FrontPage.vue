<template>
  <div style="background-color: #f5f5f5;">
    <Header :userInfo="userInfo"></Header> 
    <div style="padding-top: 70px; width: 75%; margin: 0 auto; ">
      <router-view @refresh="getUserInfo"/>     
    </div> 
    <Footer class="footer"></Footer>
  </div>
</template>

<script>
import Header from '@/components/front/Header.vue'
import Footer from '@/components/front/Footer.vue'

export default {
  components: {
    Header,
    Footer
  },
  data() {
    return {
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      carouselData:[]
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      this.request.get("/user/" + this.userInfo.id).then(res => {
        // 重新赋值后台的最新User数据
        this.userInfo = res.data
      })
    }
  }
}
</script>

<style>
  /* .footer {
    position: fixed;
    bottom: 0;
  } */
</style>
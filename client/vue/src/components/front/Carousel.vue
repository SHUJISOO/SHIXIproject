<template>
  <div class="carouselBox" style="margin: 30px 0 0;">
   
    <el-carousel :interval="5000" arrow="always" height="450px">
      <el-carousel-item v-for="item in longItems" :key="item.id">
        <img :src="item.imgUrl" width="100%" height="100%">
      </el-carousel-item>
    </el-carousel>
    <!-- <div style="margin-top: 20px;width: 100%;">
      <hhh></hhh>      
    </div> -->

    <div style="margin-top: 30px;width: 100%;">
      <div class="pageHeader">热门公司</div>
      <!-- Slider main container -->
      <div class="swiper">
        <!-- Additional required wrapper -->
        <div class="swiper-wrapper">
          <!-- Slides -->
          <div class="swiper-slide layout" style="flex-direction: row;" v-for="item in hotCompany" :key="item.id">
            <div style="width: 30%;height: 140px;display: flex; justify-content: center; align-items: center;">
              <img :src="item.companyImg" width="50%" style="margin: auto;aspect-ratio: 1;"></div>
            <div style="width: 70%;height: 120px;display: flex; align-items: center;padding-right: 4.2%;">
              <div>
                <p class="singleLine" style="width:100%;margin: 10px 0;font-weight: bold;font-size: 18px;color: #1f1f1f;">{{ item.companyName }}</p>
                <div class="Lines" style="height: 45px;">
                  <el-tag class="infoTag" v-for="tag in item.companyDescription.split(',')" :key="tag">
                    {{ tag }}
                  </el-tag>
                </div>
              </div>
            </div>         
            <div style="padding: 0 6%;height: 60px;display: flex; align-items: center;">             
              <p class="Lines" style="height: 32px;width:100%;font-size: 12px;color: #646464;margin-bottom: 15px;">{{ item.companyIntroduction }}</p>
            </div>           
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
// import hhh from '@/components/front/hhh.vue'
import Swiper from "swiper"; // 导入您需要的模块
export default {
  props: {
    carouselData: Array,
    companyData: Array
  },
  // components:{hhh}
  mounted() {
    this.$nextTick(() => {
      new Swiper(".swiper", {
        slidesPerView: 2,  //一行显示3个
        // speed: 400,
        spaceBetween: 50,
        loop : true,
        slidesPerGroup:1,
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
        autoplay: {
          delay: 3000,
        },
      })      
    })
  },
  computed: {
    longItems() {
      return this.carouselData.filter((item)=>{
        return item.type === 'long'
      })
    },
    shortItems() {
      return this.carouselData.filter((item)=>{
        return item.type === 'short'
      })
    },
    hotCompany() {
      return this.companyData.filter((item)=>{
        return item.hot === true
      })
    }
  }
}
</script>

<style lang="less">
  .carouselBox{
    .el-carousel__container {
      width: 100%;
    }
    .el-carousel__arrow  {
      width: 30px;
      height: 30px;
      font-size: 30px;
      font-weight: bold;
    }
    .el-carousel__arrow:hover {
      background-color:#f4612d;
      .el-icon-arrow-right {
        color: #fff!important;
      }
      .el-icon-arrow-left {
        color: #fff!important;
      }
    }
    .el-carousel__button {
      width: 15px;
      height: 5px;
      background-color:#f4612d; 
    }
    .el-carousel__item {
      overflow: hidden;
      border-radius: 10px;
    }
    .el-carousel img {
      transition: all .5s ease .1s;
      &:hover {
        transform: scale(1.1);        
      }
    }
    .swiper {
      /* width: 100%; */
      overflow: hidden;
      position: relative;
    }
    .swiper-slide {
      width: 50%;
      border-radius: 10px;
      position: relative;
      display: flex;
      overflow: hidden;
      flex-direction: column;
      background-color: #fff;
        &:hover {
        border: 1px solid #f4612d;
        box-shadow: 10px 10px 10px rgba(218, 217, 217, 0.5);
      }
    }
    .swiper-button-prev,.swiper-button-next {
      width: 30px;
      height: 30px;
      font-size: 18px;
      border-radius: 50%;
      background-color: #e6e8e9;
      background-size: 25px 25px;
    } 
  }

</style>
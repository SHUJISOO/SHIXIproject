<template>
  <div>
    <div style="width:100%;height:auto;">
      <Carousel :carouselData="carouselData" :companyData="companyData"></Carousel>
      <Position :positionData="positionData"></Position>
    </div>

    <Reset></Reset>
  </div> 
</template>

<script>
import Carousel from '@/components/front/Carousel.vue'
import Position from '@/components/front/Position.vue'
import Reset from '@/components/front/reset.vue'

export default {
  components: {
    Carousel,
    Position,
    Reset
  },
  data() {
    return {
      carouselData: [],
      positionList:[],
      companyData:[],
      positionData: [],     
    }
  },
  created() {
    this.getCarouselInfo()
    this.getPositionInfo()
  },
  methods: {
    getCarouselInfo(){
      this.request.get("/carousel").then(res => {
        // console.log(res.data)
        this.carouselData = res.data
      })
      this.request.get("/company").then(res => {
        // console.log(res.data)
        this.companyData = res.data
      })
    },
    getPositionInfo(){
      this.request.get("/job/jobInfo").then(res => {
        this.positionList = res.data
        console.log(this.positionList)
        this.positionList.forEach(item=>{
          // console.log(item)
          item.companys.forEach((company)=>{
            delete item.companys
            Object.assign(company, item)
            item.users.forEach(user => {
              delete item.users
              Object.assign(company, {"nickname" : user.nickname})
              Object.assign(company, {"avatarUrl" : user.avatarUrl})
            })
            this.positionData.push(company)
          })
        })
      })
      console.log(this.positionData)
    }
  }
}
</script>


<template>
  <div style="min-height: 367px;">
    <div class="pageHeader">投递情况</div>
    <div class="layout" style="margin-top: 10px;">
      <div class="cardBox layout" style="width: 48%;height: 100px;" v-for="item,index in deliveryData" :key="index">
        <div style="width: 15%;display: flex;">
          <img :src="item.companyimg" style="width: 50px;align-self: center;">
        </div>
        <div style="width: 60%;padding-left: 10px;display: flex;overflow: hidden;">
          <div style="align-self: center;">
            <p class="singleLine" style="font-size: 26px;font-weight: bold;;color: #f4612d;">{{ item.jobName }}</p>
            <p class="singleLine" >{{ item.companyname }}</p>
          </div>
        </div>
        <div style="width: 25%;">
          <el-tag type="info" class="wait" plain v-if="item.accept === 0">等待中</el-tag>
          <el-tag type="success" plain class="agree" v-if="item.accept === 1">接受</el-tag>
          <el-tag type="danger" class="refuse" plain v-if="item.accept === 2">拒绝</el-tag>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      accept: "",
      deliveryList: [],
      deliveryData: [],
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getDeliveryInfo()
  },
  methods: {
    getDeliveryInfo() {
      this.request.get("/user/delivery/" + this.userInfo.id).then(res => {
        this.deliveryList = res.data
        this.deliveryList.forEach(item => {
          item.jobs.forEach(job => {
            delete item.jobs
            Object.assign(job, item)
            this.deliveryData.push(job)
          })
        })
        console.log(this.deliveryData)
      })
    },
  }
}
</script>

<style>
  .agree, .refuse, .wait{
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    /* padding: 0 30px; */
    font-size: 22px!important;
    font-weight: bold;
    overflow: hidden;
  }
</style>
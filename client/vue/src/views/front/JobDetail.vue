<template>
  <div style="width:100%;height:auto;">
    <div v-for="item,index in infoDetailData" :key="index">
      <!-- 详情信息头部 -->
      <div class="layout" style="width: 75%;;height: 200px;background-color: #fef7f3;padding: 20px;position: fixed;top: 70px;z-index: 9999;">
        <div style="width: 80%;">
          <p style="width: 100%;font-size: 28px;font-weight: bold;color: #000;">
            {{ item.jobName }}&emsp;
            <span style="color: #f4612d;font-size: 26px;">{{ item.salary }}</span>
          </p>
          <p style="width: 100%;margin-top: 15px;">
            <span>
              <i class="el-icon-location-information"></i> {{ item.companyAddress }}
            </span>            
            <span v-for="tag in item.requirement.split(',')" :key="tag">
              <el-divider direction="vertical"></el-divider>
              {{ tag }}
            </span>
          </p>  
          <div style="margin-top: 15px;width: 100%;line-height: 40px;padding-right: 20%;" class="Lines">
            <el-tag class="textTag" style="background-color: #fff!important;" v-for="tag in item.companyDescription.split(',')" :key="tag">
              {{ tag }}
            </el-tag>
          </div>
        </div>
        <div style="width: 20%;display: flex;">
          <el-button class="resumeBtn" @click="send(item.id)" v-if="accept === -1">投简历</el-button>
          <el-button class="resumeBtn" @click="cancel(item.id)" v-else>已投递</el-button>
        </div>
      </div>

      <div class="layout" style="padding-top: 220px;">
        <div style="width: 70%;">
          <div class="cardBox">
            <div class="pageHeader">职位描述</div>
            <div style="width: 100%;line-height: 40px;">
              <el-tag class="textTag" v-for="tag in item.jobDescription.split('，')" :key="tag">
                {{ tag }}
              </el-tag>
            </div>
            <div style="margin-top: 10px;">
              {{ item.jobIntroduction }}
            </div>
          </div>

          <div class="cardBox">
            <div class="pageHeader">公司简介</div>
            <div>
              {{ item.companyIntroduction }}
            </div>
          </div>
        </div>


        <div style="width: 28%;">
          <div class="cardBox">
            <div class="pageHeader">公司信息</div>
            <div style="text-align: center;">
              <img :src="item.companyImg" style="width: 55px;height: 55px;">
            </div>
            <p class="singleLine" style="text-align: center;">{{ item.companyName }}</p>
            <div style="margin-top: 20px;font-size: 14px;line-height: 30px;">
              <p class="singleLine"><i class="el-icon-view"></i> 企业行业： 房地产开发经营</p>
              <p class="singleLine"><i class="el-icon-s-marketing"></i> 人数规模： 5000-10000人</p>
              <p class="singleLine"><i class="el-icon-coordinate"></i> 职位地址： 广州-黄埔区知识大厦会议中心1</p>
            </div>
            <div class="layout">
              <div style="width: 20%;display: flex;height: 60px;text-align: center;" >
                <img :src="item.avatarUrl" width="40" height="40" style="border-radius: 50%;align-self: center;"/>
              </div>
              <div style="width: 80%;align-self: center;">
                <p>{{ item.nickname }}</p>
                <p style="font-size: 14px">Hr</p>
              </div>
            </div>
          </div>
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
      id: this.$route.query.id,
      infoDetailList: [],
      infoDetailData: [],
      btnVision: true,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getJobDetail(),
    this.getAccept()
    // this.btnVision= true
  },
  methods: {
    getJobDetail() {
      this.request.get("/job/jobInfo/" + this.id).then(res => {
        this.infoDetailList = res.data
        this.infoDetailList.forEach(item =>{
          item.companys.forEach(company =>{
            delete item.companys
            Object.assign(company, item)
            item.users.forEach(user => {
              delete item.users
              Object.assign(company, {"nickname" : user.nickname})
              Object.assign(company, {"avatarUrl" : user.avatarUrl})
              // Object.assign(job, {"USERID" : user.id})
            })
            this.infoDetailData.push(company)
          })
        })
      })
      // console.log(this.infoDetailData)
      // console.log(this.infoDetailData.id)
    },
    getAccept(){
      if(this.userInfo.id) {
        this.request.get("/accept/" + this.id + "/" + this.userInfo.id).then(res => {       
          if(res.data[0] === undefined) {
            this.accept = -1
          }else{
            this.accept = res.data[0].accept
          }
          console.log(res.data)
        })        
      }else{
        this.accept = -1
      }
    },
    send(jobId) {
      if(this.userInfo.id) {
        this.$confirm('是否向该公司投递简历?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.request.post("/job/send/" + jobId + "/" + this.userInfo.id).then(res => {
            if (res.code === '200') {
              this.btnVision = false
              this.$message.success("投递成功！")
            } else {
              this.$message.success(res.msg)
            }
            this.accept = 0
          })
        }).catch(() => {})        
      }else{
        this.$message.error("请先登录!")
      }
    },
    cancel(jobId) {
      if(this.userInfo.id) {
        this.$confirm('是否取消投递简历?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.request.post("/job/cancel/" + jobId + "/" + this.userInfo.id).then(res => {
            if (res.code === '200') {
              this.btnVision = true
              this.$message.success("取消成功！")
            } else {
              this.$message.success(res.msg)
            }
            this.accept = -1
          })
        }).catch(() => {})        
      }else{
        this.$message.error("请先登录!")
      }
    }
  }
}
</script>

<style lang="less">
  .el-divider--vertical {
    margin: 0 15px;
  }
  .resumeBtn {
    width: 150px;
    height: 50px;
    align-self: center;
    font-size: 24px!important;
    color: #fff!important;
    background-color: #f4612d;
    border-radius: 60px!important;
    &:hover {
      background-color: #f64a0c;
    }
  }
  .textTag {
    width: auto;
    margin-right: 15px;
    height: 30px;
    line-height: 30px;
    padding: 0 10px;
    font-size: 15px!important;
    background-color:#f8f9fb!important;
    color:#666666!important;
  }
</style>
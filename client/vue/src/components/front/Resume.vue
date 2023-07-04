<template>
  <div class="resumeBox">
    <!-- 页头 -->
    <div class="pageHeader">
      在线简历
      <div style="width: 25%; float: right; padding-top: 18px;">
        <el-progress :text-inside="true" :stroke-width="16" :percentage="percentage" status="exception"></el-progress>
      </div>
    </div>

    <!-- 简历内容 -->
    <div class="resumeCard">
      <OneResume title="教育经历"   :content="resumeInfo.education"   kind="education"  @update="updateContent" :id="resumeInfo.id" :percent="percent"/>
      <OneResume title="校园经历"   :content="resumeInfo.school"      kind="school"     @update="updateContent" :id="resumeInfo.id" :percent="percent"/>
      <OneResume title="实习经历"   :content="resumeInfo.experience"  kind="experience" @update="updateContent" :id="resumeInfo.id" :percent="percent"/>
      <OneResume title="获奖经历"   :content="resumeInfo.award"       kind="award"      @update="updateContent" :id="resumeInfo.id" :percent="percent"/>
      <OneResume title="技能/语言"  :content="resumeInfo.skill"       kind="skill"      @update="updateContent" :id="resumeInfo.id" :percent="percent"/>
      <OneResume title="其它"      :content="resumeInfo.other"       kind="other"      @update="updateContent" :id="resumeInfo.id" :percent="percent"/>
    </div>
  </div>
</template>

<script>
import OneResume from '@/components/front/OneResume.vue'
export default {
  data() {
    return {
      resumeInfo: [],
      percentage: 0,
      count: 0,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  components: {
    OneResume
  },
  created() {
    this.getResumeInfo()
    this.percent()
  },
  methods: {
    getResumeInfo() {
      this.request.get("/resume/oneResume/" + this.userInfo.id).then(res => {
        if (res.code === '200') {
          // this.$message.success("请求成功！")
          this.resumeInfo = res.data[0]
          console.log(this.resumeInfo)
          this.percent()
        } else {
          this.$message.error("请求失败！")
        }
      })
    },
    percent(){
      this.count = 0
      if(this.isEmpty(this.resumeInfo.education)){
        this.count += 1
      }
      if(this.isEmpty(this.resumeInfo.school)){
        this.count += 1
      }
      if(this.isEmpty(this.resumeInfo.experience)){
        this.count += 1
      }
      if(this.isEmpty(this.resumeInfo.award)){
        this.count += 1
      }
      if(this.isEmpty(this.resumeInfo.skill)){
        this.count += 1
      }
      if(this.isEmpty(this.resumeInfo.other)){
        this.count += 1
      }
      this.percentage = parseInt(this.count * 100 / 6)
    },
    isEmpty(obj){
      if(obj === null || obj === "" || obj === undefined){
        return false
      }else{
        return true
      }      
    },
    updateContent(val,kind){
      // console.log(val,kind)
      if(kind === "education"){
        this.resumeInfo.education = val
      }
      if(kind === "school"){
        this.resumeInfo.school = val
      }
      if(kind === "experience"){
        this.resumeInfo.experience = val
      }
      if(kind === "award"){
        this.resumeInfo.award = val
      }
      if(kind === "skill"){
        this.resumeInfo.skill = val
      }
      if(kind === "other"){
        this.resumeInfo.other = val
      }
    }
  }
}
</script>

<style lang="less">
  .resumeBox {
    .resumeCard {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
    }
    // .el-card:nth-child(1),.el-card:nth-child(2) {
    //   margin-top: 5px;
    // }
    i:hover {
      cursor:pointer;
      color: #f4612d;
    }
    .el-progress-bar__inner {
      line-height: 0;
      background-color: #f4612d!important;
    }
  }
</style>
<template>
  <div style="width:100%;height:auto;">
    <div class="pageHeader">更多职位</div>

    <!-- 搜索栏 -->
    <div class="cardBox search">
      <el-input placeholder="查询职位" v-model="name" @keyup.enter.native="getJobInfo">
        <template>
          <i slot="suffix" class="el-icon-search" @click="getJobInfo"></i>
        </template>       
      </el-input>

      <!-- 地点查询 -->
      <div style="width: 100%;border-bottom: 1px solid #f0f3f7;height: 50px;line-height: 50px;">
        <span style="font-size: 16px; font-weight: bold;">地点</span>&emsp;&emsp;&emsp;
        <el-radio-group v-model="address" @change="getJobInfo">
          <el-radio label="惠州">惠州</el-radio>
          <el-radio label="深圳">深圳</el-radio>
          <el-radio label="西安">西安</el-radio>
        </el-radio-group>
      </div>

      <div style="width: 100%;border-bottom: 1px solid #f0f3f7;height: 50px;line-height: 50px;">
        <span style="font-size: 16px; font-weight: bold;">要求</span>&emsp;&emsp;&emsp;
        <el-radio-group v-model="requirement" @change="getJobInfo">
          <el-radio label="经验不限">经验不限</el-radio>
          <el-radio label="学历不限">学历不限</el-radio>
          <el-radio label="大专">大专</el-radio>
          <el-radio label="本科">本科</el-radio>
        </el-radio-group>
      </div>

    </div>

    <!-- 职位列表 -->
    <div class="layout" v-for="item,index in jobData" :key="index" @click="$router.push('/index/jobDetail?id=' + item.id)">
      <div class="cardBox jobList">
        <div class="layout" style="height: 70%;flex-direction: column;">
          <div style="width: 55%;">
            <p style="margin-bottom: 15px;font-size: 20px; font-weight: bold;">{{ item.jobName }}【{{ item.companyAddress }}】</p>
            <p style="width: 100%;" class="singleLine">
              <span style="margin-bottom: px;font-size: 18px; color: #f4612d;">{{ item.salary }}</span>&emsp;
              <el-tag class="tag" v-for="tag in item.jobDescription.split(',')" :key="tag">
                {{ tag }}
              </el-tag>
            </p>
          </div> 
          <div class="layout" style="width: 45%;display: flex;align-self: center;">
            <div style="width: 20%;">
              <img :src="item.companyImg" width="55" height="55" style="border-radius: 10px;"/>
            </div>
            <div style="width: 80%;padding-left: 10px;">
              <p class="singleLine" style="margin-bottom: 10px;font-size: 18px;width: 100%;">{{ item.companyName }} </p>
              <div style="width: 100%;" class="singleLine">
                <el-tag class="tag" v-for="tag in item.companyDescription.split(',')" :key="tag">
                  {{ tag }}
                </el-tag>
              </div>
            </div>       
          </div>
        </div>
        <hr>

        <div class="layout" style="height: 20%;">
          <div style="width:60%;display: flex;align-self: center;">
            <p style="font-size: 14px;width: 100%;" class="singleLine">
              <el-tag class="textTag" v-for="tag in item.requirement.split(',')" :key="tag">
                {{ tag }}
              </el-tag>
            </p>
          </div>
          <div class="layout" style="width:40%;">
            <div style="display: flex;align-self: center;margin-top: 5px;margin-left: 60%;">
              <img :src="item.avatarUrl" width="40" height="40" style="border-radius: 50%;flex-direction: column;"/>
              <span style="display: flex;align-self: center;padding-left: 10px;">{{ item.nickname }}</span> 
            </div> 
          </div>
        </div>
      </div>
    </div>
    <!-- 页尾 -->
    <div class="pageBar">
      <!-- :page-sizes="[10, 20, 30, 40]" -->          
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-size="pageSize"
        background
        layout="total, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <Reset></Reset>
  </div>
</template>

<script>
import Reset from '@/components/front/reset.vue'
export default {
  data() {
    return {
      name: "",
      address: "",
      requirement: "",
      jobList:[],
      jobData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
    }
  },
  components: {
    Reset
  },
  created() {
    this.getJobInfo()
  },
  methods: {
    getJobInfo(){
      this.request.get("/job/jobInfo/page", {
        params: {
          pageNum:      this.pageNum,
          pageSize:     this.pageSize,
          jobName:      this.name,
          address:      this.address,
          requirement:  this.requirement,
        }
      }).then(res => {
        // console.log(this.address)
        // console.log(this.pageNum,this.pageSize,this.name,this.requirement)
        if(res.code === '200') {
          // console.log(res)
          this.jobList = res.data.records
          // console.log(this.jobList)
          this.jobData = []
          // console.log(this.jobList)
          this.jobList.forEach(item =>{
            item.companys.forEach(company =>{
              delete item.companys
              Object.assign(company, item)
              item.users.forEach(user => {
                delete item.users
                Object.assign(company, {"nickname" : user.nickname})
                Object.assign(company, {"avatarUrl" : user.avatarUrl})
              })
              this.jobData.push(company)
            })
          })
          // console.log(this.requirement)
          this.total = res.data.total          
        }
      })
      // console.log(this.radio)
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.getJobInfo()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.getJobInfo()
    },
  }
}
</script>

<style lang="less">
  .search {
    width: 100%;
    .el-input--mini .el-input__inner {
      border-radius: 30px;
      height: 45px;
    }
    .el-input i {
      font-size: 25px;
      font-weight: bold;
      line-height: 45px;
      // color: gray;
      margin-right: 10px;
      cursor: pointer;
    }
    .el-radio {
      margin-right: 5px;
    }
    .el-radio__input {
      display: none;
    }
    .el-radio__label:hover {
      color: #f4612d;
    }
    .el-radio__input.is-checked+.el-radio__label {
      color: #f4612d;
    }
  }
  .jobList {
    width: 100%;
    height: 150px;
    &:hover {
      border: 1px solid #f4612d;
      box-shadow: 10px 10px 10px rgba(218, 217, 217, 0.5);
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
  .pageBar {
    text-align: center;
    margin-top: 10px;
  }
</style>
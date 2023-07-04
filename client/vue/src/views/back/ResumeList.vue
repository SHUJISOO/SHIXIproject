<template>
  <div>
      <!-- 页眉 -->
      <div class="PageHeader">
          <el-page-header @back="goBack" content="详情页面"></el-page-header>
      </div>

      <!-- 工具栏 -->
      <div class="search">
        <el-row>
          <el-col :span="24">
            <el-input placeholder="查询面试者或面试岗位" v-model="name" @keyup.enter.native="getInfo">
              <template>
                <i slot="suffix" class="el-icon-search icon" @click="getInfo"></i>
              </template>
              
            </el-input>
          </el-col>
        </el-row>
      </div>

      <!-- 表格数据 -->
      <div>
        <el-table :data="tableData" class="table" @expand-change="expandChange">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="基本信息" style="width:100%;">
                </el-form-item>
                <el-form-item label="学历">
                  <span>{{ props.row.degree }}</span>
                </el-form-item>
                <el-form-item label="毕业学校">
                  <span>{{ props.row.school }}</span>
                </el-form-item>
                <el-form-item label="手机号码">
                  <span>{{ props.row.phone }}</span>
                </el-form-item>
                <el-form-item label="电子邮箱">
                  <span>{{ props.row.email }}</span>
                </el-form-item>
                <el-form-item label="在线简历" style="width:100%;">
                </el-form-item>
                <el-form-item label="教育经历" style="width:100%;">
                  <span>{{ resumeList.education }}</span>
                </el-form-item>
                <el-form-item label="校园经历" style="width:100%;">
                  <span>{{ resumeList.school }}</span>
                </el-form-item>
                <el-form-item label="实习经历" style="width:100%;">
                  <span>{{ resumeList.experience }}</span>
                </el-form-item>
                <el-form-item label="获奖经历" style="width:100%;">
                  <span>{{ resumeList.award }}</span>
                </el-form-item>
                <el-form-item label="技能/语言" style="width:100%;">
                  <span>{{ resumeList.skill }}</span>
                </el-form-item>
                <el-form-item label="其他" style="width:100%;">
                  <span>{{ resumeList.else }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
          <el-table-column label="面试者" prop="username" align="center"></el-table-column>
          <el-table-column label="面试岗位" prop="jobName" align="center" width="200"></el-table-column>
          <el-table-column label="简历附件" prop="size" align="center" width="150">
            <template slot-scope="scope">
              <el-button type="success" plain @click="preview(scope.row.fileUrl)">预览</el-button>
            </template>
          </el-table-column>
          <el-table-column label="下载" width="150" align="center">
            <template slot-scope="scope">
              <el-button type="success" plain @click="download(scope.row.fileUrl)">下载</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="250" fixed="right">
            <template slot-scope="scope">
              <!-- <el-button type="danger"  plain @click="agree(scope.row.JOBID,scope.row.id)" ref="agree" v-if="scope.row.accepts[0].accept === 1">
                同意<i class="el-icon-circle-check"></i>
              </el-button> -->
              <el-button type="danger"  plain @click="agree(scope.row.JOBID,scope.row.id)" ref="agree" >
                同意<i class="el-icon-circle-check"></i>
              </el-button>
              
              <el-button type="warning" plain @click="refuse(scope.row.JOBID,scope.row.id)" >
                拒绝<i class="el-icon-circle-close"></i>
              </el-button>
            </template>
          </el-table-column>
        </el-table>  
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
  </div>


</template>

<script>
export default {
data() {
  return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 9,
      dataList:[],
      name: "",
      flag: true,
      accept: [],
      resumeList: [],
      dialogVisible: false,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
  }
},
created() {
  this.getInfo()

},
methods: {
  getInfo(){
    this.request.get("/user/resumeInfo/" + this.userInfo.id, {
      params: {
        pageNum:  this.pageNum,
        pageSize: this.pageSize,
        nickname: this.name,
        jobName:  this.name,
      }
    }).then(res => {
      this.dataList = res.data.records
      // console.log(this.dataList)
      this.tableData = []
      this.dataList.forEach(item => {      
        item.jobs.forEach(job =>{        
          delete item.jobs
          this.tableData.push(Object.assign({"JOBID" : job.id,"jobName" : job.jobName,"agree" : job.agree,"refuse" : job.refuse}, item))
        })
      })
      // console.log(this.tableData[0].)
      console.log(this.tableData)
      this.total = this.tableData.length
    })
  },
  getAccept(){
    // this.request.get("/accept/" + this.userInfo.id + "/" + this.deliveryData.id).then(res => {       
    //   if(res.data[0] === undefined) {
    //     this.accept = -1
    //   }else{
    //     this.accept = res.data[0].accept
    //   }
    //   console.log(this.accept)
    // })
  },
  expandChange(row){    
    this.request.get("/resume/oneResume/" + row.id).then(res => {       
      this.resumeList = res.data[0]
    })
  },
  agree(jobId,applicantId) {
    this.request.post("/job/agreeOrRefuse/" + jobId + "/" + applicantId + "/" + 1).then(res => {
      if (res.code === '200') {
        this.$message.success("同意面试！")
      } else {
        this.$message.error(res.msg)
      }
    })
  },
  refuse(jobId,applicantId) {
    this.request.post("/job/agreeOrRefuse/" + jobId + "/" + applicantId + "/" + 2).then(res => {
      if (res.code === '200') {
        this.$message.success("取消面试！")
      } else {
        this.$message.error(res.msg)
      }
    })
  },
  download(url) {
    window.open(url)
  },
  preview(url) {
    // window.open('https://file.keking.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
    window.open('https://file.keking.cn/preview/onlinePreview?url='+encodeURIComponent(window.btoa((url))))
  },
  goBack() {
    this.name = ""
    this.getInfo()
  },
  handleSizeChange(pageSize) {
    this.pageSize = pageSize
    this.getInfo()
  },
  handleCurrentChange(pageNum) {
    this.pageNum = pageNum
    this.getInfo()
  },
},

}
</script>

<style>
  .headerBg {
    background: #eee!important;
  }
  thead {
    color: #374d5c!important;
    font-size: 17px;
  }
  tbody {
    font-size: 14px;
    color: #000!important;
  }
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .PageHeader {
    height: 40px;
    border-bottom: 1px solid #374d5c;
  }
  .search {
    height: 60px;
    line-height: 60px;
    margin-top: 10px;
  }
  .search .el-input {
    width: 400px;
    font-size: 14px;
  }
  .search .el-input .icon {
    font-size: 25px;
    font-weight: bold;
    line-height: 40px;
    margin-top: 10px;
    margin-right: 10px;
    cursor: pointer;
  }
  .search .el-input--mini .el-input__inner {
    height: 40px;
    line-height: 40px;
    background-color: #f6f6f6;
    border: none;
    border-radius: 20px;
  }
  .el-col:nth-child(2) {
    padding-right: 10px;
  }
  .el-col:nth-child(2) .el-button {
    margin-left: 10px;
    margin-top: 16px;
    float: right;
  }
  .table {
    width: 100%;
    min-height: 440px;
    /* overflow: auto;
    overflow-x: hidden; */
  }
  .pageBar {
    text-align: center;
    margin-top: 10px;
  }
  /* 展开行的内容 */
  .el-form {
    /* padding-left: 200px; */
    text-align: center;
  }
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 100px;
    color: #99a9bf;
    font-weight: bold;
  }
  .demo-table-expand .el-form-item {
    /* padding-left: 163px; */
    text-align: center;
    width: 50%;
  }

</style>
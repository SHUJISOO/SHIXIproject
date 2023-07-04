<template>
  <div>
      <!-- 页眉 -->
      <div class="PageHeader">
          <el-page-header @back="goBack" content="公司管理"></el-page-header>
      </div>

      <!-- 工具栏 -->
      <div class="search">
        <el-row>
          <el-col :span="24">
            <el-input placeholder="查询公司信息" v-model="name" @keyup.enter.native="getInfo">
              <template>
                <i slot="suffix" class="el-icon-search icon" @click="getInfo"></i>
              </template>
              
            </el-input>
          </el-col>
        </el-row>
      </div>

      <!-- 表格数据 -->
      <div>
        <el-table :data="tableData" class="table">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="公司介绍" style="width:100%;">
                  <span>{{ props.row.companyIntroduction }}</span>
                </el-form-item>
                <el-form-item label="公司描述" style="width:100%;">
                  <el-tag class="tag" style="background-color: #fff!important;" v-for="tag in props.row.companyDescription.split(',')" :key="tag">
                    {{ tag }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="公司岗位" style="width:100%;">
                  <el-button type="warning" style="color: #f4612d!important;" plain v-for="job in props.row.jobs">
                    {{ job.jobName }}
                  </el-button>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
          <el-table-column label="LOGO" prop="companyImg" align="center" width="100">
            <template slot-scope="scope">
              <img :src="scope.row.companyImg" alt="" style="width: 50px;height: 50px;">
            </template>
          </el-table-column>
          <el-table-column label="公司名称" prop="companyName" align="center"></el-table-column>
          <el-table-column label="公司地点" prop="companyAddress" align="center" width="180"></el-table-column>
          <el-table-column label="成立时间" prop="createTime" align="center" width="180"></el-table-column>
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
    this.request.get("/company/companyList", {
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }
    }).then(res => {
      console.log(res.data)
      this.tableData = res.data.records
      this.total = res.data.total
    })     
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
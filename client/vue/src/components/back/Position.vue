<template>
  <div>
    <el-table :data="jobData" class="table">
      <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
      <el-table-column label="公司名称" prop="companyName" align="center" width="300">
      </el-table-column>
      <el-table-column label="职位名称" prop="jobName" align="center">
      </el-table-column>
      <el-table-column label="是否精选" prop="isSelected" align="center" width="150">
        <template slot-scope="props">
          <el-switch v-model="props.row.isSelected" active-color="#13ce66" inactive-color="#ff4949" @change="change1($event,props.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="是否热门" prop="hotJob" align="center" width="150">
        <template slot-scope="props">
          <el-switch v-model="props.row.hotJob" active-color="#13ce66" inactive-color="#ff4949" @change="change2($event,props.row)"></el-switch>
        </template>
      </el-table-column>
    </el-table>  

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
      name: "",
      address: "",
      requirement: "",
      jobList:[],
      jobData: [],
      form: {},
      total: 0,
      pageNum: 1,
      pageSize: 9,
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo() {
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
          
          this.total = res.data.total  
          console.log(this.form)
        }
      })
      // console.log(this.radio) 
    },
    change1(val,info){
      this.form = {}
      this.form = info
      this.form.isSelected = val
      this.save()
    },
    change2(val,info){
      this.form = {}
      this.form = info
      this.form.hotJob = val
      this.save()
    },
    save() {
      this.request.post("/job", this.form).then(res => {
        if (res.data) {
          this.$message.success("修改成功!")
          this.getInfo()
        } else {
          this.$message.error("修改失败!")
        }
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.getInfo()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.getInfo()
    },
  }
}
</script>

<style lang="less">
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
  .pageBar {
    text-align: center;
    margin-top: 10px;
  }
</style>
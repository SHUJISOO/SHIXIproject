<template>
  <div>
      <!-- 页眉 -->
      <div class="PageHeader">
          <el-page-header @back="goBack" content="详情页面"></el-page-header>
      </div>

      <!-- 工具栏 -->
      <div class="search">
        <el-row>
          <el-col :span="16">
            <el-input placeholder="查询职位" v-model="name" @keyup.enter.native="getInfo">
              <template>
                <i slot="suffix" class="el-icon-search icon" @click="getInfo"></i>
              </template>
              
            </el-input>
          </el-col>

          <el-col :span="8">
            <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              @confirm="selectDel"
              title="这是一段内容确定删除吗？">
              <el-button type="danger" plain slot="reference">删除<i class="el-icon-delete"></i></el-button>
            </el-popconfirm>  
            <el-button class="Add" @click="addInfo">添加<i class="el-icon-circle-plus-outline"></i></el-button>                      
          </el-col>
        </el-row>
      </div>

      <!-- 表格数据 -->
      <div>
        <el-table :data="jobData" class="table" @selection-change="handleSelectionChange">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="职位介绍">
                  <span>{{ props.row.jobIntroduction }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column type="selection" width="55" align="center" fixed></el-table-column>
          <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
          <el-table-column label="招聘职位" prop="jobName" align="center" width="200"></el-table-column>
          <el-table-column label="职位要求" prop="requirement" show-overflow-tooltip align="center" width="200">
            <template slot-scope="scope">
              <el-tag type="success" class="successTag" v-for="item,index in scope.row.requirement.split(',')" :key="index">
                {{ item }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="职位薪资" prop="salary" align="center" width="150"></el-table-column>
          <el-table-column label="职位描述" prop="jobDescription" show-overflow-tooltip align="center">
            <template slot-scope="scope">
              <el-tag class="tag" v-for="item,index in scope.row.jobDescription.split(',')" :key="index">
                {{ item }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="120" fixed="right">
            <template slot-scope="scope">
              <el-button type="primary" @click="editInfo(scope.row)" plain>编辑<i class="el-icon-edit"></i></el-button>
                <!-- <el-button type="danger" plain>删除<i class="el-icon-delete"></i></el-button>                       -->
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

      <el-dialog title="职位信息" :visible.sync="dialogFormVisible" width="40%" >
        <el-form label-width="80px" size="small">
          <el-form-item label="职位名称">
            <el-input v-model="form.jobName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="职位要求">
            <!-- <el-input v-model="form.requirement" autocomplete="off"></el-input> -->
            <el-tag :key="tag" v-for="tag in requirementTags" closable type="success" class="successTag" :disable-transitions="false" @close="requirementClose(tag)">
              {{ tag }}
            </el-tag>
            <el-input class="input-new-tag" v-if="requirementVisible" v-model="requirementValue" ref="requirementTagInput" size="small" @keyup.enter.native="requirementConfirm" @blur="requirementConfirm"></el-input>
            <el-button v-else type="info" class="button-new-tag" size="small" @click="requirementInput">+</el-button>
          </el-form-item>
          <el-form-item label="职位薪资">
            <el-input v-model="form.salary" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="职位描述">
            <!-- <el-input v-model="form.jobDescription" autocomplete="off"></el-input> -->
          <el-tag :key="tag" class="tag" v-for="tag in jobDescriptionTags" closable :disable-transitions="false" @close="jobDescriptionClose(tag)">
            {{tag}}
          </el-tag>
          <el-input class="input-new-tag" v-if="jobDescriptionVisible" v-model="jobDescriptionValue" ref="jobDescriptionTagInput" size="small" @keyup.enter.native="jobDescriptionConfirm" @blur="jobDescriptionConfirm">
          </el-input>
          <el-button v-else type="info" class="button-new-tag" size="small" @click="jobDescriptionInput">+</el-button>
          </el-form-item>
          <el-form-item label="职位介绍">
            <el-input v-model="form.jobIntroduction" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
  </div>


</template>

<script>
export default {
data() {
  return {
      total: 0,
      pageNum: 1,
      pageSize: 9,
      jobList:[],
      jobData:[],
      name: "",
      delIdList: [],
      flag: true,
      form: {},
      requirementTags: [],
      requirementVisible: false,
      requirementValue: '',
      jobDescriptionTags: [],
      jobDescriptionVisible: false,
      jobDescriptionValue: '',
      dialogFormVisible: false,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
  }
},
created() {
  this.getInfo()

},
methods: {
  getInfo(){
    this.request.get("/company/jobInfo/" + this.userInfo.id, {
      params: {
        jobName:  this.name,
        pageNum:  this.pageNum,
        pageSize: this.pageSize,
      }
    }).then(res => {
      console.log(this.pageNum,this.pageSize,this.name)
      if(res.code === '200') {
        // console.log()
        this.jobList = res.data.records
        // console.log(this.jobList[0].id)
        this.jobData = []
        this.jobList.forEach(item => {
          console.log(item.id)
          // Object.assign({"COMPANYID" : item.id})
          item.jobs.forEach(job =>{
            // console.log(job)
            delete item.jobs
            Object.assign(job,{"COMPANYID" : item.id})
            this.jobData.push(job)
          })
        })
        console.log(this.jobData)
      }
      this.total = this.jobData.length
    })
  },
  addInfo() {
    this.form = {}
    this.dialogFormVisible = true
  },
  editInfo(data) {
    this.form = data
    this.requirementTags = this.form.requirement.split(',')  
    this.jobDescriptionTags = this.form.jobDescription.split(',') 
    this.dialogFormVisible = true
  },
  selectDel() {
    let ids = this.delIdList.map(v => v.id)         // [{}, {}, {}] => [1,2,3]
    this.request.post("/job/del/batch", ids).then(res => {
      console.log(res)
      if (res.code === '200') {
        this.$message.success("删除成功!")
        this.getInfo()
      } else {
        this.$message.error("删除失败!")
      }
    })
  },
  handleSelectionChange(val) {
    // console.log(val)
    this.delIdList = val
  },
  save() {
    // console.log(this.form)
    this.form.requirement     = this.requirementTags.join(",")
    this.form.jobDescription  = this.jobDescriptionTags.join(",")
    this.form.companyId       = this.jobList[0].id
    this.request.post("/job", this.form).then(res => {
      if (res.data) {
        this.$message.success("保存成功!")
        this.dialogFormVisible = false
        this.getInfo()
      } else {
        this.$message.error("保存失败!")
      }
    })
  },
  requirementClose(tag) {
    this.requirementTags.splice(this.requirementTags.indexOf(tag), 1);
  },
  requirementInput() {
    this.requirementVisible = true;
    this.$nextTick(_ => {
      this.$refs.requirementTagInput.$refs.input.focus();
    });
  },
  requirementConfirm() {
    let requirementValue = this.requirementValue;
    if (requirementValue) {
      this.requirementTags.push(requirementValue);
    }
    this.requirementVisible = false;
    this.requirementValue = '';
  },
  jobDescriptionClose(tag) {
    this.jobDescriptionTags.splice(this.jobDescriptionTags.indexOf(tag), 1);
  },
  jobDescriptionInput() {
    this.jobDescriptionVisible = true;
    this.$nextTick(_ => {
      this.$refs.jobDescriptionTagInput.$refs.input.focus();
    });
  },
  jobDescriptionConfirm() {
    let jobDescriptionValue = this.jobDescriptionValue;
    if (jobDescriptionValue) {
      this.jobDescriptionTags.push(jobDescriptionValue);
    }
    this.jobDescriptionVisible = false;
    this.jobDescriptionValue = '';
  },
  download(url) {
    window.open(url)
  },
  preview(url) {
    window.open('https://file.keking.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
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
  .successTag {
    width: auto;
    margin-right: 10px;
    padding: 0 10px;
  }
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    width: auto;
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
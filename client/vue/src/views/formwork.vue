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
          <el-input placeholder="查询文件名" v-model="name" @keyup.enter.native="getInfo">
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
        </el-col>
      </el-row>
    </div>

    <!-- 表格数据 -->
    <div>
      <el-table :data="tableData" class="table" @selection-change="handleSelectionChange">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form class="demo-table-expand" style="padding: 0 15%;">
              <el-form-item>
                <span>{{ props.row.content }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" align="center" fixed></el-table-column>
        <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
        <el-table-column label="用户" width="200" prop="name" align="center"></el-table-column>
        <el-table-column label="评分" width="100" prop="rate" align="center"></el-table-column>
        <el-table-column label="联系电话" prop="phone" align="center" width="150"></el-table-column>
        <el-table-column label="电子邮箱" prop="email" align="center"  width="200"></el-table-column>
        <el-table-column label="联系地址" prop="address" align="center"></el-table-column>
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
    fileList:[],
    delIdList: [],
    name: "",
    dialogVisible: false,
  }
},
created() {
  this.getInfo()
},
methods: {
  getInfo(){
    this.request.get("/contact", {
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.name,
      }
    }).then(res => {
      this.tableData = res.data
      this.total = res.data.total
      console.log(this.tableData)
    })
  },
  save(row) {
    this.request.post("/file",row).then(res => {
      if (res.data) {
        this.$message.success("禁用成功!")
        this.dialogFormVisible = false
        this.getInfo()
      } else {
        this.$message.error("禁用失败!")
      }
    })
  },
  addInfo() {
    this.dialogVisible = true
    console.log(this.fileList)
    if(this.fileList.length != 0) {
      this.$refs.upload.clearFiles();
    }
  },
  selectDel() {
    let ids = this.delIdList.map(v => v.id)         // [{}, {}, {}] => [1,2,3]
    this.request.post("/file/del/batch", ids).then(res => {
      if (res.data) {
        this.$message.success("删除成功!")
        this.getInfo()
      } else {
        this.$message.error("删除失败!")
      }
    })
  },
  download(url) {
    window.open(url)
  },
  preview(url) {
    window.open('https://file.keking.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
  },
  handleSuccess() {
    this.getInfo()
  },
  handleExceed(file, fileList) {
    // this.$message.warning("当前限制选择 3 个文件!");
    console.log(file, fileList);
    this.$message.warning(`当前限制选择 ${ file.length } 个文件`);
  },
  beforeRemove(file) {
    return this.$confirm(`确定移除 ${ file.name }？`);
  },
  handleSelectionChange(val) {
    // console.log(val)
    this.delIdList = val
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
  width: 100%;
}

</style>
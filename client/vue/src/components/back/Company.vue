<template>
  <div>
    <el-table :data="tableData" class="table">
      <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
      <el-table-column label="公司名称" prop="companyName" align="center" >
      </el-table-column>
      <el-table-column label="是否热门" prop="hotJob" align="center" width="200">
        <template slot-scope="props">
          <el-switch v-model="props.row.hot" active-color="#13ce66" inactive-color="#ff4949" @change="change($event,props.row)"></el-switch>
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
      tableData: [],
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
      this.request.get("/company/page", {
        params: {
          pageNum:      this.pageNum,
          pageSize:     this.pageSize,
        }
      }).then(res => {
        if(res.code === '200') {
          this.tableData = res.data.records
          this.total = res.data.total 
        }
      })
    },
    change(val,info){
      this.form = {}
      this.form = info
      this.form.hot = val
      this.save()
    },
    save() {
      this.request.post("/company", this.form).then(res => {
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
<template>
  <div>
    <el-button class="Add" style="float: right; margin-right: 35px;" @click="addInfo">添加<i class="el-icon-circle-plus-outline"></i></el-button>
    <el-table :data="tableData" class="table">
      <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
      <el-table-column label="缩略图" prop="imgUrl" align="center" width="300">
        <template slot-scope="scope">
          <img :src="scope.row.imgUrl" alt="" style="width: 200;height: 100px;">
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="type" align="center">
        <template slot-scope="props">
          <span v-if="props.row.type === 'long'">封面图</span>
          <span v-else>其他图</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="props">
          <el-button type="primary" style="margin-right: 30px;" @click="editInfo(props.row)" plain>编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
            confirm-button-text='好的'
            cancel-button-text='不用了'
            icon="el-icon-info"
            icon-color="red"
            @confirm="dele(props.row.id)"
            title="这是一段内容确定删除吗？">
            <el-button type="danger" plain slot="reference">删除<i class="el-icon-delete"></i></el-button>
          </el-popconfirm>  
        </template>
      </el-table-column>
    </el-table>  

    <el-dialog title="设置走马灯" :visible.sync="dialogFormVisible" width="35%" >
      <el-form label-width="80px" size="small" style="text-align: center;">
        <el-upload
          class="avatar-uploader"
          :action="'http://localhost:9090/file/upload'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
        >
          <img v-if="form.imgUrl" :src="form.imgUrl" class="avatar" style="width: 300px; height: 150px;">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

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
      pageSize: 6,
      dialogFormVisible: false
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      this.request.get("/carousel/page", {
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }
    }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })  
    },
    editInfo(info){
      this.form = {}
      this.form = info
      this.dialogFormVisible = true
    },
    addInfo() {
      this.form = {}
      this.dialogFormVisible = true
    },
    save() {
      console.log(this.form)
      this.request.post("/carousel", this.form).then(res => {
        if (res.data) {
          this.$message.success("保存成功!")
          this.dialogFormVisible = false
          this.getInfo()
        } else {
          this.$message.error("保存失败!")
        }
      })
    },
    dele(id) {
      this.request.delete("/carousel/" + id).then(res => {
        if (res.data) {
          this.$message.success("删除成功!")
          this.getInfo()
        } else {
          this.$message.error("删除失败!")
        }
      })
    },
    handleAvatarSuccess(res) {
      console.log(res)
      this.form.imgUrl = res
      this.form.type = "long"
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
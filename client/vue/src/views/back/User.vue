<template>
    <!-- <el-table :data="tableData" style="width: 100%" stripe :header-cell-class-name="'headerBg'">
        <el-table-column prop="date" label="日期" width="180"></el-table-column>
        <el-table-column prop="name" label="姓名" width="180"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
                <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                <el-button type="text" size="small">编辑</el-button>
            </template>
        </el-table-column>
    </el-table> -->

    <div>
        <div class="PageHeader">
            <el-page-header @back="goBack" content="详情页面"></el-page-header>
        </div>

        <!-- 工具栏 -->
        <div class="search">
            <el-row>
                <el-col :span="16">
                    <el-input placeholder="查询用户名、昵称或身份" v-model="searchContent" @keyup.enter.native="getInfo">
                      <template>
                        <i slot="suffix" class="el-icon-search icon" @click="getInfo"></i>
                      </template>
                      
                    </el-input>
                    <!-- <el-button type="primary" plain style="margin-left:10px;" @click="getInfo">搜索<i class="el-icon-search"></i></el-button> -->
                    <!-- <el-button type="warning" plain>重置<i class="el-icon-switch-button"></i></el-button> -->
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
            <el-table :data="tableData" class="table" @selection-change="handleSelectionChange">
                <el-table-column type="expand">
                  <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-form-item label="用户名">
                        <span>{{ props.row.username }}</span>
                      </el-form-item>
                      <el-form-item label="昵称">
                        <span>{{ props.row.nickname }}</span>
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
                      <el-form-item label="注册详细时间">
                        <span>{{ props.row.createTime }}</span>
                      </el-form-item>
                      <el-form-item label="用户角色">
                        <span>{{ props.row.role }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column type="selection" width="55" align="center" fixed></el-table-column>
                <el-table-column label="序列" type="index" width="60" align="center" fixed></el-table-column>
                <el-table-column label="用户名" prop="username" align="center"></el-table-column>
                <el-table-column label="昵称" prop="nickname" align="center"></el-table-column>
                <el-table-column label="身份" prop="role" align="center" width="100">
                  <!-- eslint-disable-next-line -->
                  <template slot-scope="scope">
                    <el-tag type="danger" v-if="scope.row.role === '管理员'">管理员</el-tag>
                    <el-tag type="warning" v-if="scope.row.role === '企业'">企业</el-tag>
                    <el-tag type="success" v-if="scope.row.role === '学生'">学生</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="注册时间" prop="createTime" align="center" width="250">
                  <template slot-scope="scope">
                    <span>{{scope.row.createTime  | editTime }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="120" align="center" fixed="right">
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

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
          <el-form label-width="80px" size="small">
            <el-form-item label="用户名">
              <el-input v-model="form.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="form.nickname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="角色">
              <el-select clearable v-model="form.role" placeholder="请选择身份" style="width:100%;">
                <!-- <el-option v-for="item in roles" :key= "item.name" :label="item.name" :value="item.name">
                  <i :class="item.value"/> {{item.name}}
                  
                </el-option> -->
                <el-option label="企业" value="企业"></el-option>
                <el-option label="学生" value="学生"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="学历">
              <el-input v-model="form.degree" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="毕业学校">
              <el-input v-model="form.school" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="联系电话">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="电子邮箱">
              <el-input v-model="form.email" autocomplete="off"></el-input>
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
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 9,
        form: {},
        delIdList: [],
        roles: [{
          value: '选项1',
          name: '管理员'
        }, {
          value: '选项2',
          name: '企业'
        }, {
          value: '选项3',
          name: '实习生'
        }, ],
        searchContent: "",
        dialogFormVisible: false,
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo(){
        this.request.get("/user/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            username: this.searchContent,
            nickname: this.searchContent,
            role: this.searchContent,
          }
        }).then(res => {
          console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        })
        // this.request.get("/user").then(res => {
        //   console.log(res)
        //   this.tableData = res.data.data
        // //   this.total = res.data.total
        // })
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.data) {
          this.$message.success("保存成功!")
          this.dialogFormVisible = false
          this.getInfo()
        } else {
          this.$message.error("保存失败!")
        }
      })
    },
    addInfo() {
      this.dialogFormVisible = true
      this.form = {}
    },
    editInfo(data) {
      this.form = data
      this.dialogFormVisible = true
      // console.log(data)     
    },
    selectDel() {
      let ids = this.delIdList.map(v => v.id)         // [{}, {}, {}] => [1,2,3]
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.data) {
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
    goBack() {
      this.searchContent = ""
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
  filters: {
    editTime(val) {
      return val.substring(0, 10)
    }
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
    margin-right: 0;
    margin-bottom: 0;
    text-align: left;
    width: 35%;
  }

</style>


<template>
  <div>
    <!-- 页眉 -->
    <div class="PageHeader">
        <el-page-header content="详情页面"></el-page-header> <!-- @back="goBack" -->
    </div>

    <!-- 详细信息 -->
    <div class="boxInfo">
      <el-descriptions class="margin-top" title="个人信息" :column="1" border>
        
        <template slot="extra">
          <el-button type="primary" plain @click="editInfo(userInfo)">修改</el-button>
        </template>
        
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            头像
          </template>
          <img :src="userInfo.avatarUrl" class="avatar"/>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{ userInfo.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            昵称
          </template>
          {{ userInfo.nickname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            身份
          </template>
          {{ userInfo.role }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            创建时间
          </template>
          {{ userInfo.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            学历
          </template>
          {{ userInfo.degree }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            毕业学校
          </template>
          {{ userInfo.school }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            联系电话
          </template>
          {{ userInfo.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            电子邮箱
          </template>
          {{ userInfo.email }}
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <el-dialog title="个人信息" :visible.sync="dialogFormVisible" width="40%" >
      <el-upload
          class="avatar-uploader"
          :action="'http://localhost:9090/file/upload'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form label-width="80px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="form.role" autocomplete="off" disabled></el-input>
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
      <div slot="footer">
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
      userInfo: [],
      form: {},
      dialogFormVisible: false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getInfo().then(res => {
      // console.log(res)
      this.form = res
    })
  },
  methods: {
    // getInfo(){
    //   this.request.get("/user/" + this.user.id).then(res => {
    //     if (res.code === '200') {
    //       this.userInfo = res.data
    //     }
    //   })
    // },
    async getInfo() {
      this.userInfo =(await this.request.get("/user/" + this.user.id)).data
      return this.userInfo
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.data) {
          // 触发父级更新User的方法
          this.$emit("refresh")
          // 更新浏览器存储的用户信息
          this.getInfo().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })
          this.$message.success("保存成功!")
          this.dialogFormVisible = false
          this.getInfo()
        } else {
          this.$message.error("保存失败!")
        }
      })
    },
    editInfo(data) {
      this.form = data
      this.dialogFormVisible = true
      // console.log(data)     
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    }
  }
}
</script>

<style>
  .PageHeader {
    height: 40px;
    border-bottom: 1px solid #374d5c;
  }
  .boxInfo {
    width: 50%;
    margin: 20px auto 0;
  }
  .boxInfo .el-descriptions__title {
    color: gray;
    font-size: 20px;
  }
  .boxInfo table {
    font-size: 14px;
  }
  .boxInfo th{
    width: 40%;
    height: 45px!important;
    font-weight: bold!important;
  }
  .el-dialog {
    margin-top: 3vh!important;
  }
  .avatar {
    width: 138px;
    height: 138px;
    display: block;
  }
  .avatar-uploader {
    text-align: center;
    padding-bottom: 10px;
  }
</style>
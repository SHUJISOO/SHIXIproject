<template>
  <div>
    <!-- 页眉 -->
    <div class="PageHeader">
        <el-page-header content="详情页面"></el-page-header> <!-- @back="goBack" -->
    </div>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>修改密码</span>
      </div>
      <div class="text item">
        <el-form label-width="120px" size="small" :model="form" :rules="rules" ref="pass">
          <el-form-item label="原密码" prop="password">
            <el-input v-model="form.password" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="confirmPassword">
            <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="save" plain>确 定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import router from "@/router";
export default {
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules: {
        password: [
          { required: true, message: '请输入原密码！', trigger: 'blur' },
          { min: 1, message: '长度不少于3位！', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码！', trigger: 'blur' },
          { min: 1, message: '长度不少于3位！', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码！', trigger: 'blur' },
          { min: 1, message: '长度不少于3位！', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.form.username = this.user.username
  },
  methods: {
    logout() {
      // 清空缓存
      localStorage.removeItem("user")
      // localStorage.removeItem("menus")
      router.push("/login")
      // // 重置路由
      // resetRouter()
    },
    save() {
      this.$refs.pass.validate((valid) => {
        if (valid) {
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message.error("2次输入的新密码不相同！")
            return false
          }
          this.request.post("/user/password", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("修改成功,请重新登录！")
              // this.$store.commit("logout")
              this.logout()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style>
  .el-card {
    margin: 100px auto 0;
    width: 50%;
  }
  /* .el-card .el-button {
    margin: 0 auto;
  } */
  .PageHeader {
    height: 40px;
    border-bottom: 1px solid #374d5c;
  }
</style>
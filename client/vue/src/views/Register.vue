<template>
  <div class="wrapper">
    <div class="BoxRegister">
      <div class="login">
        <div style="text-align: center; line-height: 50px; color: #fff;">
          <p style="font-size: 20px; font-weight: bold;">已有账号？</p>
          <p>请点击登录您的账号吧🌹</p>
          <button @click="$router.push('/login')">登录</button>
        </div>
      </div>
      <div class="register">
        <div class="title">
          <div>
            <b style="font-size: 24px">注册</b><br>
            <span>请注册您的账号🌹</span>
          </div>
        </div>
        <div class="content">
          <el-form :model="user" :rules="rules" ref="userForm">
            <el-form-item prop="role">
              <span>请选择您的注册身份：</span>&emsp;
              <el-radio v-model="user.role" label="学生">学生</el-radio>
              <el-radio v-model="user.role" label="企业">企业</el-radio>
            </el-form-item>
            <el-form-item prop="username">
              <el-input placeholder="请输入用户名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input placeholder="请输入昵称" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.nickname"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword">
              <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="footer">
          <el-button type="warning" @click="register" style="background-color: #f4612d!important;">注册</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
    user: {},
    rules: {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
      ],
      role: [
        { required: true, message: '请选择注册身份', trigger: 'change' }
      ],
    }
    }
  },
  methods: {
    register() {
      // console.log(this.user.nicknam)
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.user.password !== this.user.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if(res.code === '200') {
              this.$message.success("注册成功，请登录！")
              this.user = {}
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style lang="less">
  .wrapper {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    // background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
    overflow: hidden;
  }
  .BoxRegister {
    width: 900px;
    height: 600px;  
    border-radius: 10px;
    display: flex;
    flex-direction: row;
    margin: auto;
    background-color: #fff; 
    box-shadow: 10px 10px 10px rgba(181, 180, 180, 0.5);
    .register {
      width: 50%; 
      height: 100%;
      background-color: #fff;
      border-radius: 0 10px 10px 0;
    }
    .login {
      width: 50%; 
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #f4612d;
      border-radius: 10px 0 0 10px;
      button {
        width: 80px;
        height: 40px;
        color: #fff;
        font-size: 16px;
        background-color: #f4612d;
        border: 1px #fff solid;
        border-radius: 20px;
        &:hover {
          font-weight: bold;
          color: #f4612d;
          background-color: #fff;
        }
      }
    }
    .title {
      text-align: center; 
      height: 25%; 
      display: flex; 
      justify-content: center; 
      align-items: center;
    }
    .content {
      padding: 5px 30px;
      height: 60%;
    }
    .footer {
      height: 15%;
      padding: 0 30px;
    }
    .el-button {
      width: 100%;
      height: 45px;
      border-radius: 15px!important;
    }
  }
  .el-radio-group {
    width: 390px!important;
  }
  .el-radio-group .el-radio-button__inner {
    width: 130px;
    height: 45px;
    line-height: 30px;
    font-size: 14px;
    font-weight: bold;
  }
  .el-input__inner {
    height: 45px!important;
    width: 100%;
    border-radius: 15px;
  }
</style>

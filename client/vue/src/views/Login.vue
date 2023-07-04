<template>
  <div class="wrapper">
    <div class="BoxLogin">
      <div class="login">
        <div class="title">
          <div>
            <b style="font-size: 24px">登 录</b><br>
            <span>请登录您的账号🌹</span>
          </div>
        </div>
        <div class="content">
          <el-form :model="user" ref="userForm" label-position="top" :rules="rules">
            <el-form-item prop="role" label="请选择您的身份">
              <el-radio-group v-model="user.role">
                <el-radio-button label="管理员"></el-radio-button>
                <el-radio-button label="企业"></el-radio-button>
                <el-radio-button label="学生"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item prop="username">
              <!-- <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input> -->
              <el-input style="margin: 5px 0" size="medium" placeholder="请输入账号" prefix-icon="el-icon-user" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <!-- <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input> -->
              <el-input style="margin: 5px 0" size="medium" placeholder="请输入密码" prefix-icon="el-icon-lock" v-model="user.password" show-password></el-input>
            </el-form-item>
            <el-form-item>
            </el-form-item>
          </el-form>
        </div>
        <div class="footer">
          <el-button type="primary" size="small" autocomplete="off" @click="login" style="background-color: #f4612d!important;">登录</el-button>
        </div>
      </div>
      <div class="register">
        <div style="text-align: center; line-height: 50px; color: #fff;">
          <p style="font-size: 20px; font-weight: bold;">没有账号？</p>
          <p>点击注册一个属于您的账号吧🌹</p>
          <button @click="$router.push('/register')">注册</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import router from "@/router";
export default {
  name: "Login",
  data() {
    return {
      user: {},
      // status:'',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login() {
      this.request.post("/user/login", this.user).then(res => {
        // console.log(res.data)
        if(res.code === '200'){
          localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
          if(res.data.role === this.user.role){
            if(this.user.role === '学生') {
              this.$message.success("登录成功！")
              this.$router.push("/index")
            }else {
              this.$message.success("登录成功！")
              this.$router.push("/home")
            }
          }
        }else {
          this.$message.error("登录信息有误，请重新登录！")
        }
      })
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
  .BoxLogin {
    width: 900px;
    height: 600px;  
    border-radius: 10px;
    display: flex;
    flex-direction: row;
    margin: auto;
    background-color: #fff; 
    box-shadow: 10px 10px 10px rgba(181, 180, 180, 0.5);
    .login {
      width: 50%; 
      height: 100%;
      background-color: #fff;
      border-radius: 10px 0 0 10px;
    }
    .register {
      width: 50%; 
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #f4612d;
      border-radius: 0 10px 10px 0;
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
      height: 50%;
    }
    .footer {
      height: 25%;
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

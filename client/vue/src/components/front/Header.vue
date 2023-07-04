<template>
  <div class="frontNavbar">
    <el-row>
      <el-col :span="7">
        <div class="frontLogo">
          <!-- <img src="@/assets/logo.png" alt="" style="width: 40px;"/> -->
          <span>在校大学生实习招聘平台</span>
          <i class="el-icon-menu menu" @click="drawer = true"></i>
        </div>
        <el-drawer title="大学生实习系统" :visible.sync="drawer" direction="ltr">
          <!-- <span>我来啦!</span> -->
          <!-- 头像 -->
          <div class="info">
            <div class="borderBox" v-if="flag">
              <img :src="userInfo.avatarUrl" alt="" style="width: 60px; border-radius: 50%; display: block;position: absolute; top: 50%; left: 8%;transform: translateY(-50%);"/>
              <div class="txt">
                <p>Hi,<span style="font-weight: bold;">{{ userInfo.nickname }}</span></p>
                <p style="font-size:14px;">{{ userInfo.email }}</p>
              </div>
            </div>

            <div class="borderBox" v-else>
              <div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-50%);">
                <p>请先登录！<a href="/login" style="color: red;">登录</a></p>
              </div>
            </div>
          </div>
          <el-menu class="el-menu-demo" mode="vertical" router>
            <el-menu-item index="/index" class="drawerItem">首页</el-menu-item>
            <el-menu-item index="/index/job" class="drawerItem">职位</el-menu-item>
            <el-menu-item index="/index/community" class="drawerItem">社区论坛</el-menu-item>
            <el-menu-item index="/index/contact" class="drawerItem">联系我们</el-menu-item>
          </el-menu>
        </el-drawer>
      </el-col>
      <el-col :span="11" class="col">
        <el-menu class="el-menu-demo" mode="horizontal" router>
          <el-menu-item index="/index" class="menuItem">首页</el-menu-item>
          <el-menu-item index="/index/job" class="menuItem">职位</el-menu-item>
          <el-menu-item index="/index/community" class="menuItem">社区论坛</el-menu-item>
          <el-menu-item index="/index/contact" class="menuItem">联系我们</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="6">
        <div>         
          <el-dropdown style="width: 250px; cursor: pointer;text-align: right;" v-if="flag">
            <div style="line-height: 70px; padding-left: 60%; display: flex; align-items: center;">
              <img :src="userInfo.avatarUrl" alt="" style="width: 50px; border-radius: 50%; ">    
              <span style="font-size: 17px; margin-left: 15px;">{{ userInfo.nickname }}</span>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
              <el-dropdown-item style="font-size: 16px; padding: 5px 0">
                <router-link to="/index/info">个人信息</router-link>
                <!-- 个人信息 -->
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 16px; padding: 5px 0">
                <router-link to="/index/delivery">投递消息</router-link>
                <!-- 投递消息 -->
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 16px; padding: 5px 0">
                <span @click="changePassword">修改密码</span>
                <!-- 修改密码 -->
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 16px; padding: 5px 0">
                <!-- <span style="text-decoration: none">退出</span> -->
                <span style="text-decoration: none" @click="logout">退出</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <router-link to="/login" v-else style="line-height: 70px; padding-left: 60%;">
            <i class="el-icon-s-custom loginLogo"></i>
          </router-link>
        </div>
      </el-col>
    </el-row>

    <!-- 修改密码弹窗 -->
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%" >
      <el-form size="small" :model="form" :rules="rules" ref="pass">
        <el-form-item label="原密码" prop="password">
          <el-input v-model="form.password" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import router from "@/router";
export default {
  data() {
    return {
      drawer: false,
      flag: true,
      dialogFormVisible: false,
      form: {},
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
  props: {
    userInfo: Object
  },
  created() {    
    if(Object.keys(this.userInfo).length === 0){
      this.flag = false
    }else{
      this.flag = true
    }
    this.form.username = this.userInfo.username
  },
  methods: {
    changePassword(){
      this.dialogFormVisible = true
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
    logout() {
    //   this.$store.commit("logout")
      // 清空缓存
      localStorage.removeItem("user")
    //   localStorage.removeItem("menus")
      router.push("/index")
      location.reload()
      this.$message.success("退出成功")
    }
  }
}
</script>

<style lang="less">
  .frontNavbar {
    // text-align: center;
    background-color: #fff;
    height: 70px;
    width: 100%;
    z-index: 999;
    position: fixed;
    box-shadow: 10px 10px 10px rgba(218, 217, 217, 0.5);
  }
  .frontLogo {
    line-height: 70px;
    span {
      font-size:28px;
      margin: 0 25px;
      color: gray;
      font-weight: bold;
    }
    .menu {
      font-size: 30px;
      color: gray;
    }
  }
  .v-modal {
    position: relative;
  }
  .el-drawer__header {
    height: 60px!important;
  }
  .el-drawer__header span {
    font-size: 24px;
    font-weight: bold;
  }
  .el-drawer__header i {
    font-size: 24px;
    font-weight: bold;
  }
  .el-drawer .drawerItem {
    width: 100%;
    height: 86px;
    color: gray;
    line-height: 86px;
    font-size: 20px;
    font-weight: bold;
    text-align: center;
    &:hover {
      background-color: #fff7f3;
    }
  }
  .col .el-menu{
    /* width: 80%; */
    margin: 0 auto;
    display: flex;
    justify-content:space-around;
  }
  .frontNavbar .menuItem {
    width: 20%;
    height: 70px;
    line-height: 70px;
    font-size: 19px;
    /* font-weight: bold; */
    text-align: center;
  }
  // .info {
  //   height: 100px;
  //   position: relative;
  // }
  // .info .borderBox {
  //   width: 70%;
  //   height: 80px;
  //   border: 2px #c6c4c4 dashed;
  //   border-radius: 10px;
  //   position: absolute;
  //   top: 50%;
  //   left: 50%;
  //   transform: translate(-50%, -50%);
  // }
  // .info .txt {
  //   position: absolute;
  //   top: 50%;
  //   left: 50%;
  //   transform: translate(-15%,-50%);
  //   line-height: 26px;
  //   text-align: left;
  // }
  // .info .borderBox p {
  //   width: 200px;
  //   color: gray;
  //   font-size: 17px;
  //   overflow: hidden;
  // }
  .el-menu--horizontal>.el-menu-item:hover {
    border-bottom: 3px solid #f4612d!important;
    font-weight: bold;
    color: #f4612d!important;
  }
  .el-menu--horizontal>.el-menu-item.is-active {
    color: #fff!important;
    border-bottom: 3px solid #f4612d!important;
    background-color: #f4612d!important;
    font-weight: bold;
  }
  .loginLogo {
    font-size: 40px; 
    margin: 20px 20px 0 0; 
    color: gray; 
    cursor: pointer;
    &:hover {
      color: #f4612d;
    }
  }
  .el-dropdown-menu__item:hover {
    background-color: #fff7f3!important;
    // color: #f4612d!important;
    span {
      color: #f4612d!important;
    }
    /* background-color: red!important; */
  }
</style>
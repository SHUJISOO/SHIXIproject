<template>
    <div style="position:relative;">
        <!-- Header -->
        <el-row>
            <el-col :span="12">
                <div style="float:left;position: relative;">
                    <!-- <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
                    <b style="color: white" v-show="logoTextShow">后台管理系统</b> -->
                    <!-- <img src="@/assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px"> -->
                    <b style="font-size:26px;">后台管理系统</b>&ensp;&ensp;&ensp;
                    <span :class="collapseBtnClass" style="cursor: pointer; font-size: 26px; position: absolute;top: 18px;" @click="collapse"></span>
                    <!-- <i class="el-icon-s-fold" style="font-size: 25px;"></i> -->
                </div>
            </el-col>
            <el-col :span="12">
                <div style="float:right;margin-right: 20px;">
                    <el-dropdown style="width: 250px; cursor: pointer;text-align: right;">
                        <div style="display: inline-block">
                            <!-- <img :src="user.avatarUrl" alt="" style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px"> -->
                            <!-- <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i> -->
                            <img :src="userInfo.avatarUrl" alt="" style="width: 40px; border-radius: 50%; position: relative; top: 10px; right: 15px">    
                            <span>Hi, {{ userInfo.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                        </div>
                        <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
                            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                                <router-link to="/info">
                                  <span>个人信息</span>
                                </router-link>
                                <!-- 个人信息 -->
                            </el-dropdown-item>
                            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                                <router-link to="/password">
                                  <span>修改密码</span>
                                </router-link>
                                <!-- 修改密码 -->
                            </el-dropdown-item>
                            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                                <!-- <span style="text-decoration: none">退出</span> -->
                                <span style="text-decoration: none" @click="logout">退出</span>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>                    
                </div>
            </el-col>
        </el-row>
        <!-- <hr> -->
        <!-- <div class="menu">
            首页
        </div> -->
    </div>
</template>

<script>
import router from "@/router";
export default {
  props: {
    collapseBtnClass: String,
    userInfo: Object,
    collapse: '',
  },
  methods: {
    logout() {
    //   this.$store.commit("logout")
      // 清空缓存
      localStorage.removeItem("user")
    //   localStorage.removeItem("menus")
      router.push("/login")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style lang="less">
  .menu {
    height: 36px;
    font-size: 18px;
    line-height: 20px;
    text-align: left;
  }
  hr {
    position:absolute;
    top: 55px;
    color: gray;
    width: 100%;
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
<template>
    <div class="box">
        <el-menu router :collapse="isCollapse" :collapse-transition="false" style="width: navside + 'px';">
            <!-- logo图标 -->
            <div class="logo">
                <img src="@/assets/logo.png" alt="" style="width: 30px;">
            </div>

            <!-- 头像 -->
            <div class="info">
                <img :src="userInfo.avatarUrl" alt="" style="width: 50px; border-radius: 50%; position: absolute;left:25px;top:15px;">
                <div class="text" v-show="logoTextShow">
                    <p style="font-size:15px;">{{ userInfo.nickname }}</p>
                    <p><i class="el-icon-s-custom"></i>&ensp;{{ userInfo.role }}</p>
                </div>
            </div>

            <div class="nav">
                <!-- 导航栏 -->
                <el-menu-item index="/home">
                    <i class="el-icon-monitor" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;后台首页
                        <!-- <router-link to="/home">后台首页</router-link> -->
                    </span>
                </el-menu-item>

                <!-- <el-submenu index="2" v-if="userInfo.role === '管理员'">
                    <template slot="title">
                        <i class="el-icon-monitor" style="font-size: 26px;"></i>
                        <span slot="title" v-show="logoTextShow">&emsp;系统管理</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="/user">
                            <i class="el-icon-s-custom" style="font-size: 26px;"></i>
                            <span slot="title">&emsp;用户管理
                            </span>
                        </el-menu-item>
                    </el-menu-item-group>
                </el-submenu> -->

                <el-menu-item index="/user" v-if="userInfo.role === '管理员'">
                    <i class="el-icon-s-custom" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;用户管理</span>
                </el-menu-item>

                <el-menu-item index="/company" v-if="userInfo.role === '管理员'">
                    <i class="el-icon-school" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;公司管理</span>
                </el-menu-item>

                <el-menu-item index="/file" v-if="userInfo.role === '管理员'">
                    <i class="el-icon-folder-opened" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;文件管理</span>
                </el-menu-item>

                <el-menu-item index="/resumeList" v-if="userInfo.role === '企业'">
                    <i class="el-icon-document-copy" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;投递情况</span>
                </el-menu-item>
                <el-menu-item index="/positionList" v-if="userInfo.role === '企业'">
                    <i class="el-icon-collection" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;职位管理</span>
                </el-menu-item>
                <el-menu-item index="/companyInfo" v-if="userInfo.role === '企业'">
                    <i class="el-icon-warning-outline" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;公司信息</span>
                </el-menu-item>
                <el-menu-item index="/manage" v-if="userInfo.role === '管理员'">
                    <i class="el-icon-bell" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;首页管理</span>
                </el-menu-item>
                <el-menu-item index="/contactList" v-if="userInfo.role === '管理员'">
                    <i class="el-icon-message" style="font-size: 26px;"></i>
                    <span slot="title">&emsp;意见反馈</span>
                </el-menu-item>
            </div>
        </el-menu>   
    </div>
</template>

<script>
// import router from "@/router";
export default {
    props: {
      isCollapse: Boolean,
      logoTextShow:Boolean,
      navside:Number,
      userInfo: Object
    },
}
</script>

<style scoped>
    /* .box {
        position: relative;
    } */
    .el-menu {
        width: 270px;
        min-height: 500px;
        border-radius: 30px;
        margin: 20px 30px 0 20px;
        overflow-x: hidden;
        background-color: #fff;
        position: fixed;
    }
    .el-menu-item {
        background-color: #fff;
    }
    .el-menu--collapse {
        width: 100px;
    }
    span {
        color: #909399;
        font-size: 18px;
    }
    .logo {
        height: 60px;
        padding: 10px;
        line-height: 60px;
    }
    .info {
        height: 80px;
        position: relative;
    }
    .info .text {
        width: 140px;
        height: 50px;
        top: 15px;
        left: 100px;
        border: 1px #c6c4c4 dashed;
        padding: 5px 10px 0 10px;
        border-radius: 10px;
        line-height: none;
        position: absolute;
    }
    .info .text p {
        width: 120px;
        color: gray;
        font-size: 13px;
        font-weight: bold;
        overflow: hidden;
    }
</style>



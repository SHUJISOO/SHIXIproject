<template>
  <div class="frontInfo">
    <!-- 头像信息 -->
    <div class="square">
      <img :src="userInfo.avatarUrl" style="width: 100px; height: 100px; border-radius: 50%; outline: 6px solid rgb(128 128 128 / 0.3);"/>
      <span style="margin-left: 30px;align-self: flex-end; color: #000;">
        <p style="font-size: 40px;font-weight: bold;">{{ userInfo.nickname }}</p> 
        <span style="font-size: 20px;width: 200px;display: inline-block;">
          <i class="el-icon-user">&ensp;{{ userInfo.username }}</i>
        </span>&emsp;
        <span style="font-size: 20px;width: 250px;display: inline-block;">
          <i class="el-icon-time" v-if="userInfo.createTime">&ensp;{{ userInfo.createTime | editTime }}</i>
        </span> 
      </span> 
      <el-button type="primary" plain style="width: 150px; height: 50px; font-size: 22px!important;align-self: center;margin-left: auto;" @click="editInfo(userInfo)">修改</el-button>
    </div>

    <!-- 小组件 -->
    <el-row :gutter="24" type="flex" justify="space-between" style="color: #fff">
      <el-col :span="12">
        <div class="square" style="background-color: #ff884e;">
          <i class="el-icon-upload firstIcon"></i>
          <div style="align-self: center; margin-left: 30px;">
            <p style="font-size: 50px;">
              3<span style="font-size: 28px;font-weight: bold;">&ensp;份</span>
            </p>
            <p style="font-size: 20px;margin-left: 5px; ">已投简历</p>
          </div>
          <i class="el-icon-s-comment iconContext"></i>
        </div>
      </el-col>

      <el-col :span="12">
        <div class="square" style="background-color: #409EFF;">
          <i class="el-icon-s-order firstIcon"></i>
          <div style="margin-left: 20px; text-align: center; align-self: center;">
            <p style="font-size:30px;font-weight: bold;width: 100%;">附件简历</p>
            <p style="color: rgb(148, 246, 130);font-size:20px;width: 100%;" v-if="userInfo.fileUrl">
              已存在<i class="el-icon-success"></i>
            </p>
          </div>
          <i class="el-icon-circle-plus-outline outline" @click="resume"></i>
          <i class="el-icon-s-comment iconContext"></i>
        </div>
      </el-col>
    </el-row>

    <!-- 详细信息 -->
    <div class="square">
      <!-- <el-page-header style="width: 100%" content="详情页面"></el-page-header> -->
      <el-descriptions class="margin-top" title="个人信息" :column="2" :colon="false">  
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

    <!-- 在线简历 -->
    <div class="square" style="flex-direction:column;">
      <Resume></Resume> 
    </div>

    <!-- 修改信息弹窗 -->
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

    <!-- 上传简历弹窗 -->
    <el-dialog title="上传简历" :visible.sync="ResumeVisible" width="40%" class="resumeDialog">
      <el-upload
        class="upload-demo"
        action="http://localhost:9090/file/upload"
        :on-success="handleSuccess"
        :file-list="fileList"
        list-type="picture">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">最多上传一个文件</div>
      </el-upload>
    </el-dialog>    
  </div>
</template>

<script>
import Resume from '@/components/front/Resume.vue'
export default {
  data() {
    return {
      userInfo: [],
      form: {},
      fileList:[],
      space:"",
      dialogFormVisible: false,
      ResumeVisible: false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  components: {
    Resume
  },
  created() {
    this.getInfo().then(res => {
      this.form = res
    })
  },
  methods: {
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
    },
    resume() {
      this.ResumeVisible = true
    },
    handleSuccess(res) {
      this.form.fileUrl = res
      this.save()
    }
  },
  filters: {
    editTime(val) {
      return val.substring(0, 10)
    }
  }
}
</script>

<style>
  .square {
    border-radius: 20px;
    overflow: hidden;
    width: 100%;
    height: 100%;
    display: flex;
    position: relative;
    padding: 20px 30px;
    margin-top: 20px;
    background-color: #fff;
  }
  .el-descriptions__title {
    font-size: 22px;
  }
  .el-descriptions-item__container {
    height: 35px;
    line-height: 35px;
    font-size: 16px;
  }
  .el-descriptions-item__label  {
    width: 120px;
    font-size: 17px;
    font-weight: bold; 
  }
  .el-descriptions-item__content {
    overflow: hidden;
  }
  .el-row {
    margin-bottom: 40px;
  }
  .firstIcon {   
    width: 70px;
    height: 70px;
    color: #fff;
    line-height: 70px;
    text-align: center;
    font-size: 40px;
    border-radius: 50%;
    align-self: center;
    background-color: rgba(255,255,255,0.4);
  }
  .iconContext {
    font-size: 140px;
    opacity: 30%;
    color: rgb(255, 255, 255);
    position: absolute;
    top: 50%;
    right: 0;
    transform: translateY(-50%);
  }
  .el-col .outline {
    color: #fff;
    font-size: 90px;
    height: 100px;
    align-self: center;
    margin-left: 20px;
    cursor: pointer;
    border-radius: 50%;
  }
  .el-dialog {
    margin-top: 3vh!important;
  }
  .resumeDialog {
    margin-top: 15vh!important;
  }
  .resumeDialog .el-dialog__body {
    height: 200px!important;
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
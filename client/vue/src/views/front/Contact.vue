<template>
  <div>
    <div class="pageHeader"> </div>
    <div class="contactBox layout">
      <el-card style="width: 55%;">
        <div slot="header" style="text-align: center;">
          <span style="font-size: 22px; font-weight: bold;">联系我们</span>
        </div>
        <div style="padding: 20px;">
        <el-form label-position="top" label-width="80px" :model="form">
          <el-form-item label="姓名">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-row type="flex" justify="space-between">
            <el-col :span="11">
              <el-form-item label="联系电话">
                <el-input v-model="form.phone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="电子邮箱">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
            </el-col>
          </el-row>   
          <el-form-item label="地址">
            <el-input v-model="form.address"></el-input>
          </el-form-item>
          <el-form-item label="评分">
            <el-rate v-model="form.rate" :colors="colors"></el-rate>
          </el-form-item>
          <el-form-item label="内容">
            <el-input type="textarea" v-model="form.content"></el-input>
          </el-form-item>
          <el-button type="primary" style="margin-top: 20px;" plain @click="send">提&emsp;交</el-button>
        </el-form>
      </div>
      </el-card>

      <div class="aboutBox layout" style="width: 40%;height: 600px;flex-direction: column;">
        <div class="layout" style="height: 100px;">
          <div style="width: 100%;height: 70px;padding-left: 10%;">
            <p style="height: 60%;font-size: 26px;font-weight: bold;line-height: 42px;">与我们谈谈</p>
            <p style="height: 40%;line-height: 28px;">
              请随时与我们联系！我们总是乐于讨论新的项目、创意或有机会成为您愿景中的一部分。
            </p>
          </div>
        </div>

        <div class="layout" style="height: 100px;">
          <div class="iconBox" >
            <i class="el-icon-setting" style="color: #f4612d;"></i>
          </div>
          <div class="textBox">
            <div style="width: 100%;height: 70px;">
              <p style="height: 60%;font-size: 26px;font-weight: bold;line-height: 42px;">关于</p>
              <p style="height: 40%;line-height: 28px;" class="singleLine">在校大学生实习招聘平台</p>
            </div>
          </div>
        </div>

        <div class="layout" style="height: 100px;">
          <div class="iconBox" >
            <i class="el-icon-map-location" style="color: #409EFF;"></i>
          </div>
          <div class="textBox">
            <div style="width: 100%;height: 70px;">
              <p style="height: 60%;font-size: 26px;font-weight: bold;line-height: 42px;">地址</p>
              <p style="height: 40%;line-height: 28px;" class="singleLine">广东省汕尾市华南师范大学汕尾校区</p>
            </div>
          </div>
        </div>
        <div class="layout" style="height: 100px;">
          <div class="iconBox" >
            <i class="el-icon-phone-outline" style="color: #E6A23C"></i>
          </div>
          <div class="textBox">
            <div style="width: 100%;height: 70px;">
              <p style="height: 60%;font-size: 26px;font-weight: bold;line-height: 42px;">联系电话</p>
              <p style="height: 40%;line-height: 28px;" class="singleLine">0755 — 82181643</p>
            </div>
          </div>
        </div>
        <div class="layout" style="height: 100px;">
          <div class="iconBox" >
            <i class="el-icon-chat-line-round" style="color: #67C23A"></i>
          </div>
          <div class="textBox">
            <div style="width: 100%;height: 70px;">
              <p style="height: 60%;font-size: 26px;font-weight: bold;line-height: 42px;">电子邮箱</p>
              <p style="height: 40%;line-height: 28px;" class="singleLine">735419479@qq.com</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      colors: ['#C0C4CC', '#F7BA2A', '#f4612d']  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
    }
  },
  methods: {
    send() {
      if(this.user.id) {
        this.request.post("/contact", this.form).then(res => {
          if (res.code === '200') {
            this.$message.success("提交成功!")
            this.form = {}
          }else {
            this.$message.error("提交失败!")
          }
          // console.log(res)
        })        
      }else{
        this.$message.error("请先登录!")
      }
    }
  }
}
</script>

<style lang="less"> 
  .contactBox {
    // padding: 20px;
    .el-input__inner {
      height: 40px;
      font-size: 15px;
      border-radius: 30px;
    }
    .el-form--label-top .el-form-item__label {
      padding: 0;
      font-size: 16px;
      font-weight: bold;
    }
    .el-textarea__inner {
      height: 160px;
      border-radius: 10px;
    }
    .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item {
      margin-bottom: 10px;
    }
    .el-button {
      width: 100%;
      height: 55px;
      border-radius: 30px!important;
      font-size: 22px!important;
      margin-top: 10px;
    }
    .el-rate__icon {//评分图标大小
      font-size: 25px;
    }
  }
  .aboutBox {
    .iconBox {
      justify-content: center; /* 水平居中 */
      align-items: center;     /* 垂直居中 */
      width: 30%;
      display: flex;
      i {
        // width: 100%;
        // height: 80px;
        font-size: 60px;
        // background-color: #f5f5f5;
        align-self: center;
        align-items: center;
        justify-content: center;
        // color: #5f5f5f;
      }
    }
    .textBox {
      width: 70%;
      display: flex;
      align-items: center;     /* 垂直居中 */
    }
  }
</style>
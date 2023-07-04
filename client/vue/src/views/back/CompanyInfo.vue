<template>
  <div>
    <!-- 页眉 -->
    <div class="PageHeader">
        <el-page-header content="详情页面"></el-page-header> <!-- @back="goBack" -->
    </div>

    <!-- 详细信息 -->
    <div class="companyInfo">
      <el-descriptions title="公司信息" :column="1" border>      
        <template slot="extra">
          <el-button type="primary" plain @click="editInfo(companyInfo)">修改</el-button>
        </template>
        
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            企业LOGO
          </template>
          <img :src="companyInfo.companyImg" class="avatar"/>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            公司名称
          </template>
          {{ companyInfo.companyName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            公司地址
          </template>
          {{ companyInfo.companyAddress }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            创建时间
          </template>
          {{ companyInfo.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            公司描述
          </template>
          <template v-if="this.companyInfo.companyDescription">
            <el-tag class="tag" v-for="item,index in this.companyInfo.companyDescription.split(',')" :key="index">
              {{ item }}
            </el-tag>
          </template>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            公司介绍
          </template>
          {{ companyInfo.companyIntroduction }}
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <el-dialog title="公司信息" :visible.sync="dialogFormVisible" width="60%" >
      <el-upload
          class="avatar-uploader"
          :action="'http://localhost:9090/file/upload'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="companyInfo.companyImg" :src="companyInfo.companyImg" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form label-width="80px" size="small">
        <el-form-item label="公司名称">
          <el-input v-model="companyInfo.companyName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="公司地址">
          <el-input v-model="companyInfo.companyAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <!-- <el-input v-model="companyInfo.createTime" autocomplete="off"></el-input> -->
          <el-date-picker v-model="companyInfo.createTime" value-format="yyyy-MM-dd" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="公司描述">
          <!-- <el-input v-model="companyInfo.companyDescription" autocomplete="off"></el-input> -->
          <el-tag :key="tag" class="tag" v-for="tag in companyDescriptionTags" closable :disable-transitions="false" @close="companyDescriptionClose(tag)">
            {{tag}}
          </el-tag>
          <el-input class="input-new-tag" v-if="companyDescriptionVisible" v-model="companyDescriptionValue" ref="companyDescriptionTagInput" size="small" @keyup.enter.native="companyDescriptionConfirm" @blur="companyDescriptionConfirm">
          </el-input>
          <el-button v-else type="info" class="button-new-tag" size="small" @click="companyDescriptionInput">+</el-button>
        </el-form-item>
        <el-form-item label="公司介绍">
          <el-input v-model="companyInfo.companyIntroduction" autocomplete="off"></el-input>
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
      companyInfo: [],
      form: {},
      dialogFormVisible: false,
      companyDescriptionTags: [],
      companyDescriptionVisible: false,
      companyDescriptionValue: '',
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo(){
      this.request.get("/company/companyInfo/" + this.userInfo.id).then(res => {
        if (res.code === '200') {
          this.companyInfo = res.data[0]
        }
        console.log(this.companyInfo)
      })
    },
    save() {
      this.form.companyDescription  = this.companyDescriptionTags.join(",")
      this.request.post("/company", this.form).then(res => {
        if (res.data) {
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
      if(this.companyInfo.companyDescription != undefined) {
        this.companyDescriptionTags = this.companyInfo.companyDescription.split(',') 
      }
      this.dialogFormVisible = true
      console.log()     
    },
    handleAvatarSuccess(res) {
      this.form.companyImg = res
    },
    companyDescriptionClose(tag) {
      this.companyDescriptionTags.splice(this.companyDescriptionTags.indexOf(tag), 1);
    },
    companyDescriptionInput() {
      this.companyDescriptionVisible = true;
      this.$nextTick(_ => {
        this.$refs.companyDescriptionTagInput.$refs.input.focus();
      });
    },
    companyDescriptionConfirm() {
      let companyDescriptionValue = this.companyDescriptionValue;
      if (companyDescriptionValue) {
        this.companyDescriptionTags.push(companyDescriptionValue);
      }
      this.companyDescriptionVisible = false;
      this.companyDescriptionValue = '';
    },
  }
}
</script>

<style>
  .PageHeader {
    height: 40px;
    border-bottom: 1px solid #374d5c;
  }
  .companyInfo {
    width: 60%;
    margin: 20px auto 0;
  }
  .companyInfo .el-descriptions__title {
    color: gray;
    font-size: 20px;
  }
  .companyInfo table {
    font-size: 14px;
  }
  .companyInfo th{
    width: 30%;
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
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    width: auto;
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
<template>
  <div class="oneResumeBox">
    <el-card>
      <h3>
        {{ title }}
        <i class="el-icon-edit" style="float: right; padding-right: 5px;" v-if="this.educationEdit === false" @click="educationEdit = true"></i>
        <i class="el-icon-check" style="float: right; padding-right: 5px;" v-else @click="submit"></i>
      </h3>  
      <div style="margin-top: 15px;">
        <div v-if="content && this.educationEdit === false" style="white-space: pre-line">
          <pre>{{ content }}</pre>
        </div> 
        <div v-if="(content === '' || content === null) && this.educationEdit === false">
          暂无填写！
        </div> 
        <el-input v-if="this.educationEdit === true" v-model="resume" type="textarea" placeholder="有什么新鲜事想分享给大家" style="white-space: pre-line"></el-input>
      </div> 
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      resumeInfo: [],
      testForm: {},
      contentForm: {},
      resume: this.content,
      educationEdit: false,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  props: {
    id: {
      type: Number
    },
    title: {
      type: String
    },
    content: {
      type: String
    },
    kind: {
      type: String
    },
    percent: {
      type: Function
    }
  },
  watch:{
    content() {
        this.resume = this.content //监听父组件的数据，同步更新子组件数据
    }
  },
  methods: {
    submit(){
      this.testForm.id = this.id
      this.testForm.userId = this.userInfo.id
      this.testForm.kind = this.resume
      this.contentForm= JSON.parse(JSON.stringify(this.testForm).replace(/kind/g,`${this.kind}`))
      console.log(this.contentForm)
      this.request.post("/resume", this.contentForm).then(res => {
        if (res.code === '200') {
          this.$message.success("填写成功！")
          this.percent()
        } else {
          this.$message.error("填写失败！")
        }
      })
      this.$emit('update', this.resume,this.kind)
      this.educationEdit = false
    }
  }
}
</script>

<style lang="less">
  .oneResumeBox {
    width: 48.5%;
    .el-textarea__inner {
      height: 150px!important;
      font-size: 15px;
    }
    .el-card {
      margin-top: 30px;
    }  
    pre {
      line-height: 1.5;
      font-size: 15px;
      white-space: pre-wrap;
      white-space: -moz-pre-wrap;
      white-space: -pre-wrap;
      white-space: -o-pre-wrap;
      word-wrap: break-word;
      word-break: break-all;
    }
  }
</style>
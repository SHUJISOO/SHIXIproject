<template>
  <div class="communityBox">
    <!-- 标签栏 -->
    <el-tabs type="border-card" v-model="activeName">
      <el-tab-pane label="说说" name="说说">
        <el-form label-width="80px" label-position="top" :model="form">
          <el-form-item>
            <el-input v-model="form.content" type="textarea" placeholder="有什么新鲜事想分享给大家"></el-input>
          </el-form-item>
          <el-form-item>
            <div style="text-align: right;">
              <el-button type="warning" plain @click="save">发送</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="文章" name="文章">
        <el-form label-width="80px" label-position="top" :model="form">
          <el-form-item>
            <el-input v-model="form.title" autocomplete="off" placeholder="请输入标题！"></el-input>
          </el-form-item>
          <el-form-item>
            <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
          </el-form-item>
          <el-form-item style="width: 100%;">
            <div style="width: 60%;display:inline-block">
              <div style="display:inline-block;">请设置封面</div>
              <div style="display:inline-block;margin-left: 40px;">
                <el-upload
                  class="avatar-uploader"
                  action="http://localhost:9090/file/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess">
                  <img v-if="form.imgUrl" :src="form.imgUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
            </div>
            <div style="width: 40%;display:inline-block;text-align: right;">
              <el-button type="warning" plain @click="save">发送</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <div v-for="item,index in communityInfo" :key="index">
      <div class="cardBox" v-if="item.type === '说说'">
        <!-- 头部 -->
        <div class="layout">
          <div style="width: 50%; height: 55px; display: flex;">
            <img :src="item.avatarUrl" style="width:55px; height: 55px; border-radius: 50%;">
            <!-- <p>555</p> -->
            <p style="height: 55px;line-height: 55px; font-weight: bold;padding-left: 15px;">
              {{ item.user }}
            </p>
          </div>
          <div style="width: 50%;text-align: right;line-height: 55px;">
            {{ item.createTime }}
          </div>
        </div>
        
        <div style="padding-left: 70px;margin-top: 5px;">
          <!-- 内容 -->
          <div style="padding-right: 90px;">{{ item.content }}</div>
          <div style="padding-right: 90px;text-align: right; font-size: 13px;" v-if="userInfo.id === item.userId"> 
            <a @click="editInfo('community',item)">编辑</a>&emsp;&emsp;
            <a @click="del('community',item.id)">删除</a>
          </div>

          <!-- 评论框 -->
          <div class="commentBox" style="display: flex; flex-direction: row; margin-top: 20px;">
            <!-- <img :src="userInfo.avatarUrl" style="width:45px; height: 45px; border-radius: 50%;"> -->
            <el-input type="textarea" label="item.id" v-model="commentForm.comment" placeholder="评论些什么呢？" style="margin-left: 20px;padding-right: 25px;"></el-input>
            <el-button type="warning" plain @click="send(item)">发送</el-button>       
          </div>

          <div v-for="commentItem,index in commentInfo" :key="index">
            <!-- 评论列表 -->
            <div v-if="(commentItem.communityId === item.id && commentItem.commentId === null  && commentItem.replyId === null)" style="display: flex; flex-direction: row; margin-top: 20px;padding-right: 90px;">
              <img :src="commentItem.avatarUrl" style="width:45px; height: 45px; border-radius: 50%;">
              <div class="comment">
                <p>
                  <span style="font-size: 14px; font-weight: bold;">{{ commentItem.username }}</span>                  
                </p>
                <p style="font-size: 14px; color: #70757d;margin-top: 5px;">{{ commentItem.comment }}</p>
                <p class="singleLine" style="margin-top: 10px; font-size: 14px;font-size: 13px;">
                  <span>评论于：{{ commentItem.commentTime }}</span>
                  <span style="float: right;">
                    <span v-if="userInfo.id === commentItem.userId">
                      <a @click="editInfo('comment',commentItem)">编辑</a>&emsp;
                      <a @click="del('comment',commentItem.id)">删除</a>&emsp;
                    </span>
                    <a @click="reply(commentItem)">回复</a>
                  </span>
                </p>
                

                <!-- 回复列表 -->
                <div v-for="replyItem in commentItem.children" style="margin-top: 10px; padding-top: 10px;border-top: 1px dashed gray;">
                  <div>
                    <p>
                      <span style="font-size: 14px; font-weight: bold;">
                        {{ replyItem.username }}&ensp;
                        <!-- 回复&ensp; -->
                        <!-- <br> -->
                        <span style="color: #f4612d;">回复&ensp;@{{ replyItem.users[0].nickname }}：</span>
                        &ensp;
                      </span>
                      <!-- <br> -->
                      <span style="font-size: 14px; color: #70757d;">
                        {{ replyItem.comment }}
                      </span>
                    </p>
                    <p class="singleLine" style="margin-top: 10px; font-size: 14px;font-size: 13px;">
                      <span>回复于：{{ replyItem.commentTime }}</span>
                      <span style="float: right;">
                        <span v-if="userInfo.id === replyItem.userId">
                          <a @click="editInfo('comment',replyItem)">编辑</a>&emsp;
                          <a @click="del('comment',replyItem.id)">删除</a>&emsp;
                        </span>
                        <a @click="reply(replyItem)">回复</a>
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="cardBox" style="padding: 30px 20px;" v-if="item.type === '文章'">
        <div class="layout" style="cursor:pointer;padding-bottom: 20px;border-bottom: 1px dashed gray;" @click="$router.push('/index/communityDetail?id=' + item.id)">
          <div style="width: 35%; display: flex; justify-content: center;/*水平主轴居中*/align-items: center;/*垂直交叉轴居中*/">
            <img :src="item.imgUrl" style="width: 240px; height: 160px;">
          </div>
          <div style="width: 65%; padding: 0 50px 0 10px; display: flex; align-items: center;">
            <div style="width: 100%; padding-right: 50px;">
              <p class="singleLine" style="font-size: 24px; font-weight: bold;">{{ item.title }}</p>
              <p class="Lines" style="margin-top: 20px;">{{ item.content }}</p>
              <p class="singleLine" style="font-size: 13px; color: #70757d;margin-top: 20px;">
                <span>发布者：{{ item.user }}</span>
                <span style="float: right;">发布于：{{ item.createTime }}</span>
                <span style="float: right;padding-right: 20px;text-align: right; font-size: 13px;" v-if="userInfo.id === item.userId"> 
                  <a @click="edit(item.id)">编辑</a>&emsp;&emsp;
                  <a @click="del('community',item.id)">删除</a>
                </span>   
              </p>
            </div>
          </div>
        </div>

        <div style="padding-left: 70px;margin-top: 30px;">
          <!-- 评论框 -->
          <div class="commentBox" style="display: flex; flex-direction: row; margin-top: 20px;">
            <!-- <img :src="userInfo.avatarUrl" style="width:45px; height: 45px; border-radius: 50%;"> -->
            <el-input type="textarea" label="item.id" v-model="commentForm.comment" placeholder="评论些什么呢？" style="margin-left: 20px;padding-right: 25px;"></el-input>
            <el-button type="warning" plain @click="send(item)">发送</el-button>       
          </div>

          <div v-for="commentItem,index in commentInfo" :key="index">
            <!-- 评论列表 -->
            <div v-if="(commentItem.communityId === item.id && commentItem.commentId === null  && commentItem.replyId === null)" style="display: flex; flex-direction: row; margin-top: 20px;padding-right: 90px;">
              <img :src="commentItem.avatarUrl" style="width:45px; height: 45px; border-radius: 50%;">
              <div class="comment">
                <p>
                  <span style="font-size: 14px; font-weight: bold;">{{ commentItem.username }}</span>                  
                </p>
                <p style="font-size: 14px; color: #70757d;margin-top: 5px;">{{ commentItem.comment }}</p>
                <p class="singleLine" style="margin-top: 10px; font-size: 14px;font-size: 13px;">
                  <span>评论于：{{ commentItem.commentTime }}</span>
                  <span style="float: right;">
                    <span v-if="userInfo.id === commentItem.userId">
                      <a @click="editInfo('comment',commentItem)">编辑</a>&emsp;
                      <a @click="del('comment',commentItem.id)">删除</a>&emsp;
                    </span>
                    <a @click="reply(commentItem)">回复</a>
                  </span>
                </p>
                

                <!-- 回复列表 -->
                <div v-for="replyItem in commentItem.children" style="margin-top: 10px; padding-top: 10px;border-top: 1px dashed gray;">
                  <div>
                    <p>
                      <span style="font-size: 14px; font-weight: bold;">
                        {{ replyItem.username }}&ensp;
                        <!-- 回复&ensp; -->
                        <!-- <br> -->
                        <span style="color: #f4612d;">回复&ensp;@{{ replyItem.users[0].nickname }}：</span>
                        &ensp;
                      </span>
                      <!-- <br> -->
                      <span style="font-size: 14px; color: #70757d;">
                        {{ replyItem.comment }}
                      </span>
                    </p>
                    <p class="singleLine" style="margin-top: 10px; font-size: 14px;font-size: 13px;">
                      <span>回复于：{{ replyItem.commentTime }}</span>
                      <span style="float: right;">
                        <span v-if="userInfo.id === replyItem.userId">
                          <a @click="editInfo('comment',replyItem)">编辑</a>&emsp;
                          <a @click="del('comment',replyItem.id)">删除</a>&emsp;
                        </span>
                        <a @click="reply(replyItem)">回复</a>
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>     
      </div>

      <el-dialog title="回复" :visible.sync="dialogFormVisible" width="50%" >
        <!-- <p>{{ this.replyComment }}</p> -->
        <el-form label-width="80px" size="small">
          <el-form-item>
            <p>{{ replyComment }}</p>
          </el-form-item>
          <el-form-item label="回复内容">
            <el-input type="textarea" v-model="form1.comment" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel" size="small">取 消</el-button>
          <el-button type="primary" @click="confirm(commentForm)"  size="small">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="编辑" :visible.sync="editDialogFormVisible" width="50%" >
        <el-form label-width="80px" size="small">
          <el-form-item label="回复内容">
            <el-input type="textarea" v-model="text" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editDialogFormVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="editConfirm"  size="small">确 定</el-button>
        </div>
      </el-dialog>     
    </div>
     
    <!-- 页尾 -->
    <div class="pageBar">
      <!-- :page-sizes="[10, 20, 30, 40]" -->          
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-size="pageSize"
        background
        layout="total, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <Reset></Reset>
  </div>
</template>

<script>
import axios from "axios"
import Reset from '@/components/front/reset.vue'

export default {
  data() {
    return {
      form: {},
      form1: {},
      total: 0,
      pageNum: 1,
      pageSize: 10,
      text: "",
      comment: "",
      content: "",
      replyComment: "",
      test: [],
      contentForm: {},
      commentForm: {},
      activeName: '说说',
      communityInfo: [],
      commentList: [],
      commentInfo: [],
      dialogFormVisible: false,
      editDialogFormVisible: false,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  components: {
    Reset
  },
  created() {
    this.getCommunityInfo()
  },
  methods: {
    getCommunityInfo() {
      this.request.get("/community/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.communityInfo = res.data.records
        this.total = res.data.total
      })
      this.request.get("/comment/allInfo").then(res => {
        this.commentList = res.data
        // console.log(this.commentList)

        function commentapi(api) {
          const b = api
          const c = b.filter(item => {
            return item.commentId == null
          })
          const d = c.map(item => {
            // console.log(item.id)
            let cchild = b.filter(item2 => {
                return item2.commentId == item.id
            })
            item.children = cchild
            return item
          })
          return d
        }
        this.commentInfo = commentapi(this.commentList)
        // console.log(this.commentInfo)

      })
      
    },
    edit(id){
      this.$router.push('/index/communityDetail?id=' + id)
      // document.getElementById("editActicle").click()
      this.$refs.editActicle.click()
    },
    editInfo(type,data){
      this.editDialogFormVisible = true    
      if(type === 'community'){
        this.contentForm = data
        this.text = this.contentForm.content
      }else{
        this.form1 = data
        this.text = this.form1.comment
      }

      // console.log(this.form1)
      // this.form.createTime = ''
    },
    editConfirm(){
      this.form1.comment = this.text
      this.contentForm.content = this.text
      // console.log(this.contentForm)
      // console.log(this.form1)
      if(this.form1.id){
        this.request.post("/comment", this.form1).then(res => {
          if (res.code === '200') {
            this.$message.success("编辑成功！")
            this.editDialogFormVisible = false
            this.getCommunityInfo()
          } else {
            this.$message.error("编辑失败！")
          }
        })
      }else{
        this.request.post("/community", this.contentForm).then(res => {
          if (res.code === '200') {
            this.$message.success("编辑成功！")
            this.editDialogFormVisible = false
            this.getCommunityInfo()
          } else {
            this.$message.error("编辑失败！")
          }
        })
      }
      this.form1 = {}
      this.text = ""
      this.contentForm = {}
    },
    reply(data) {
      this.dialogFormVisible = true
      this.commentForm = data
      this.replyComment = this.commentForm.comment
      // console.log(this.replyComment)
    },
    confirm(data) {
      console.log(data)
      this.form1.replyId = data.userId
      this.form1.communityId = data.communityId
      this.form1.commentId = data.commentId
      this.form1.username = this.userInfo.nickname
      this.form1.userId = this.userInfo.id
      console.log(this.form1)
      this.request.post("/comment", this.form1).then(res => {
        if (res.code === '200') {
          this.$message.success("发送成功！")
          this.form1 = {}
          this.dialogFormVisible = false
          this.getCommunityInfo()
        } else {
          this.$message.error("发送失败！")
        }
      })
    },
    cancel() {
      this.dialogFormVisible = false
      this.commentForm = {}
      this.replyComment = ""
      this.form = {}
      this.form1 = {}
    },
    send(data) {
      if(this.userInfo.id) {
        this.commentForm.username = this.userInfo.nickname
        this.commentForm.userId = this.userInfo.id
        this.commentForm.avatarUrl = this.userInfo.avatarUrl
        this.commentForm.communityId = data.id
        // console.log(this.commentInfo.id)
        // console.log(data)
        this.request.post("/comment", this.commentForm).then(res => {
          if (res.code === '200') {
            this.$message.success("发送成功！")
            this.commentForm = {}
            this.getCommunityInfo()
          } else {
            this.$message.error("发送失败！")
          }
        })
      }else{
        this.$message.error("请先登录!")
      }
    },
    del(type,data){
      console.log(type)
      console.log(data)
    },
    save() {
      if(this.userInfo.id) {
        this.form.type = this.activeName
        this.form.user = this.userInfo.nickname
        this.form.avatarUrl = this.userInfo.avatarUrl

        this.request.post("/community", this.form).then(res => {
          if (res.code === '200') {
            this.$message.success("发送成功！")
            this.getCommunityInfo()
          } else {
            this.$message.error("发送失败！")
          }
        })
        this.form = {}            
      }else{
        this.$message.error("请先登录!")
      }
    },
    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      let $vm = this.$refs.md
      // 第一步.将图片上传到服务器.
      const formData = new FormData();
      formData.append('file', $file);
      axios({
        url: 'http://localhost:9090/file/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        $vm.$img2Url(pos, res.data);
      })
    },
    handleAvatarSuccess(res) {
      this.form.imgUrl = res
      console.log(this.form)
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.getCommunityInfo()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.getCommunityInfo()
    }
  }
}
</script>

<style lang="less">
  .communityBox {
    a:hover {
      cursor:pointer;
      color: #f4612d;
    }
    .el-button {
      height: 45px;
      width: 63.24px;
    }
    .el-tabs {
      margin-top: 30px;
      border-radius: 10px;
      margin-bottom: 20px; 
      .el-textarea__inner {
        min-height: 80px!important;
        font-size: 16px;
      }
      .el-input {
        font-size: 16px;
      }
      .el-input--mini .el-input__inner {
        height: 35px;
      }
    }
    .el-tabs__header {
      border-radius: 10px 10px 0 0;    
      .el-tabs__nav-scroll {
        font-size: 20px!important;
      }
      .el-tabs__item:hover {
        color: #f4612d!important;
      }
      .el-tabs__item.is-active {
        color: #f4612d;
        font-size: 18px;
        font-weight: bold;
        // border-radius: 10px 10px 0 0;  
      }
    }
    .commentBox {
      // .el-button--mini, .el-button--mini.is-round {
      //     padding: 7px 0;
      // }
      .el-button {
        height: 45px;
        width: 70px!important;
      }
      .el-textarea__inner {
        font-size: 16px;
        height: 45px!important;
      }
    }
    .comment {
      background-color: #edf2f6;
      min-width: 60%;
      padding: 10px 20px;
      border-radius: 10px;
      margin-left: 20px;
      padding-right: 20px;
    }
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #f4612d;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 200px;
      height: 150px;
      line-height: 150px;
      text-align: center;
    }
    .avatar {
      width: 200px;
      height: 150px;
      display: block;
    }
    .pageBar {
      text-align: center;
      margin-top: 10px;
    }
  }
</style>
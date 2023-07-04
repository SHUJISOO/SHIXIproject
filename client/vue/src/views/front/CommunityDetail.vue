<template>
  <div style="padding-top: 20px;background-color: #fff;">
    <!-- 标题、发布者、创建时间 -->
    <div style="padding: 0 25px;">
      <p style="font-weight: bold; font-size: 30px;;">{{ communityDetail.title }}</p>
      <p style="margin: 10px 0 30px 0; color: gray;" class="singleLine">
        <i class="el-icon-user">{{ communityDetail.user }}</i>&emsp;&emsp;
        <i class="el-icon-time">{{ communityDetail.createTime }}</i>
        <span style="float: right;" v-if="(userInfo.id === communityDetail.userId)">
          <a ref="editActicle" @click="editCommunity(communityDetail)">编辑</a>&emsp;
          <a @click="del(this.id)">删除</a>&emsp;
        </span>
      </p>   
    </div>

    <!-- 内容 -->
    <div>
      <div v-if="(this.articleDialogFormVisible === false)">
        <mavon-editor
          class="md"
          style="z-index: 0;"
          :value="communityDetail.content"
          :subfield="false"
          :defaultOpen="'preview'"
          :toolbarsFlag="false"
          :editable="false"
          :scrollStyle="true"
          :ishljs="true"/>
      </div>
      <div style="margin-bottom: 100px;" v-else>
        <p style="text-align: center;margin-bottom: 20px; color: red; font-size: 20px;font-weight: bold;">----- 编辑文章中 -----</p>
        <div style="padding-left: 25px;">
          设置封面
          <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="communityForm.imgUrl" :src="communityForm.imgUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <mavon-editor style="z-index: 0;" ref="md" v-model="text" :ishljs="true" @imgAdd="imgAdd"/>
        <div style="float: right;margin: 20px 25px 0 0;">
          <el-button @click="articleDialogFormVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="articleConfirm"  size="small">确 定</el-button>
        </div>
      </div>
    </div>

    <div style="margin-top: 50px;padding: 0 25px 30px;">
      <!-- 评论框 -->
      <div style="display: flex; flex-direction: row; margin-top: 20px;">
        <img :src="userInfo.avatarUrl" style="width:48px; height: 48px; border-radius: 50%;">
        <el-input type="textarea" label="item.id"  placeholder="评论些什么呢？" style="margin-left: 20px;padding-right: 25px;"></el-input>
        <el-button type="warning" plain style="height: 48px;font-size: 17px!important;">发送</el-button>       
      </div>

      <div v-for="commentItem,index in commentInfo" :key="index">
        <!-- 评论列表 -->
        <div style="display: flex; flex-direction: row; margin-top: 20px;padding-right: 90px;">
          <img :src="commentItem.avatarUrl" style="width:45px; height: 45px; border-radius: 50%;">
          <div class="comment">
            <p>
              <span style="font-size: 14px; font-weight: bold;">{{ commentItem.username }}</span>                 
            </p>
            <p style="font-size: 14px; color: #70757d;margin-top: 5px;">{{ commentItem.comment }}</p>
            <p style="margin-top: 10px; font-size: 14px;font-size: 13px;">
              <span>评论于：{{ commentItem.commentTime }}</span>
              <span style="float: right;">
                <span v-if="userInfo.id === commentItem.userId">
                  <a @click="editInfo(commentItem)">编辑</a>&emsp;
                  <a @click="del(commentItem.id)">删除</a>&emsp;
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
                <p style="margin-top: 10px; font-size: 14px;font-size: 13px;">
                  <span>回复于：{{ replyItem.commentTime }}</span>
                  <span style="float: right;">
                    <span v-if="userInfo.id === replyItem.userId">
                      <a @click="editInfo(replyItem)">编辑</a>&emsp;
                      <a @click="del(replyItem.id)">删除</a>&emsp;
                    </span>
                    <a @click="reply(replyItem)">回复</a>
                  </span>
                </p>
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
            <el-input type="textarea" v-model="commentForm.comment" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel" size="small">取 消</el-button>
          <el-button type="primary" @click="confirm(comment)"  size="small">确 定</el-button>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: this.$route.query.id,
      test:[], 
      text: "",
      replyComment: "",
      form: {},
      comment: {},
      communityForm: {},
      commentForm: {},
      commentList: [],
      commentInfo: [],
      communityDetail: [],
      dialogFormVisible: false,
      editDialogFormVisible: false,
      articleDialogFormVisible: false,
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getCommunityDetailInfo()
  },
  methods: {
    getCommunityDetailInfo() {
      this.request.get("/community/" + this.id).then(res => {
        this.communityDetail = res.data
      })
      this.request.get("/comment/allInfo").then(res => {
        this.commentList = res.data
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
          // console.log(d)
          // const e = d.filter(item => item.communityId === num)
          return d         
        }
        this.test = commentapi(this.commentList)
        this.commentInfo = this.test.filter(item => item.communityId === parseInt(this.id))
        console.log(this.commentInfo)
      })
    },
    editInfo(data){
      this.editDialogFormVisible = true    
      this.form = data
      this.text = this.form.comment
    },
    editCommunity(data){
      this.articleDialogFormVisible = true
      this.communityForm = data
      this.text = this.communityForm.content
    },
    editConfirm(){
      this.form.comment = this.text
      this.request.post("/comment", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("编辑成功！")
          this.editDialogFormVisible = false
          this.getCommunityInfo()
        } else {
          this.$message.error("编辑失败！")
        }
      })
      this.form = {}
      this.text = ""
    },
    articleConfirm(){
      this.communityForm.content = this.text
      console.log(this.communityForm)
      this.request.post("/community", this.communityForm).then(res => {
        if (res.code === '200') {
          this.$message.success("编辑成功！")
          this.articleDialogFormVisible = false
          this.getCommunityDetailInfo()
        } else {
          this.$message.error("编辑失败！")
        }
      })
      this.communityForm = ""
    },
    del(data){
      console.log(data)
    },
    reply(data) {
      this.dialogFormVisible = true
      this.comment = data
      this.replyComment = data.comment
      // console.log(this.replyComment)
    },
    confirm(data) {
      // console.log(data)
      this.commentForm.replyId = data.userId
      this.commentForm.communityId = this.id
      this.commentForm.commentId = data.id
      this.commentForm.username = this.userInfo.nickname
      this.commentForm.userId = this.userInfo.id
      // console.log(this.commentForm)
      this.request.post("/comment", this.commentForm).then(res => {
        if (res.code === '200') {
          this.$message.success("发送成功！")
          this.commentForm = {}
          this.dialogFormVisible = false
          this.getCommunityDetailInfo()
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
      this.communityForm.imgUrl = res
    }
  }
}
</script>

<style>
  .el-textarea__inner {
    font-size: 16px;
    height: 48px!important;
  }
  .comment {
    background-color: #edf2f6;
    min-width: 50%;
    padding: 10px 20px;
    border-radius: 10px;
    margin-left: 20px;
    padding-right: 20px;
  }
  a:hover {
    cursor:pointer;
    color: #f4612d;
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
</style>
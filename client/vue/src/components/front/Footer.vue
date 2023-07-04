<template>
  <el-footer class="footerBox">
    <div>
      <div class="footerContent layout">
        <div class="footerItem">
          <p>在校大学生实习招聘平台</p>
          <div class="mt20">
            &emsp; &emsp;集中更多的实习机会信息，供在校大学生选择，以此来磨练意志、汲取经验、提升能力，为将来求职打下夯实的基础，更快找到适合的工作，也能缓解高校毕业生的就业问题。
          </div>
        </div>
        <div class="footerItem">
          <p>菜单栏</p>
          <div class="mt20">
            <ul>
              <li>
                <a href="/index">首&emsp;&emsp;页</a>
              </li>
              <li>
                <a href="/index/job">职&emsp;&emsp;位</a>
              </li>
              <li>
                <a href="/index/community">社区论坛</a>
              </li>
              <li>
                <a href="/index/contact">联系我们</a>
              </li>
            </ul>
          </div>
        </div>
        <div class="footerItem">
          <p>持续关注</p>
          <div class="mt20">
            <!-- 微信 -->
            <el-popover placement="top-start" trigger="hover">
              <div class="layout item" style="height: 50px;" slot="reference" >
                <div style="width: 50%;text-align: right;padding: 5px 25px 0 0;">
                  <img src="@/assets/wechat.jpeg">
                </div>
                <div style="width: 50%; display: flex; align-items: center;">
                  <span style="line-height: 50px;">微信</span>
                </div>
              </div>
              <div>
                <img src="@/assets/wechat.jpeg" width="50px"/>
              </div>
            </el-popover>
            <!-- 微博 -->
            <el-popover placement="top-start" trigger="hover">
              <div class="layout item" style="height: 50px;" slot="reference" >
                <div style="width: 50%;text-align: right;padding: 5px 25px 0 0;">
                  <img src="@/assets/weibo.jpeg">
                </div>
                <div style="width: 50%; display: flex; align-items: center;">
                  <span style="line-height: 50px;">微博</span>
                </div>
              </div>
              <div>
                <img src="@/assets/weibo.jpeg" width="50px"/>
              </div>
            </el-popover>
            <!-- 小红书 -->
            <el-popover placement="top-start" trigger="hover">
              <div class="layout item" style="height: 50px;" slot="reference" >
                <div style="width: 50%;text-align: right;padding: 5px 25px 0 0;">
                  <img src="@/assets/red.png">
                </div>
                <div style="width: 50%; display: flex; align-items: center;">
                  <span style="line-height: 50px;">小红书</span>
                </div>
              </div>
              <div>
                <img src="@/assets/red.png" width="50px"/>
              </div>
            </el-popover>
          </div>
        </div>
      </div>
      <p style="bottom: 15px;position: absolute;text-align: center;width: 75%;">
        <!-- Copyright © 2022 BOSS直聘 -->
        Copyright © Designed & Developed by SHUJISOO 2022
      </p>
    </div>
  </el-footer>
</template>

<script>
export default {

}
</script>

<style lang="less">
  .footerBox {
    bottom: 0;
    height: 280px!important;
    background-color: #fff;
    margin-top: 40px;
    position: relative;
    padding: 0 12.5%;
    .footerContent {
      p {
        text-align: center;
        color: #f4612d;
        font-size: 22px;
        font-weight: bold;        
      }
      div {
        Word-wrap:break-word;
      }
    }
    .footerItem {
      padding: 30px 40px 20px;
      width: 33.3%; 
      height: 150px;
      .mt20 {
        margin-top: 15px;
      }
      img {
        width: 37px;
        border: 1px #f4612d solid;
        border-radius: 50%;
      }
      .item:hover {
        background-color: #fff7f3;
      }
    }
    li {
      text-align: center;
      height: 35px;
      line-height: 35px;
      list-style: none;
      &:hover {
        background-color: #fff7f3;
      }
      a {
        color: #000;
      }
    }
  }
</style>
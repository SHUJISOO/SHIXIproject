<el-row class="HomeRow">
  <!-- 左侧 -->
  <el-col :span="12">
    <el-row>
      <div class="box" style="background-color: #ffa755;">
        <div class="textBox">
          <i class="el-icon-user textIcon"></i>
          <div style="float: left;line-height: 45px;margin-left: 20px;">
            <p style="font-size: 45px;">
              {{ total }}<span style="font-size: 28px;font-weight: bold;">&ensp;人</span>
            </p>
            <p style="font-size: 20px;margin-left: 5px; ">总用户</p>
          </div>
          <i class="el-icon-s-promotion iconContext"></i>
        </div>
      </div>
      <div class="box" style="background-color: #b48dd3;">
        <div class="textBox">
          <i class="el-icon-s-custom textIcon"></i>
          <div style="float: left;line-height: 45px;margin-left: 20px;">
            <p style="font-size: 45px;">
              {{ companyNum }}<span style="font-size: 28px;font-weight: bold;">&ensp;人</span>
            </p>
            <p style="font-size: 20px;margin-left: 5px; ">企业用户</p>
          </div>
          <i class="el-icon-s-cooperation iconContext"></i>
        </div>
      </div>
    </el-row>
  </el-col>

  <!-- 右侧 -->
  <el-col :span="12">
    <el-row>
      <div class="box" style="background-color: #68e365;">
        <div class="textBox">
          <i class="el-icon-s-management textIcon"></i>
          <div style="float: left;line-height: 45px;margin-left: 20px;">
            <p style="font-size: 45px;">
              58<span style="font-size: 28px;font-weight: bold;">&ensp;条</span>
            </p>
            <p style="font-size: 20px;margin-left: 5px; ">招聘信息</p>
          </div>
          <i class="el-icon-s-opportunity iconContext"></i>
        </div>
      </div>
      <div class="box" style="background-color: #5dcfc6;">
        <div class="textBox">
          <i class="el-icon-user-solid textIcon"></i>
          <div style="float: left;line-height: 45px;margin-left: 20px;">
            <p style="font-size: 45px;">
              {{ applicantNum }}<span style="font-size: 28px;font-weight: bold;">&ensp;人</span>
            </p>
            <p style="font-size: 20px;margin-left: 5px; ">实习生</p>
          </div>
          <i class="el-icon-s-check iconContext"></i>
        </div>
      </div>
    </el-row>
  </el-col>
</el-row>
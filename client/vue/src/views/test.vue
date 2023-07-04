<div class="wrapper">
  <div class="BoxLogin">
    <div style="margin: 20px 0; text-align: center;">
      <b style="font-size: 24px">登 录</b><br>
      <span>请登录您的账号🌹</span>
    </div>
    <el-form :model="user" ref="userForm" label-position="top">
      <el-form-item prop="role" label="请选择你的身份">
        <el-radio-group v-model="user.role">
          <el-radio-button label="管理员"></el-radio-button>
          <el-radio-button label="企业"></el-radio-button>
          <el-radio-button label="实习生"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="username">
        <!-- <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input> -->
        <el-input style="margin: 5px 0" size="medium" placeholder="请输入账号" prefix-icon="el-icon-user" v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <!-- <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input> -->
        <el-input style="margin: 5px 0" size="medium" placeholder="请输入密码" prefix-icon="el-icon-lock" v-model="user.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
      </el-form-item>
      <el-form-item class="last">
        <el-button type="primary" size="small" autocomplete="off" @click="login" style="background-color: #f4612d!important;">登录</el-button>
        没有账号？<a @click="$router.push('/register')">立即注册>></a>
      </el-form-item>
    </el-form>
  </div>
</div>


<div class="wrapper">
  <div class="BoxRegister">
    <div style="margin: 20px 0; text-align: center;">
      <b style="font-size: 24px">注 册</b><br>
      <span>请注册您的账号🌹</span>
    </div>
    <el-form :model="user" :rules="rules" ref="userForm">
      <el-form-item prop="role">
        <span>请选择您的注册身份：</span>&emsp;
        <el-radio v-model="user.role" label="实习生">实习生</el-radio>
        <el-radio v-model="user.role" label="企业">企业</el-radio>
      </el-form-item>
      <el-form-item prop="username">
        <el-input placeholder="请输入用户名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item prop="nickname">
        <el-input placeholder="请输入昵称" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.nickname"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
      </el-form-item>
      <el-form-item class="last">
        <el-button type="warning" @click="register">注册</el-button>
        已有账号，<a @click="$router.push('/login')">立即登录>></a>
      </el-form-item>
    </el-form>
  </div>
</div>
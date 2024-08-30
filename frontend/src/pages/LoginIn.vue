<template>
  <div>
    <!-- 整体背景 <loginLogo/>-->
    <div class="login-wrap">
      <div class="form-wrapper">
        <div class="header">
          <span>LOGING IN</span>
        </div>
        <el-form :model="loginForm" ref="loginForm" label-width="70px" class="input-wrapper" :rules="rules">
          <el-form-item prop="username" class="border-wrapper">
            <input v-model="loginForm.username" placeholder="用户名" class="border-item" autocomplete="off"></input>
          </el-form-item>
          <el-form-item prop="password" class="border-wrapper">
            <input type="password" v-model="loginForm.password" placeholder="密码" class="border-item" autocomplete="off"></input>
          </el-form-item>
          <div class="login-btn">
            <button @click="goSignUp" class="btn-signup">注册</button>
            <button type="primary" @click="handleLoginIn" class="btn-login">登录</button>
          </div>
        </el-form>
        <!---->
      </div>
    </div>
  </div>
</template>
<script>
import loginLogo from '../components/LoginLogo'
import {mixin} from '../mixins'
import {loginIn} from '../api/index'
import CodeCard from '../components/CodeCard'

export default {
  name: 'login-in',
  mixins: [mixin],
  components: {
    loginLogo,
    CodeCard
  },
  data () {
    return {
      //默认使用帐号登录的方式
      activeName: 'account',
      isCreateQRCodeDom: false,
      loginForm: {
        username: '',       //用户名
        password: '',       //密码
      },
      rules: {
        username: [
          {required: true, trigger: 'blur', message: '请输入用户名'}
        ],
        password: [
          {required: true, trigger: 'blur', message: '请输入密码'}
        ]
      }
    }
  },
  mounted () {
    this.changeIndex('登录')
  },
  methods: {
    //刷新二维码
    refreshQRCode () {
      this.isCreateQRCodeDom = false
      setTimeout(() => {
        this.isCreateQRCodeDom = true
      }, 500)
    },
    //选择标签卡
    handleClick (tab, event) {
      this.activeName = tab.name
      if (tab.name === 'qrcode') {
        this.isCreateQRCodeDom = true
      } else {
        this.isCreateQRCodeDom = false
      }
    },
    handleLoginIn () {
      let _this = this
      let params = new URLSearchParams()
      params.append('username', this.loginForm.username)
      params.append('password', this.loginForm.password)
      console.log(params);
      // _this.notify('登录成功', 'success')
      // _this.$store.commit('setLoginIn', true)
      // _this.changeIndex('首页')
      // _this.$router.push({path: '/'})
      loginIn(params)
        .then(res => {
          console.log(res);
          if (res.code !== 200) {
            _this.notify(res.msg, 'error')
          } else {
            _this.notify('登录成功', 'success')
            _this.$store.commit('setLoginIn', true)
            _this.$store.commit('setUserId', res.data.id)
            _this.$store.commit('setUsername', res.data.username)
            _this.$store.commit('setAvator', res.data.avator)
            _this.$store.commit('setPhoneNum', res.data.phoneNum)
            _this.$store.commit('setEmail', res.data.email)
            _this.$store.commit('setBirth', res.data.birth)
            _this.$store.commit('setLocation', res.data.location)
            _this.$store.commit('setIntroduction', res.data.introduction)
            _this.$store.commit('setSex', res.data.sex)
            _this.$store.commit('setCreateTime', res.data.createTime)
            console.log("---------------",res.data)
            _this.$store.commit('userVipStatus', res.data.isVipUser)
            _this.changeIndex('首页')
            _this.$router.push({path: '/'})
            console.log('登录成功');
            console.log(this.$store.getters.userId);
            console.log(this.$store.getters.username);
            console.log(this.$store.getters.avator);
            console.log(this.$store.getters.phoneNum);
            console.log(this.$store.getters.email);
          }
        })
        .catch(err => {
          _this.notify('用户名或密码错误', 'error');
          console.log('登录fail');
        })
    },
    goSignUp () {
      this.changeIndex('注册')
      this.$router.push({path: '/sign-up'})
    },
    changeIndex (value) {
      this.$store.commit('setActiveName', value)
    }
  }
}
</script>
<!--
<style lang="scss" scoped>
@import '../assets/css/sign-up.scss';

.login-tab {
  width: 100%;
  height: 100%;

  .qrcode-box {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }

  .fresh-qrcode {
    color: #fff;
    margin: 20px 25px;
    padding: 15px;
    font-size: 16px
  }

  /*刷新二维码的中间状态*/
  .fresh-qrcode-status {
    width: 200px;
    height: 200px;
  }
}

</style>-->


<style scoped>
.login-wrap {
  height: 100vh;
  width: 100vw;
  font-family: JetBrains Mono Medium;
  display: flex;
  align-items: center;
  justify-content: center;
  /* background-color: #0e92b3; */
  background: url('../assets/img/img.png');
  background-size: 100% 100%;
}

.form-wrapper {
  width: 350px;
  background-color: rgba(41, 45, 62, 0.6);
  color: #fff;
  border-radius: 80px;
  padding: 50px;
}

.form-wrapper:hover {
  width: 350px;
  background-color: rgba(41, 45, 62, 0.8);
  color: #fff;
  border-radius: 80px;
  padding: 50px;
}

.form-wrapper .header {
  text-align: center;
  font-family: 'Courier New', Courier, monospace;
  font-size: 35px;
  text-transform: uppercase;
  line-height: 100px;
}

.form-wrapper .header:hover {
  text-align: center;
  font-family: 'Courier New', Courier, monospace;
  font-size: 37px;
  font-weight: 520;
  text-transform: uppercase;
  line-height: 100px;
}

.form-wrapper .input-wrapper input {
  background-color: rgb(41, 45, 62);
  border: 0;
  width: 100%;
  text-align: center;
  font-size: 15px;
  color: #fff;
  outline: none;
}

.form-wrapper .input-wrapper input::placeholder {
  text-transform: uppercase;
}

.form-wrapper .input-wrapper .border-wrapper {
  background-image: linear-gradient(to right, hwb(321 38% 41%), #2288b7);
  width: 100%;
  height: 50px;
  margin-bottom: 20px;
  border-radius: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-wrapper .input-wrapper .border-wrapper:hover {
  background-image: linear-gradient(to right, hwb(320 64% 3%), #6dcbf6);
  width: 100%;
  height: 50px;
  margin-bottom: 20px;
  border-radius: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-wrapper .input-wrapper .border-wrapper .border-item {
  height: 46px;
  width: 347px;
  margin-right: 70px;
  border-radius: 30px;
  font-family: 'Courier New', Courier, monospace;
}

.form-wrapper .login-btn {
  display: flex;
  justify-content: center;
}

.form-wrapper .login-btn .btn-signup {
  width: 40%;
  text-transform: uppercase;
  border: 1px solid #3aa6d8;
  background-image: linear-gradient(to right, hwb(321 38% 41%), #2288b7);
  text-align: center;
  line-height: 40px;
  border-radius: 30px;
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.8);
  color: #060606;
  margin-right: 7px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 15px;
}

.form-wrapper .login-btn .btn-signup:hover {
  width: 40%;
  text-transform: uppercase;
  border: 1px solid #3aa6d8;
  background-image: linear-gradient(to right, hwb(320 53% 1%), #6ccffd);
  text-align: center;
  line-height: 40px;
  border-radius: 30px;
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.8);
  color: #060606;
  margin-right: 7px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 15px;
}

.form-wrapper .login-btn .btn-login:hover {
  width: 40%;
  text-transform: uppercase;
  border: 1px solid #3aa6d8;
  background-image: linear-gradient(to right, hwb(320 53% 1%), #6ccffd);
  text-align: center;
  line-height: 40px;
  border-radius: 30px;
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.8);
  color: #060606;
  margin-right: 7px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 15px;
}

.form-wrapper .login-btn .btn-login {
  width: 40%;
  text-transform: uppercase;
  border: 1px solid #3aa6d8;
  background-image: linear-gradient(to right, hwb(321 38% 41%), #2288b7);
  text-align: center;
  line-height: 40px;
  border-radius: 80px;
  cursor: pointer;
  background-color: rgba(41, 45, 62, 0.8);
  color: #fff;
  margin-right: 7px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 15px;
}

.form-wrapper .action .btn:hover {
  background-image: linear-gradient(120deg, #84fab0 0%, #8fd3f4 100%);
}

.form-wrapper .icon-wrapper {
  text-align: center;
  width: 60%;
  margin: 0 auto;
  margin-top: 20px;
  border-top: 1px dashed rgb(146, 146, 146);
  padding: 20px;
}

.form-wrapper .icon-wrapper i {
  font-size: 20px;
  color: rgb(187, 187, 187);
  cursor: pointer;
  border: 1px solid #fff;
  padding: 5px;
  border-radius: 20px;
}

.form-wrapper .icon-wrapper i:hover {
  background-color: #0e92b3;
}
</style>

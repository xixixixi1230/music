<template>
<div>
    <!-- 整体背景 <loginLogo/>-->
    <div class="signup-wrap">
      <div class="form-wrapper">
        <div class="header">
            <span>SIGNING UP</span>
        </div>
        <el-form :model="registerForm" ref="registerForm" label-width="70px" class="input-wrapper" :rules="rules">
            <el-form-item prop="username" class="border-wrapper">
                <input v-model="registerForm.username" placeholder="用户名" class="border-item" ></input>
            </el-form-item>
            <el-form-item prop="password" class="border-wrapper">
                <input type="password" v-model="registerForm.password" placeholder="密码" class="border-item" ></input>
            </el-form-item>
            <el-form-item prop="sex" class="border-wrapper">
                <el-radio-group v-model="registerForm.sex">
                    <el-radio :label="0">♀</el-radio>
                    <el-radio :label="1">♂</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="phoneNum" class="border-wrapper">
                <input v-model="registerForm.phoneNum" placeholder="手机" class="border-item" ></input>
            </el-form-item>
            <el-form-item prop="email" class="border-wrapper">
                <input v-model="registerForm.email" placeholder="邮箱" class="border-item" ></input>
            </el-form-item>
            <el-form-item prop="introduction" class="border-wrapper">
              <input
                v-model="registerForm.introduction"
                placeholder="签名"
                class="border-item">
              </input>
            </el-form-item>
            <el-form-item class="form-row">
              <el-date-picker
                v-model="registerForm.birth"
                placeholder="请选择生日"
                >
              </el-date-picker>
              <el-select
                v-model="registerForm.location"
                placeholder="地区"
                >
                <el-option
                  v-for="item in cities"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <div class="signup-btn">
                <el-button @click="goback(-1)" class="btn-quit">取消</el-button>
                <el-button type="primary" @click="submitRegister" class="btn-confirm">确定</el-button>
            </div>
        </el-form>
      </div>
    </div>
</div>
</template>
<script>
import loginLogo from '../components/LoginLogo'
import {rules,cities} from '../assets/data/form'
import {mixin} from '../mixins'
import {SignUp} from '../api/index'

export default {
    name: 'sign-up',
    mixins: [mixin],
    components: {
        loginLogo
    },
    data() {
        return {
            registerForm: {
                username: '',       //用户名
                password: '',       //密码
                sex: '',            //性别
                phoneNum: '',       //手机
                email: '',          //邮箱
                birth: '',          //生日
                introduction: '',   //签名
                location: ''       //地区
            },
            cities: [],            //所有的地区--省
            rules: {}               //表单提交的规则
        }
    },
    created() {
        this.rules = rules;
        this.cities = cities;
    },
    methods:{
      //提交注册
      submitRegister() {
    this.$refs['registerForm'].validate((valid) => {
        if (valid) {
            let _this = this;
            let d = new Date(this.registerForm.birth);
            let datetime = `${d.getFullYear()}-${(d.getMonth() + 1).toString().padStart(2, '0')}-${d.getDate().toString().padStart(2, '0')}`;             
            console.log(datetime);
            
            // 构建参数对象
            let params = {
                username: this.registerForm.username,
                password: this.registerForm.password,
                sex: this.registerForm.sex,
                phoneNum: this.registerForm.phoneNum,
                email: this.registerForm.email,
                birth: d,
                introduction: this.registerForm.introduction,
                location: this.registerForm.location,
                avator: '/img/user.jpg'
            };

            // 使用 SignUp 方法提交数据
            SignUp(params).then(res => {
                if (res.code == 200) {
                    _this.notify('注册成功', 'success');
                    setTimeout(function () {
                        _this.$router.push({ path: '/' });
                    }, 2000);
                } else {
                    _this.notify('注册失败', 'error');
                }
            }).catch(err => {
                _this.notify('注册失败', 'error');
            });
        }
    });
},

      // submitRegister(){
      //     this.$refs['registerForm'].validate((valid) => {
      //       if(valid){
      //         let _this = this;
      //         let d = this.registerForm.birth;
      //         let datetime = d.getFullYear() + '-' +(d.getMonth() + 1) +'-' + d.getDate();
      //         let params = new URLSearchParams();
      //         params.append('username',this.registerForm.username);
      //         params.append('password',this.registerForm.password);
      //         params.append('sex',this.registerForm.sex);
      //         params.append('phoneNum',this.registerForm.phoneNum);
      //         params.append('email',this.registerForm.email);
      //         params.append('birth',datetime);
      //         params.append('introduction',this.registerForm.introduction);
      //         params.append('location',this.registerForm.location);
      //         params.append('avator','/img/user.jpg');
      //         SignUp(params).then(res => {
      //           if (res.code == 1) {
      //             _this.notify('注册成功','success');
      //             setTimeout(function(){
      //               _this.$router.push({path: '/'});
      //             }, 2000);
      //           } else {
      //             _this.notify('注册失败','error');
      //           }
      //         }).catch(err => {
      //           _this.notify('注册失败','error');
      //       })
      //     }
      //   });
      // },
      goback(index){
        this.$router.go(index);
      }
    }
}
</script>

<style scoped>
.signup-wrap {
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
  background-color: rgba(184, 174, 188, 0.8);
  color: #000000;
  border-radius: 80px;
  padding: 45px;
  margin-top: 60px;
}

.form-wrapper:hover {
  width: 350px;
  background-color: rgba(184, 174, 188, 0.281);
  color: #000000;
  border-radius: 80px;
  padding: 45px;
  margin-top: 60px;
}

.form-wrapper .header {
  text-align: center;
  font-family: 'Courier New', Courier, monospace;
  font-size: 35px;
  font-weight: 500;
  text-transform: uppercase;
  line-height: 50px;
}

.form-wrapper .header:hover {
  text-align: center;
  font-family: 'Courier New', Courier, monospace;
  font-size: 37px;
  font-weight: 520;
  text-transform: uppercase;
  line-height: 50px;
}

.form-wrapper .input-wrapper input {
  font-family: 'Courier New', Courier, monospace;
  background-color: rgba(255, 255, 255, 0.55);
  border: 0;
  width: 100%;
  text-align: center;
  font-size: 15px;
  color: #000000;
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
  background-image: linear-gradient(to right, hwb(320 51% 2%), #6cccf9);
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

.form-wrapper .signup-btn {
  display: flex;
  justify-content: center;
}

.form-wrapper .signup-btn .btn-quit {
  width: 40%;
  text-transform: uppercase;
  border: 1px solid #3aa6d8;
  background-image: linear-gradient(to right, hwb(321 38% 41%), #2288b7);
  text-align: center;
  line-height: 20px;
  border-radius: 30px;
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.8);
  color: #060606;
  margin-right: 7px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 15px;
}

.form-wrapper .signup-btn .btn-confirm {
  width: 40%;
  text-transform: uppercase;
  border: 1px solid #3aa6d8;
  background-image: linear-gradient(to right, hwb(321 38% 41%), #2288b7);
  text-align: center;
  line-height: 20px;
  border-radius: 80px;
  cursor: pointer;
  background-color: rgba(41, 45, 62, 0.8);
  /* background-color: rgba(127, 219, 250, 0.59); */
  color: #fff;
  margin-right: 7px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 15px;
}

.form-wrapper .signup-btn .btn-confirm:hover {
  background-image: linear-gradient(to right, hwb(319 49% 0% / 0.972), #58c5f7);
}

.form-wrapper .signup-btn .btn-quit:hover {
  background-image: linear-gradient(to right, hwb(319 39% 16% / 0.325), #32bcfc52);
}

/*  单选 */
.el-radio {
  margin-right: 60px;
  /*  默认文本样式 */
  .el-radio__label {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: #ffffff;
    font-weight: 400;
  }
  /* icon 默认样式 */
  .el-radio__inner {
    background: #23262a77;
    border: 1px solid rgba(23, 150, 165);
  }
  /* 选中文本样式 */
  .el-radio__input.is-checked + .el-radio__label {
    font-family: PingFangSC-Semibold;
    font-size: 14px;
    color: #00ffff;
    font-weight: 500;
  }
  /* 选中 icon 样式 */
  .el-radio__inner::after {
    width: 8px;
    height: 8px;
    background-color: #00ffff;
  }
  /* 覆盖默认 样式 */
  .el-radio__input.is-checked .el-radio__inner {
    border: 1px solid #00ffff;
    background-color: transparent;
  }
}


.form-row {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.form-row .el-date-picker {
  width: 10%;  /* 让元素均分宽度 */
  flex : 1;
  margin-right: 10px; /* 为元素之间添加间距 */

}


.form-row .el-select {
  width: 78.5%;  /* 让元素均分宽度 */
  flex : 1;
  margin-right: 10px; /* 为元素之间添加间距 */
  margin-top: 10px;
}

.form-row .el-select {
  margin-right: 0; /* 取消最后一个元素的右边距 */
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

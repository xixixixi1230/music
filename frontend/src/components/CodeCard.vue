<template>
  <div id="qqq">
    <div v-if="valid">
      <div class="qrCode" ref="qrCodeDiv"></div>
    </div>
    <div class="qrCode" v-else>{{ showMsg }}</div>
  </div>
</template>

<script>
import QRCode from 'qrcodejs2'
import axios from 'axios'
import {loginWithPhoneNum} from '../api/index'

const host = 'localhost'
export default {
  name: 'CodeCard',
  data () {
    return {
      //本组件专属的 axios
      localAxios: null,
      //时间戳
      time: '',
      //全局计时器
      globalTimer: null,
      //是否销毁二维码的父组件
      valid: true,
      //销毁二维码父组件后,二维码框框里面显示的内容
      showMsg: ''
    }
  },
  //组件销毁前销毁计时器对象
  beforeDestroy () {
    this.clearMyTimer()
  },
  mounted () {
    this.localAxios = axios.create({
      baseURL: `http://${host}:9090/login/`,
      timeout: 5000,
      headers: {'kkl': 'kkk'},
      withCredentials: false
    })
    this.$nextTick(function () {
      this.bindQRCode()
    })
  },
  methods: {
    //绑定二维码
    bindQRCode () {
      this.initQRCode(true)
      this.requestThirdParty()
    },
    //初始化二维码图片
    initQRCode (content) {
      let qrcodeDom
      //判断是否要渲染二维码
      if (this.valid) {
        //outsourcing:http://localhost:9090/login?chuo=1677667092328
        this.time = new Date().getTime() + ''
        const text = `outsourcing:http://${host}:9090/login?chuo=${this.time}`
        qrcodeDom = new QRCode(this.$refs.qrCodeDiv, {
          text,
          width: 200,
          height: 200,
          colorDark: '#333333', //二维码颜色
          colorLight: '#fff', //二维码背景色
          correctLevel: QRCode.CorrectLevel.M//容错率，L/M/H
        })
      } else {
        this.showMsg = content
        //这段操作无效,但是不删除了,最终使用的方法是销毁其父元素,而不是销毁二维码本身
        if (qrcodeDom) {
          this.$refs.qrCodeDiv.innerHTML = ''
          qrcodeDom._el = ''
        }
      }
    },
    /*请求第三方服务*/
    requestThirdParty () {
      //使用定时器获取 这个 时间戳对应的手机号
      this.globalTimer = setInterval(() => {
        this.localAxios.post(this.time).then(rs => {
          if (rs.data.code === 200) {
            console.log(rs.data.code, rs.data.msg, rs.data.obj)
            this.clearMyTimer()
            //发起登录
            loginWithPhoneNum(rs.data.obj).then(rs => {
              //服务器返回500 则将服务器返回的 msg 显示到提示框
              if (rs.code === 500) {
                //将二维码销毁
                this.valid = false
                this.initQRCode(rs.msg)
                //提示错误消息
                return this.$message.error(rs.msg)
              }
              this.$store.commit('setLoginIn', true)
              this.$store.commit('setUserId', rs.data.id)
              this.$store.commit('setUsername', rs.data.username)
              this.$store.commit('setAvator', rs.data.avator)
              this.$store.commit('userVipStatus', rs.data.isVipUser)
              this.$message.success('登录成功')
              this.$router.push('/')
            })
          } else if (rs.data.code === 666) {
            this.$message.info('二维码长时间未使用,重新刷新')
            //二维码长时间没有扫描,刷新  清除原本的dom元素
            this.valid = false
            this.initQRCode('二维码长时间未使用,重新刷新')
            //清除定时器
            this.clearMyTimer()
          } else {
            /*console.log('---还没拿到数据')
            console.log('---定时器', this.globalTimer)*/
          }
        })
      }, 1500)
    },
    /*清除计时器*/
    clearMyTimer () {
      clearInterval(this.globalTimer)
      this.globalTimer = null
    }
  }
}
</script>

<style scoped>
#qqq {
  background-color: #fff;
  margin: 0 auto; /*水平居中*/
}

.qrCode {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  margin: 0 auto; /*水平居中*/
  width: 200px;

}

.qrCode img {
  width: 200px;
  height: 200px;
  background-color: #fff;
  padding: 6px;
}

</style>

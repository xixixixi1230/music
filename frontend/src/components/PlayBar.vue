<template>
  <div class="play-bar" :class="{show:!playerStatus}">
    <div @click="toggleStatus" class="item-up" :class="{turn: playerStatus}">
      <svg class="icon">
        <use xlink:href="#icon-jiantou-xia-cuxiantiao"></use>
      </svg>
    </div>
    <div class="kongjian">
      <!-- 上一首 -->
      <div class="item" @click="prev">
        <svg class="icon">
          <use xlink:href="#icon-ziyuanldpi"></use>
        </svg>
      </div>
      <!-- 播放 -->
      <div class="item" @click="togglePlay">
        <svg :key="playButtonUrl" class="icon">
          <use :xlink:href="playButtonUrl"></use>
        </svg>
      </div>
      <!-- 下一首 -->
      <div class="item" @click="next">
        <svg class="icon">
          <use xlink:href="#icon-ziyuanldpi1"></use>
        </svg>
      </div>
      <!-- 歌曲图片 -->
      <div class="item-img" @click="toLyric">
        <img :src="picUrl"/>
      </div>
      <!-- 播放进度 -->
      <div class="playing-speed">
        <!-- 播放开始时间 -->
        <div class="current-time">{{ nowTime }}</div>
        <div class="progress-box">
          <div class="item-song-title">
            <div>{{ this.title }}</div>
            <div>{{ this.artist }}</div>
          </div>
          <div ref="progress" class="progress" @mousemove="mousemove">
            <!-- 进度条 -->
            <div ref="bg" class="bg" @click="updatemove">
              <div ref="curProgress" class="cur-progress" :style="{width:curLength+'%'}"></div>
            </div>
            <!-- 拖动的点点 -->
            <div ref="idot" class="idot" :style="{left:curLength+'%'}" @mousedown="mousedown" @mouseup="mouseup"></div>
          </div>
        </div>
        <!-- 播放结束时间 -->
        <div class="left-time">{{ songTime }}</div>
        <!-- 音量 -->
        <div class="item item-volume">
          <svg v-if="volume == 0" class="icon">
            <use xlink:href="#icon-yinliangjingyinheix"></use>
          </svg>
          <svg v-else class="icon">
            <use xlink:href="#icon-yinliang1"></use>
          </svg>
          <el-slider class="volume" v-model="volume" :vertical="true"></el-slider>
        </div>
        <!-- 收藏 -->
        <div class="item" @click="collection">
          <svg :class="{active:isActive}" class="icon">
            <use xlink:href="#icon-xihuan-shi"></use>
          </svg>
        </div>
        <!-- 下载 -->
        <div class="item" @click="download">
          <svg class="icon">
            <use xlink:href="#icon-xiazai"></use>
          </svg>
        </div>
        <!-- 当前播放的歌曲列表 -->
        <div class="item" @click="changeAside">
          <svg class="icon">
            <use xlink:href="#icon-liebiao"></use>
          </svg>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import {mapGetters} from 'vuex'
import {download, setCollect, getCollectOfUserId} from '../api/index'

export default {
  name: 'play-bar',
  data () {
    return {
      nowTime: '00:00',        //当前播放进度的时间
      songTime: '00:00',        //当前歌曲总时间
      curLength: 0,            //进度条的位置
      progressLength: 0,      //进度条的总长度
      mouseStartX: 0,      //拖拽开始位置
      tag: false,             //拖拽开始结束的标志，当开始拖拽，它的值才会变成true
      volume: 50,             //音量，默认一半
      toggle: true ,        //显示隐藏播放器页面
      isActive:false         //初始未收藏
    }
  },
  computed: {
    ...mapGetters([
      'id',               //歌曲id
      'url',              //歌曲地址
      'isPlay',           //播放状态
      'playButtonUrl',   //播放状态的图标
      'picUrl',            //正在播放的音乐的图片
      'title',                  //歌名
      'artist',                 //歌手名
      'duration',                //音乐时长
      'curTime',                //当前音乐的播放位置
      'showAside',            //是否显示播放中的歌曲列表
      'listIndex',            //当前歌曲在歌单中的位置
      'listOfSongs',          //当前歌单列表
      'autoNext',             //自动播放下一首
      'loginIn',              //用户是否已登录
      'userId',               //当前登录用户的id
      'isActive',             //当前播放的歌曲是否已收藏
      'playerStatus'
    ])
  },
  watch: {
  //   resetProgress() {
  //   this.currentTime = 0; // 重置当前播放时间
  //   this.$refs.audio.currentTime = 0; // 如果使用 <audio> 标签
  //   this.$refs.progressBar.value = 0; // 如果使用 <input type="range"> 作为进度条
  // },
  resetProgress() {
    this.curTime = 0;  // 重置当前时间
    this.nowTime = this.formatSeconds(0); // 将显示的当前时间设置为 00:00
    this.curLength = 0; // 重置进度条长度
    this.songTime = this.formatSeconds(this.duration); // 更新歌曲的总时间
    this.$forceUpdate(); // 强制更新视图
  },

    //切换播放状态的图标
    isPlay () {
      if (this.isPlay) {
        this.$store.commit('setPlayButtonUrl', '#icon-zanting')
      } else {
        this.$store.commit('setPlayButtonUrl', '#icon-bofang')
      }
    },
    curTime () {
      this.nowTime = this.formatSeconds(this.curTime)
      this.songTime = this.formatSeconds(this.duration)
      this.curLength = (this.curTime / this.duration) * 100
    },
    // 添加对 playButtonUrl 的监听
  // playButtonUrl(newVal, oldVal) {
  //   // 手动触发组件的重新渲染
  //     this.$forceUpdate();
  // },
  playButtonUrl() {
    return this.isPlay ? '#icon-zanting' : '#icon-bofang';
  },
    //音量变化
    volume () {
      this.$store.commit('setVolume', this.volume / 100)
    },
    //自动播放下一首
    autoNext () {
      this.next()
    }
  },
  mounted () {
    this.progressLength = this.$refs.progress.getBoundingClientRect().width
    document.querySelector('.item-volume').addEventListener('click', function (e) {
      document.querySelector('.volume').classList.add('show-volume')
      e.stopPropagation()
    }, false)
    document.querySelector('.volume').addEventListener('click', function (e) {
      e.stopPropagation()
    }, false)
    document.addEventListener('click', function () {
      document.querySelector('.volume').classList.remove('show-volume')
    }, false)
  },

methods: {
  // 是否显示播放器
  toggleStatus () {
    this.$store.commit("updPlayerStatus", !this.$store.getters.playerStatus)
  },
  // 提示信息
  notify (title, type) {
    this.$notify({
      title: title,
      type: type
    })
  },
  // 控制音乐播放、暂停
  togglePlay () {
    if (this.isPlay) {
      this.$store.commit('setIsPlay', false)
    } else {
      this.$store.commit('setIsPlay', true)
    }
  },
  // 解析时间
  formatSeconds (value) {
    let theTime = parseInt(value)
    let result = ''
    let hour = parseInt(theTime / 3600)
    let minute = parseInt((theTime / 60) % 60)
    let second = parseInt(theTime % 60)
    if (hour > 0) {
      result = (hour < 10 ? '0' : '') + hour + ':'
    }
    result += (minute < 10 ? '0' : '') + minute + ':'
    result += (second < 10 ? '0' : '') + second
    return result
  },
  // 拖拽开始
  mousedown (e) {
    this.mouseStartX = e.clientX
    this.tag = true
  },
  // 拖拽结束
  mouseup () {
    this.tag = false
  },
  // 拖拽中
  mousemove (e) {
    if (!this.duration) return
    if (this.tag) {
      let movementX = e.clientX - this.mouseStartX
      let curLength = this.$refs.curProgress.getBoundingClientRect().width
      let newPercent = ((movementX + curLength) / this.progressLength) * 100
      if (newPercent > 100) newPercent = 100
      this.curLength = newPercent
      this.mouseStartX = e.clientX
      this.changeTime(newPercent)
    }
  },
  // 更改歌曲进度
  changeTime (percent) {
    let newCurTime = (percent * 0.01) * this.duration
    this.$store.commit('setChangeTime', newCurTime)
  },
  // 点击播放条切换播放进度
  updatemove (e) {
    if (!this.tag) {
      let newPercent = (e.offsetX / this.progressLength) * 100
      if (newPercent > 100) newPercent = 100
      else if (newPercent < 0) newPercent = 0
      this.curLength = newPercent
      this.changeTime(newPercent)
    }
  },
  // 显示播放中的歌曲列表
  changeAside () {
    this.$store.commit('setShowAside', true)
  },
  // 上一首
  prev () {
    if (this.listIndex !== -1 && this.listOfSongs.length > 1) {
      if (this.listIndex > 0) {
        this.$store.commit('setListIndex', this.listIndex - 1)
      } else {
        this.$store.commit('setListIndex', this.listOfSongs.length - 1)
      }
      this.toplay(this.listOfSongs[this.listIndex].url)
      this.resetProgress()
      this.togglePlay() // 确保播放状态被设置为播放
    }
  },
  // 下一首
  next () {
    if (this.listIndex !== -1 && this.listOfSongs.length > 1) {
      if (this.listIndex < this.listOfSongs.length - 1) {
        this.$store.commit('setListIndex', this.listIndex + 1)
      } else {
        this.$store.commit('setListIndex', 0)
      }
      this.toplay(this.listOfSongs[this.listIndex].url)
      this.resetProgress()
      this.togglePlay() // 确保播放状态被设置为播放
    }
  },


  // 播放音乐
  toplay (url) {
    if (url && url !== this.url) {
      this.$store.commit('setId', this.listOfSongs[this.listIndex].id)
      this.$store.commit('setUrl', this.$store.state.configure.HOST + url)
      this.$store.commit('setPicUrl', this.$store.state.configure.HOST + this.listOfSongs[this.listIndex].pic)
      this.$store.commit('setTitle', this.replaceFName(this.listOfSongs[this.listIndex].name))
      this.$store.commit('setArtist', this.replaceLName(this.listOfSongs[this.listIndex].name))
      this.$store.commit('setLyric', this.parseLyric(this.listOfSongs[this.listIndex].lyric))
      this.$store.commit('setIsActive', false)
      if (this.loginIn) {
        getCollectOfUserId(this.userId)
          .then(res => {
            for (let item of res) {
              if (item.songId == this.id) {
                this.$store.commit('setIsActive', true)
                break
              }
            }
          })
      }
    }
  },
  // // 获取名字前半部分--歌手名
  // replaceLName (str) {
  //   let arr = str.split('-')
  //   return arr[0]
  // },
  // // 获取名字后半部分--歌名
  // replaceFName (str) {
  //   let arr = str.split('-')
  //   return arr[1]
  // },
  // 解析歌词
  parseLyric (text) {
    let lines = text.split('\n')
    let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g
    let result = []
    if (!(/\[.+\]/.test(text))) return [[0, text]]
    while (!pattern.test(lines[0])) lines = lines.slice(1)
    for (let item of lines) {
      let time = item.match(pattern)
      let value = item.replace(pattern, '')
      for (let item1 of time) {
        let t = item1.slice(1, -1).split(':')
        if (value !== '') result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value])
      }
    }
    result.sort((a, b) => a[0] - b[0])
    return result
  },


  // 页面
  toLyric() {
    if (this.$route.path === '/lyric') {
      this.$router.go(-1);
    } else {
      this.$router.push({ path: '/lyric' });
    }
  },
  // 下载音乐


  download () {
    download(this.url)
      .then(res => {
        let content = res.data
        let eleLink = document.createElement('a')
        eleLink.download = `${this.artist}-${this.title}.mp3`
        eleLink.style.display = 'none'
        let blob = new Blob([content])
        eleLink.href = URL.createObjectURL(blob)
        document.body.appendChild(eleLink)
        eleLink.click()
        document.body.removeChild(eleLink)
      })
      .catch(err => {
        console.log(err)
      })
  },
  // 收藏
  collection() {
      if (this.loginIn) {
        // const params = new URLSearchParams();
        // params.append('userId', this.userId);
        // params.append('type', 0);
        // params.append('songId', this.id);
        let params={
          'userId': this.userId,
          'type': this.type,
          'songId':this.songId,
        }
        setCollect(params)
          .then(res => {
            if (res.code === 200) {
              this.isActive = !this.isActive; // 切换图标状态
              this.$store.commit('setIsActive', this.isActive); // 更新 Vuex 状态
              this.notify('收藏成功', 'success');
            } else if (res.code === 2) {
              this.notify('已收藏', 'warning');
            }
          })
          .catch(() => {
            this.notify('操作失败', 'error');
          });
      } else {
        this.notify('请登录后再收藏', 'warning');
      }
    },

  // collection () {
  //   if (this.loginIn) {
  //     var params = new URLSearchParams()
  //     params.append('userId', this.userId)
  //     params.append('type', 0)
  //     params.append('songId', this.id)
  //     setCollect(params)
  //       .then(res => {
  //         if (res.code === 1) {
  //           this.$store.commit('setIsActive', true)
  //           this.notify('收藏成功', 'success')
  //         } else if (res.code === 2) {
  //           this.notify('已收藏', 'warning')
  //         }
  //       })
  //   } else {
  //     this.notify('请登录后再收藏', 'warning')
  //   }
  // },
  // 重置播放进度和时间
  resetProgress() {
    this.nowTime = '00:00'
    this.songTime = this.formatSeconds(this.duration)
    this.curLength = 0
    this.$store.commit('setChangeTime', 0)
  }
}




}
</script>
<style lang="scss" scoped>
@import '../assets/css/play-bar.scss';
</style>

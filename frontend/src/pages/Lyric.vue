<template>
    <div class="song-lyric">
      <h1 class="lyric-title">歌词</h1>
      <!-- 有歌词 -->
      <ul class="has-lyric" v-if="lyr.length">
        <li v-for="(item, index) in lyr" :key="index" :class="{ 'active': index === activeIndex }">
          {{ item[1]}}
        </li>
      </ul>
      <!-- 没有歌词 -->
      <div v-else class="no-lyric">
        <span>暂无歌词</span>
      </div>
    </div>
  </template>
  
  <script>
  import { mixin } from '../mixins';
  import { mapGetters } from 'vuex';
  
  export default {
    name: 'lyric',
    mixins: [mixin],
    data() {
      return {
        lyr: [],           // 当前歌曲的歌词
        activeIndex: -1    // 当前高亮的歌词索引
      };
    },
    computed: {
      ...mapGetters([
        'curTime',      // 当前歌曲播放到的位置
        'id',           // 当前播放的歌曲id
        'lyric',        // 歌词
        'listIndex',    // 当前歌曲在歌单中的位置
        'listOfSongs',  // 当前歌单列表
      ])
    },
    created() {
        console.log('Lyric component created');
        this.lyr = this.lyric; 
        console.log('Initial Lyrics:', this.lyr);
    },
    watch: {
      id() {
        const song = this.listOfSongs[this.listIndex];
        if (song) {
            this.lyr = this.parseLyric(song.lyric);
            console.log('Updated Lyrics in Watch:', this.lyr); // 确保更新
        }


        // this.lyr = this.parseLyric(this.listOfSongs[this.listIndex].lyric);
        // console.log('Updated lyrics:', this.lyr); // 调试信息
      },
      curTime() {
        if (this.lyr.length > 0) {
          for (let i = 0; i < this.lyr.length; i++) {
            if (this.curTime >= this.lyr[i][0]) {
              this.activeIndex = i;
            }
          }
        }
      }
    },
    parseLyric(text) {
      let lines = text.split('\n');
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      if (!(/\[.+\]/.test(text))) {
        return [[0, text]];
      }
      while (!pattern.test(lines[0])) {
        lines = lines.slice(1);
      }
      for (let item of lines) {
        let time = item.match(pattern);
        let value = item.replace(pattern, '');
        for (let item1 of time) {
          let t = item1.slice(1, -1).split(':');
          if (value != '') {
            result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value]);
          }
        }
      }
      result.sort((a, b) => a[0] - b[0]);
      return result;
    },
  };
  </script>
  
  <style lang="scss" scoped>
  @import '../assets/css/lyric.scss';
  
  .has-lyric li.active {
    color: #95d2f6;
    font-size: 25px;
  }
  
  .has-lyric li {
    color: #000;
    font-size: 15px;
  }
  </style>
  
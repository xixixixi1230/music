<template>
  <div class="content">
    <h1 class="title">
      <slot name="title"></slot>
      <hr />
    </h1>
    <ul>
      <li>
        <div class="song-item">
          <span class="item-index"></span>
          <span class="item-title">歌曲名</span>
          <span class="item-name" v-if="!issinger">歌手</span>
          <span class="item-intro">专辑</span>
          <span>音乐MV</span>
        </div>
      </li>
      <span v-for="(item, index) in songList" :key="index">
        <div
          class="song-item"
          @click="
            toplay(
              item.id,
              item.url,
              item.pic,
              index,
              item.name,
              item.lyric,
            )
          "
        >
          <span class="item-index">
            {{ index + 1 }}
          </span>
          <span class="item-title">{{ item.name }}</span>
          <span class="item-name" v-if="!issinger">{{ item.singerName }}</span>
          <span class="item-intro">{{ item.introduction }}</span>
          <span
            ><a
              v-if="item.mvurl"
              @click="playmv(item.mvurl)"
              style="color: green"
              >音乐MV</a
            >
            <span v-if="!item.mvurl" style="color: blue">暂无MV</span></span
          >
        </div>
      </span>
    </ul>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
import {getSongIdBySongListId} from '../api/index.js'

export default {
  name: "album-content",
  mixins: [mixin],
  data() {
    return {
      sid: "", //传来的歌曲id
      issinger:""
    };
  },
  filters: {
    showVipLogo(isVip) {
      console.log("---是不是vip", isVip);
      if (isVip) {
        return "vip";
      }
    },
  },
  created() {
    console.log("进入Album");

    // console.log('---------接收到的歌单', this.songList)
    let sid = localStorage.getItem("songListId");
    if (sid && sid !== "undefined") {
      console.log("=sid==>>>>>>", sid);
      this.sid = JSON.parse(sid);
    } else {
      this.sid = this.$route.params.sid;
      localStorage.setItem("sid", JSON.stringify(this.$route.params.sid));
    }
    // getSongIdBySongListId(sid).then(res=>{
    // });
    console.log(this.songList);
    let source=localStorage.getItem("contentList");
    console.log(source);
    if(source=="Singer") this.issinger=true;
    
  },
  beforeDestroy() {
    
    localStorage.removeItem("songListId");
  },
  props: ["songList"],
  methods: {
    playmv(mvurl) {
      this.$router.push({ name: "playmv", params: { mvurl: mvurl } });
      //this.$router.push({path:"/playmv",query:{mvurl:mvurl}})
      //this.$router.push({path: `/playmv`});
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../assets/css/album-content.scss";
.show-vip {
  display: inline-block;
  width: 20px;
  height: 20px;
  background: url("../assets/img/vip.png") no-repeat;
  background-size: cover;
}
.song-item{
  margin-top: 10px;
}
</style>

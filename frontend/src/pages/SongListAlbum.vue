<template>
  <div class="song-list-album">
    <div class="album-slide">
      <div class="album-img">
        <img :src="attachImageUrl(tempList.pic)" />
      </div>
      <div class="album-info">
        <h2>简介</h2>
        <span>
          {{ tempList.introduction }}
        </span>
      </div>
    </div>
    <div class="album-content">
      <div class="album-title">
        <p>{{ tempList.title }}</p>
      </div>
      <div class="album-score">
        <div>
          <h3>歌单评分：</h3>
          <div>
            <el-rate v-model="average" disabled></el-rate>
          </div>
        </div>
        <span>{{ score }}</span>
        <div>
          <h3>评价：</h3>
          <div>
            <el-rate v-model="rank" allow-half show-text @change="setRank"></el-rate>
          </div>
        </div>
      </div>
      <div class="songs-body">
        <album-content :songList="songLists">
          <template slot="title">歌单</template>
        </album-content>
        <comment :playId="songListId" :type="1"></comment>
      </div>
    </div>
  </div>
</template>
<script>
import { mixin } from "../mixins";
import { mapGetters } from "vuex";
import {
  listSongDetail,
  songOfSongId,
  setRank,
  getRankOfSongListId,
  getSongListScore,
  getUserRank,
  songListRank
} from "../api/index";
import AlbumContent from "../components/AlbumContent";
import Comment from "../components/Comment";

export default {
  name: "song-list-album",
  mixins: [mixin],
  components: {
    AlbumContent,
    Comment,
  },
  data() {
    return {
      songLists: [], //当前页面需要展示的歌曲列表
      songListId: "", //前面传来的歌单id
      average: 0, //平均分
      score:0,
      rank: 0, //提交评价的分数
    };
  },
  computed: {
    ...mapGetters([
      "listOfSongs", //当前播放列表
      "tempList", //当前歌单对象
      "loginIn", //用户是否已登录
      "userId", //当前登录用户id
    ]),
  },
  created() {
    this.songListId = this.$route.params.id;
    this.getSongId();
    this.getRank(this.songListId);
    console.log(this.songListId);
    this.getOldRank();
  },
  methods: {
    getOldRank(){
      getUserRank(this.songListId,this.userId)
      .then(res=>{
        this.rank=res.data/2;
        console.log(this.rank);
        
      })
    },
    //获取当前歌单的歌曲列表
    getSongId() {
      listSongDetail(this.songListId)
        .then((res) => {
          console.log(res);
          for (let item of res.data) {
            this.getSongList(item.songId);
          }
          this.$store.commit("setListOfSongs", this.songLists);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //根据歌曲id获取歌曲信息
    getSongList(id) {
      songOfSongId(id)
        .then((res) => {
          console.log("=======>>歌曲列表", res.data);
          this.songLists.push(res.data[0]);
          console.log(this.songLists);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //获取歌单评分
    getRank(id) {
      getSongListScore(id)
        .then((res) => {
          console.log(id,res);
          this.score = res.data;
          this.average=res.data/2;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //提交评分
    setRank() {
      if (this.loginIn) {
        let params = {
          "songListId":this.songListId,
          "consumerId":this.userId,
          "score":this.rank * 2
        }
        songListRank(params)
          .then((res) => {
            if (res.code == 200) {
              this.notify("评分成功", "success");
              this.getRank(this.songListId);
            } else {
              this.notify("评分失败", "error");
            }
          })
          .catch((err) => {
            this.notify("您已经评价过啦", "error");
          });
      } else {
        this.rank = null;
        this.notify("请先登录", "warning");
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../assets/css/song-list-album.scss";
</style>
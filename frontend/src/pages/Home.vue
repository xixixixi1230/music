<template>
  <div class="home">
    <swiper />
    <div class="section" v-for="(item,index) in songsList" :key="index">
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper";
import contentList from '../components/ContentList';
import {mapGetters} from 'vuex';
import {getAllSinger,getAllSongList,topSong,topRecommend,topSongList} from '../api/index';
export default {
  name: 'home',
  components: {
    Swiper,
    contentList
  },
  data () {
    return {
      songsList: [
	    {name:"TOP 🎵 10 ",list: []}
    //     {name:"歌单",list: []},
    //     {name:"歌手",list: []},
		// {name:"歌曲推荐",list: []}
      ]
    }
  },
  computed:{
        ...mapGetters([
            'userId',           //当前登录用户id
        ])
  },
  created () {
   this.getTopSong();
   localStorage.setItem('contentList', 'Home');
  //  this.getSongList();
  //  this.getSinger();
  //  this.getRecommend(this.userId);
  },
  methods: {
    getTopSong(){                      //获取播放次数前十条歌曲
      topSongList().then((res) => {
        console.log(res);
        this.songsList[0].list = res.data;
      }).catch((err) => {
        console.log(err);
      })
    },
    // getSongList(){                      //获取前十条歌单
    //   getAllSongList().then((res) => {
    //     this.songsList[1].list = res.slice(0,10);
    //   }).catch((err) => {
    //     console.log(err);
    //   })
    // },
    // getSinger(){                      //获取前十名歌手
    //   getAllSinger().then((res) => {
    //     this.songsList[2].list = res.slice(0,10);
    //   }).catch((err) => {
    //     console.log(err);
    //   })
    // },
    // getRecommend(userId){                      //获取前五首推荐歌曲
    //   topRecommend(userId).then((res) => {
    //     this.songsList[3].list = res;
    //   }).catch((err) => {
    //     console.log(err);
    //   })
    // }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>

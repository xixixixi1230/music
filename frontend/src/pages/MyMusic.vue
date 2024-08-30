<template>
    <div class="my-music">
        <div class="album-slide">
            <div class="album-img">
                <img :src="attachImageUrl(avator)">
            </div>
            <ul class="album-info">
                <li>昵称:{{username}}</li>
                <li v-if="userSex === 0">性别: 女</li>
                <li v-else-if="userSex === 1">性别: 男</li>
                <li v-else>性别: 未指定</li> <!-- 如果 sex 既不是 0 也不是 1，显示 "未指定" -->
                <li>生日：{{birth}}</li>
                <li>故乡：{{location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="album-title">
                MY🤷TAG：{{introduction}}
            </div>
            <div class="songs-body">
                <album-content :songList="collectList">
                    <template slot="title">MY COLLECTION</template>
                </album-content>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import {getUserOfId,getCollectOfUserId,songOfSongId,getCollectById} from '../api/index';
import AlbumContent from "../components/AlbumContent";

export default {
    name: 'my-music',
    mixins: [mixin],
    components:{
        AlbumContent
    },
    data(){
        return {
            avator: '',       //用户头像
            username: '',     //昵称
            userSex: '',      //性别
            birth: '',        //生日
            location: '',     //故乡
            introduction: '', //签名
            collection: [],     //收藏的歌曲列表
            collectList: [],    //收藏的歌曲列表（带歌曲详情）
        }
    },
    computed:{
        ...mapGetters([
            'listOfSongs',      //当前播放列表
            'userId',           //当前登录用户id
        ])
    },
    mounted(){
        this.getMsg();
        this.getCollection(this.userId);
    },
    methods:{
        getMsg(){
            this.avator=this.$store.getters.avator;
            this.username=this.$store.getters.username;
            this.userSex=this.$store.getters.sex;
            this.birth=this.$store.getters.birth;
            this.location=this.$store.getters.location;
            this.introduction=this.$store.getters.introduction;
            console.log(this.avator);
            console.log(this.username);
        },
        //获取我的收藏列表
        getCollection(userId){
            getCollectById(userId)
                .then(res =>{
                        this.collection = res.data;
                        //通过歌曲id获取歌曲信息
                        for(let item of this.collection){
                            this.getSongsOfIds(item.songId);
                        }
                        console.log(this.collectList);
                        
                    })
                .catch(err => {
                    console.log(err);
                })
        },
        //通过歌曲id获取歌曲信息
        getSongsOfIds(id){
            songOfSongId(id)
                .then(res =>{
                        this.collectList.push(res.data[0]);
                    })
                .catch(err => {
                    console.log(err);
                })
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>

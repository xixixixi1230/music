<template>
    <div class="content-list">
        <ul class="section-content">
            <li class="content-item" v-for="(item,index) in contentList" :key="index">
                <div class="kuo" @click="goAlbum(item)">
                    <img class="item-img" :src="attachImageUrl(item.pic)">
                    <div class="mask">
                        <svg class="icon">
                            <use xlink:href="#icon-bofang"></use>
                        </svg>
                    </div>
                </div> 
                <p class="item-name">{{item.name||item.title}}</p>               
            </li>
        </ul>
    </div>
</template>
<script>
import {mixin} from '../mixins';
export default {
    name: 'content-list',
    mixins: [mixin],
    props: ['contentList'],
    methods: {
        goAlbum(item){
            let content=localStorage.getItem('contentList');
            console.log(content);
            
            if(content=="Home"){
                console.log(content);
                
                console.log("ContentList",item.songListId);
                localStorage.setItem('songListId', item.songListId);
                this.$store.commit("setTempList",item);
                console.log("歌单跳转");
                this.$router.push({path:`song-list-album/${item.songListId}`});
            }
            else{
                console.log(content);
                console.log("ContentList",item.id);
                localStorage.setItem('songListId', item.id);
                this.$store.commit("setTempList",item);
                console.log("歌单跳转");
                this.$router.push({path:`song-list-album/${item.id}`});
            }
            
        }
    }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
</style>

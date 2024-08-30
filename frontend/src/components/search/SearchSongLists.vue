<template>
    <div class="search-song-Lists">
       <content-list :contentList="songLists"></content-list>
    </div>
</template>
<script>
import ContentList from '../ContentList';
import {getSongListOfLikeTitle} from '../../api/index';
import {mixin} from "../../mixins";
import {mapGetters} from "vuex";

export default {
    name: 'search-song-lists',
    components:{
        ContentList
    },
    data(){
        return{
            albumDatas: []
        }
    },
    computed:{
        ...mapGetters([
            'songLists'
        ])
    },
    mounted(){
        console.log(111111111);
        
        this.getSearchList();
        console.log(this.$route.query.keywords);
    },
    methods:{
        getSearchList(){
            if(!this.$route.query.keywords){
                this.notify('您输入的内容为空','warning')
            }else{
                getSongListOfLikeTitle(this.$route.query.keywords)
                    .then(res =>{
                        console.log(res);
                        if(res){
                            this.albumDatas = res.data
                        }else{
                            this.notify('暂无该歌曲内容','warning')
                        }
                    })
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-song-lists.scss';
</style>
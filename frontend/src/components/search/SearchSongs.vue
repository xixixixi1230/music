<template>
    <div class="search-songs">
        <div class="songs-body">
            <album-content :songList="listOfSongs"></album-content>
        </div>
    </div>
</template>
<script>
import {mapGetters} from "vuex";
import {mixin} from "../../mixins";
import AlbumContent from "../AlbumContent";
import {songOfName} from '../../api/index.js'

export default {
    name: 'search-songs',
    components:{
        AlbumContent
    },
    mixins: [mixin],
    computed:{
        ...mapGetters([
            'listOfSongs'
        ])
    },
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-songs.scss';
</style>
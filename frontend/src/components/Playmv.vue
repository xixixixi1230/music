<template>
    <video controls autoplay >
	 <source :src="this.$store.state.configure.HOST+this.mvurl" />
     您的浏览器不支持 HTML5 video 标签。
    </video>
</template>

<script>
import {mapGetters} from "vuex";
export default {
 data () {
  return {
   mvurl: {}
  }
 },
 created () {
  const mvurl = localStorage.getItem("mvurl")
  if (mvurl) {
   this.mvurl = JSON.parse(mvurl)
  } else {
   this.mvurl = this.$route.params.mvurl

   localStorage.setItem("mvurl", JSON.stringify(this.$route.params.mvurl))
  }
 },
 beforeDestroy () {
  localStorage.removeItem("mvurl")
 }
}
</script>
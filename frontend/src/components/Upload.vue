<template>
    <div class="upload">
        <p class="title">修改头像</p>
        <hr/>
        <div class="section">
            <el-upload
                drag
                :action="uploadFile()"
                :show-file-list="false"
                :on-success="handleAvatorSuccess"
                :before-upload="beforeAvatorUpload"
                >
                <i class="el-icon-upload"></i>
                <div>
                    将文件拖到此处，或<span style="color:blue">修改头像</span>
                </div>
                <div slot="tip">只能上传jpg/png文件，且不能超过10MB</div>
            </el-upload>
            <!-- 预览当前头像 -->
            <img :src="avator" alt="用户头像" v-if="avator" class="preview-img" />
            <el-button @click="uploadFile">Upload</el-button>
        </div>
    </div>
</template>
<script>
import {mapGetters} from 'vuex'
import {mixin} from '../mixins'
import {updateUserAvator} from '../api/index'

export default {
    name: 'upload',
    data() {
      return {
        selectedFile: null, // 存储用户选择的文件
      };
     },
    mixins: [mixin],
    computed:{
        ...mapGetters([
            'userId',
            'avator',
        ])
    },
    methods:{
        uploadFile(){
          if (!this.selectedFile) {
            alert('Please select a file to upload.');
            return;
          }
          // 创建一个FormData对象并附加文件数据
          const formData = new FormData();
          formData.append('file', this.selectedFile); // "file"是请求的参数名

          updateUserAvator(this.userId,formData)
            .then(res =>{
              console.log(res);
                if(res.code == 200){
                    this.notify('修改成功','success');
                }else{
                    this.notify('修改失败','error');
                }
            })
            .catch(err =>{
                console.log(err);
            })
        },
         //上传成功
         handleAvatorSuccess(res, file) {
            // 在上传成功后直接设置 Vuex 中的 avator 状态
            const reader = new FileReader();
            reader.onload = (e) => {
                this.$store.commit('setAvator', e.target.result); // 使用 Base64 数据设置头像
                this.notify('修改成功', 'success');
            };
            reader.readAsDataURL(file.raw); // 读取上传的文件为 Base64
        },
        //上传之前的校验
        beforeAvatorUpload(file){
            const isJPG = file.type=='image/jpeg';
            const isLt10M = file.size /1024/1024<10;
            if(!isJPG){
                this.notify('上传头像图片只能是JPG格式','error');
                return false;
            }
            if(!isLt10M){
                this.notify('上传头像图片不能大于10MB','error');
                return false;
            }
            return true;
        }
    }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/upload.scss';
</style>

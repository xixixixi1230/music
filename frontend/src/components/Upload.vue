<template>
  <div class="upload">
    <p class="title">修改头像</p>
    <hr />
    <div class="section">
      <el-upload
        action="http://localhost:8080/"
        list-type="picture-card"
        :auto-upload="false"
        :on-change="handleChange"
        :show-file-list="false"
      >
        <i class="el-icon-plus"></i>
      </el-upload>
      <div v-if="imageUrl">
        <img
          :src="imageUrl"
          alt="Uploaded Image"
          style="width: 100px; height: 100px;"
        />
      </div>
      <button @click="uploadFile">上传</button>
      <!-- <el-upload
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
      <!-- <img :src="avator" alt="用户头像" v-if="avator" class="preview-img" />
            <el-button @click="uploadFile">Upload</el-button> -->
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
import { updateUserAvator } from "../api/index";
import axios from 'axios';


export default {
  name: "upload",
  data() {
    return {
      imageUrl: "",
      selectedFile: null
    };
  },
  mixins: [mixin],
  computed: {
    ...mapGetters(["userId", "avator"])
  },
  mounted() {
    console.log(this.userId);
    console.log(this.avator);
  },
  methods: {
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    handleChange(file) {
      // 保存文件和生成本地预览 URL
      this.selectedFile = file.raw;
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    uploadFile() {
      if (!this.selectedFile) {
        this.$message.error("请先选择文件！");
        return;
      }
      console.log("upload");
      

    //   创建 FormData 对象
        const formData = new FormData();
        formData.append("file", this.selectedFile);
        console.log(formData);
        console.log(this.userId, this.selectedFile);

        let params={
            "file":this.selectedFile,
            "id":this.userId
        }

        let id=this.userId
    //   const formData = new FormData();
    //   formData.append("file", this.selectedFile);
    //   formData.append("userId", this.userId);
      axios
        .post(`http://localhost:8080/user/avatar/update?id=${id}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          //console.log(res);
          const newAvatarUrl = res.data.data;
          // 确认 URL 是否正确
          console.log("New Avatar URL: ", newAvatarUrl);
          // 更新 Vuex Store 中的 avator 状态
          this.$store.commit('setAvator', newAvatarUrl);

          // 强制组件重新渲染
          this.$forceUpdate();
          console.log("File uploaded successfully");
        })
        .catch(error => {
          console.error("Error uploading file:", error);
        });

    }

  }
  
};
</script>
<style lang="scss" scoped>
@import "../assets/css/upload.scss";
</style>

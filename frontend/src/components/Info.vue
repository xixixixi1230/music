<template>
    <div>
        <div class="info">
            <div class="title">
                <span>编辑个人资料</span>
            </div>
            <hr />
            <div class="personal">

                <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm"
                    :rules="rules">
                    <el-form-item label="头像">
                        <div class="upload-container">
                            <el-upload action="http://localhost:8080/" list-type="picture-card" :auto-upload="false"
                                :on-change="handleChange" :show-file-list="false">
                                <i class="el-icon-plus"></i>
                            </el-upload>
                            <div v-if="imageUrl">
                                <img :src="imageUrl" alt="Uploaded Image" style="width: 100px; height: 100px;" />
                            </div>
                        </div>
                    </el-form-item>
                    <el-form-item prop="username" label="用户名">
                        <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                    </el-form-item>
                    <el-form-item prop="password" label="密码">
                        <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
                    </el-form-item>
                    <el-form-item prop="sex" label="性别">
                        <el-radio-group v-model="registerForm.sex">
                            <el-radio :label="0">女</el-radio>
                            <el-radio :label="1">男</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item prop="phoneNum" label="手机">
                        <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
                    </el-form-item>
                    <el-form-item prop="email" label="邮箱">
                        <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
                    </el-form-item>
                    <el-form-item prop="birth" label="生日">
                        <el-date-picker type="date" :editable="false" v-model="registerForm.birth" placeholder="选择日期"
                            style="width: 100%;"></el-date-picker>
                    </el-form-item>
                    <el-form-item prop="introduction" label="签名">
                        <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
                    </el-form-item>
                    <el-form-item prop="location" label="地区">
                        <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
                            <el-option v-for="item in cities" :key="item.value" :label="item.label"
                                :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div class="btn">
                    <div @click="saveMsg">保存</div>
                    <div @click="goback(-1)">取消</div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { rules, cities } from "../assets/data/form";
import { mixin } from "../mixins";
import { getUserOfId, updateUserMsg } from "../api/index";
import axios from "axios";
import Upload from './Upload.vue';

export default {
    name: "info",
    mixins: [mixin],
    data() {
        return {
            registerForm: {
                username: "", //用户名
                password: "", //密码
                sex: "", //性别
                phoneNum: "", //手机
                email: "", //邮箱
                birth: "", //生日
                introduction: "", //签名
                location: "" //地区
            },
            cities: [], //所有的地区--省
            rules: {}, //表单提交的规则
            imageUrl: "",
            selectedFile: null
        };
    },
    components: {
        Upload
    },
    mixins: [mixin],
    computed: {
        ...mapGetters(["userId", "avator"])
    },
    created() {
        this.rules = rules;
        this.cities = cities;
        console.log(this.avator);
        this.imageUrl = "http://192.168.134.90:9005/my-bucket" + this.avator;
    },
    mounted() {
        this.getMsg(this.userId);
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

            let id = this.userId;
            axios
                .post(`http://localhost:8080/user/avatar/update?id=${id}`, formData, {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                })
                .then(res => {
                    console.log(res);

                    console.log("File uploaded successfully");
                })
                .catch(error => {
                    console.error("Error uploading file:", error);
                });
        },
        getMsg(userId) {
            getUserOfId(userId)
                .then(res => {
                    this.registerForm.username = res.username;
                    this.registerForm.password = res.password;
                    this.registerForm.sex = res.sex;
                    this.registerForm.phoneNum = res.phoneNum;
                    this.registerForm.email = res.email;
                    this.registerForm.birth = res.birth;
                    this.registerForm.introduction = res.introduction;
                    this.registerForm.location = res.location;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        saveMsg() {
            let _this = this;
            let d = new Date(this.registerForm.birth);
            let datetime =
                d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
            let params = new URLSearchParams();
            params.append("id", this.userId);
            params.append("username", this.registerForm.username);
            params.append("password", this.registerForm.password);
            params.append("sex", this.registerForm.sex);
            params.append("phoneNum", this.registerForm.phoneNum);
            params.append("email", this.registerForm.email);
            //   params.append("birth", datetime);
            params.append("introduction", this.registerForm.introduction);
            params.append("location", this.registerForm.location);
            updateUserMsg(params)
                .then(res => {
                    if (res.code == 1) {
                        _this.$store.commit("setUsername", this.registerForm.username);
                        _this.notify("修改成功", "success");
                        this.uploadFile();
                        setTimeout(function () {
                            _this.$router.push({ path: "/" });
                        }, 2000);
                    } else {
                        _this.notify("修改失败", "error");
                    }
                })
                .catch(err => {
                    _this.notify("修改失败", "error");
                });
        },
        goback(index) {
            this.$router.go(index);
        }
    }
};
</script>

<style lang="scss" scoped>
@import "../assets/css/info.scss";

.upload-container {
    display: flex;
    align-items: center;
    /* 垂直居中对齐 */
    gap: 20px;
    /* 控制左右间距 */
}

.upload-section {
    flex: 1;
    /* 占用可用空间 */
}

.image-section {
    flex: 1;
    /* 占用可用空间 */
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
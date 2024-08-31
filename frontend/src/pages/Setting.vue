<template>
    <div class="setting" style="background-color: rgba(255,255,255,0.5);">
        <!-- <div class="leftCol">
            <div class="settingsMainHeader">设置</div>
            <ul class="setting-aside">
                <li v-for="(item,index) in settingList" :key="index" :class="{activeColor: activeName==item.name}"
                    @click="handleClick(item)">
                    {{item.name}}
                </li>
            </ul>
        </div> -->
        <div class="contentCol">
            <!-- <component :is="componentSrc"></component> -->
            <component :is="componentSrc" :uploadAction="uploadAction"></component>
        </div>
    </div>
</template>
<script>
import Info from '../components/Info';
import Upload from '../components/Upload';

export default {
    name: 'setting',
    components:{
        Info,
        Upload
    },
    data(){
        return {
            settingList:[
                {name: '个人信息',path: 'Info'},
                {name: '修改头像',path: 'Upload'}
            ],
            activeName: '个人信息',
            componentSrc: 'Info',
            uploadAction: '/user/avatar/update'  // 设置上传的API路径
        }
    },
    methods:{
        handleClick(item){
            this.activeName = item.name;
            this.componentSrc = item.path;
        }
    }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/setting.scss';
</style>
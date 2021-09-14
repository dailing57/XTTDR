<template>
  <el-button size="mini" v-if="user.userType !== 'student'" @click="manageVideo">管理课程视频</el-button>
  <el-row class="tac">
    <el-col :span="20" v-if="this.videoList.length !== 0">
      <div class="player-container" v-loading="loading">
        <vue3-video-player @play="your_method" :src="this.source"></vue3-video-player>
      </div>
    </el-col>
    <el-col :span="20" v-else>
      <el-empty description="暂无视频"></el-empty>
    </el-col>
    <el-col :span="4">
      <el-menu
          default-active="0"
          class="el-menu-vertical-demo"
          :default-openeds="['1']"
          @open="handleOpen"
          @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <i class="el-icon-location"></i>
            <span>课程列表</span>
          </template>
          <el-menu-item-group v-for="(item,index) in this.videoList">
            <el-menu-item :index="index.toString()" @click="changeSource(item.path)">{{item.name}}</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
      </el-menu>
    </el-col>
  </el-row>
</template>
<script>
import request from "@/utils/request";

export default {
  name: "CourseVideo",
  computed: {
    courseId() {
      return this.$store.state.curCourseId
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.user = JSON.parse(userStr)
    this.getVideos()
  },
  data () {
    return {
      loading: false,
      user: {},
      videoList: [],
      source: 'https://media.vued.vanthink.cn/y2mate.com%20-%20sparkle_your_name_amv_K_7To_y9IAM_1080p.mp4'
    }
  },
  methods: {
    getVideos(){
      this.loading = true
      request.get('/video',{params:{courseId: this.courseId}}).then( res => {
        this.videoList = res.data
        if(this.videoList.length !== 0){
          this.source = this.videoList[0].path
        }
        this.loading = false
      })
    },
    changeSource(path){
      this.source = path
    },
    manageVideo(){
      this.$router.push('/coursePage/videoList')
    }
  }
}
</script>

<style scoped>
@media all and (max-width: 768px) {
  #app .test-player-wrap {
    width: 100%;
    height: auto;
  }
}
</style>

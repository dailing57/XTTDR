<template>
    <el-menu
        :default-active="path"
        class="el-menu-demo"
        mode="horizontal"
        router
        style="margin-bottom: 10px"
    >
      <el-menu-item index="/coursePage/video">课程</el-menu-item>
      <el-menu-item index="/coursePage/discuss">讨论</el-menu-item>
      <el-menu-item index="/coursePage/homework">作业</el-menu-item>
      <el-menu-item index="/coursePage/material">资料</el-menu-item>
    </el-menu>
    <router-view></router-view>
</template>

<script>

export default {
  name: "CoursePage",
  data() {
      return {
        path: this.$route.path
      }
  },
  created() {
  },
  methods: {

  }
}
</script>

<style scoped>

</style>

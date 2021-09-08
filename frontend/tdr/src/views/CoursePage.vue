<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="课程" name="course">课程</el-tab-pane>
    <el-tab-pane label="讨论" name="discuss">讨论</el-tab-pane>
    <el-tab-pane label="作业" name="homework">作业</el-tab-pane>
    <el-tab-pane label="资料" name="material">
      <router-view></router-view>
    </el-tab-pane>
  </el-tabs>
</template>

<script>

export default {
  name: "CoursePage",
  data() {
      return {
        activeName: 'course',
      }
  },
  methods: {
    handleClick(tab, event) {
      this.$router.push('/coursePage/'+this.activeName)
    },
  }
}
</script>

<style scoped>

</style>

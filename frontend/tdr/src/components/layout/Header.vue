<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 300px; padding-left: 30px; font-weight: bold; color: dodgerblue">学通天地人在线学习平台</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{ user.id }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="vis = true">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

  <el-dialog v-model='vis' title="个人信息">
    <el-tag style="font-size: xx-large;display: block;margin: 20px">ID:{{user.id}}</el-tag>
    <el-tag style="font-size: xx-large;display: block;margin: 20px">姓名:{{user.user.name}}</el-tag>
    <el-tag style="font-size: xx-large;display: block;margin: 20px">工号:{{user.user.workId}}</el-tag>
    <el-tag style="font-size: xx-large;display: block;margin: 20px">学院编号:{{user.schoolId}}</el-tag>
  </el-dialog>

</template>

<script>
export default {
  name: "Header",
  props: ['user'],
  data() {
    return{
      vis: false
    }
  },
  created() {

  }
}
</script>

<style scoped>

</style>

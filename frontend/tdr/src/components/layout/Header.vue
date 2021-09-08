<template>
  <div style='height: 70px; line-height: 50px; border-bottom: 1px solid #4a389b; display: flex ;background:#ffffff;'>
    <img :src = "logoUrl" height="70" width="170" />
    <div style="width: 400px;margin-top: 20px; padding-left: 30px; font-weight: bold; color:#4a389b">学通天地人&nbsp;&nbsp;在线学习平台</div>
    <div style="flex: 1"></div>
    <div style="width: 100px ;margin-top: 20px; ">
      <el-dropdown >
        <span class="el-dropdown-link" style="font-size: 20px" >
          {{ userType[user.id=="student"? 0:(user.id=="teacher"?1:2)] }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu >
            <el-dropdown-item  @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: ['user'],
  data() {
    return{
      logoUrl: require("../../assets/title.png"),
      userType: ["学生","教师","管理员"]
    }

  },
  created() {
  }
}
</script>
<style scoped>
div >>>{
  font-family: ok;
  font-size: 30px;
}
div >>> .el-dropdown{
  color: #4a389b;
}
</style>

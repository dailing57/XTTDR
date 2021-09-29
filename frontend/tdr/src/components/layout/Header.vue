<template>
  <div style='height: 70px; line-height: 50px; border-bottom: 1px solid #4a389b; display: flex ;background:#ffffff;'>
    <img :src = "logoUrl" height="70" width="170" />
    <div style="width: 400px;margin-top: 20px; padding-left: 30px; font-weight: bold; color:#4a389b">学通天地人&nbsp;&nbsp;在线学习平台</div>
    <div style="flex: 1"></div>

    <div style="width: 300px;margin-top: 30px; color:rgba(74,56,155,0.75);font-size: 20px;" @click="ch"> 欢迎登录！

      {{ user.id + "(" +usersType[user.userType=="student"? 0:(user.userType=="teacher"?1:2)] +")"}}</div>

    <div style="width: 120px ;margin-top: 20px;margin-right: 10px " >

          <el-button type="danger" icon="el-icon-back"  @click="$router.push('/login')">Exit</el-button>

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
      usersType: ["学生","教师","管理员"],

    }

  },
  created() {

  },
  methods:{
    ch(){
      console.log("Header")
      console.log(this.user)
    },
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
.el-dropdown-link {
  cursor: pointer;
}
.el-icon-arrow-down {
  font-size: 12px;
}

</style>

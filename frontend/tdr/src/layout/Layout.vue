<template>
  <div>
   <el-container>
  <el-header><Header :user="user"/></el-header>
    <el-container>

      <Aside/>

      <el-container>


        <el-main>
          <router-view style="flex: 1"/>
        </el-main>

        <el-footer>   <Footer/></el-footer>

      </el-container>

    </el-container>
      </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";

import Aside from "@/components/layout/Aside";
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";
export default {
  name: "Layout",
  components: {
    Header,
    Aside,
    Footer
  },
  data() {
    return {
      user: {}
    }
  },
  created() {
    this.refreshUser()
  },
  methods: {
    refreshUser() {
      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).id
      request.get("/account/" + userId).then(res => {
        this.user = res.data
      })
    }
  }
}
</script>

<style scoped>
@import '../assets/css/style.css';
div >>> .el-header{
  padding: 0;
}
</style>

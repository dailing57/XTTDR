<template>
  <div>
    <el-container>
      <Aside/>
      <el-container>
        <Header :user="user"/>
        <el-main>
          <router-view style="flex: 1"/>
        </el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/layout/Aside";
import Header from "@/components/layout/Header";
import request from "@/utils/request";

export default {
  name: "Layout",
  components: {
    Header,
    Aside
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
      this.user = JSON.parse(userJson)
      let userId = JSON.parse(userJson).id
    }
  }
}
</script>

<style scoped>

</style>

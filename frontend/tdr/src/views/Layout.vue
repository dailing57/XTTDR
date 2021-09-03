<template>
  <div>
    <el-container>
      <Aside/>
      <el-container>
        <Header/>
        <el-main>Main</el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "Layout",
  components: {
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>

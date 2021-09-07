<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<script>
  import { ElConfigProvider } from 'element-plus'

  import zhCn from 'element-plus/lib/locale/lang/zh-cn'
  export default {
    name: "App",
    components: {
      [ElConfigProvider.name]: ElConfigProvider,
    },
    data() {
      return {
        locale: zhCn,
      }
    },
    created() {
      if (window.localStorage.getItem("curCourseId") ) {
        this.$store.replaceState(Object.assign({}, this.$store.state,JSON.parse(window.localStorage.getItem("curCourseId"))))
      }
      window.addEventListener("beforeunload",()=>{
        window.localStorage.setItem("curCourseId",JSON.stringify(this.$store.state))
      })
    }
  }
</script>

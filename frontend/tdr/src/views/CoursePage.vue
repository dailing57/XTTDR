<template>
  <el-tag>课程编号：{{courseId}}</el-tag>
  <el-button size="mini" v-if="user.userType !== 'student'" @click="getList" style="margin-left: 10px">学生名单</el-button>
  <el-popconfirm title="确定退选这门课吗？" @confirm="deleteStudy">
    <template #reference>
      <el-button size="mini" type="danger">退选课程</el-button>
    </template>
  </el-popconfirm>
  <el-popconfirm title="确定删除这门课吗？" v-if="user.userType !== 'student'" @confirm="deleteCourse">
    <template #reference>
      <el-button size="mini" type="danger">删除课程</el-button>
    </template>
  </el-popconfirm>
  <el-dialog title="学生名单" v-model="vis" width="40%">
    <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%;margin-top: 10px;">
      <el-table-column prop="studentId" label="学生编号" width="400"> </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-popconfirm title="确定删除吗？" @confirm="deleteStudent(scope.row.studentId)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
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

import request from "@/utils/request";

export default {
  name: "CoursePage",
  data() {
      return {
        tableData:[],
        loading: false,
        vis: false,
        user: {},
        path: this.$route.path
      }
  },
  computed: {
    courseId() {
      return this.$store.state.curCourseId
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.user = JSON.parse(userStr)
  },
  methods: {
    deleteStudy(){
      request.post('/course/deleteStudy/'+this.courseId).then( res => {
        if(res.code === '0'){
          this.$router.push('/course')
          this.$message({
            type: "success",
            message: "已退出"})
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    deleteCourse(){
      request.post('/course/delete/'+this.courseId).then( res => {
        if(res.code === '0'){
          this.$router.push('/course')
          this.$message({
            type: "success",
            message: "已删除"})
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    getList(){
      this.vis = true
      this.studentList()
    },
    deleteStudent(studentId){
      request.post('/course/deleteStudent',null,{params:{courseId: this.courseId,studentId: studentId}}).then( res => {
        if(res.code === '0'){
          this.studentList()
          this.$message({
            type: "success",
            message: "已删除"})
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    studentList(){
      this.loading = true
      request.get('/course/studentList',{params:{courseId: this.courseId}}).then( res => {
        if(res.code === '0'){
          this.tableData = res.data
          this.loading = false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

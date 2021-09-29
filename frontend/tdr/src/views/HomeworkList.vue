<template>
  <el-button  icon="el-icon-back" @click="backToAll()" >作业列表</el-button>
  <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%;margin-top: 10px;">
    <el-table-column prop="id" label="学生编号" width="360"> </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="mini" @click="handleDownload(scope.row.path,scope.row.id)">查看作业</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.path,scope.row.id)">
          <template #reference>
            <el-button size="mini" type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>

    <el-table-column prop="createdTime" label="评分" width="360">
      <template #default="scope">
        <el-rate v-model="scope.row.score" allow-half style="display: inline"/>
        <el-button size="mini" type="success" @click="submitPoints(scope.row.id,scope.row.score)">提交</el-button>
      </template>
    </el-table-column>
  </el-table>

  <div style="text-align: center">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
import request from "@/utils/request";
import fileDownload from "js-file-download";

export default {
  name: "HomeworkList",
  data() {
    return {
      curScore: 0,
      loading: true,
      rate: null,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: []
    }
  },
  computed: {
    homeworkId(){
      return this.$store.state.curHomeworkId
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleDownload(path,id){
      request.get("/files/download/"+path,{responseType: 'blob'}).then(res => {
        fileDownload(res,path)
      }).catch(rse=>{
        console.log('download error')
      })
    },
    handleDelete(path,id){
      request.post("/homework/deleteSubmit",null,{params:{homeworkId: this.homeworkId,path: path,id: id}}).then( res => {
        if(res.code === '0'){
          this.$message({
            type: "success",
            message: "删除成功"})
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()
      })
    },
    submitPoints(id,score){
      request.post('/homework/submitPoints',null,{
        params:{
          homeworkId: this.homeworkId,
          id: id,
          score: score
        }
      }).then( res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "成功评分"
            })
            this.fileList=[]
          } else {
            this.$message({
              type: "error",
              message: res.msg
      })}
    })},
    load() {
      this.loading = true
      console.log("HOMEWORKID:"+this.homeworkId)
      request.get("/homework/submittedHomework",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          homeworkId: this.homeworkId,

        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
    backToAll(){
      this.$router.push({
        name: '/coursePage/homework',

      })
    },
  }
}
</script>

<style scoped>

</style>

<template>
  <el-button type="primary" @click="this.dialogVisible = true" style="margin-right: 10px">添加用户</el-button>
  <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%;margin-top: 10px;">
    <el-table-column prop="name" label="学院名称" width="360"> </el-table-column>
    <el-table-column prop="schoolId" label="学院编号"> </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.schoolId)">
          <template #reference>
            <el-button size="mini" type="danger">删除</el-button>
          </template>
        </el-popconfirm>
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

  <el-dialog title="添加学院" v-model="dialogVisible" width="30%">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="学院编号">
        <el-input v-model="form.schoolId"></el-input>
      </el-form-item>
      <el-form-item label="学院名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-button type="primary" @click="handleadd">立即创建</el-button>
    </el-form>
  </el-dialog>

</template>

<script>
import request from "@/utils/request";
import fileDownload from "js-file-download";
import E from 'wangeditor'
import {h} from "vue";

let editor
export default {
  name: "CourseHomework",
  data() {
    return {
      fileList: [],
      uploadFileList: [],
      user: {},
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      ids: [],
      vis: false,
      detail: {}
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.user = JSON.parse(userStr)
    this.load()
  },
  computed: {
  },
  methods: {
    handleadd(){
      request.post('/manage/school/add',null,{params:{schoolId:this.form.schoolId, workId: this.form.workId, name:this.form.name}}).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "新增成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.dialogVisible = false
        this.load()
      })
    },
    handleDelete(id){
      request.post('/manage/school/delete/'+id).then(res => {
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
            this.load() // 刷新表格的数据
          }
      )
    },
    load() {
      this.loading = true
      request.get("/manage/school/all",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
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
    }
  }
}
</script>

<style scoped>

</style>

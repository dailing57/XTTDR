<template>
  <el-button type="primary" @click="handleadd" v-if="user.userType !== 'student'">添加作业</el-button>

  <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%;margin-top: 10px;">
    <el-table-column prop="name" label="作业名称" width="360"> </el-table-column>
    <el-table-column prop="createdTime" label="起始日期" width="360"> </el-table-column>
    <el-table-column prop="deadline" label="结束日期"> </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="mini" type="success" @click="details(scope.row)">查看</el-button>
        <el-button size="mini" v-if="user.userType !== 'student'" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button size="mini" v-if="user.userType !== 'student'" @click="homeworkList(scope.row.homeworkId)">批阅</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.homeworkId)" v-if="user.userType !== 'student'">
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

  <el-dialog title="添加作业" v-model="dialogVisible" width="80%">
    <el-form :model="form" label-width="120px">
      <el-form-item label="作业名称">
        <el-input v-model="form.name" style="width: 20%"></el-input>
      </el-form-item>
      <el-form-item label="起始时间">
        <el-date-picker v-model="form.createdTime" value-format="YYYY-MM-DD" type="date" style="width: 20%" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="截止时间">
        <el-date-picker v-model="form.deadline" value-format="YYYY-MM-DD" type="date" style="width: 20%" clearable></el-date-picker>
      </el-form-item>
      <div id="div1"></div>
    </el-form>
    <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
    </template>
  </el-dialog>

  <el-dialog title="详情" v-model="vis" width="50%">
    <el-upload
        class="upload-demo"
        ref="upload"
        multiple
        action=""
        :limit="1"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :http-request="httpRequest"
        :file-list="fileList"
        :auto-upload="false"
    >
      <template #trigger>
        <el-button size="small" type="primary">选取文件</el-button>
      </template>
      <el-button
          style="margin-left: 10px;"
          size="small"
          type="success"
          @click="submitUpload"
      >提交上传
      </el-button>
    </el-upload>
    <el-card style="margin-top: 10px">
      <div v-html="detail.content" style="min-height: 100px"></div>
    </el-card>
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
      filesUploadUrl: "http://localhost:9090/homework/submit",
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
    courseId() {
      return this.$store.state.curCourseId
    }
  },
  methods: {
    homeworkList(homeworkId){
      this.$store.commit('setHomeworkId',homeworkId)
      this.$router.push({
        name: 'homeworkList',
        params: {
          homeworkId: homeworkId
        }
      })
    },
    details(row) {
      this.detail = row
      this.vis = true
    },
    httpRequest(param) {
      console.log(param)
      let fileObj = param.file // 相当于input里取得的files
      let fd = new FormData()// FormData 对象
      fd.append('file', fileObj)// 文件对象
      fd.append('homeworkId', this.detail.homeworkId)
      fd.append('studentId',this.user.id)
      let url = this.filesUploadUrl
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      request.post(url, fd, config).then(res=>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "提交成功"
          })
          this.fileList=[]
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    handleRemove(file, fileList) {
      this.fileList=fileList
    },
    handlePreview(file) {
      console.log(file)
    },
    handleadd(){
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个 editor对象
        if (!editor) {
          editor = new E('#div1')
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/files/editor/upload'
          editor.config.uploadFileName = "file"  // 设置上传参数名称
          editor.create()
        }
      })
    },
    save() {
      this.form.content = editor.txt.html()  // 获取 编辑器里面的值，然后赋予到实体当中
      if (this.form.homeworkId) {  // 更新
        request.post("/homework/update", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      } else {  // 新增
        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr)
        this.form.teacherId = user.id
        this.form.courseId = this.courseId
        request.post("/homework/add", this.form).then(res => {
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
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        if (!editor) {
          editor = new E('#div1')
          editor.config.uploadImgServer = 'http://localhost:9090/files/editor/upload'
          editor.config.uploadFileName = "file"  // 设置上传参数名称
          editor.create()
        }
        editor.txt.html(row.content)
      })
    },
    handleDelete(homeworkId){
      request.post('/homework/delete/'+homeworkId).then(res => {
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
      request.get("/homework",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          courseId: this.courseId
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

<template>
  <el-upload
      v-if="user.userType !== 'student'"
      class="upload-demo"
      ref="upload"
      multiple
      action=""
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :on-change="handleChange"
      :http-request="httpRequest"
      :file-list="fileList"
      :auto-upload="false"
      :disabled="user.userType=='student'? true : false"
  >
    <template #trigger>
      <el-button size="small" type="primary" >
       选取文件</el-button>
    </template>
    <el-button
        style="margin-left: 10px;"
        size="small"
        type="success"
        :disabled="fileList.length == 0 ? true : false"
        @click="submitUpload"
    >上传文件{{fileList.length}}
    </el-button>
  </el-upload>
  <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%;margin-top: 10px; height: 60vh">
    <el-table-column prop="name" align="center" label="文件名" width="360"> </el-table-column>
    <el-table-column prop="createdTime" align="center" label="日期" width="360" :formatter="dateFormat"> </el-table-column>
    <el-table-column prop="teacherId" align="center" label="上传者"> </el-table-column>
    <el-table-column align="center" label="操作">
      <template #default="scope">
        <el-button size="mini" @click="handleDownload(scope.row.courseId,scope.row.name)">下载</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.materialId)" v-if="user.userType !== 'student'">
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

</template>

<script>
import request from "@/utils/request";
import fileDownload from "js-file-download";

export default {
  name: "CourseMaterial",
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
      isSelect: true
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
    httpRequest(param) {
      console.log(param)
      let fileObj = param.file // 相当于input里取得的files
      let fd = new FormData()// FormData 对象
      fd.append('file', fileObj)// 文件对象
      fd.append('courseId', this.courseId)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      request.post('/courseMaterial/add', fd, config).then(res=>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "新增成功"
          })
          this.fileList=[]
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load() // 刷新表格的数据
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
    handleChange(file, fileList) {
      this.fileList = fileList
    },
    handleDownload(path,fileName){
      request.get('/files/download/'+path+fileName, {responseType: 'blob'}).then(res => {
        fileDownload(res, fileName);
      }).catch((res)=>{
        console.log('download error');
        }
      )
    },
    handleDelete(id){
      request.post('/courseMaterial/delete/'+id).then(res => {
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
      request.get("/courseMaterial/"+this.courseId, {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
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
    dateFormat(row,column){
      var t=new Date(row.createdTime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      var year=t.getFullYear(),
          month=t.getMonth()+1,
          day=t.getDate(),
          hour=t.getHours(),
          min=t.getMinutes(),
          sec=t.getSeconds();
      var newTime=year+'-'+
          (month<10?'0'+month:month)+'-'+
          (day<10?'0'+day:day)+' '+
          (hour<10?'0'+hour:hour)+':'+
          (min<10?'0'+min:min)+':'+
          (sec<10?'0'+sec:sec);
      return newTime;
      },
  }
}
</script>

<style scoped>

</style>

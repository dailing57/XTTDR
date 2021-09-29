<template>
  <el-button  icon="el-icon-back" @click="backToAll()" >返回课程视频</el-button>
  <el-button type="success" @click="upload">上传视频</el-button>
  <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%;margin-top: 10px;">
    <el-table-column prop="name" label="视频名称" width="360"> </el-table-column>
    <el-table-column prop="createdTime" label="日期" width="360" :formatter="dateFormat"> </el-table-column>
    <el-table-column prop="seq" label="播放顺序" width="360"> </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.videoId)">
          <template #reference>
            <el-button size="mini" type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog title="添加视频" v-model="vis" width="50%">
    <el-upload
        class="upload-demo"
        ref="upload"
        multiple
        action=""
        :limit="1"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :http-request="httpRequest"
        :before-upload="beforeVideoUpload"
        :on-change="handleChange"
        :file-list="fileList"
        :auto-upload="false"
    >
      <template #trigger>
        <el-button size="small" type="primary">选取文件</el-button>
      </template>
      <el-form :model="form" label-width="120px" style="margin-top: 10px">
        <el-form-item label="视频名称">
          <el-input v-model="form.name" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="播放次序">
          <el-input v-model="form.order" style="width: 80%"></el-input>
        </el-form-item>
        <div id="div1"></div>
      </el-form>
      <span class="dialog-footer">
        <el-button @click="vis = false">取 消</el-button>
        <el-button type="primary" @click="save" :disabled=this.dialogVisible >确 定</el-button>
      </span>
    </el-upload>
  </el-dialog>


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
  name: "VideoList",
  data() {
    return {
      form: {},
      vis: false,
      loading: true,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      dialogVisible:true
    }
  },
  computed: {
    courseId(){
      return this.$store.state.curCourseId
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleDelete(id){
      request.post("/video/delete",null,{params:{videoId: id}}).then( res => {
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
    upload(){
      this.vis = true
    },
    handleRemove(file, fileList) {
      this.fileList=fileList
      this.dialogVisible = fileList.length == 0 ? true : false
    },
    handlePreview(file) {
      console.log(file)
    },
    handleChange(file, fileList) {
      this.fileList = fileList
      this.dialogVisible = fileList.length == 0 ? true : false
    },
    beforeVideoUpload(file) {
      const isMP4 = file.type === 'video/mp4'
      if (!isMP4) {
        this.$message.error('上传视频只能是 MP4 格式!')
      }
      return isMP4
    },
    httpRequest(param) {
      let fileObj = param.file // 相当于input里取得的files
      let fd = new FormData()// FormData 对象
      fd.append('file', fileObj)// 文件对象
      fd.append('courseId', this.courseId)
      if((!this.form.name) || (!this.form.order)){
        this.$message({
          type: "warning",
          message: "请填写内容"
        });
        return;
      }
      fd.append('name',this.form.name)
      fd.append('order',this.form.order)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      request.post('/video/add', fd, config).then(res=>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "提交成功"
          })
          this.fileList=[]
          this.load()//0920更改，上传后刷新列表
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    save(){
      this.$refs.upload.submit()
      this.vis = false

    },
    load() {
      this.loading = true
      request.get("/video/list",{
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
    },
    dateFormat(row,column){
      var t=new Date(row.createdTime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      var year=t.getFullYear(),
          month=t.getMonth()+1,
          day=t.getDate();
      var newTime=year+'-'+
          (month<10?'0'+month:month)+'-'+
          (day<10?'0'+day:day);
      return newTime;
    },
    backToAll(){
      this.$router.push({
        name: '/coursePage/video',
        params: {
          examId: this.examId
        }
      })
    },
  },

}
</script>

<style scoped>

</style>

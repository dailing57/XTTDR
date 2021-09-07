<template>
  <el-upload
      class="upload-demo"
      ref="upload"
      action="https://jsonplaceholder.typicode.com/posts/"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
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
    >上传文件</el-button
    >
  </el-upload>

  <el-table :data="tableData" border stripe style="width: 100%;margin-top: 10px">
    <el-table-column prop="createdTime" label="日期" width="360"> </el-table-column>
    <el-table-column prop="materialId" label="编号" width="360"> </el-table-column>
    <el-table-column prop="teacherId" label="上传者"> </el-table-column>
    <el-table-column label="操作"> </el-table-column>

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

export default {
  name: "CourseMaterial",
  data() {
    return {
      fileList: [],
      user: {},
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      //filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
      ids: []
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.load()
  },
  computed: {
    courseId() {
      return this.$store.state.curCourseId
    }
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit()
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
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
    }
  }
}
</script>

<style scoped>

</style>

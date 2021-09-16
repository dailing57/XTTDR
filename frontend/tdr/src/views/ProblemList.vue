<template>
  <el-button size="mini" type="primary" style="margin-right: 10px" @click="getinpaper">加入试卷</el-button>
  <el-upload
      v-if="user.userType !== 'student'"
      class="upload-demo"
      ref="upload"
      multiple
      action=""
      style="display: inline"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :http-request="httpRequest"
      :file-list="fileList"
      :auto-upload="false"
  >
    <template #trigger>
      <el-button size="mini" type="primary">选取文件</el-button>
    </template>
    <el-button
        style="margin-left: 10px;"
        size="mini"
        type="success"
        @click="submitUpload"
    >上传文件
    </el-button>
  </el-upload>
  <el-card  v-loading="this.loading" style="margin-top: 10px">
    <el-tag style="font-size: large">题库题目：</el-tag>
    <el-table
        v-loading="loading"
        :data="problems"
        border
        stripe
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="describes"
          label="题目描述">
      </el-table-column>
      <el-table-column label="操作" width="80">
        <template #default="scope">
          <el-popconfirm title="确定删除吗？" @confirm="deleteProblem(scope.row.problemId)" v-if="user.role !== 'student'">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
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

  <el-card style="margin-top: 10px">
    <el-form
        :model="form"
        :rules="rules"
        ref="form"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="题目描述" prop="describes">
        <el-input type="textarea" style="font-size: larger" v-model="form.describes"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="6">
          <el-form-item label="A:" prop="optionA">
            <el-input v-model="form.optionA"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="B:" prop="optionB">
            <el-input v-model="form.optionB"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="C:" prop="optionC">
            <el-input v-model="form.optionC"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="D:" prop="optionD">
            <el-input v-model="form.optionD"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="答案" prop="answer">
        <el-radio-group v-model="form.answer">
          <el-radio label="A"></el-radio>
          <el-radio label="B"></el-radio>
          <el-radio label="C"></el-radio>
          <el-radio label="D"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="答案解析" prop="parse">
        <el-input type="textarea" style="font-size: larger" v-model="form.parse"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="submitForm('form')">创建题目</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "ProblemList",
  computed:{
    examId() {
      return this.$store.state.curExamId
    }
  },
  data() {
    return {
      loading: false,
      problems:[],
      form: {},
      rules: {
        describes:[{ required: true, message: '请输入题目描述', trigger: 'blur' }],
        optionA: [{ required: true, message: '请填写A选项内容', trigger: 'blur' }],
        optionB: [{ required: true, message: '请填写B选项内容', trigger: 'blur' }],
        optionC: [{ required: true, message: '请填写C选项内容', trigger: 'blur' }],
        optionD: [{ required: true, message: '请填写D选项内容', trigger: 'blur' }],
        answer: [{ required: true, message: '请设置正确答案', trigger: 'change' }],
        parse: [{ required: true, message: '请输入答案解析', trigger: 'blur' }]
      },
      currentPage: 1,
      pageSize: 10,
      total: 0,
      fileList: [],
      uploadFileList: [],
      user: {},
      checked: []
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.user = JSON.parse(userStr)
    this.loadProblems();
  },
  methods:{
    getinpaper(){
      request.post('/exam/paper/addProblems',this.ids,{params:{examId: this.examId}}).then(res => {
        if(res.code==='0'){
          this.$message({
            message: '题目成功导入试卷',
            type: 'success'
          })
        }
        else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(v => v.problemId)   // [{id,name}, {id,name}] => [id,id]
    },
    loadProblems() {
      this.loading = true
      request.get("/exam/problems/teacher",{//后端相关
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }}).then(res => {
        this.problems = res.data.records
        this.total = res.data.total
        this.loading = false
      })
    },
    submitForm(form) {
      this.$refs['form'].validate(valid => {
        if(valid){
          this.form.examId = this.examId
          request.post("/exam/problems/add",this.form,{params:{examId:this.examId}} ).then(res => {
            if (res.code === '0') {
              this.$message({
                message: "添加成功",
                type: "success"
              });
            } else {
              this.$message({
                message: res.msg,
                type: "error"
              });
            }
            this.form = {}
            this.loadProblems();
          })
        }
        else{
          alert('请填写必要内容')
        }
      })
    },
    deleteProblem(id) {
      request.post("/exam/problems/delete" ,null,{params:{problemId: id}}).then(res => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.loadProblems()
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.loadProblems()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.loadProblems()
    },
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
      request.post('/exam/problems/import', fd, config).then(res=>{
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
    problemBase(){
      this.$router.push('/ProblemList')
    }
  }

}
</script>

<style scoped>

</style>

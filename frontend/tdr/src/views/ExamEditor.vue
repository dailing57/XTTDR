<template>
  <el-button size="mini" type="primary" style="margin-right: 10px" @click="handout">发布试卷</el-button>
  <el-button size="mini" type="success" @click="problemBase">进入题库</el-button>
  <el-tag>考试编号：{{this.examId}}</el-tag>
  <el-card  v-loading="this.loading" style="margin-top: 10px">
    <el-tag style="font-size: large">现有题目：</el-tag>
    <el-card v-for="item in problems" style="font-size: larger;margin-top: 10px">
      <el-row>
        <el-button type="danger" size="mini" @click="deleteProblem(item.problemId)">删除</el-button>
      </el-row>
      <el-row>
        {{item.describes}}
      </el-row>
      <el-row>
        <el-col :span="6">A {{item.optionA}}</el-col>
        <el-col :span="6">B {{item.optionB}}</el-col>
        <el-col :span="6">C {{item.optionC}}</el-col>
        <el-col :span="6">D {{item.optionD}}</el-col>
      </el-row>
      <el-row style="margin-top:10px;">
        <el-tag style="font-size: medium;margin-right: 10px">答案：</el-tag>{{item.answer}}
      </el-row>
      <el-row style="margin-top:10px;">
        <el-tag type="success" style="font-size: medium;margin-right: 10px">解析：</el-tag>{{item.parse}}
      </el-row>
    </el-card>
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
  name: "ExamEditor",
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
      user: {},
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.user = JSON.parse(userStr)
    this.loadProblems();
  },
  methods:{
    handout(){
      request.post("/exam/handout",null,{params:{examId: this.examId}}).then( res => {
        if(res.code === '0'){
          this.$message({
            message: '考卷发布成功',
            type: 'success'
          })
          this.$router.push('/exam')
        }
        else{
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    loadProblems() {
      this.loading = true
      request.get("/exam/problems/exam",{//后端相关
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          examId: this.examId
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
      request.post("/exam/paper/delete" ,null,{params:{examId:this.examId,problemId:id}}).then(res => {
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
    problemBase(){
      this.$router.push('/ProblemList')
    }
  }

}
</script>

<style scoped>

</style>

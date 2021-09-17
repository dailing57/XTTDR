<template>
  <el-button v-if="user.userType!=='student'" style="margin: 10px 0;padding: 0 10px;" type="primary" @click="this.dialogVisible = true">新建考试</el-button>
  <el-table :data="tableData"  v-loading="loading" stripe style="width: 100%; height: 55vh">
    <el-table-column prop="name" align="center" label="考试名" width="240"> </el-table-column>
    <el-table-column prop="courseId" align="center" label="考试课程" width="240"> </el-table-column>
    <el-table-column prop="begintime" align="center" label="开考时间" width="240" :formatter="dateFormat"> </el-table-column>
    <el-table-column prop="lasttime" align="center" label="考试时长" width="90" > </el-table-column>
    <el-table-column prop="teacherId" align="center" label="创建者"> </el-table-column>
    <el-table-column align="center" label="操作">
      <template #default="scope">
        <el-button  type="primary" size="mini" @click="enterExam(scope.row)">考试</el-button>
        <el-button size="mini" v-if="user.userType !== 'student' " @click="editPaper(scope.row.examId)">编辑</el-button>
        <el-button size="mini" @click="getScore(scope.row.examId)">结果</el-button>
        <el-popconfirm title="确定删除吗？"  @confirm="handleDelete(scope.row.examId)" v-if="user.userType !== 'student'">
          <template #reference>
            <el-button  type="danger" size="mini">删除</el-button>
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
  <el-dialog title="新建考试" v-model="dialogVisible" width="30%">
    <el-form :model="form" label-width="120px">
      <el-form-item label="课程编号">
        <el-input v-model="form.courseId" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="考试名称">
        <el-input v-model="form.name" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="开考时间">
        <el-date-picker
            v-model="form.begintime"
            type="datetime"
            placeholder="选择日期时间"
            :default-time="this.defaultTime"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="考试时长">
        <el-select v-model="form.lasttime" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog title="考试结果" v-model="vis" width="10%">
    <el-progress type="dashboard" :percentage="this.score" :color="colors"></el-progress>
  </el-dialog>
</template>

<script>
import fileDownload from "js-file-download";
import request from "@/utils/request";

export default {
  name: "Exam",
  data() {
    return {
      colors: [
        {color: '#f56c6c', percentage: 20},
        {color: '#e6a23c', percentage: 40},
        {color: '#5cb87a', percentage: 60},
        {color: '#1989fa', percentage: 80},
        {color: '#6f7ad3', percentage: 100}
      ],
      options: [
        {
          value: 1,
          label: '1h',
        },
        {
          value: 1.5,
          label: '1.5h',
        },
        {
          value: 2,
          label: '2h',
        },
        {
          value: 2.5,
          label: '2.5h',
        },

      ],
      score: 0,
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
      isSelect: true,
      courseList:[],//这个是打算记录当前用户拥有课程的数组
      vis: false
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")
    this.user = JSON.parse(userStr)
    this.load()
  },
  computed: {
    examId() {
      return this.$store.state.curExamId
    }
  },
  methods: {
    getScore(id){
      request.get('/exam/doExams/score',{params:{examId: id}}).then( res => {
        this.score = res.data
        if(res.code === '-1'){
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
        else{
          if(this.score === -1){
            this.$message({
              message: '您还未参与考试',
              type: 'error'
            })
          }
          else{
            this.vis = true
          }
        }
      })
    },
    handleDelete(id){//删除考试
      request.post('/exam/delete/'+id).then(res => {
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
    editPaper(examId){
      console.log(examId)
      this.$store.commit('setExamId',examId)
      this.$router.push({
        name: 'editExam',
        params: {
          examId: examId
        }
      })
    },
    enterExam(row){
      request.get('/exam/doExams/score',{params:{examId: row.examId}}).then( res => {
        this.score = res.data
        if(res.code === '-1'){
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
        else{
          if(this.score !== -1){
            this.$message({
              message: '您已参与此考试',
              type: 'error'
            })
          }
          else{
            this.$store.commit('setExamId',row.examId)
            let begintime = row.begintime;
            let lasttime = row.lasttime;
            this.$router.push({
              name: 'doExam',
              params: {
                examId: row.examId,
                lasttime:lasttime,
                begintime: begintime
              }
            })
          }
        }
      })
    },
    save() {
      request.post("/exam/add",this.form).then(res => {
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
        this.load()
        this.dialogVisible = false
      })
    },
    load() {
      this.loading = true
      if(this.user.userType === 'student'){
        request.get("/exam/examList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
          }
        }).then(res => {
          this.loading = false
          this.tableData = res.data.records
          this.total = res.data.total
          console.log(this.tableData)
        })
      }
      else{
        request.get("/exam/all", {
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
      }

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
    dateFormat2(curTime){
      var t=new Date(curTime);//row 表示一行数据, updateTime 表示要格式化的字段名称
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

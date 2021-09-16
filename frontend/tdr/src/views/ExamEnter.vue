<template>
  <el-row :gutter="20">
    <el-col :span="3">
      <el-progress type="circle" :percentage="percentage" :color="customColors" style="margin-left: 10px;margin-top:10px;"></el-progress>
      <el-tag style="margin-left: 10px"><i class="el-icon-time" ></i> 剩余时间：{{this.h}}:{{this.m}}:{{this.s}}</el-tag>
    </el-col>
    <el-col :span="21">
      <el-card>
        <el-form
            :model="form"
            ref="form"
            label-width="100px"
        >
          <el-button type="primary" v-if="this.curtime>0" @click="submitForm('form')">提交试卷</el-button>
          <el-card v-for="(item,index) in problems" :key='index' style="font-size: larger;margin-top: 10px">
            <el-row>
              <div style="margin-left: 60px">
                第{{index+1}}题：{{item.describes}}
              </div>
            </el-row>
            <el-row style="margin-top: 10px">
              <el-form-item label="选项" prop="answer">
                <el-radio-group v-model="item.answer">
                  <el-radio label="A">A {{item.optionA}}</el-radio>
                  <el-radio label="B">B {{item.optionB}}</el-radio>
                  <el-radio label="C">C {{item.optionC}}</el-radio>
                  <el-radio label="D">D {{item.optionD}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-row>
          </el-card>
        </el-form>
      </el-card>
    </el-col>
  </el-row>


</template>

<script>
import request from "@/utils/request";

export default {
  name: "ExamEnter",
  data() {
    return {
      form: {},
      problems: [],
      percentage: 100,
      lasttime: 3600,
      begintime: 0,
      curtime: 0,
      customColors: [{
        color: '#f56c6c',
        percentage: 20
      },
        {
          color: '#e6a23c',
          percentage: 40
        },
        {
          color: '#5cb87a',
          percentage: 60
        },
        {
          color: '#1989fa',
          percentage: 80
        },
        {
          color: '#6f7ad3',
          percentage: 100
        }
      ],
      h: 0,
      m: 0,
      s: 0,
      loading: false,
    };
  },
  created() {
    this.load()
    this.decrease()
    this.loadProblem()
  },
  computed:{
    examId() {
      return this.$store.state.curExamId
    }
  },
  methods: {
    decrease() {
      let me = this;
      let interval = window.setInterval(function () {
        me.percentage = Number((me.curtime / me.lasttime) * 100).toFixed(0);
        --me.curtime;
        me.h = Math.floor(me.curtime/3600)
        me.m = Math.floor(me.curtime/60%60)
        me.s = Math.floor(me.curtime%60)
        if (me.curtime < 0) {
          me.percentage = 0;
          me.curtime = 0;
          window.clearInterval(interval);
        }
      }, 1000);
    },
    load() {
      request.get('exam/id/' + this.examId).then(res => {
        this.begintime = Date.parse(res.data.begintime)
        this.lasttime = res.data.lasttime * 3600
        this.curtime = this.lasttime - ((new Date() - this.begintime) / 1000)
        if (this.curtime > this.lasttime) {
          alert('考试时间已超过')
        }
      })
    },
    loadProblem() {
      this.loading = true
      request.get("/exam/problems/doexam", {
        params: {
          examId: this.examId
        }
      }).then(res => {
        this.problems = res.data
        this.loading = false
      })
    },
    submitForm(form) {
      let answer = []
      for(let item of this.problems){
        answer.push(item.answer)
      }
      request.post('/exam/doExams/judgeScore',answer,{params:{examId: this.examId}}).then( res => {
        if(res.code === '0'){
          this.$message({
            message: '已交卷',
            type: 'success'
          })
          this.$router.push('exam')
        }
        else{
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

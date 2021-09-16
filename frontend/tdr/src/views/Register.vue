<template>
  <div style="width: 100%; height: 100vh; overflow: hidden">
    <div style="width: 400px; margin: 100px auto">
      <div style="font-size: 30px; text-align: center; padding: 30px 0">欢迎注册</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="id" label="注册ID">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item prop="workId" label="工号">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.workId"></el-input>
        </el-form-item>
        <el-form-item prop="pwd" label="密码">
          <el-input prefix-icon="el-icon-lock" v-model="form.pwd" show-password></el-input>
        </el-form-item>
        <el-form-item prop="confirm" label="确认密码">
          <el-input prefix-icon="el-icon-lock" v-model="form.confirm" show-password></el-input>
        </el-form-item>

        <div style="text-align: center;margin-bottom: 20px">
          <el-radio-group v-model="form.userType">
            <el-radio-button label="student">学生</el-radio-button>
            <el-radio-button label="teacher">教师</el-radio-button>
            <el-radio-button label="admin">管理员</el-radio-button>
          </el-radio-group>
        </div>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",
  data() {
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    register() {

      if (this.form.pwd !== this.form.confirm) {
        this.$message({
          type: "error",
          message: '2次密码输入不一致！'
        })
        return
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          let account = {
            id: this.form.id,
            pwd: this.form.pwd,
            userType: this.userType,
            user: {
              id: this.form.id,
              workId: this.form.workId,
              name: '',
              schoolId: '',
              avatar: '',
            }
          }
          request.post("/register", account).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功"
              })
              this.$router.push("/login")  //登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

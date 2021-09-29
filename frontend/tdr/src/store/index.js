import { createStore } from 'vuex'

export default createStore({
  state: {
    user: {},
    curCourseId: '',
    curHomeworkId: '',
    curExamId: '',
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setCourseId(state,courseId){
      state.curCourseId=courseId
    },
    setHomeworkId(state,homeworkId){
      state.curHomeworkId=homeworkId
    },
    setExamId(state,examId){
      state.curExamId=examId
    },

  },
  actions: {
    SET_USER({commit}, user) {
      this.state.user = user
    }
  },
  getters: {
    getUser: (state) => state.user
  }
})

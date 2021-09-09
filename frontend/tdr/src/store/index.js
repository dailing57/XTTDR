import { createStore } from 'vuex'

export default createStore({
  state: {
    user: {},
    curCourseId: ''
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setCourseId(state,courseId){
      state.curCourseId=courseId
    }
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

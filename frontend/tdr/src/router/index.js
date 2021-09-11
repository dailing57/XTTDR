import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/layout/Layout";
import * as path from "path";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/course",
    children: [
      {
        path: '/course',
        name: 'course',
        component: () => import('@/views/Course')
      },
      {
        path: '/coursePage',
        name: '/coursePage',
        component: () => import('@/views/CoursePage'),
        redirect: "/coursePage/video",
        children: [
          {
            path: '/coursePage/material',
            name: '/coursePage/material',
            component: ()=>import('@/views/CourseMaterial')
          },
          {
            path: '/coursePage/homework',
            name: '/coursePage/homework',
            component: () => import('@/views/CourseHomework')
          },
          {
            path: '/coursePage/video',
            name: '/coursePage/video',
            component: () => import('@/views/CourseVideo')
          },
          {
            path: '/coursePage/discuss',
            name: '/coursePage/discuss',
            component: () => import('@/views/CourseDiscuss')
          },
          {
            path: '/coursePage/homeworkList',
            name: 'homeworkList',
            component: () => import('@/views/HomeworkList')
          }
        ]
      }
    ]
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 限制某些页面禁止未登录访问
let limitPagePath = ["/course"]

router.beforeEach((to, from, next) => {
  if (limitPagePath.includes(to.path)) {
    // 判断sessionStorage是否保存了用户信息
    let userStr = sessionStorage.getItem("user") || "{}"
    let user = JSON.parse(userStr)
    if (!user.id) {
      // 跳转到登录页面
      next({path: "/login"})
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router

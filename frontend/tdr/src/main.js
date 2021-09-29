import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//import '../theme/index.css'
 import ElementPlus from 'element-plus';
 import 'element-plus/dist/index.css'

import '../src/assets/style/theme/index.css'

import '@/assets/css/global.css'
import Vue3VideoPlayer from '@cloudgeek/vue3-video-player'
import '@cloudgeek/vue3-video-player/dist/vue3-video-player.css'

import * as echarts from 'echarts'
const app = createApp(App)
    .use(store)
    .use(router)
    .use(ElementPlus, {size: 'small' })
	  .use(Vue3VideoPlayer, {lang: 'zh-CN'})
    .mount('#app')
app.echarts = echarts



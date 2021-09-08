import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//import '../theme/index.css'
 import ElementPlus from 'element-plus';
 import 'element-plus/dist/index.css'

import '../src/assets/style/theme/index.css'

import '@/assets/css/global.css'

const app = createApp(App)
    .use(store)
    .use(router)
    .use(ElementPlus, {size: 'small' })
    .mount('#app')


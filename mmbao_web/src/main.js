import './assets/css/bootstrap.min.css'
import './assets/css/animate.min.css'
import './assets/css/remixicon.css'
import './assets/css/flaticon.css'
import './assets/css/jquery-ui.min.css'
import './assets/css/magnific-popup.min.css'
import './assets/css/simplebar.min.css'
import './assets/css/metismenu.min.css'
import './assets/css/owl.carousel.min.css'
import './assets/css/owl.theme.default.min.css'
import './assets/css/style.css'
import './assets/css/responsive.css'
// 导入 element-plus样式表
import 'element-plus/dist/index.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import persistedstate from "pinia-plugin-persistedstate"

let app = createApp(App)

//在vue的全局参数中 配置第三方依赖库

// 导入 Element-Plus插件
import ElementPlus from 'element-plus'
//element-plus 全局 国际化插件
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
//element-plus 图标插件
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const pinia = createPinia();
pinia.use(persistedstate);
app.use(pinia)

app.use(router)

//配置ElementPlus插件给app实例，并配置国际化
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus, {
    locale: zhCn,
})

app.mount('#app')

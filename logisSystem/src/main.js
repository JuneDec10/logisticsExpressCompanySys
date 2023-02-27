import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/global.css'
import  './assets/css/base.css'
import mitt from 'mitt'
import store from "./store";
import VueCookies from 'vue-cookies'
//导入Svg图片插件，可以在页面上显示Svg图片
import 'virtual:svg-icons-register'
import SvgIcon from "./components/SvgIcon.vue";
import axios from "axios";
import VDistpicker from 'v-distpicker'

const app=createApp(App)
app.config.globalProperties.emitter = mitt()
app.config.globalProperties.$axios = axios
app.config.globalProperties.$cookies = VueCookies
app.config.warnHandler = () => null

axios.defaults.baseURL = "http://localhost:8081"
app.use(router).use(store).use(ElementPlus).component('v-distpicker', VDistpicker).component('SvgIcon',SvgIcon).mount('#app')

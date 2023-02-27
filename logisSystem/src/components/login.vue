<template>
  <div class="body">
    <div class="main">
      <div class="switch" id="switch-cnt">
        <div class="switch__circle"></div>
        <div class="switch__circle switch__circle--t"></div>
        <div class="switch__container" id="switch-c1">
          <h2 class="switch__title title">欢迎!</h2>
          <p class="switch__description description">已有帐号，现在登陆？</p>
          <button class="switch__button button switch-btn" @click="change">用户登陆</button>
          <button class="switch__button button switch-btn" @click="toBack">后台登陆</button>
        </div>
        <div class="switch__container is-hidden" id="switch-c2">
          <h2 class="switch__title title">您好！</h2>
          <p class="switch__description description">没有账号，现在注册？</p>
          <button class="switch__button button switch-btn" @click="change">注册</button>
        </div>
      </div>
<!--      signup-->
      <div class="container a-container" id="a-container">
        <el-form class="form" id="a-form" method="" action="">
          <h2 class="form_title title">注册</h2>
          <el-input class="form__input" type="text" placeholder="姓名"/>
          <br>
          <el-input class="form__input" type="text" placeholder="邮箱"/>
          <br>
          <el-input class="form__input" type="password" placeholder="密码"/>
          <button class="form__button button submit">注册</button>
        </el-form>
      </div>
<!--      signin-->
      <div class="container b-container" id="b-container">
        <el-form :model="foreuser" :rules="rules" ref="ruleFormRef" class="form" id="b-form" method="" action="#">
          <h2 class="form_title title">登陆</h2>
          <el-form-item prop="mobile">
            <el-input class="form__input" type="text" placeholder="电话号码" v-model="foreuser.mobile" />
          </el-form-item>
          <br>
          <el-form-item prop="password">
            <el-input class="form__input" type="password" placeholder="密码" v-model="foreuser.password" show-password/>
          </el-form-item>
           <a class="form__link">忘记密码?</a>
          <el-form-item>
            <el-button class="form__button button submit" @click="login">登陆</el-button>
          </el-form-item>
          </el-form>
      </div>
    </div>
  </div>
</template>

<script  lang="ts" setup>

import {ref, onMounted, reactive, getCurrentInstance} from 'vue'
import useEmitter from '../hooks/useEmitter.js'
import {useCookies} from "@vueuse/integrations/useCookies"
import {useRouter} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";
import request from "../../src/request";

const cookie = useCookies()
const{proxy } = getCurrentInstance()
let foreuser = reactive({mobile:'',password:'',email:''})
const rules = reactive({
  mobile: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    (val) => val != null || val?.trim() !== '' || '手机号不能为空',
    val=>/^(((13[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[3-8]{1})|(18[0-9]{1})|(19[0-9]{1})|(14[5-7]{1}))+\d{8})$/.test(val)|| '请输入正确的手机号格式'

  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],

  email: [
    {required:true,message:'请输入邮箱',trigger:'blur'},
    (val) => val != null || val?.trim() !== '' || '邮箱不能为空',
    val=>/^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/.test(val)|| '请输入正确的邮箱格式'
  ]



})
const route = useRouter()

const toBack = () => {
  route.push({name: 'backLogin'})
}

const login = () =>{
  proxy.$refs.ruleFormRef.validate((valid) =>{
    console.log("valid:"+valid);
    if(valid){
      console.log(foreuser.mobile)
      //往后台发请求
      request.post('/fore/user/login',foreuser).then(res=>{
        console.log(res)
        if(res.data.id!=null){//请求成功
          ElNotification({
            type:'success',
            message:"登陆成功"
          })
          localStorage.setItem("user",JSON.stringify(res.data))
          if(cookie.get('username')){
            cookie.remove('username')
          }
          if(cookie.get('id')){
            cookie.remove('id')
          }
          cookie.set('username',res.data.username)
          cookie.set('id',res.data.id)
          console.log("-----------------------")

          console.log(res.data)
          route.push({name: 'foreHomeView',params: {id: res.data.id}})
        }else{//请求失败
          ElNotification({
            type:'error',
            message:res.msg
          })
        }
      })
    }else{
      ElMessage({
        type:'error',
        message:'用户名或密码错误'
      })
    }
  })
}

// switch的js
const sidebarOpen = ref(true)
const emitter = useEmitter()
const change = () => {
  const switchC1 = document.querySelector("#switch-c1") as any;
  const switchC2 = document.querySelector("#switch-c2") as any;
  const switchCircle = document.querySelectorAll(".switch__circle") as any;
  const switchCtn = document.querySelector("#switch-cnt") as any;
  switchCtn.classList.add("is-gx");
  setTimeout(function(){
    switchCtn.classList.remove("is-gx");
  }, 1500)
  switchCtn.classList.toggle("is-txr");
  switchCircle[0].classList.toggle("is-txr");
  switchCircle[1].classList.toggle("is-txr");
  switchC1.classList.toggle("is-hidden");
  switchC2.classList.toggle("is-hidden");

  sidebarOpen.value = !sidebarOpen.value
  emitter.emit('change', sidebarOpen.value)
}
//signup
onMounted(() => {
  emitter.on('change', (isOpen : boolean) => {
    watch: {
      isOpen:{
        let aContainer = document.querySelector("#a-container") as any;
        aContainer.classList.toggle("is-txl");
        //b中样式
        let bContainer = document.querySelector("#b-container") as any;
        bContainer.classList.toggle("is-txl");
        bContainer.classList.toggle("is-z200");


      }
    }
  })
})

</script>

<style scoped >
*, *::after, *::before {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  user-select: none;
}

/* Generic */
.body {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Montserrat', sans-serif;
  font-size: 12px;
  background-color: #ecf0f3;
  color: #a0a5a8;
}

/**/
.main {
  position: relative;
  width: 1000px;
  min-width: 1000px;
  min-height: 600px;
  height: 600px;
  padding: 25px;
  background-color: #ecf0f3;
  box-shadow: 10px 10px 10px #d1d9e6, -10px -10px 10px #f9f9f9;
  border-radius: 12px;
  overflow: hidden;
}

@media (max-width: 1200px) {
  .main {
    transform: scale(0.7);
  }
}

@media (max-width: 1000px) {
  .main {
    transform: scale(0.6);
  }
}

@media (max-width: 800px) {
  .main {
    transform: scale(0.5);
  }
}

@media (max-width: 600px) {
  .main {
    transform: scale(0.4);
  }
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  width: 600px;
  height: 100%;
  padding: 25px;
  background-color: #ecf0f3;
  transition: 1.25s;
}

.form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 70%;
  height: 100%;
}

.form__icon {
  object-fit: contain;
  width: 30px;
  margin: 0 5px;
  opacity: .5;
  transition: .15s;
}

.form__icon:hover {
  opacity: 1;
  transition: .15s;
  cursor: pointer;
}

.form__span {
  margin-top: 30px;
  margin-bottom: 12px;
}

.form__link {
  color: #181818;
  font-size: 15px;
  margin-top: 25px;
  border-bottom: 1px solid #a0a5a8;
  line-height: 2;
}

.title {
  font-size: 34px;
  font-weight: 700;
  line-height: 3;
  color: #181818;
}

.description {
  font-size: 14px;
  letter-spacing: .25px;
  text-align: center;
  line-height: 1.6;
}

.button {
  width: 180px;
  height: 50px;
  border-radius: 25px;
  margin-top: 50px;
  font-weight: 700;
  font-size: 14px;
  letter-spacing: 1.15px;
  background-color: #4B70E2;
  color: #f9f9f9;
  box-shadow: 8px 8px 16px #d1d9e6, -8px -8px 16px #f9f9f9;
  border: none;
  outline: none;
}

/**/
.a-container {
  z-index: 100;
  left: calc(100% - 600px);
}

.b-container {
  left: calc(100% - 600px);
  z-index: 0;
}

.switch {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 400px;
  padding: 50px;
  z-index: 200;
  transition: 1.25s;
  background-color: #ecf0f3;
  overflow: hidden;
  box-shadow: 4px 4px 10px #d1d9e6, -4px -4px 10px #f9f9f9;
}

.switch__circle {
  position: absolute;
  width: 500px;
  height: 500px;
  border-radius: 50%;
  background-color: #ecf0f3;
  box-shadow: inset 8px 8px 12px #d1d9e6, inset -8px -8px 12px #f9f9f9;
  bottom: -60%;
  left: -60%;
  transition: 1.25s;
}

.switch__circle--t {
  top: -30%;
  left: 60%;
  width: 300px;
  height: 300px;
}

.switch__container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: absolute;
  width: 400px;
  padding: 50px 55px;
  transition: 1.25s;
}

.switch__button {
  cursor: pointer;
}

.switch__button:hover {
  box-shadow: 6px 6px 10px #d1d9e6, -6px -6px 10px #f9f9f9;
  transform: scale(0.985);
  transition: .25s;
}

.switch__button:active, .switch__button:focus {
  box-shadow: 2px 2px 6px #d1d9e6, -2px -2px 6px #f9f9f9;
  transform: scale(0.97);
  transition: .25s;
}

/**/
.is-txr {
  left: calc(100% - 400px);
  transition: 1.25s;
  transform-origin: left;
}

.is-txl {
  left: 0;
  transition: 1.25s;
  transform-origin: right;
}

.is-z200 {
  z-index: 200;
  transition: 1.25s;
}

.is-hidden {
  visibility: hidden;
  opacity: 0;
  position: absolute;
  transition: 1.25s;
}

.is-gx {
  animation: is-gx 1.25s;
}

@keyframes is-gx {
  0%, 10%, 100% {
    width: 400px;
  }
  30%, 50% {
    width: 500px;
  }
}

.form__input :deep(.el-input__inner) {
  width: 350px;
  height: 40px;
  margin: 4px 0;
  padding-left: 25px;
  font-size: 13px;
  letter-spacing: 0.15px;
  border: none;
  outline: none;
  font-family: "Montserrat", sans-serif;
  background-color: #ecf0f3;
  transition: 0.25s ease;
  border-radius: 8px;
  box-shadow: inset 3px 3px 3px #d1d9e6, inset -3px -3px 3px #f9f9f9;
  cursor: default !important;
}

.form__input :deep(.el-input__wrapper) {
  display: inline-flex;
  /* flex-grow: 1; */
  /* align-items: center; */
  /* justify-content: center; */
  /* padding: 1px 11px; */
  background-color:transparent;
  background-image: none;
  cursor:default;
  /*border-radius: var(--el-input-border-radius,var(--el-border-radius-base));*/
  transition: var(--el-transition-box-shadow);
  border: none;
  box-shadow: 0 0 0 1px var(--el-input-border-color,var(--el-border-color)) inset;
}

</style>
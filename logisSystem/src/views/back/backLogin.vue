<template>
      <div class="elCard">
        <el-col :span="14">
          <el-card class="Card">
            <template #header>
              <h2>登陆</h2>
            </template>
            <el-form :model="foreuser" :rules="rules" ref="ruleFormRef"  action="#">


           <el-col :span="18">
             <el-form-item prop="username">
               <el-input  placeholder="User" class="input" v-model="foreuser.username" :prefix-icon="User"/>
             </el-form-item>
           </el-col>

              <el-col :span="18">
                <el-form-item prop="password">
                  <el-input  placeholder="Password" class="input" v-model="foreuser.password" :prefix-icon="Lock" show-password />
                </el-form-item>
              </el-col>

              <el-col :span="14">
              <el-form-item style="padding-left: 110px" label="用户类型">
                <el-select v-model="userType" placeholder="--请选择--">
                  <el-option label="其他管理员" value="0"/>
                  <el-option label="快递员" value="1"/>
                </el-select>
              </el-form-item>
        </el-col>
              <el-form-item style="padding-left: 206px">
                <a href="#" class="link">忘记密码？</a>
              </el-form-item>

              <el-form-item style="padding-left: 206px">
                <el-button class="btn" type="primary" @click="login">登录</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>

      </div>

</template>

<style scoped>
.elCard{
  background-color:#ecf0f3;
  padding-top:150px;
  padding-left: 350px;
  height:79vh;
}
.input{
padding-left: 106px;
}
.Card{
  /*background-color: #d4dbe1;*/
}
</style>

<script setup>
import {ref, reactive, getCurrentInstance} from 'vue'
import {User,Lock} from '@element-plus/icons-vue'
import {ElMessage, ElNotification} from "element-plus";
import request from "../../request";
import { useRouter } from "vue-router"
import {useCookies} from "@vueuse/integrations/useCookies"
const { proxy } = getCurrentInstance()
let foreuser = reactive({username:'',password:'',mobile:''})
const route = useRouter();
const userType=ref('');
const rules = reactive({
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
      ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],

})
const cookie = useCookies()


const login = () =>{

  proxy.$refs.ruleFormRef.validate((valid) =>{
    if(valid){
      if(userType.value=='1'){
        foreuser.mobile=foreuser.username,
        foreuser.username=null
      }

      //往后台发请求
      request.post('/system/login',foreuser).then(res=>{
        if(res.data.id!=null){//请求成功
          ElNotification({
            type:'success',
            message:"登陆成功"
          })
          localStorage.setItem("user",JSON.stringify(res.data))
          console.log(res.data)
          if(cookie.get('username')){
            cookie.remove('username')
          }
          if(cookie.get('roleId')){
            cookie.remove('roleId')
          }
          if(cookie.get('id')){
            cookie.remove('id')
          }
          cookie.set('username',res.data.username)
          cookie.set('roleId',res.data.roleId)
          cookie.set('id',res.data.id)

          route.push({name:'system',params:{username:res.data.username,roleId:res.data.roleId,id:res.data.id}})

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


</script>
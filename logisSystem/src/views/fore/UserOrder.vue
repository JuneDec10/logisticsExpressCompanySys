<template>

  <el-form :rules="addUserFormRules" ref="form" style="padding-left: 230px;" :model="form" label-width="120px">
    <el-col :span="18">
      <div class="sender" >
        <el-card shadow="hover" >
          <template #header>
            <h3>
              <SvgIcon name="editPan" style="width: 20px;height:20px;color: #1e71b4"/>
              寄方信息</h3>
          </template>
          <el-form-item label="姓名" prop="senderName">
            <el-col :span="8">
              <el-input placeholder="请输入寄件人姓名" v-model="form.senderName" clearable />
            </el-col>

          </el-form-item>
          <el-form-item label="手机号" prop="senderMobile">
            <el-col :span="12">
              <el-input placeholder="请输入寄件人手机号" v-model="form.senderMobile" clearable />
            </el-col>

          </el-form-item>
          <el-form-item label="发件地址" prop="sendAddress">
            <div class="divwrap" v-if="show">
              <v-distpicker @selected="onSelected"></v-distpicker>
            </div>
          </el-form-item>
          <el-form-item label="详细地址" prop="sendFullAddress">
            <el-col :span="15">
              <el-input v-model="form.sendFullAddress" clearable size="large"/>
            </el-col>

          </el-form-item>
          <el-form-item label="付款方式" prop="paymentMethod">
            <el-select v-model="form.paymentMethod" placeholder="请选择你的寄件方式">
              <el-option label="到付" value="0" />
              <el-option label="寄付" value="1" />
            </el-select>
          </el-form-item>
        </el-card>

      </div>
    </el-col>
    <br>
    <el-col :span="18">
      <div class="receiver">
        <el-card shadow="hover" >
          <template #header>

            <h3>
              <SvgIcon name="editPan" style="width: 20px;height:20px;color: #de7f44"/>
              收方信息</h3>
          </template>
          <el-form-item label="姓名" prop="recipientsName">
            <el-col :span="8">
              <el-input placeholder="请输入收件人姓名" v-model="form.recipientsName" clearable />
            </el-col>

          </el-form-item>
          <el-form-item label="手机号" prop="recipientsMobile">
            <el-col :span="12">
              <el-input placeholder="请输入收件人手机号" v-model="form.recipientsMobile" clearable />
            </el-col>

          </el-form-item>
          <el-form-item label="收件地址" prop="recipientsAddress">
            <div class="divwrap" v-if="show">
              <v-distpicker @selected="onReciverSelected"></v-distpicker>
            </div>
          </el-form-item>
          <el-form-item label="详细地址" prop="recipientsFullAddress">
            <el-col :span="15">
              <el-input v-model="form.recipientsFullAddress" />
            </el-col>

          </el-form-item>


          <el-form-item label="物品类型" prop="itemType">
            <el-radio-group v-model="form.itemType">
              <el-radio label="文件" />
              <el-radio label="服饰" />
              <el-radio label="食品" />
              <el-radio label="数码用品" />
              <el-radio label="生活用品" />
              <el-radio label="其他" />
            </el-radio-group>
          </el-form-item>

          <el-form-item label="物品重量" prop="itemWeight">
            <el-col :span="2">
              <el-input  v-model="form.itemWeight" />
            </el-col>

          </el-form-item>
          <el-form-item label="备注">
            <el-col :span="21">
              <el-input v-model="form.remark" type="textarea" />
            </el-col>

          </el-form-item>

        </el-card>

      </div>
    </el-col>

      <br>
      <el-form-item style="padding-left: 105px;">
        <el-button type="primary" @click="onSubmit('form')">下单</el-button>
        <el-button>取消</el-button>
      </el-form-item>
  </el-form>



</template>
<script>
import VDistpicker from 'v-distpicker'
import {useRouter} from "vue-router";
import request from "../../request";
import {ElNotification} from "element-plus";
import SvgIcon from "../../components/SvgIcon.vue";
import {useCookies} from "@vueuse/integrations/useCookies";

const route = useRouter();
export default {
created() {
  localStorage.getItem("user")
},
  data(){
    var checkMobile = (rule,value,callback) =>{
      console.log(value)
        //验证手机号的正则表达式
        const regMobile = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
        if(value==''||value==undefined||value==null){
          callback(new Error("请填写手机号"));
        }else{
          if(!regMobile.test(value)){
            return callback(new Error("请输入合法的手机号"));
          }else
            return callback(); //合法手机号
        }

      }

    return{

      show:true,
      senderSelected:{
        province: "",
        city: "",
        area: ""
      },
      receiverSelected:{
        province: "",
        city: "",
        area: ""
      },
      form :{
        senderName: '',
        senderMobile: '',
        sendAddress: '',
        paymentMethod: '',
        sendFullAddress: '',
        recipientsName: '',
        recipientsMobile: '',
        recipientsAddress: '',
        recipientsFullAddress: '',
        itemType: '',
        itemWeight: '',
        remark: '',
        foreUserId:''
      },


      //添加表单的验证规则对象
      addUserFormRules: {
        senderName: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        senderMobile: [
          {required: true, validator: checkMobile, trigger: 'blur'}]
        ,
        sendFullAddress: [
          {required: true, message: '请输入详细地址', trigger: 'blur'}
        ],
        sendAddress:[
          {required: true, message: '请选择地址', trigger: 'change'}
        ],
        paymentMethod: [
          {required: true, message: '请选择付款方式', trigger: 'change'}
        ],
        itemType: [
          {required: true, message: '请选择商品类型', trigger: 'change'}
        ],
        recipientsName: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        recipientsMobile: [
          {required: true, validator: checkMobile, trigger: 'blur'}]
        ,
        recipientsFullAddress:[
          {required: true, message: '请输入详细地址', trigger: 'blur'}
        ],
        recipientsAddress: [
          {required: true, message: '请选择地址', trigger: 'change'}
        ],
        itemWeight: [
          {required: true, message: '请输入重量', trigger: 'blur'}
        ],
      }

    }
  },
  computed:{
    senderAddress:function(){
      return this.senderSelected.province +"-"+ this.senderSelected.city +"-"+ this.senderSelected.area
    },
    receiverAddress:function(){
      return this.receiverSelected.province +"-"+ this.receiverSelected.city +"-"+ this.receiverSelected.area
    }
  },
  methods:{
    onSubmit(formName){
      this.$refs[formName].validate(valid => {
        if(valid){
          console.log("valid"+valid)
          console.log(this.form)
          this.form.foreUserId =
          request.post('/fore/expressMailOrder/add',this.form).then(res=>{
            console.log(res)
            if(res.msg=='success'){
              ElNotification({
                type:'success',
                message:'下单成功'
              })
              window.open("http://localhost:8081/alipay/pay?subject=订单支付&traceNo=" + res.data.waybillNumber + "&totalAmount=" + res.data.price)
            }else{
              console.log("error"+res.msg)
              ElNotification({
                type:'error',
                message:res.msg
              })
            }
          })

        }else{
          return false;
        }
      })


    },
    onSelected(data){
      const cookie = useCookies();
      const {province,city,area} = data;
      if(!province.code && !city.code && !city.code) return;
      this.senderSelected.province = province.value;
      this.senderSelected.city = city.value;
      this.senderSelected.area = area.value;
      this.form.sendAddress= this.senderAddress;
      console.log(this.form.sendAddress)
      let that = this;
      this.form.foreUserId = cookie.get('id');
    },
    onReciverSelected(data){
      const {province,city,area} = data;
      if(!province.code && !city.code && !city.code) return;
      this.receiverSelected.province = province.value;
      this.receiverSelected.city = city.value;
      this.receiverSelected.area = area.value;
      this.form.recipientsAddress = this.receiverAddress;

    }
  },
  components:{
    SvgIcon,
    VDistpicker
  }
}
</script>

<style scoped>

.divwrap{
  height: 40px;
}
.divwrap >>> select{
  height:35px;
  color: #13171c;
}
.divwrap >>> .address-header{
  position: fixed;
  bottom: 400px;
  width: 100%;
  background: #7676a8;
  color:#fff;
}
.divwrap >>> .address-header ul li{
  flex-grow: 1;
  text-align: center;
}
.divwrap >>> .address-header .active{
  color: rgb(255, 255, 255);
  border-bottom:#666 solid 8px
}
.divwrap >>> .address-container .active{
  color: rgba(142, 154, 175, 0.52);
}

</style>


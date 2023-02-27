<template>
  <el-row>
  <el-col :span="22">

      <el-form :rules="addUserFormRules" ref="form" style="padding-left: 100px;" :model="form" label-width="100px">
        <el-col :span="24">

            <div class="sender" >
              <el-card shadow="hover" >
                <template #header>
                  <h3>
                    <SvgIcon name="search" style="width: 20px;height:20px;color: #1e71b4"/>
                    运费时效查询</h3>
                </template>
                <el-row>
                <el-col :span="16">
                  <el-form-item label="发件地址" prop="sendAddress">
                    <div class="divwrap" >
                      <v-distpicker @selected="onSelected"></v-distpicker>
                    </div>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="详细地址" >

                    <el-input v-model="form.sendFullAddress" clearable/>

                  </el-form-item>
                </el-col>

                <el-col :span="16">
                  <el-form-item label="收件地址" prop="recipientsAddress">
                    <div class="divwrap" >
                      <v-distpicker @selected="onReciverSelected"></v-distpicker>
                    </div>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="详细地址" >

                    <el-input v-model="form.recipientsFullAddress" />


                  </el-form-item>
                </el-col>

                </el-row>
                <el-form-item label="物品重量" prop="itemWeight">
                  <el-col :span="2">
                    <el-input  v-model="form.itemWeight" />
                  </el-col>
                  kg
                </el-form-item>
                <el-form-item >
                  <el-button type="primary" @click="onSubmit('form')" plain>查询</el-button>
                </el-form-item>
              </el-card>
            </div>
        </el-col>
        <br>
      </el-form>
  </el-col>
  </el-row>

<el-row>
  <el-col :span="22" >

    <div class="receiver" style="padding-left: 100px;" v-show="isShow">

      <el-card shadow="hover" >
        <template #header>

          <h3>
            <SvgIcon name="search" style="width: 20px;height:20px;color: #de7f44"/>
            运费时效</h3>
        </template>

        <el-table :data="agingQueryList">
          <el-table-column align="center" :prop="item.prop" :label="item.label" v-for="item in agingList" :key="item.prop"/>

        </el-table>



      </el-card>

    </div>
  </el-col>
</el-row>


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

  data(){
    return{
      randomItem:'',
      isShow:false,
      agingList:[
        {
          label:"我们的产品"
          ,prop:"title"
        },
        {
          label:"重量"
          ,prop:"weight"
        },
        {
          label:"预计时效"
          ,prop:"eta"
        }, {
          label:"寄付价"
          ,prop:"price"
        },{
          label:"计费规则"
          ,prop:"priceRule"
        }
      ],
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
      agingQueryList: [],
      form :{
        sendAddress: '',
        sendFullAddress: '',
        recipientsAddress: '',
        recipientsFullAddress: '',
        itemWeight: '1',
        foreUserId:''
      },

      //添加表单的验证规则对象
      addUserFormRules: {
        sendAddress:[
          {required: true, message: '请选择地址', trigger: 'change'}
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
  created() {
  },
  methods:{
    onSubmit(formName){

      this.$refs[formName].validate(async valid => {
        if (valid) {
         await this.getAgingQuery()
        } else {
          return false;
        }
      })


    },
    async getAgingQuery(){
      console.log("validessss")
      console.log(this.form)
      const {data: res} = await this.$axios.get('/fore/expressMailOrder/agingQuery',{
        params: this.form
      })
      console.log(res)
      if(res.length!=0){
        this.agingQueryList = res
        console.log(this.agingQueryList)
        this.isShow = true
      }

    },
    onSelected(data){
      const cookie = useCookies();
      const {province,city,area} = data;
      if(!province.code && !city.code && !city.code) return;
      this.senderSelected.province = province.value;
      this.senderSelected.city = city.value;
      this.senderSelected.area = area.value;
      this.form.sendAddress= this.senderAddress;
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
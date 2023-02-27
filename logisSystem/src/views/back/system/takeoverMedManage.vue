<template>
  <div>

    <!-- 卡片视图区域 -->

    <el-row :gutter="20">
      <el-col :span="8">
        <!-- 搜索与添加区域 -->
        <el-input placeholder="请输入内容" >
          <template #append>
            <SvgIcon name="search" style="width: 1.4rem;height:1.4rem"/>
          </template>
        </el-input>
      </el-col>
    </el-row>
    <!-- 用户列表区域  -->
    <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column prop="expressMailOrderId.id" v-if="false"></el-table-column>
      <el-table-column :v-if="item.show" :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" align = "center">
      </el-table-column>
      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <!-- 运输收货按钮 -->
          <el-button type="primary" v-if="transportShow" size="mini" @click="deliver(scope.row)" plain>
           运输收货
          </el-button>

          <!-- 确认到达按钮 -->
          <el-button type="primary" v-if="arriveShow"  size="mini" @click="branchArrive(scope.row)" plain>
            确认到达
          </el-button>
        </template>
      </el-table-column>
    </el-table>
<!--    消息弹出框-->
    <el-dialog
        v-model="dialogVisible"
        title="提示"
        width="30%"
        :before-close="handleClose"
    >
      <span>收货成功！</span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确认</el-button>
      </span>
      </template>
    </el-dialog>
    <!-- 页面区域 -->
    <el-pagination :current-page="queryInfo.pagenum" :page-sizes="[1, 2, 5, 10]"
                   :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>
<script >
import CommonForm from "../../../components/CommonForm.vue";
import request from "../../../request";
import {ElNotification} from "element-plus";
export default {
  components: {CommonForm},
  data () {
    return {
      transportShow:false,
      arriveShow:false,
      dialogVisible:false,
      operateType:'add',
      nextBranch:'',
      thisItemId:'',
      thisRecordId:'',
      roleType:'',
      nextBranchForm:[],
      // 获取用户列表的参数对象
      queryInfo: {
        query: '', // 查询参数
        pagenum: 1, // 当前页码
        pagesize: 2, // 每页显示条数
        userId:''
      },
      // 用户列表
      userlist: [],
      // 总数据条数
      total: 0,
      fields : [
        {
          label:"订单编号"
          ,prop:"expressMailOrderId.waybillNumber"

        },
        {
          label:"收件人联系方式"
          , prop:"expressMailOrderId.recipientsMobile"

        },
        {
          label:"收件人地址"
          , prop:"expressMailOrderId.recipientsAddress"

        }
        ,{
          label:"物品类型"
          , prop:"expressMailOrderId.itemType"

        },{
          label:"物品重量"
          , prop:"expressMailOrderId.itemWeight"

        },{
          label:"付款方式"
          , prop:"expressMailOrderId.paymentMethod"

        },{
          label:"下单时间"
          , prop:"expressMailOrderId.createTime"

        }
      ]
    }
  },
  created () {
    let that = this
    let user = JSON.parse(localStorage.getItem("user"))
    if(user==null){
      that.$router.push({name:'login'})
    }
    this.queryInfo.userId = user.id;
    this.roleType = user.roleId
    if(this.roleType==1){
    this.arriveShow = true
  }else if(this.roleType==2){
      this.transportShow = true
    }
    this.getUserList()
  },
  methods: {
    branchArrive(row){
     let that = this
      this.thisItemId  = row.expressMailOrderId.id
      this.thisRecordId = row.id
      let axiosData = {
        expressMailOrderId:this.thisItemId,
        userId:this.queryInfo.userId,
        recordId:this.thisRecordId
      }
      request.post('/system/ProcessRecords/confirmDelivery',axiosData).then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.msg=='success'){
            this.dialogVisible = true
            this.getUserList()
          }else{
            ElNotification({
              type:'error',
              message:res.msg
            })
          }
        }
      })
    },
  deliver(row){
      var that=this
      this.thisItemId  = row.expressMailOrderId.id
      let axiosData = {
        itemId:this.thisItemId,
        recordId:row.id,
        userId:this.queryInfo.userId
      }
      request.post('/system/ProcessRecords/takeDeliveryOfGoods',axiosData).then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.msg=='success'){
            this.dialogVisible = true
            this.getUserList()
          }else{
            ElNotification({
              type:'error',
              message:res.msg
            })
          }
        }

    })
    },
    async getUserList () {
      let that  = this;
      // this.queryInfo.userId = that.$route.params.id;
      let axiosData ={
        id:this.queryInfo.userId
      };
      console.log(this.queryInfo.userId)
      await request.post('/system/ProcessRecords/list',axiosData).then(res=>{
        console.log(res)
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.msg=='success'){
            this.userlist = res.data
            this.total = res.data.length
            for (let i = 0; i < res.data.length; i++) {
              this.userlist[i].expressMailOrderId.recipientsAddress = this.userlist[i].expressMailOrderId.recipientsAddress+' '+this.userlist[i].expressMailOrderId.recipientsFullAddress
            }
          }else{

          }
        }
      })

    },
    // 监听 page size 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getUserList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getUserList()
    },

    roleType (row,index){
      if(row.roleId =="0"){
        return "超级管理员";
      }else if(row.roleId =="1"){
        return "网点";
      }else if(row.roleId =="2"){
        return "运输点";
      }else if(row.roleId =="3"){
        return "快递员";
      }
    }
  },




}
</script>

<style lang="less" scoped>

</style>
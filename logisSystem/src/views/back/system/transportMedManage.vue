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

      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" align = "center">
      </el-table-column>
<!--  运输按钮  -->
      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <el-button type="primary" @click="pickUp(scope.row)" plain>
            运输
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    选择运输点-->
    <el-dialog title="发出货物" v-model="transIsShow">

      <div>下一个运输点|网点:</div>
      <el-select style="width: 95%" v-model="nextBranch" placeholder="请选择：">
        <el-option
            v-for="item in nextBranchForm"
            :key="item.id"
            :label="item.username"
            :value="item.id"
        >
          <span style="float: left">{{ item.username }} | </span>
          <span>地址：</span>
          <span>{{ item.address }}</span>
        </el-option>
        <br/>
      </el-select>
      <span class="dialog-footer">
        <el-button @click="transIsShow = false">取消</el-button>
        <el-button type="primary" @click="confirmDeliver"
        >确认</el-button>
      </span>
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
      transIsShow:false,
      nextBranch:'',
      thisItemId:'',
      thisRecordId:'',
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
        },{
          label:"收件人联系方式"
          , prop:"expressMailOrderId.recipientsMobile"
        },
        {
          label:"收件人地址"
          , prop:"expressMailOrderId.recipientsAddress"
        },{
          label:"物品类型"
          , prop:"expressMailOrderId.itemType"
        },{
          label:"物品重量"
          , prop:"expressMailOrderId.itemWeight"
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
    this.getUserList()
  },
  methods: {

    async confirmDeliver(){
      let that = this
      let axiosData = {
        preBranchId:this.queryInfo.userId,
        expressMailOrderId:this.thisItemId,
        nextBranchId:this.nextBranch,
        recordId:this.thisRecordId
      }
      await request.post('/system/ProcessRecords/deliveryTheItem',axiosData).then(async res => {
        if (res.code === -20001 || res.code == -20002) {
          that.$router.push({name: 'login'})
        } else {
          if (res.msg == 'success') {
            this.transIsShow = false
            await this.getUserList()
            ElNotification({
              type: 'success',
              message: '发货成功'
            })
          } else {
            ElNotification({
              type: 'error',
              message: res.msg
            })
          }
        }
      })
    },

  async pickUp(row){

      this.transIsShow = true
      this.thisItemId  = row.expressMailOrderId.id
      this.thisRecordId = row.id
    let that = this
      let axiosData = {
        itemId:this.thisItemId,
        userId:this.queryInfo.userId
      }
      await request.post('/system/ProcessRecords/PreDelivery',axiosData).then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          this.nextBranchForm =res.data
        }
      })


    },

    async getUserList () {
      let that = this;
      let axiosData = {
        id: this.queryInfo.userId
      };
      await request.post('/system/ProcessRecords/receiveList', axiosData).then(res => {
        if (res.code === -20001 || res.code == -20002) {
          that.$router.push({name: 'login'})
        } else {
          this.userlist = res.data
          if (res.data.length != 0) {

            for (let i = 0; i < res.data.length; i++) {
              this.userlist[i].expressMailOrderId.recipientsAddress = this.userlist[i].expressMailOrderId.recipientsAddress + ' ' + this.userlist[i].expressMailOrderId.recipientsFullAddress
            }
          }
          this.total = res.data.length
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
  }

}
</script>

<style lang="less" scoped>

</style>
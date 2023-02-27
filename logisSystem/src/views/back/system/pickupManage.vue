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
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" align = "center">
      </el-table-column>

      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <!-- 揽收按钮 -->
          <el-button type="primary" size="mini" @click="pickUp(scope.row)" plain>
            揽收
          </el-button>
        </template>
      </el-table-column>
    </el-table>


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
          ,prop:"waybillNumber"
        },
        {
          label:"寄件人联系方式"
          , prop:"senderMobile"
        },{
          label:"寄件人地址"
          ,prop:"sendAddress"
        },{
          label:"收件人联系方式"
          , prop:"recipientsMobile"
        },
        {
          label:"收件人地址"
          , prop:"recipientsAddress"
        },{
          label:"物品类型"
          , prop:"itemType"
        },{
          label:"物品重量"
          , prop:"itemWeight"
        },{
          label:"下单时间"
          , prop:"createTime"
        }
      ]
    }
  },
  created () {

    this.getUserList()
  },
  methods: {
    pickUp(row){
let that = this;
      let user = JSON.parse(localStorage.getItem("user"))
      if(user==null){
        that.$router.push({name:'login'})
      }
      this.queryInfo.userId = user.id;
const userId = this.queryInfo.userId
      this.$confirm(
          '确认揽收?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        let params= {
          userId:userId,
          itemId:row.id
        }
        request.post('/system/expressMailOrder/pickUp',params).then(res=>{
          if(res.code===-20001||res.code==-20002){
            that.$router.push({name:'login'})
          }else{
            if(res.msg=='success'){
              this.$message({
                type: 'success',
                message: '揽收成功',
              })
              this.getUserList()
            }else{
              this.$message({
                type: 'error',
                message: res.msg,
              })
            }
          }

        })
          })
    },


    async getUserList () {
      let that  = this;
      let user = JSON.parse(localStorage.getItem("user"))
      if(user==null){
        that.$router.push({name:'login'})
      }
      this.queryInfo.userId = user.id;
      let axiosData ={
        id:this.queryInfo.userId
      };
      await request.post('/system/expressMailOrder/list',axiosData).then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          this.userlist = res.data
          this.total = res.data.length
          if(res.data.length!=0){

            for (let i = 0; i < res.data.length; i++) {
              this.userlist[i].senderAddress = this.userlist[i].senderAddress + ' '+this.userlist[i].sendFullAddress
              this.userlist[i].recipientsAddress = this.userlist[i].recipientsAddress+' '+this.userlist[i].recipientsFullAddress
            }
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
  }

}
</script>

<style lang="less" scoped>

</style>
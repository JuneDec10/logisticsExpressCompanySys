<template>
  <div>
    <el-tabs type="border-card" v-model="getTabs" :tab-position="tabPosition" style="height: 800px" class="demo-tabs" @tab-click="changeTabs">
      <el-tab-pane :name="item.name" :label="item.label" v-for="item in fileds">
              <!-- 卡片视图区域 -->
              <el-row :gutter="20">
                <el-col :span="8">
                  <!-- 搜索与添加区域 -->
                  <el-input placeholder="请输入订单编号" v-model="searchItemId" clearable>
                    <template #append>
                      <el-button @click="searchItem">
                        <SvgIcon name="search" style="width: 1.4rem;height:1.4rem"/>
                      </el-button>
                    </template>
                  </el-input>
                </el-col>
              </el-row>
              <!-- 用户列表区域  -->
              <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
                <el-table-column prop="id" v-if="false"></el-table-column>

                <el-table-column :prop="item.prop" :label="item.label" v-for='item in orderManage' :key="item.prop" >
                </el-table-column>
                <el-table-column prop="price" label="邮费(元)" align="center" width="80"></el-table-column>
                <el-table-column label="状态" prop="abolished" align="center" >
                  <template v-slot="scope">
                    <el-tag v-if="scope.row.abolished === 0" type="success">正常</el-tag>
                    <el-tag v-else-if="scope.row.abolished === 1" >取消中</el-tag>
                    <el-tag v-else type="warning">订单已取消</el-tag>
                  </template>
                </el-table-column>

                <!--  按钮  -->
                <el-table-column label="操作" width="180px">
                  <template v-slot="scope">
                    <el-button type="primary" v-if="scope.row.orderStatus!==8" size="mini" @click="forDetail(scope.row)" plain>
                      详情
                    </el-button>
                    <el-button type="primary"  v-else-if="scope.row.abolished===0&&scope.row.orderStatus===8" size="mini" @click="payFor(scope.row)" plain>
                      支付
                    </el-button>
                    <el-button v-if="scope.row.abolished===0" type="danger" size="mini" @click="consertOrder(scope.row)" plain>
                      取消
                    </el-button>

                    <el-button v-if="scope.row.abolished!==0" type="danger" size="mini" @click="consertOrder(scope.row)" plain disabled>
                      取消
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
      </el-tab-pane>
    </el-tabs>

    <!-- 物流进度的对话框 -->
    <el-dialog :title="物流进度" v-model="progressVisible" width="50%" center draggable>



  <el-timeline >
    <el-timeline-item
        center
        placement="top"
        v-for="(activity, index) in progressInfo"
        :key="index"
        :timestamp="activity.createTime"
    >
      {{ activity.context }}
    </el-timeline-item>
  </el-timeline>
      <span slot="footer">
        <el-button @click="progressVisible = false">取 消</el-button>
        <el-button type="primary" @click="progressVisible = false">确 定</el-button>
    </span>
    </el-dialog>

  </div>



</template>
<script>

import {ElNotification} from "element-plus";
import {useCookies} from "@vueuse/integrations/useCookies";
import request from "../../request";

export default {

  data(){
    return{
      getTabs:'1',
      thisItemId:'',
      progressVisible: false,
      // 获取用户列表的参数对象
      queryInfo: {
        query: '', // 查询参数
        pagenum: 1, // 当前页码
        pagesize: 2, // 每页显示条数
        userId:''
      },
      // 订单列表
      userlist: [],
      // 总数据条数
      total: 0,
      tabPosition:'left',
      fileds:[
          {
       label:"寄出全部订单",
            name:'1'
      },{
          label:"未支付（寄出）",
          name:'100'
        },
        {
          label:"已下单（寄出）",
          name:'10'
        },{
          label:"运输中（寄出）",
          name:'11'
        },{
          label:"已完成（寄出）",
          name:'12'
        },{
          label:"收到全部订单",
          name:'2'
        },{
          label:"已下单（收到）",
          name:'20'
        },{
          label:"运输中（收到）",
          name:'21'
        },{
          label:"已签收（收到）",
          name:'22'
        },
      ],
      orderManage:[
        {
          label:"订单编号"
          ,prop:"waybillNumber"
        },
        {
          label:"寄件人 > 收件人",
          prop:"sendAndTo"
        },
        {
          label:"物品类型",
          prop:"itemType"
        },
        {
        label:"下单时间",
          prop:"createTime"
        }
      ],
      progressInfo: '这是一段信息',
      searchItemId:''
    }
  },

  created () {
    const cookie = useCookies();
    this.getUserList()
  },
  methods: {

    //取消订单
    async  consertOrder(row){
      let axiosData = {
        itemId:row.id,
      }
      const { data: res } = await this.$axios.get('/fore/expressMailOrder/consertItem',{
        params: axiosData
      })
      console.log(res)
      if(res.msg=='success'){
        ElNotification({
          type:'success',
          message:"您的订单正在取消中，请耐心等候！"
        })
        await this.getUserList()
      }else{
        ElNotification({
          type:'error',
          message:res.msg
        })
      }

    },

   async searchItem(){

     let axiosData = {
       itemId:this.searchItemId,
     }
     await request.post('/fore/expressMailOrder/searchItem',axiosData).then(res=>{
       if(res.msg=="success"){
         this.progressInfo = res.data
         this.progressVisible = true;
       }
     })
    },

    payFor(row){
      window.open("http://localhost:8081/alipay/pay?subject=" + row.id + "&traceNo=" + row.waybillNumber + "&totalAmount=" + row.price)
      this.$message.success("请求支付宝成功")
    },

    changeTabs(tab,event){
      this.getTabs = tab.props.name
      console.log(this.getTabs)
      this.getUserList()
},

    async forDetail(row){
      this.thisItemId  = row.id
      let axiosData = {
        itemId:this.thisItemId,
      }
      await request.post('/fore/expressMailOrder/forDetail',axiosData).then(res=>{
        if(res.msg="success"){
          this.progressInfo = res.data
          this.progressVisible = true;
        }
      })
},

    async getUserList () {
      const cookie = useCookies();
      let that  = this;
      this.queryInfo.userId = cookie.get('id');
      console.log(localStorage.getItem("user"))
      let axiosData ={
        idOrMobile:this.queryInfo.userId,
        status:this.getTabs,
        token:localStorage.getItem("user")
      };

      console.log(axiosData)
      const { data: res } = await this.$axios.get('/fore/expressMailOrder/listOrder',{
        params: axiosData
      })
      console.log(res)
      this.userlist = res
      if(res.length!=0){

        for (let i = 0; i < res.length; i++) {
          this.userlist[i].sendAndTo = res[i].senderName+'('+res[i].sendAddress+') > '+res[i].recipientsName+'('+res[i].recipientsAddress+')'
        }
      }
      this.total = res.length
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
<style scoped>
.el-timeline-item {
  position: relative;
  padding-bottom: 10px;
}

/*.demo-tabs > .el-tabs__content {*/
/*  padding: 32px;*/
/*  color: #6b778c;*/
/*  font-size: 32px;*/
/*  font-weight: 600;*/
/*}*/

/*.el-tabs--right .el-tabs__content,*/
/*.el-tabs--left .el-tabs__content {*/
/*  height: 100%;*/
/*}*/
</style>


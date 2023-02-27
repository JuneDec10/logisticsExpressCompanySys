<template>
  <div>

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
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop">


      </el-table-column>
      <el-table-column label="状态" prop="orderStatus" align="center" >
        <template v-slot="scope">
          <el-tag v-if="scope.row.orderStatus === 0">已下单</el-tag>
          <el-tag v-else-if="scope.row.orderStatus === 7" type="success">已签收</el-tag>
          <el-tag v-else type="warning">运输中</el-tag>
        </template>

      </el-table-column>

      <!--  运输按钮  -->
      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <el-button type="primary" size="mini" @click="forDetail(scope.row)" plain>
            物流详情
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

<script >

import CommonForm from "../../../components/CommonForm.vue";
import request from "../../../request";
import {ElNotification} from "element-plus";
export default {
  components: {CommonForm},
  data () {

    return {
      progressVisible:false,
      operateType:'add',
      thisRoleId:'',
      thisItemId:'',
      searchItemId:'',
      userlist:[],
      operateForm:{
        id:'',
        username:'',
        mobile:'',
        status:''
      },
      // 获取用户列表的参数对象
      queryInfo: {
        query: 'courier', // 查询参数
        userId:'',
        pagenum: 1, // 当前页码
        pagesize: 2 // 每页显示条数
      },
      // 总数据条数
      total: 0,
      fields : [
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
    }
  },
  created () {
    let that = this
    this.thisRoleId = that.$route.params.roleId
    this.getUserList()
  },
  methods: {
    async searchItem(){
      var that = this
      let axiosData = {
        itemId:this.searchItemId,
      }
      await request.post('/fore/expressMailOrder/searchItem',axiosData).then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.code===0){

            this.progressInfo = res.data
            this.progressVisible = true;
          }else{
            ElNotification({
              type:"error",
              message:res.msg
            })
          }
        }
      })
    },
    async forDetail(row){
      var that = this

      this.thisItemId  = row.id
      let axiosData = {
        itemId:row.id,
      }

      await request.post('/fore/expressMailOrder/forDetail',axiosData).then(res=>{
        console.log(res)
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.data!=null){
            console.log(res.data)
            that.progressInfo = res.data
            that.progressVisible = true;
          }
        }
      })
    },

    async getUserList () {
      var that = this
      await request.post('/system/listAllPackage').then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          that.userlist = res.data
          if(res.data.length!=0){

            for (let i = 0; i < res.data.length; i++) {
              that.userlist[i].sendAndTo = res.data[i].senderName+'('+res.data[i].sendAddress+') > '+res.data[i].recipientsName+'('+res.data[i].recipientsAddress+')'
            }
          }
          that.total = res.data.length
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
    }
  }

}
</script>

<style lang="less" scoped>

</style>
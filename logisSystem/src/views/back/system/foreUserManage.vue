<template>
  <div>

    <!-- 用户列表区域  -->
    <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" align = "center">
      </el-table-column>

      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <!-- 修改按钮 -->
          <el-button type="primary" plain size="mini" @click="manage(scope.row)" v-if="scope.row.status==0">
            处理
          </el-button>
          <el-button type="primary" plain size="mini" @click="manage(scope.row)" :disabled="true" v-if="scope.row.status==1">
            处理
          </el-button>

          <!-- 删除按钮 -->
          <el-button type="success" plain size="mini" @click="logicDelete(scope.row)" v-if="scope.row.status==1">
            完成
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
      operateType:'add',
      // 获取用户列表的参数对象
      queryInfo: {
        query: 'transport', // 查询参数
        pagenum: 1, // 当前页码
        pagesize: 2 // 每页显示条数
      },
      // 用户列表
      userlist: [],
      // 总数据条数
      total: 0,
      fields : [
          {
        label:"运单编号"
        , prop:"waybillNumber"
      },
        {
          label:"投诉人名字"
          ,prop:"complaintName"
        },
        {
          label:"投诉人电话"
          ,prop:"mobile"
        },{
          label:"投诉内容"
          , prop:"content"
        }
      ]
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    async  manage(row){
      let axiosData = {
        complaintId:row.id
      }

      await request.post('system/complaint/handleTheComplaint',axiosData).then(async res => {
        if (res.code === -20001 || res.code == -20002) {
          that.$router.push({name: 'login'})
        } else {
          if (res.msg == 'success') {
            ElNotification({
              type: 'success',
              message: "处理投诉中"
            })
            await this.getUserList()
          } else {
            ElNotification({
              type: 'error',
              message: res.mag
            })
          }
        }
      })
    },
    async logicDelete(row){
      let axiosData = {
        complaintId:row.id
      }
      const { data: res } = await this.$axios.get('system/complaint/finishedTheComplaint', {
        params:axiosData
      })
      if(res.msg=='success'){
        ElNotification({
          type:'success',
          message:"处理投诉中"
        })
        await this.getUserList()
      }else{
        ElNotification({
          type:'error',
          message:res.mag
        })
      }

    },
    async getUserList () {
      await request.post('system/complaint/list').then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          this.userlist = res.data
          this.total = res.data.length
        }
      })
      const { data: res } = await this.$axios.get('system/complaint/list', {
        params: this.queryInfo
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


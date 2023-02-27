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
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop">


      </el-table-column>

      <el-table-column label="状态" prop="status" align="center">
        <template v-slot="scope">
          <el-tag type="warning" v-if="scope.row.status === 0">禁用</el-tag>
          <el-tag v-else type="success">正常</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="角色类型" prop="roleId" align="center" :formatter="roleType">
用户
      </el-table-column>

      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <!-- 删除按钮 -->
          <el-button type="primary"  size="mini" @click="confirmEdit(scope.row)" plain v-if="scope.row.status === 1">
            禁用
          </el-button>
          <el-button type="success"  size="mini" @click="confirmEdit(scope.row)" plain v-if="scope.row.status === 0">
            解除
          </el-button>
          <!-- 删除按钮 -->
          <el-button type="danger" size="mini" @click="logicDelete(scope.row)" plain>
            删除
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
      isshow:false,
      operateType:'add',
      operateForm:{
        id:'',
        username:'',
        address:'',
        mobile:'',
        status:'',
        roleId:''
      },
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
          label:"用户名称"
          ,prop:"name"
        },
        {
          label:"省市区"
          , prop:"address"
        },{
          label:"手机"
          ,prop:"mobile"
        },{
          label:"添加时间"
          , prop:"createTime"
        }
      ]
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    async  confirmEdit(row){
      let params = {
        id:row.id,
        status:row.status
      }

      await request.post('system/banUser',params).then(async res => {
        if (res.code === -20001 || res.code == -20002) {
          that.$router.push({name: 'login'})
        } else {
          if (res.msg == 'success') {
            await this.getUserList()
            ElNotification({
              type: 'success',
              message: '用户状态已更改'
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
    logicDelete(row){
      this.$confirm(
          '此操作将永久删除该用户，是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        this.operateForm.id = row.id
        request.post('/system/transport/delete',this.operateForm).then(res=>{
          console.log(res)
          if(res.msg==='success'){
            this.$message({
              type: 'success',
              message: '删除成功',
            })
            this.getUserList()
          }else {
            this.$message({
              type: 'error',
              message: '删除失败',
            })
          }
        })
      })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除',
            })
          })


    },


    async getUserList () {
      await request.post('system/foreUserList').then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          this.userlist = res.data
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
    edit(row){
      this.operateType='edit'
      this.isshow = true
      this.operateForm = row
      this.operateForm.id = row.id
      console.log("11111")
      console.log(row.id);
    },
    addUser(){
      this.operateForm = {}
      this.operateType = 'add'
      this.isshow = true
    }
  }

}
</script>

<style lang="less" scoped>

</style>



<template>
  <div>

    <!-- 卡片视图区域 -->

    <el-row :gutter="20">
      <el-col :span="4">
        <el-button type="primary" @click="addUser" v-if="this.thisRoleId==1" plain>新增用户</el-button>
      </el-col>
    </el-row>
    <!-- 用户列表区域  -->
    <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" align = "center">


      </el-table-column>
      <el-table-column label="状态" prop="status" align="center" :formatter="statusType">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status === 1" type="success">正常</el-tag>
          <el-tag v-else type="warning">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="角色类型" prop="roleId" align="center" :formatter="roleType"></el-table-column>

      <el-table-column label="操作" width="180px" v-if="this.thisRoleId==1">
        <template v-slot="scope">
          <!-- 修改按钮 -->
          <el-button type="primary"  size="mini" @click="edit(scope.row)" plain >
            <SvgIcon name="edit" style="width: 1.4rem;height:1.4rem"/>
          </el-button>

          <!-- 删除按钮 -->
          <el-button type="danger" size="mini" @click="logicDelete(scope.row)" plain >
            <SvgIcon name="delete" style="width: 1.4rem;height:1.4rem" />
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    对话弹窗-->

    <el-dialog :title="operateType === 'add'?'新增用户':'更新用户'" v-model="isshow">
      <common-form :form-label="operateFormLabel" :form="operateForm"></common-form>
      <!--对话弹窗的按钮-->
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="isshow = false">取消</el-button>
        <el-button type="primary" @click="confirmEdit"
        >确认</el-button>
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
      isshow:false,
      operateType:'add',
      thisRoleId:'',
      operateForm:{
        id:'',
        username:'',
        mobile:'',
        status:''
      },
      operateFormLabel:[
        {
          model: 'username',
          label: '快递员名'
        },
        {
          model: 'status',
          label: '状态',
          type: 'select',
          opts: [
            {
              label: '正常',
              value: 1
            },
            {
              label: '禁用',
              value: 0
            }
          ]
        },
        {
          model: 'mobile',
          label: '手机号',
        }
      ],
      // 获取用户列表的参数对象
      queryInfo: {
        query: 'courier', // 查询参数
        userId:'',
        pagenum: 1, // 当前页码
        pagesize: 2 // 每页显示条数
      },
      // 用户列表
      userlist: [],
      // 总数据条数
      total: 0,
      fields : [
        {
          label:"快递员名"
          ,prop:"username"
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
    let that = this
    this.thisRoleId = that.$route.params.roleId
    this.getUserList()
  },
  methods: {
    confirmEdit(){
      if(this.operateType=== 'edit'){
        request.post('/system/transport/updateCourier',this.operateForm).then(res=>{
          console.log("this.operateForm.id:"+this.operateForm.id)
          if(res.msg=='success'){
            this.isshow = false
            this.getUserList()
            ElNotification({
              type:'success',
              message:'数据更新成功'
            })
          }else{
            ElNotification({
              type:'error',
              message:res.msg
            })
          }

        })
      }else{
        request.post('/system/transport/add',this.operateForm).then(res=>{
          console.log("this.operateForm.id:"+this.operateForm.id)

          if(res.msg=='success'){
            this.isshow = false
            this.getUserList()
            ElNotification({
              type:'success',
              message:'数据添加成功'
            })
          }else{
            ElNotification({
              type:'error',
              message:res.msg
            })
          }

        })
      }

    },
    logicDelete(row){

      this.$confirm(
          '此操作将永久删除该文件，是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            this.operateForm.id = row.id
            request.post('/system/transport/delete',this.operateForm).then(res=>{
              console.log(res)
              if(res=='1'){
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
      let that = this
      if(localStorage.getItem("user")!=null){
        that.queryInfo.userId = JSON.parse(localStorage.getItem("user")).id
      }else{
        that.$router.push({name:'login'})
      }
      await request.post('system/transport/courierList',this.queryInfo).then(res=>{
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

    statusType(row){
      if(row.status =="1"){
        return "正常";
      }else return "禁用";
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

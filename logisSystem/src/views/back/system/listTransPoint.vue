<template>
  <div>


        <!-- 卡片视图区域 -->
        <el-row :gutter="20">
          <el-col :span="8">
            <!-- 搜索与添加区域 -->
            <el-input placeholder="请输入城市范围" v-model="searchItemId" clearable>
              <template #append>
                <el-button @click="searchItem">
                  <SvgIcon name="search" style="width: 1.4rem;height:1.4rem"/>
                </el-button>
              </template>
            </el-input>
          </el-col>
          <el-button type="info" @click="addUser" plain>添加用户</el-button>
        </el-row>

    <!-- 用户列表区域  -->
    <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop">


      </el-table-column>
      <el-table-column label="状态" prop="status" align="center">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status === 1" type="success">正常</el-tag>
          <el-tag v-else type="warning">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="角色类型" prop="roleId" align="center" :formatter="roleType">

      </el-table-column>

      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <!-- 修改按钮 -->
          <el-button type="primary"  size="mini" @click="edit(scope.row)" plain>
            <SvgIcon name="edit" style="width: 1.4rem;height:1.4rem"/>
          </el-button>

          <!-- 删除按钮 -->
          <el-button type="danger" size="mini" @click="logicDelete(scope.row)" plain>
            <SvgIcon name="delete" style="width: 1.4rem;height:1.4rem"/>
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
      operateForm:{
        id:'',
        username:'',
        address:'',
        mobile:'',
        status:'',
        roleId:''
      },
      searchItemId:'',
      operateFormLabel:[
        {
          model: 'username',
          label: '运输点名'
        },
        {
          model: 'address',
          label: '地址'
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
        },
        {
          model: 'roleId',
          label: '用户类型',
          type:'select',
          opts:[
            {
              label: '超级管理员',
              value: 0
            },
            {
              label: '网点',
              value: 1
            },{
              label: '运输点',
              value: 2
            },{
              label: '快递员',
              value: 3
            }
          ]
        }
      ],
      // 获取用户列表的参数对象
      queryInfo: {
        query: 'all', // 查询参数
        pagenum: 1, // 当前页码
        pagesize: 5 // 每页显示条数
      },
      // 用户列表
      userlist: [],
      // 总数据条数
      total: 0,
      fields : [
        {
          label:"名称"
          ,prop:"username"
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

    async searchItem(){

      let axiosData = {
        address:this.searchItemId,
      }
      await request.post('/system/transport/search',axiosData).then(res=>{
        console.log("search:"+res)
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.msg=='success'){
            this.userlist = res.data
          }
        }
      })
    },

    confirmEdit(){
      if(this.operateType=== 'edit'){
        console.log(this.operateForm)
        request.post('/system/transport/update',this.operateForm).then(res=>{
          if(res.code===-20001||res.code==-20002){
            that.$router.push({name:'login'})
          }else{
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
          }
        })
      }else{
        request.post('/system/transport/add',this.operateForm).then(res=>{
          if(res.code===-20001||res.code==-20002){
            that.$router.push({name:'login'})
          }else{
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
          }
        })
      }
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
            request.post('/system/transport/delete',this.operateForm).then(async res => {

              if(res.code===-20001||res.code==-20002){
                that.$router.push({name:'login'})
              }else{
                if (res.msg == 'success') {
                  await this.getUserList()
                  this.$message({
                    type: 'success',
                    message: '删除成功',
                  })
                } else {
                  this.$message({
                    type: 'error',
                    message: res.msg,
                  })
                }
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
      await request.post('system/transport/list',this.queryInfo).then(res=>{
        console.log(res)
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.msg!='success'){
            ElNotification({
              type:"error",
              message:res.mag
            })
          }else{
            this.userlist = res.data
            this.total = res.data.length
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
    ,
    edit(row){
      this.operateType='edit'
      this.isshow = true
      this.operateForm = row
      this.operateForm.id = row.id
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
<template>
  <div>


    <!-- 卡片视图区域 -->
    <el-row :gutter="20">
      <el-button type="info" @click="addUser" plain>添加运费时效</el-button>
    </el-row>

    <!-- 用户列表区域  -->
    <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column prop="origin" label="出发地"></el-table-column>
      <el-table-column prop="destination" label="目的地"></el-table-column>
      <el-table-column prop="originPrice" label="起始价（元1kg）"></el-table-column>
      <el-table-column prop="pricePerKilogram" label="每千克续重（元/kg）" width="160px"></el-table-column>
      <el-table-column prop="eta" label="预计到达时间（天）" width="160px"></el-table-column>
      <el-table-column label="操作" width="180px">
        <template v-slot="scope">
          <!-- 修改按钮 -->
          <el-button type="primary"  size="mini" @click="edit(scope.row)" plain>
            <SvgIcon name="edit" style="width: 1.4rem;height:1.4rem"/>
          </el-button>

          <!-- 删除按钮 -->
          <el-button type="danger" disabled v-if="scope.row.origin==='区1'||scope.row.origin==='城市1'||scope.row.origin==='省1'" size="mini" @click="logicDelete(scope.row)" plain>
            <SvgIcon name="delete" style="width: 1.4rem;height:1.4rem"/>
          </el-button>
          <el-button type="danger" v-else size="mini" @click="logicDelete(scope.row)" plain>
            <SvgIcon name="delete" style="width: 1.4rem;height:1.4rem"/>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    对话弹窗-->

    <el-dialog :title="updateName" v-model="sameisshow">
      <common-form :form-label="fields" :form="operateForm"></common-form>
      <!--对话弹窗的按钮-->
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="sameisshow = false">取消</el-button>
        <el-button type="primary" @click="confirmEdit"
        >确认</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog :title="operateType === 'add'?'新增运费时效':'更新运费时效'" v-model="isshow">
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
      sameisshow:false,
      updateName:'',
      isshow:false,
      operateType:'add',
      operateForm:{
        id:'',
        origin:'',
        destination:'',
        originPrice:'',
        pricePerKilogram:'',
        eta:''
      },
      searchItemId:'',
      operateFormLabel:[
        {
          model: 'origin',
          label: '出发地'
        },
        {
          model: 'destination',
          label: '目的地'
        },{
          model: 'originPrice',
          label: '起始价',
        },
        {
          model: 'pricePerKilogram',
          label: '每千克续重',
        },
        {
          model: 'eta',
          label: '预计到达时间',
        }
      ],
      // 获取用户列表的参数对象
      queryInfo: {
        query: '', // 查询参数
        pagenum: 1, // 当前页码
        pagesize: 5 // 每页显示条数
      },
      // 用户列表
      userlist: [],
      // 总数据条数
      total: 0,
      fields : [
        {
          model: 'originPrice',
          label: '起始价',
        },
        {
          model: 'pricePerKilogram',
          label: '每千克续重',
        },
        {
          model: 'eta',
          label: '预计到达时间',
        }
      ]
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    confirmEdit(){
      if(this.operateType=== 'edit'){
        console.log(this.operateForm)
        request.post('/system/agingQuery/edit',this.operateForm).then(res=>{
          if(res.code===-20001||res.code==-20002){
            that.$router.push({name:'login'})
          }else{
            if(res.msg=='success'){
              this.sameisshow = false
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
        request.post('/system/agingQuery/add',this.operateForm).then(res=>{
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
          '此操作将永久删除该运费时效，是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        this.operateForm.id = row.id
        request.post('/system/agingQuery/delete',this.operateForm).then(async res => {

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
      var that =this
      await request.post('system/agingQuery/list').then(res=>{
        console.log(res.data)
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          if(res.msg!='success'){
            ElNotification({
              type:"error",
              message:res.mag
            })
          }else{
            that.userlist = res.data
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
      if(row.origin==='区1'){
        this.sameisshow = true
        this.updateName='城市内运费时效更新'
      }else if(row.origin==='城市1'){
        this.sameisshow = true
        this.updateName='省内运费时效更新'
      }else if(row.origin==='省1'){
        this.sameisshow = true
        this.updateName='跨省运费时效更新'
      }else{
        this.isshow = true
      }
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
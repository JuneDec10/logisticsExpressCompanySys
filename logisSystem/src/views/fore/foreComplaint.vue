<template>
  <div>
    <el-tabs type="border-card" v-model="getTabs" :tab-position="tabPosition" style="height: 800px" class="demo-tabs" @tab-click="changeTabs">
      <el-tab-pane :name="item.name" :label="item.label" v-for="item in fileds">
        <div v-show="item.name=='1'">

          <!-- 用户列表区域  -->
          <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)"
          :cell-style="cellStyle"
          >
            <el-table-column prop="id" v-if="false"></el-table-column>
            <el-table-column :prop="item.prop" :label="item.label" v-for='item in orderManage' :key="item.prop" align = "center">
            </el-table-column>
            <el-table-column label="投诉进度" prop="status" >

            </el-table-column>
            <!--  运输按钮  -->
            <el-table-column label="操作" width="180px">
              <template v-slot="scope">
                <el-button type="danger" size="mini" @click="logicDelete(scope.row)" plain>
                  取消投诉
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

        <div v-show="item.name=='2'" >
          <el-form :rules="addUserFormRules" ref="form" style="padding-left: 45px;" :model="form" label-width="120px">
            <el-col :span="22">
                <el-card shadow="always" >
                  <template #header>
                    <h3>
                      <SvgIcon name="editPan" style="width: 20px;height:20px;color: #bd371f"/>
                      我要投诉</h3>
                  </template>
                  <el-form-item label="订单编号" prop="waybillNumber">
                    <el-col :span="8">
                      <el-input placeholder="请输入订单编号" v-model="form.waybillNumber" clearable />
                    </el-col>

                  </el-form-item>

                  <el-form-item label="您的姓名" prop="complaintName">
                    <el-col :span="8">
                      <el-input placeholder="请输入您的姓名" v-model="form.complaintName" clearable />
                    </el-col>

                  </el-form-item>
                  <el-form-item label="联系方式" prop="mobile">
                    <el-col :span="12">
                      <el-input placeholder="请输入您的手机号" v-model="form.mobile" clearable />
                    </el-col>

                  </el-form-item>

                  <el-form-item label="您的身份" prop="foreUserType">
                    <el-select v-model="form.foreUserType" placeholder="--请选择--">
                      <el-option label="寄件人" value="0" />
                      <el-option label="收件人" value="1" />
                    </el-select>
                  </el-form-item>

                  <el-form-item label="投诉内容" prop="content">
                    <el-col :span="21">
                      <el-input v-model="form.content" type="textarea" />
                    </el-col>

                  </el-form-item>
                  <br>
                  <el-form-item style="padding-left: 220px;">
                    <el-button color="#bd371f"  @click="onSubmit('form')">立即提交</el-button>
                  </el-form-item>

                </el-card>
            </el-col>
            </el-form>
        </div>


      </el-tab-pane>
    </el-tabs>

    <!-- 物流进度的对话框 -->
    <el-dialog :title="物流进度" v-model="progressVisible" width="50%" center draggable>

      <span slot="footer">
        <el-button @click="progressVisible = false">取 消</el-button>
        <el-button type="primary" @click="progressVisible = false">确 定</el-button>
    </span>
    </el-dialog>

  </div>



</template>
<script>

import {ElNotification} from "element-plus";
import request from "../../request";
import {useCookies} from "@vueuse/integrations/useCookies";

export default {

  data(){
    var checkMobile = (rule,value,callback) =>{
      console.log(value)
      //验证手机号的正则表达式
      const regMobile = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if(value==''||value==undefined||value==null){
        callback(new Error("请输入您的手机号"));
      }else{
        if(!regMobile.test(value)){
          callback(new Error("请输入合法的手机号"));
        }else
          console.log("hai=")
          callback(); //合法手机号
      }

    }
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
          label:"投诉处理进度",
          name:'1'
        },{
          label:"我要投诉",
          name:'2'
        }
      ],
      orderManage:[
        {
          label:"订单编号"
          ,prop:"waybillNumber"
        },
        {
          label:"投诉内容",
          prop:"content"
        },
        {
          label:"投诉时间",
          prop:"createTime"
        }
      ],
      progressInfo: '这是一段信息',
      searchItemId:'',
      form :{
        id:'',
        foreUser: '',
        mobile: '',
        waybillNumber: '',
        complaintName: '',
        foreUserType: '',
        content: '',
      },

      //添加表单的验证规则对象
      addUserFormRules: {
        complaintName: [
          {required: true, message: '请输入您的姓名', trigger: 'blur'}
        ],
        mobile: [
          {required: true, validator: checkMobile, trigger: 'blur'}]
        ,
        waybillNumber: [
          {required: true, message: '请输入订单编号', trigger: 'blur'}
        ],
        foreUserType: [
          {required: true, message: '请选择你是寄件人还是收件人', trigger: 'change'}
        ],
        content: [
          {required: true, message: '请输入投诉内容', trigger: 'blur'}
        ]
      }


    }
  },

  created () {
    let that = this
    const cookie = useCookies();
    that.queryInfo.userId = cookie.get('id')
    this.getUserList()
  },
  methods: {

    onSubmit(formName){
      this.form.foreUser = this.queryInfo.userId
          request.post('/fore/complaint/add',this.form).then(res=>{
            if(res){

              ElNotification({
                type:'success',
                message:'您的投诉已提交，请保持今日联系畅通，工作人员将于今日晚18：00前与您取得联系，谢谢配合'
              })


            }else{
              ElNotification({
                type:'error',
                message:res.msg
              })
            }
          })
    },

    changeTabs(tab,event){
      this.getTabs = tab.props.name
      console.log(this.getTabs)
      this.getUserList()
    },

    cellStyle ({ row, column, rowIndex,columnIndex }){
     console.log(row.column)
     //  console.log(row.status === '' && columnIndex === 4)
     if(row.status === '投诉未处理' && column.label=== '投诉进度'){
       return {"color":"#ec2f3e"}
     }
     else if(row.status=='投诉处理中'&& column.label=== '投诉进度'){
       return {"color":"#4face0"}
     }else if(row.status=='投诉处理完成'&& column.label=== '投诉进度'){
       return {"color":"#3fce2e"}
     }

   },

    async getUserList () {
      const cookie = useCookies();
      let that  = this;
      this.queryInfo.userId = cookie.get('id')
      let axiosData ={
        userId:this.queryInfo.userId,
      };
      const { data: res } = await this.$axios.get('/fore/complaint/complaintList',{
        params: axiosData
      })
      console.log(res)
      this.userlist = res
      if(res.length!=0){
        for (let i = 0; i < res.length; i++) {
          if(this.userlist[i].status==0){
            this.userlist[i].status="投诉未处理"
          }else if(this.userlist[i].status==1){
            this.userlist[i].status="投诉处理中"
          }else{
            this.userlist[i].status="投诉处理完成"
          }
          }
      }
      this.total = res.length
    },

    logicDelete(row){
      console.log("hhhhhhhhhhhhh")
      console.log(row.id)

      this.$confirm(
          '此操作将永久删除该投诉，是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        this.form.id = row.id
        request.post('/fore/complaint/logicDelete',this.form).then(res=>{
          console.log(res)
          if(res.msg=='success'){
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
</style>
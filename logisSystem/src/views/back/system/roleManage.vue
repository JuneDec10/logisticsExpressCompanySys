<template>
  <div>

    <!-- 用户列表区域  -->
    <el-table border stripe :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" >
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" >
      </el-table-column>
     <el-table-column  label="权限等级" prop="sort">
       <template v-slot="scope">
         <el-tag v-if="scope.row.sort === 0">一级</el-tag>
         <el-tag v-else-if="scope.row.sort === 1" type="success">二级</el-tag>
         <el-tag v-else type="warning">三级</el-tag>
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
          label:"权限名称"
          ,prop:"name"
        },
        {
          label:"路径名称"
          , prop:"routerName"
        }
      ]
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    async getUserList () {
      await request.post('system/menu/list').then(res =>{
        console.log(res)
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
  }

}
</script>

<style lang="less" scoped>

</style>
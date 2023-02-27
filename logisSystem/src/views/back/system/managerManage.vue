<template>
  <div>
    <!-- 表格视图 -->
      <el-table :data="rolesList">
        <el-table-column  type="id" v-if="false"></el-table-column>

        <el-table-column type="expand">
          <template v-slot="scope">
            <el-row :class="['bdtop bdbottom']" v-for="item1 in scope.row.submenu" :key="item1.id">
              <!-- 渲染一级权限 -->
              <el-col :span='5'>
                <el-tag  round closable
                        @close="removeRightById(scope.row,item1.id)">{{item1.name}}</el-tag>
                <i class="el-icon-caret-right"></i><!-- 箭头小图标 -->
              </el-col>
              <!-- 渲染二级 -->
              <el-col :span='19'>

                <!-- 通过for循环，嵌套渲染二级权限 -->
                <el-row :class="['bdbottom']" v-for="item2 in item1.submenu" :key="item2.id">
                  <!-- 渲染二级权限 -->
                  <el-col :span='5'>
                    <el-tag round type="success" closable
                    @close="removeRightById(scope.row,item2.id)"
                    >{{item2.name}}</el-tag>
                    <i class="el-icon-caret-right"></i><!-- 箭头小图标 -->
                  </el-col>
                </el-row>

              </el-col>
            </el-row>
          </template>
        </el-table-column>

        <el-table-column label="角色名称" prop="name"></el-table-column>
        <el-table-column label="角色描述" prop="remark"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template v-slot="scope">
            <el-button size= "mini" type="danger" @click="logicDelete(scope.row)" plain>删除</el-button>
            <el-button @click="showSetRightDialog(scope.row)"  size= "mini" type="warning" plain>分配权限</el-button>
          </template>
        </el-table-column>
      </el-table>

    <el-dialog title="分配权限" v-model="setRightDialogVisible" width="50%">
      <!-- 树形组件 -->
      <el-tree :data="rightsList" :props="treeProps" show-checkbox default-expand-all
      node-key="id" :default-checked-keys="defkeys" ref="treeRef"
      ></el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights()">确 定</el-button>
    </span>

    </el-dialog>

  </div>
</template>

<script >

import CommonForm from "../../../components/CommonForm.vue";
import {useCookies} from "@vueuse/integrations/useCookies";
import request from "../../../request";
import {ElNotification} from "element-plus";
export default {
  components: {CommonForm},

  data () {
const cookie = useCookies();
    return {
    //角色列表
      rolesList:[],
      //控制分配权限对话框的显示与隐藏
      setRightDialogVisible: false,
      //所有权限的数据
      rightsList: [],
      //树形控件的属性绑定对象
      treeProps: {
        label: 'name',
        children: 'submenu'
      },
      //默认选中的权限ID数组对象
      defkeys: [],
      //当前即将分配权限的角色ID
      roleId: ''
    }
  },
  created () {
    this.getRolesList()
  },
  methods: {
    async getRolesList () {
      await request.post('system/role/list').then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          this.rolesList = res.data
        }
      })
    },

    //根据ID删除对应的权限
    removeRightById(role,rightId){
      //弹框提示用户是否需要删除
      this.$confirm('是否删除该权限?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        console.log(role.id+' '+rightId)
        let axiosData = {
          roleId:role.id,
          menuId:rightId
        }
        //进行权限删除
        await request.post('system/role/deleteRoleMenu',axiosData).then(async res => {
          if (res.code === -20001 || res.code == -20002) {
            that.$router.push({name: 'login'})
          } else {
            if (res.msg !== 'success') {
              return this.$message.error(res.msg);
            }
            this.$message({type: 'success', message: '删除权限成功!'});
            await this.getRolesList();
          }
        })
      }).catch(() => {
        this.$message({type: 'info',message: '已取消删除' });
      });
    },

//展示权限分配的对话框
    async  showSetRightDialog(role){
      var that = this
      that.roleId = role.id;
      await request.post('system/menu/getAllRoleMenuList').then(res=>{
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          //把获取到的权限数据保存到data中
          that.rightsList = res.data;
          that.defkeys = [];//清空数组
          that.getLeafKeys(role,this.defkeys);
          that.setRightDialogVisible = true;
        }
      })
      // const { data: res } = await this.$axios.get('system/menu/getAllRoleMenuList', {
      // })
    },
    getLeafKeys(node,arr){
      if(!node.submenu){
        return arr.push(node.id);
      }else if(node.submenu.length==0){
        return arr.push(node.id);
      }
      node.submenu.forEach(item => this.getLeafKeys(item,arr));
    },

    //点击为角色分配权限
    async  allotRights(){
      var that = this
      //"..."为“展开运算符”，将一个数组转为用逗号分隔的参数序列
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]

      const idStr = keys.join(",");
      let axiosData = {
        roleId:this.roleId,
        idStr:idStr
      }
      console.log(idStr);
      await request.post('system/role/insertMany',axiosData).then(async res => {
        console.log("res456564646:"+res)
        if (res.code === -20001 || res.code == -20002) {
          that.$router.push({name: 'login'})
        } else {
          //进行权限分配
          if (res.msg != 'success') {
            return that.$message.error('分配权限失败！');
          }
          that.$message.success('分配权限成功！');
          //重新刷新角色列表
          await that.getRolesList();
          //关闭对话框
          that.setRightDialogVisible = false;
        }
      })

    },

    async logicDelete(row){
      var that = this
      //弹框提示用户是否需要删除
      this.$confirm('是否删除该用户类型?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {

        let axiosData = {
          roleId:row.id
        }
        //调用删除API进行权限删除
        await request.post('system/role/logicDelete',axiosData).then(async res => {
          if (res.code === -20001 || res.code == -20002) {
            that.$router.push({name: 'login'})
          } else {
            if (res.msg !== 'success') {
              return this.$message.error(res.msg);
            }
            this.$message({type: 'success', message: '删除用户类型成功!'});
            await this.getRolesList();
          }
        })
      }).catch(() => {
        this.$message({type: 'info',message: '已取消删除' });
      });
    }
  }

}
</script>

<style scoped>
.el-tag {
  margin: 7px;
}
.bdtop{
  border-top: 1px solid #eee;
}
.bdbottom{
  border-bottom: 1px solid #eee;
}
</style>
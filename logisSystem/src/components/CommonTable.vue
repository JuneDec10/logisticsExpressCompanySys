<template>
  <div class="common-table">
    <!--
      将表格数据tableData赋值给data
        -stripe：斑马纹显示表格
        -v-loading：显示loading加载过程,值有父组件data数据config对象传递，决定是否显示加载效果
    -->
    <el-table :data="tableData" height="90%" stripe v-loading="config.loading">

      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column v-for="item in tableLabel" :key="item.prop" :label="item.label" show-overflow-tooltip :width="item.width ? item.width : 125">
      </el-table-column>
      <!-- Table表格操作列 -->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination class="pager" layout="prev, pager, next" :total="config.total" :current-page.sync="config.page" @current-change="changePage">
    </el-pagination>
  </div>
</template>

<script>
export default {
  // 接收父组件传来的数据
  props: {
    tableData: Array,
    tableLabel: Array,
    config: Object
  },
  methods: {
    // ---表格操作列方法---
    // 向父组件传入编辑事件
    handleEdit(row) {
      this.$emit('edit', row)
    },
    // 向父组件传递删除事件
    handleDelete(row) {
      this.$emit('del', row)
    },
    // ---分页操作方法：当改变当前页数时，向父组件发送当前页数
    changePage(page) {
      this.$emit('changePage', page)
    }
  }
}
</script>

<style lang="scss" scoped>
//.common-table {
//  // 设置表格高度，减去页面header高度
//  height: calc(100% - 62px);
//  // 设置表格背景色
//  background-color: #fff;
//  // 设置相对定位
//  position: relative;
//  // 设置分页样式
//  .pager {
//    position: absolute;
//    bottom: 0;
//    right: 20px;
//  }
//}
</style>


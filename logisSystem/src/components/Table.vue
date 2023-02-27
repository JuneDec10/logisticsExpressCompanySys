<template>
  <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border>
    <el-table-column :prop="item.prop" :label="item.label" v-for='item in fields' :key="item.prop" align = "center"/>
  </el-table>
  <div class="page">
    <el-pagination
        :currentPage="pageNum"
        :page-size="pageSize"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>
<script setup>
import {watch} from "vue";
const props = defineProps({
  tableData:{
    type:Array,
    default:()=>{
      return []
    }
  },
  fields:{
    type:Array,
    default:()=>{
      return []
    }
  },
  small:{
    type:Boolean,
    default:false
  },
  background:{
    type:Boolean,
    default:false
  },
  disabled:{
    type:Boolean,
    default:false
  },pageSize:{
    type:Number,
    default:10
  },
  pageNum:
      {
        type:Number,
        default:1
      },
  total:{
    type:Number,
    default:1
  }

})

const emit = defineEmits(['update:pageSize','update:pageNum','getList'])
function handleSizeChange (val){
emit('update:pageSize',val)
  emit('update:pageNum',1)
  console.log("update:pagesize"+val)
};
function handleCurrentChange (val){
  emit('update:pageNum',val)
}
watch(()=> props.pageSize,(newV,oldV)=>{
emit('getList')
})
watch(()=> props.pageNum,(newV,oldV)=>{
  emit('getList')
})
</script>
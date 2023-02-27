<template>
  <el-form :inline="inline" :model="form" ref="form" label-width="100px">
    <el-form-item v-for="item in formLabel" :key="item.model" :label="item.label">
      <!-- 输入框 -->
      <el-input v-model="form[item.model]" :placeholder="'请输入' + item.label" v-if="!item.type"></el-input>
      <!-- 下拉框 -->
      <el-select v-model="form[item.model]" placeholder="请选择" v-if="item.type === 'select'">
        <el-option v-for="item in item.opts" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <!-- switch开关 -->
      <el-switch v-model="form[item.model]" v-if="item.type === 'switch'"></el-switch>
    </el-form-item>
    <!-- 按钮使用插槽，由父组件决定按钮 -->
    <el-form-item><slot></slot></el-form-item>
  </el-form>
</template>

<script>
export default {
  props: {
    // form表单是否为行内表单
    inline: Boolean,
    // 表单数据对象
    form: Object,
    // 表单每个item标签
    formLabel: Array
  }
}
</script>

<style lang="scss" scoped>
</style>

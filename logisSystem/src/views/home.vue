<template v-slot:default>
  <div :class="['content',isCollapse?'menu--fold':'menu--unfold']">
    <!-- 侧边菜单栏 -->
    <div class="menuLeft">
      <div class="menu-nav-header">
        <span>{{isCollapse?'控制台':'管理控制台'}}</span>
      </div>
      <!--todo 菜单栏组件 -->

      <!--todo 菜单栏组件 -->
      <el-menu active-text-color="#fff" background-color="#263238" class="el-menu-vertical-demo"
               :collapse-transition="false" default-active="2" text-color="#96a4ab " @open="handleOpen"
               @close="handleClose" :collapse="isCollapse">
        <el-menu-item index="1" @click="toindex">
          <SvgIcon name="home" class="icon-svg" />
          <span slot="">
            <router-link to="/systemHome/index">
             &nbsp;&nbsp;首页
            </router-link>
            </span>
        </el-menu-item>
        <el-sub-menu index="2">
          <template #title>
            <SvgIcon name="img" class="icon-svg" />
            <span>&nbsp;&nbsp;图片管理</span>
          </template>
          <el-menu-item index="1-1">
            <SvgIcon name="img" class="icon-svg" />
            <span>&nbsp;&nbsp;图片1</span>
          </el-menu-item>
          <el-menu-item index="1-2">
            <SvgIcon name="img" class="icon-svg" />
            <span>&nbsp;&nbsp;图片2</span>
          </el-menu-item>
          <el-sub-menu index="1-4">
            <template #title>
              <SvgIcon name="img" class="icon-svg" />
              <span>&nbsp;&nbsp;图片3</span>
            </template>
            <el-menu-item index="1-4-1">
              <SvgIcon name="img" class="icon-svg" />
              <span>&nbsp;&nbsp;图片三级子菜单</span>
            </el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <SvgIcon name="collection" class="icon-svg" />
            <span>&nbsp;&nbsp;收藏管理</span>
          </template>
          <el-menu-item index="3">
            <SvgIcon name="collection" class="icon-svg" />
            <span class="icon-text">&nbsp;&nbsp;收藏</span>
          </el-menu-item>
        </el-sub-menu>

        <el-menu-item index="4">
          <SvgIcon name="about" class="icon-svg" />
          <span>&nbsp;&nbsp;设置</span>
        </el-menu-item>

      </el-menu>



    </div>
    <!-- 右边内容 -->
    <div class="content-main">
      <div class="navTop horizontalView">
        <div class="nav_tools horizontalView">
          <SvgIcon :name="isCollapse?'expand':'fold'" class="icon-svg" @click="isCollapse=!isCollapse" />
        </div>
      </div>
      <!-- todo 内容组件 -->
    </div>
  </div>
</template>

<script setup>
import {ref} from "@vue/reactivity";
import {useRoute} from "vue-router";
import router from "../router";

const route = useRoute()
// import index from "../views/back/system/index.vue";
let isCollapse=ref(false)
console.log("--------------------------")
console.log(route.params.username)
const toindex= ()=> {
  router.push("/index")
}

</script>

<style>
/*@import url('src/views/back/system/home.css');*/
</style>

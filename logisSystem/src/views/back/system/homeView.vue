<template v-slot:default>
  <div :class="['content',isCollapse?'menu--fold':'menu--unfold']">
    <!-- 侧边菜单栏 -->
    <div class="menuLeft">
<!--后台管理系统折叠与打开时的名称-->
      <div class="menu-nav-header">
        <span>{{isCollapse?littleTitle:longTitle}}</span>
      </div>
<!--  后台管理系统折叠与打开时的名称    -->
      <el-menu active-text-color="#fff"
               background-color="#263238"
               class="el-menu-vertical-demo"
               :collapse-transition="false"
               text-color="#96a4ab "
               @open="handleOpen"
               @close="handleClose"
               :collapse="isCollapse"
      >
        <template v-for="(item,index) in menus" >

          <el-menu-item v-if="item.submenu.length==0" :index="index" @click="$router.push({ name: item.routerName, params:{id:this.Id,roleId:this.roleId} })" :disabled="item.disabled">
            <SvgIcon :name="item.iconName" class="icon-svg" />
            <span slot="">&nbsp;&nbsp;{{item.name}}</span>
          </el-menu-item>
          <el-sub-menu v-else :index="index">
            <template #title>
              <SvgIcon :name="item.iconName" class="icon-svg" />
              <span slot="">&nbsp;&nbsp;{{item.name}}</span>
            </template>
            <template v-for="(submenuItem,submenuIndex) in item.submenu">

              <el-menu-item :index="index+'-'+submenuIndex" :disabled="submenuItem.disabled"
                            @click="$router.push({ name: submenuItem.routerName,params: {id:this.Id,roleId:this.roleId} })">
                <SvgIcon :name="submenuItem.iconName" class="icon-svg" />
                <span slot="">&nbsp;&nbsp;{{submenuItem.name}}</span>
              </el-menu-item>

            </template>
          </el-sub-menu>
        </template>
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

      <el-tabs v-if="$route.meta.isTab" v-model="mainTabsActiveName" :closable="true"
               @tab-click="selectedTabHandle" @tab-remove="removeTabHandle">

        <el-scrollbar ref="scroll" :height="siteContentViewHeight+32+'px'" @scroll="scroll">
          <el-tab-pane v-for="item in mainTabs" :label="item.title" :name="item.name">
            <el-card :style="'min-height:'+siteContentViewHeight + 'px'">

              <router-view v-if="item.name === mainTabsActiveName" />

            </el-card>
          </el-tab-pane>
        </el-scrollbar>
      </el-tabs>
      <div v-else>
        <el-scrollbar ref="scroll" :height="siteContentViewHeight+32+'px'" @scroll="scroll">
          <!-- 主入口标签页 e -->
          <el-card :style="'min-height:'+ siteContentViewHeight + 'px'">
            <router-view />
          </el-card>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<script>
import mu from '../../../router/menu/menu.js';
import {useRoute} from "vue-router";
import {useCookies} from "@vueuse/integrations/useCookies";
import request from "../../../request";
export default {
  components: {

  },
  data: function() {

    return {
      isCollapse: false,
      mainTabs: [],
      mainTabsActiveName: '',
      menuActiveName: '',
      menus: [],
      roleId:'',
      Id:'',
      route:''
    }
  },
  created() {

    const cookie = useCookies();
    let that = this;
    that.routeHandle(that.$route);
    that.roleId = cookie.get('roleId');
    that.Id = cookie.get('id');
    that.route = useRoute();
    that.longTitle = mu.longTitle;
    that.littleTitle = mu.littleTitle;
    that.getMenuList();
  },

  // 监听路由变化
  watch: {
    $route: {
      handler(to, from) {
        if (to.path != from.path) {
          // 处理路由
          this.routeHandle(to);
        }
      }
    }
  },
  methods: {

    async getMenuList() {
      var that = this
      let user = JSON.parse(localStorage.getItem("user")) ? JSON.parse(localStorage.getItem("user")):null
      console.log(user)
      let axiosData ={
          roleId: user.roleId
        };
      await request.post('/system/menu/getRoleMenuList', axiosData).then(res => {
        if(res.code===-20001||res.code==-20002){
          that.$router.push({name:'login'})
        }else{
          that.menus = res.data
        }
      })

      console.log("menus:"+that.menus)
    },

    selectedTabHandle: function(tab, e) {

      tab = this.mainTabs.filter(item => item.name === tab.paneName);
      if (tab.length >= 1) {
        this.$router.push({
          name: tab[0].name,
          query: tab[0].query,
          params: tab[0].params
        });
      }
    },
    removeTabHandle: function(tabName) {
      this.mainTabs = this.mainTabs.filter(item => item.name !== tabName);
      if (this.mainTabs.length >= 1) {
        // 当前选中tab被删除
        if (tabName === this.mainTabsActiveName) {
          var tab = this.mainTabs[this.mainTabs.length - 1];
          this.$router.push(
              { name: tab.name, query: tab.query, params: tab.params },
              () => {
                this.mainTabsActiveName = this.$route.name;
              }
          );
        }
      } else {
        this.menuActiveName = '';
        this.$router.push({ name: 'system' });
      }
    },
    resetDocumentClientHeight: function() {
      this.documentClientHeight = document.documentElement['clientHeight'];
      window.onresize = () => {
        this.documentClientHeight = document.documentElement['clientHeight'];
        this.loadSiteContentViewHeight();
      };
    },
    loadSiteContentViewHeight: function() {
      let height = this.documentClientHeight - 52; //减去导航栏高度50

      if (this.$route.meta.isTab) {
        height -= 70; //减去tab栏高度40,减去上下边距30
        /* this.siteContentViewHeight = {
          'min-height': height + 'px'
        }; */
        this.siteContentViewHeight = height;
      } else {
        height -= 30;
        //给内容区设置高度
        this.siteContentViewHeight = height;
      }

    },
    routeHandle: function(route) {
      //每次切换页面，重新计算页面高度和内容区高度
      this.resetDocumentClientHeight();
      this.loadSiteContentViewHeight();
      if (route.meta.isTab) {
        // tab选中, 不存在先添加
        var tab = this.mainTabs.filter(item => item.name === route.name)[0];
        if (!tab) {
          if (route.meta.isDynamic) {
            route = this.dynamicMenuRoutes.filter(item => item.name === route.name)[0];
            if (!route) {
              return console.error('未能找到可用标签页!');
            }
          }
          tab = {
            menuId: route.meta.menuId || route.name,
            name: route.name,
            title: route.meta.title,
            iframeUrl: route.meta.iframeUrl || '',
            params: route.params,
            query: route.query
          };
          this.mainTabs = this.mainTabs.concat(tab);
        }
        this.menuActiveName = tab.menuId + '';
        this.mainTabsActiveName = tab.name;
      }
    },
    mounted: function() {
      let that = this;
      that.resetDocumentClientHeight();
      that.loadSiteContentViewHeight();
    }

  }
}
</script>

<style>
@import url('home.css');
.content {
  width: 100%;
  height: 100%;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

/* 水平布局 居中*/
.horizontalView {
  position: relative;
  flex-direction: row;
  display: flex;
  align-items: center;
}

/* 垂直布局居中 */
.verticalView {
  position: relative;
  flex-direction: column;
  display: flex;
  align-items: center;
}

/* 居中 */
.center {
  position: absolute;
  top: 50%;
  left: 50%;
  font-size: 28px;
  transform: translate(-50%, -50%);
}

.w100 {
  width: 100%;
}

.h100 {
  height: 100%;
}
.icon-svg {
  width: 1.4rem;
  height: 1.4rem;
  fill: currentColor;
  overflow: hidden;
}
</style>

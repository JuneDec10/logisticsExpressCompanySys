<template>

    <el-menu
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
        :router="true"
    >

      <h2 style="font-family: '华文隶书';font-size: 25px">
        <SvgIcon name="promotion" style="width: 20px;height:20px;color: #213547"/>
        筋斗云物流系统</h2>

      <div class="flex-grow" />
<template v-for="item in menuList">

  <el-menu-item v-if="!item.submenu" :index="item.id+''" @click="$router.push({name:item.routerName,params:{id:this.Id}})">
    <span>{{item.name}}</span>
  </el-menu-item>


  <el-sub-menu v-else :index="item.id+''" :key="item.id">
    <template #title>
      <span>{{item.name}}</span>
    </template>

    <el-menu-item :index="item.id+'-'+submenu.id" v-for="submenu in item.submenu"
    @click="$router.push({name:submenu.routerName,params:{id:this.Id}})">
      <template #title>
        <span>{{submenu.name}}</span>
      </template>
    </el-menu-item>
  </el-sub-menu>
</template>







    </el-menu>


</template>

<script >
import mu from '../../src/router/menu/menu.js';
import {useRoute} from "vue-router";
import SvgIcon from "./SvgIcon.vue";
import {useCookies} from "@vueuse/integrations/useCookies";
export default {
  components: {SvgIcon},
  data(){
    return{
      menuList:[],
      Id:'',
    }
  },
  created() {
    const cookie = useCookies();
    this.menuList=mu.userItems;
    let that = this;
    that.route = useRoute()
    that.Id = cookie.get('id');
    cookie.set('id',that.Id);
    localStorage.getItem("user")
  },
  methods:{
    handleSelect(){
    }
  }
}

</script>

<style>

.flex-grow {
  flex-grow: 1;
}
</style>






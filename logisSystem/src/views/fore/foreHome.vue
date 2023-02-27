<template>
<div id="app">

  <header>
    <Navbar/>
  </header>
  <el-main class="elMain">

      <router-view></router-view>

  </el-main>
</div>

</template>

<script>
import Carousel from "../../components/Carousel.vue";
import mu from '../../router/menu/menu.js';
import {useRoute} from "vue-router";
import SvgIcon from "../../components/SvgIcon.vue";
import Navbar from "../../components/Navbar.vue";
import {useCookies} from "@vueuse/integrations/useCookies";

export default {
  name: "foreHome",
  components: {
    Navbar,
    Carousel,

    SvgIcon
  },
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
    let route = useRoute()
    that.Id = cookie.get('id');
    cookie.set('id',that.Id)
  },

  methods:{
    handleSelect(){
    }
  }
}
</script>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
}
body{
  font-family: "Montserrat", sans-serif;
}

header {
  width: 100%;
  background-color: rgb(255, 255, 255);
  padding: 5px;
  padding-right:0px;
}
.elMain{

  background-color: #f2f2f2;
}
.elFoot{
  background-color: #f2f2f2;
}
.elCard{
  padding-left: 230px;
  background-color: #F5F6F8F9;
  /*opacity: 50%;*/
}

</style>
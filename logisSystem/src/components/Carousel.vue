<template>
  <el-carousel indicator-position="outside" :interval="5000" arrow="always" height="445px">
    <el-carousel-item v-for="item in carouseData" :key="item">
      <img :src="item.url" alt=""/>
    </el-carousel-item>
  </el-carousel>
</template>

<script setup>
const carouseData = [
  {url: new URL("../assets/sf1.jpg",import.meta.url).href},
  {url: new URL("../assets/sf9.jpg",import.meta.url).href},
  {url: new URL("../assets/sf2.jpg",import.meta.url).href},
  {url: new URL("../assets/sf5.jpg",import.meta.url).href},

]
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 285px;
  margin: 0;
  text-align: center;
}
img {
  max-width: 100%;
  max-height: 100%;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>

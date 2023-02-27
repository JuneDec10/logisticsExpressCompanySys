<template>
<Carousel/>
</template>

<script>
import {useCookies} from "@vueuse/integrations/useCookies";
import Carousel from "../../components/Carousel.vue";
export default {
  name: "foreHomeView",
  components: {Carousel},

  create(){
    localStorage.getItem("user")
}

}
</script>

<style scoped>

</style>